package types;

public class StudyGroup implements Comparable<StudyGroup> {
    private static Integer nextID = 1;

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
        id =  nextID++;
        creationDate = new java.util.Date();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

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

    public void setGroupAdmin(Person groupAdmin) {
        if (groupAdmin != null) {
            this.groupAdmin = groupAdmin;
        } else {
            throw new IllegalArgumentException("GroupAdmin cannot be null");
        }
    }

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

    @Override
    public int compareTo(StudyGroup o) {
        return creationDate.compareTo(o.creationDate);
    }
}
