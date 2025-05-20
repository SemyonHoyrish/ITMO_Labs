package itmo.semyonh.lab8.helpers;

import itmo.semyonh.lab8.types.*;
import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Database {
    private static final Logger logger = LoggerFactory.getLogger(Database.class);

    private PGSimpleDataSource dataSource;
    private Connection connection;
    private String hashPepper;

    private static Database instance;

    public static void configure(String server, String database, String user, String password) {
        if (instance != null) {
            throw new RuntimeException("Database already configured");
        }
        instance = new Database(server, database, user, password, 5432);
    }
    public static void configure(String server, String database, String user, String password, int port) {
        if (instance != null) {
            throw new RuntimeException("Database already configured");
        }
        instance = new Database(server, database, user, password, port);
    }
    public static Database getInstance() {
        if (instance == null) {
            throw new RuntimeException("Database not configured");
        }
        return instance;
    }
    public void setHashPepper(String s) {
        hashPepper = s;
    }

    private Database(String server, String database, String user, String password, int port) {
        dataSource = new PGSimpleDataSource();
        dataSource.setServerNames(new String[]{server});
        dataSource.setDatabaseName(database);
        dataSource.setUser(user);
        dataSource.setPassword(password);
        dataSource.setPortNumbers(new int[]{port});
    }

    private StudyGroup parseStudyGroup(int study_group_id, Connection conn) throws SQLException {
        var st = conn.createStatement();
        var rs = st.executeQuery("SELECT * FROM study_group JOIN coordinates ON (study_group.coordinates_id = coordinates.id) WHERE study_group.id = " + study_group_id);
        if (!rs.next()) {
            rs.close();
            st.close();
            return null;
        }
        StudyGroup group = new StudyGroup();
        group.setId(rs.getInt("id"));
        group.setName(rs.getString("name"));
        Coordinates coords = new Coordinates();
        coords.setX(rs.getFloat("x"));
        coords.setY(rs.getDouble("y"));
        group.setCoordinates(coords);
        group.setCreationDate(new Date(rs.getTimestamp("creation_date").getTime()));
        group.setStudentsCount(rs.getInt("students_count"));
        group.setShouldBeExpelled(rs.getLong("should_be_expelled"));
        group.setFormOfEducation(FormOfEducation.valueOf(rs.getString("form_of_education")));
        group.setSemester(Semester.valueOf(rs.getString("semester")));
        var person = parsePerson(rs.getInt("group_admin_id"), conn);
        if (person == null) {
            rs.close();
            st.close();
            return null;
        }
        group.setGroupAdmin(person);
        rs.close();
        st.close();
        return group;
    }

    private Person parsePerson(int person_id, Connection conn) throws SQLException {
        var st = conn.createStatement();
        var rs = st.executeQuery("SELECT * FROM person WHERE id = " + person_id);
        if (!rs.next()) {
            rs.close();
            st.close();
            return null;
        }
        Person person = new Person();
        person.setName(rs.getString("name"));
        person.setWeight(rs.getDouble("weight"));
        person.setEyeColor(Color.valueOf(rs.getString("eye_color")));
        {
            var v = rs.getString("eye_color");
            if (v == null) {
                person.setHairColor(null);
            } else {
                person.setHairColor(Color.valueOf(v));
            }
        }
        person.setNationality(Country.valueOf(rs.getString("nationality")));
        rs.close();
        st.close();
        return person;
    }

    public List<StudyGroup> retrieveAll() throws SQLException {
        List<StudyGroup> list = new ArrayList<>();

        var conn = dataSource.getConnection();

        var st = conn.createStatement();
        var res = st.executeQuery("SELECT * FROM study_group");
        while (res.next()) {
            var group = parseStudyGroup(res.getInt("id"), conn);
            if (group == null) {
                logger.error("Group was not retrieved");
            } else {
                list.add(group);
            }
        }

        res.close();
        st.close();
        conn.close();

        return list;
    }

    private int insertStudyGroup(StudyGroup group, Connection conn) throws SQLException {
        var st = conn.prepareStatement("INSERT INTO study_group (name, coordinates_id, creation_date, students_count, should_be_expelled, form_of_education, semester, group_admin_id) VALUES(?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        st.setString(1, group.getName());
        var coords_id = insertCoordinates(group.getCoordinates(), conn);
        if (coords_id == -1) {
            logger.error("coords_id is -1");
            st.close();
            return -1;
        }
        st.setInt(2, coords_id);
        st.setTimestamp(3, new Timestamp(group.getCreationDate().getTime()));
        st.setLong(4, group.getStudentsCount());
        st.setLong(5, group.getShouldBeExpelled());
        st.setObject(6, group.getFormOfEducation(), Types.OTHER);
        st.setObject(7, group.getSemester(), Types.OTHER);
        var person_id = insertPerson(group.getGroupAdmin(), conn);
        if (person_id == -1) {
            logger.error("person_id is -1");
            st.close();
            return -1;
        }
        st.setInt(8, person_id);

        var res = st.executeUpdate();
        if (res == 0) {
            logger.error("no update result");
            st.close();
            return -1;
        }
        if (!st.getGeneratedKeys().next()) {
            st.close();
            return -1;
        }
        var r = st.getGeneratedKeys().getInt(1);
        st.close();
        return r;
    }

    private int insertCoordinates(Coordinates coords, Connection conn) throws SQLException {
        var st = conn.prepareStatement("INSERT INTO coordinates (x, y) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
        st.setFloat(1, coords.getX());
        st.setDouble(2, coords.getY());

        var res = st.executeUpdate();
        if (res == 0) {
            st.close();
            return -1;
        }
        if (st.getGeneratedKeys().next()) {
            var r = st.getGeneratedKeys().getInt("id");
            st.close();
            return r;
        } else {
            st.close();
            return -1;
        }
    }

    private int insertPerson(Person p, Connection conn) throws SQLException {
        var st = conn.prepareStatement("INSERT INTO person (name, weight, eye_color, hair_color, nationality) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        st.setString(1, p.getName());
        st.setDouble(2, p.getWeight());
        st.setObject(3, p.getEyeColor(), Types.OTHER);
        if (p.getHairColor() == null) {
            st.setNull(4, Types.OTHER);
        } else {
            st.setObject(4, p.getHairColor(), Types.OTHER);
        }
        st.setObject(5, p.getNationality(), Types.OTHER);

        var res = st.executeUpdate();
        if (res == 0) {
            logger.error("no person insert res");
            st.close();
            return -1;
        }
        if (st.getGeneratedKeys().next()) {
            var r = st.getGeneratedKeys().getInt("id");
            st.close();
            return r;
        } else {
            logger.error("no person insert res set entries");
            st.close();
            return -1;
        }
    }

    private boolean linkAccountObject(int accountID, int objectID, Connection conn) throws SQLException {
        var st = conn.prepareStatement("INSERT INTO ownership VALUES (?, ?)");
        st.setInt(1, accountID);
        st.setInt(2, objectID);
        var res = st.executeUpdate();
        if (res == 0) {
            st.close();
            return false;
        }
        st.close();
        return true;
    }

    private boolean confirmOwnership(int accountID, int objectID, Connection conn) throws SQLException {
        var st = conn.prepareStatement("SELECT 1 FROM ownership WHERE account_id = ? AND object_id = ?");
        st.setInt(1, accountID);
        st.setInt(2, objectID);
        var res = st.executeQuery();
        if (!res.next()) {
            st.close();
            return false;
        }
        st.close();
        return true;
    }

    public boolean insert(StudyGroup group, int accountID) throws SQLException {
        var conn = dataSource.getConnection();
        conn.setAutoCommit(false);

        var id = insertStudyGroup(group, conn);
        group.setId(id);

        var res = linkAccountObject(accountID, id, conn);
        if (res) {
            conn.commit();
            conn.close();
            return true;
        }
        conn.rollback();
        conn.close();
        return false;
    }

    public boolean update(int originID, StudyGroup group, int accountID) throws SQLException {
        var conn = dataSource.getConnection();
        conn.setAutoCommit(false);

        if (!confirmOwnership(accountID, originID, conn)) {
            conn.close();
            return false;
        }

        if (!removeStudyGroup(originID, conn)) {
            conn.rollback();
            conn.close();
            return false;
        }

        var id = insertStudyGroup(group, conn);
        group.setId(id);
        var res = linkAccountObject(accountID, id, conn);
        if (res) {
            conn.commit();
            conn.close();
            return true;
        }
        conn.rollback();
        conn.close();
        return false;
    }

    private boolean removeStudyGroup(int groupID, Connection conn) throws SQLException {
        var st = conn.prepareStatement("DELETE FROM study_group WHERE id = ?");
        st.setInt(1, groupID);
        var res = st.executeUpdate();
        if (res == 0) {
            st.close();
            return false;
        }
        st.close();
        return true;
    }

    public boolean remove(int originID, int accountID) throws SQLException {
        var conn = dataSource.getConnection();
        conn.setAutoCommit(false);

        if (!confirmOwnership(accountID, originID, conn)) {
            conn.close();
            return false;
        }

        var res = removeStudyGroup(originID, conn);

        if (res) {
            conn.commit();
            conn.close();
            return true;
        }
        conn.rollback();
        conn.close();
        return false;
    }

    private byte[] hash(String value, String pepper, String salt) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA3-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        var res = digest.digest((value + pepper + salt).getBytes());
        return res;
    }

    private String randomSalt() {
        return new Random().ints((int)'!', (int)'~')
                .limit(16)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public boolean registerAccount(String login, String password) throws SQLException {
        var conn = dataSource.getConnection();

        {//check for user existence
            var st = conn.prepareStatement("SELECT 1 FROM account WHERE login = ?");
            st.setString(1, login);
            var res = st.executeQuery();
            if (res.next()) {
                res.close();
                st.close();
                conn.close();
                return false;
            }
        }


        var st = conn.prepareStatement("INSERT INTO account (login, password_hash, salt) VALUES (?,?,?)");
        st.setString(1, login);
        var salt = randomSalt();
        st.setBytes(2, hash(password, hashPepper, salt));
        st.setString(3, salt);

        var res = st.executeUpdate();
        if (res == 0) {
            st.close();
            conn.close();
            return false;
        }
        st.close();
        conn.close();
        return true;
    }

    public int validateAccount(String login, String password) throws SQLException {
        var conn = dataSource.getConnection();

        var st = conn.prepareStatement("SELECT * FROM account WHERE login = ?");
        st.setString(1, login);
        var res = st.executeQuery();
        if (!res.next()) {
            res.close();
            st.close();
            conn.close();
            return -1;
        }
        var ph = res.getBytes("password_hash");
        var salt = res.getString("salt");
        var nh = hash(password, hashPepper, salt);

        for (int i = 0; i < ph.length; ++i) {
            if (ph[i] != nh[i]) {
                res.close();
                st.close();
                conn.close();
                return -1;
            }
        }

        var res_id = res.getInt("id");
        res.close();
        st.close();
        conn.close();
        return res_id;
    }

    public String getObjectOwner(int studyGroupID) throws SQLException {
        var conn = dataSource.getConnection();

        var st = conn.prepareStatement("SELECT account.login FROM ownership JOIN account ON (account.id = ownership.account_id) WHERE ownership.object_id = ?");
        st.setInt(1, studyGroupID);
        var res = st.executeQuery();
        if (!res.next()) {
            res.close();
            st.close();
            conn.close();
            return null;
        }

        var result = res.getString("login");
        res.close();
        st.close();
        conn.close();
        return result;
    }
}
