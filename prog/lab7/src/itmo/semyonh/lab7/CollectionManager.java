package itmo.semyonh.lab7;

import com.google.gson.Gson;
import itmo.semyonh.lab7.commands.Command;
import itmo.semyonh.lab7.commands.CommandResult;
import itmo.semyonh.lab7.types.StudyGroup;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import java.io.*;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;


/**
 * The CollectionManager class is a central entity that controls the collection `data`,
 * additional data related to that (like `initData`),
 * and handles command execution, that affects underlying data or manager itself (data load/store to a file).
 */
public class CollectionManager {

    private LinkedHashSet<StudyGroup> data;
    private Date initDate;
    private List<Command> history;
    private String filename = "test.json";

    private ReentrantLock lock;


    /**
     * A default constructor to initialize components of the class.
     */
    public CollectionManager() {
        data = new LinkedHashSet<>();
        history = new ArrayList<>();
        initDate = new Date();
        lock = new ReentrantLock();
    }

    /**
     * Executes commands on `data`,
     * also handles special cases (`save` command).
     *
     * @param c Command to execute
     */
    public CommandResult executeCommand(Command c) {
        lock.lock();
        history.add(c);

        // handle special commands
        switch (c.getName()) {
            case "save":
                try {
                    writeFile();
                } catch (IOException e) {
                    System.out.println("Cannot write file: " + e.getMessage());
                }
                break;

            default:
                var res = c.execute(data);
                lock.unlock();
                return res;
        }

        lock.unlock();
        return null;
    }

    /**
     * Returns date of underlying collection initialization,
     * does not designed to be changed outside.
     *
     * @return Date object of collection initialization time.
     */
    public Date getInitDate() {
        return initDate;
    }

    /**
     * Returns list of commands history.
     * Currently unlimited (may change).
     *
     * @return List of Command in order of execution.
     */
    public List<Command> getHistory() {
        return history;
    }

    /**
     * Returns filename of the file to write and read data of current collection.
     *
     * @return filename of data file.
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Sets filename of the file, that is used to write and read data of current collection.
     *
     * @param filename
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }


    /**
     * Actually serializes `data` into json and write in file.
     *
     * @throws IOException on I/O write error
     */
    public void writeFile() throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filename));

        Gson gson = new Gson();
        var result = gson.toJson(data);

//        System.out.println(result);

        writer.write(result);

        writer.flush();
        writer.close();
    }

    /**
     * Reads json stored data from file and deserializes it
     *
     * @throws FileNotFoundException if file was not found
     */
    public void readFile() throws FileNotFoundException {
        InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));

        Gson gson = new Gson();
        Type type = new TypeToken<LinkedHashSet<StudyGroup>>() {}.getType();
        data = gson.fromJson(reader, type);

        int id = 0;
        for (StudyGroup g : data) {
            if (g.getId() > id) {
                id = g.getId();
            }
        }
        StudyGroup.setBaseID(id + 1);
    }
}
