package types;

public class Person {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Double weight; //Поле не может быть null, Значение поля должно быть больше 0
    private Color eyeColor; //Поле не может быть null
    private Color hairColor; //Поле может быть null
    private Country nationality; //Поле не может быть null

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

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        if (weight != null && weight > 0) {
            this.weight = weight;
        } else {
            throw new IllegalArgumentException("Weight should be greater than zero");
        }
    }

    public Color getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(Color eyeColor) {
        if (eyeColor != null) {
            this.eyeColor = eyeColor;
        } else {
            throw new IllegalArgumentException("EyeColor cannot be null");
        }
    }

    public Color getHairColor() {
        return hairColor;
    }

    public void setHairColor(Color hairColor) {
        this.hairColor = hairColor;
    }

    public Country getNationality() {
        return nationality;
    }

    public void setNationality(Country nationality) {
        if (nationality != null) {
            this.nationality = nationality;
        } else {
            throw new IllegalArgumentException("Nationality cannot be null");
        }
    }

    @Override
    public String toString() {
        return "Person :: "
                + name + " "
                + weight + " "
                + eyeColor + " "
                + hairColor + " "
                + nationality + " ;";
    }
}
