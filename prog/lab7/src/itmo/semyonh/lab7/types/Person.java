package itmo.semyonh.lab7.types;

/**
 * Represent data of Person type.
 */
public class Person {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Double weight; //Поле не может быть null, Значение поля должно быть больше 0
    private Color eyeColor; //Поле не может быть null
    private Color hairColor; //Поле может быть null
    private Country nationality; //Поле не может быть null

    public String getName() {
        return name;
    }

    /**
     * Sets value that is not null and not empty.
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

    public Double getWeight() {
        return weight;
    }

    /**
     * Sets value that is not null and greater than 0.
     *
     * @param weight value
     */
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

    /**
     * Sets value that is not null
     *
     * @param eyeColor value
     */
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

    /**
     * Sets value, possible null
     *
     * @param hairColor value
     */
    public void setHairColor(Color hairColor) {
        this.hairColor = hairColor;
    }

    public Country getNationality() {
        return nationality;
    }

    /**
     * Sets value that is not null
     *
     * @param nationality value
     */
    public void setNationality(Country nationality) {
        if (nationality != null) {
            this.nationality = nationality;
        } else {
            throw new IllegalArgumentException("Nationality cannot be null");
        }
    }

    /**
     * String representation of the data.
     *
     * @return String representation
     */
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
