package itmo.semyonh.lab8.types;

import javafx.beans.property.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class StudyGroupWrapper {
    public SimpleStringProperty owner = new SimpleStringProperty();
    public SimpleIntegerProperty id = new SimpleIntegerProperty();
    public SimpleStringProperty name = new SimpleStringProperty();
    public SimpleFloatProperty coordinatesX = new SimpleFloatProperty();
    public SimpleDoubleProperty coordinatesY = new SimpleDoubleProperty();
    public SimpleStringProperty creationDate = new SimpleStringProperty();
    public SimpleLongProperty studentsCount = new SimpleLongProperty();
    public SimpleLongProperty shouldBeExpelled = new SimpleLongProperty();
    public SimpleStringProperty formOfEducation = new SimpleStringProperty();
    public SimpleStringProperty semester = new SimpleStringProperty();

    public PersonWrapper groupAdmin;

    public StudyGroupWrapper(StudyGroup value, String ownerLogin) {
        owner.setValue(ownerLogin);
        id.setValue(value.getId());
        name.setValue(value.getName());
        coordinatesX.setValue(value.getCoordinates().getX());
        coordinatesY.setValue(value.getCoordinates().getY());
        creationDate.setValue(value.getCreationDate().toString()); //TODO: cfg
        studentsCount.setValue(value.getStudentsCount());
        shouldBeExpelled.setValue(value.getShouldBeExpelled());
        formOfEducation.setValue(value.getFormOfEducation() != null ? value.getFormOfEducation().toString() : "");
        semester.setValue(value.getSemester() != null ? value.getSemester().toString() : "");
        groupAdmin = new PersonWrapper(value.getGroupAdmin());
    }

    public StudyGroup convert() throws IllegalArgumentException {
        var g = new StudyGroup();
        g.setId(id.getValue());
        g.setName(name.getValue());
        var c = new Coordinates();
        c.setX(coordinatesX.getValue());
        c.setY(coordinatesY.getValue());
        g.setCoordinates(c);
        // TODO
        g.setCreationDate(Date.from(ZonedDateTime.parse(creationDate.getValue(), DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy")).toInstant()));
        g.setStudentsCount(studentsCount.getValue());
        g.setShouldBeExpelled(shouldBeExpelled.getValue());
        g.setFormOfEducation(FormOfEducation.valueOf(formOfEducation.getValue()));
        g.setSemester(Semester.valueOf(semester.getValue()));
        g.setGroupAdmin(groupAdmin.convert());

        return g;
    }

    public void migrate(StudyGroupWrapper other) {
        if (!owner.getValue().equals(other.owner.getValue())) {
            owner.setValue(other.owner.getValue());
        }
        if (!id.getValue().equals(other.id.getValue())) {
            id.setValue(other.id.getValue());
        }
        if (!name.getValue().equals(other.name.getValue())) {
            name.setValue(other.name.getValue());
        }
        if (!coordinatesX.getValue().equals(other.coordinatesX.getValue())) {
            coordinatesX.setValue(other.coordinatesX.getValue());
        }
        if (!coordinatesY.getValue().equals(other.coordinatesY.getValue())) {
            coordinatesY.setValue(other.coordinatesY.getValue());
        }
        if (!creationDate.getValue().equals(other.creationDate.getValue())) {
            creationDate.setValue(other.creationDate.getValue());
        }
        if (!studentsCount.getValue().equals(other.studentsCount.getValue())) {
            studentsCount.setValue(other.studentsCount.getValue());
        }
        if (!shouldBeExpelled.getValue().equals(other.shouldBeExpelled.getValue())) {
            shouldBeExpelled.setValue(other.shouldBeExpelled.getValue());
        }
        if (!formOfEducation.getValue().equals(other.formOfEducation.getValue())) {
            formOfEducation.setValue(other.formOfEducation.getValue());
        }
        if (!semester.getValue().equals(other.semester.getValue())) {
            semester.setValue(other.semester.getValue());
        }
        groupAdmin.migrate(other.groupAdmin);
    }
}
