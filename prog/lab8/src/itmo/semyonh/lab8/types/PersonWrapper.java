package itmo.semyonh.lab8.types;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class PersonWrapper {
    public SimpleStringProperty name = new SimpleStringProperty();
    public SimpleDoubleProperty weight = new SimpleDoubleProperty();
    public SimpleStringProperty eyeColor = new SimpleStringProperty();
    public SimpleStringProperty hairColor = new SimpleStringProperty();
    public SimpleStringProperty nationality = new SimpleStringProperty();

    public PersonWrapper(Person value) {
        name.setValue(value.getName());
        weight.setValue(value.getWeight());
        eyeColor.setValue(value.getEyeColor() != null ? value.getEyeColor().toString() : "");
        hairColor.setValue(value.getHairColor() != null ? value.getHairColor().toString() : "");
        nationality.setValue(value.getNationality() != null ? value.getNationality().toString() : "");
    }

    public Person convert() throws IllegalArgumentException {
        var p = new Person();
        p.setName(name.getValue());
        p.setWeight(weight.getValue());
        p.setEyeColor(Color.valueOf(eyeColor.getValue()));
        if (hairColor.getValue().isEmpty()) {
            p.setHairColor(null);
        } else {
            p.setHairColor(Color.valueOf(hairColor.getValue()));
        }
        p.setNationality(Country.valueOf(nationality.getValue()));
        return p;
    }
}
