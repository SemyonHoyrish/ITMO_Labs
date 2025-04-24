package itmo.semyonh.lab7.types;

/**
 * Represent data of StudyGroup type.
 */
public class StudyGroup implements Comparable<StudyGroup> {
    private static Integer nextID = 1;
    public static void setBaseID(int id) {
        nextID = id;
    }

    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long studentsCount; //Значение поля должно быть больше 0
    private Long shouldBeExpelled; //Значение поля должно быть больше 0, Поле может быть null
    private FormOfEducation formOfEducation; //Поле не может быть null
    private Semester semesterEnum; //Поле не может быть null
    private Person groupAdmin; //Поле не может быть null

    public StudyGroup() {
        regenerate();
    }

    public void regenerate() {
        id =  nextID++;
        creationDate = new java.util.Date();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    /**
     * Sets value that is not null and not empty
     *
     * @param name value
     */
    public void setName(String name) {
        if (name != null && !name.isEmpty()) {
            this.name = name;
        } else {
            throw new IllegalArgumentException("Name cannot be empty");
        }
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Sets value that is not null
     *
     * @param coordinates value
     */
    public void setCoordinates(Coordinates coordinates) {
        if (coordinates != null) {
            this.coordinates = coordinates;
        } else {
            throw new IllegalArgumentException("Coordinates cannot be empty");
        }
    }

    public java.util.Date getCreationDate() {
        return creationDate;
    }

    public long getStudentsCount() {
        return studentsCount;
    }

    /**
     * Sets value that is greater than 0
     *
     * @param studentsCount value
     */
    public void setStudentsCount(long studentsCount) {
        if (studentsCount > 0) {
            this.studentsCount = studentsCount;
        } else {
            throw new IllegalArgumentException("StudentsCount should be greater than zero");
        }
    }

    public Long getShouldBeExpelled() {
        return shouldBeExpelled;
    }

    /**
     * Sets value that is not null and greater than 0
     *
     * @param shouldBeExpelled value
     */
    public void setShouldBeExpelled(Long shouldBeExpelled) {
        if (shouldBeExpelled != null && shouldBeExpelled > 0) {
            this.shouldBeExpelled = shouldBeExpelled;
        } else {
            throw new IllegalArgumentException("ShouldBeExpelled should be greater than zero");
        }
    }

    public FormOfEducation getFormOfEducation() {
        return formOfEducation;
    }

    /**
     * Sets value that is not null
     *
     * @param formOfEducation value
     */
    public void setFormOfEducation(FormOfEducation formOfEducation) {
        if (formOfEducation != null) {
            this.formOfEducation = formOfEducation;
        } else {
            throw new IllegalArgumentException("FormOfEducation cannot be null");
        }
    }

    public Semester getSemester() {
        return semesterEnum;
    }

    /**
     * Sets value that is not null
     *
     * @param semesterEnum value
     */
    public void setSemester(Semester semesterEnum) {
        if (semesterEnum != null) {
            this.semesterEnum = semesterEnum;
        } else {
            throw new IllegalArgumentException("SemesterEnum cannot be null");
        }
    }

    public Person getGroupAdmin() {
        return groupAdmin;
    }

    /**
     * Sets value that is not null
     *
     * @param groupAdmin value
     */
    public void setGroupAdmin(Person groupAdmin) {
        if (groupAdmin != null) {
            this.groupAdmin = groupAdmin;
        } else {
            throw new IllegalArgumentException("GroupAdmin cannot be null");
        }
    }

    /**
     * String representation of the data.
     *
     * @return String representation
     */
    @Override
    public String toString() {
        return "StudyGroup :: "
                + id + " "
                + name + " "
                + coordinates + " "
                + creationDate + " "
                + studentsCount + " "
                + shouldBeExpelled + " "
                + formOfEducation + " "
                + semesterEnum + " "
                + groupAdmin + " ;";
    }

    /**
     * Compares objects based on creationDate
     *
     * @param o the object to be compared.
     * @return value as required for Comparable interface
     */
    @Override
    public int compareTo(StudyGroup o) {
        return creationDate.compareTo(o.creationDate);
    }
}
