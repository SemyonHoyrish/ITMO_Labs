package itmo.semyonh.lab6.helpers;

import itmo.semyonh.lab6.types.*;

import java.util.Scanner;


/**
 * Utility class to read all kinds of values from user input and
 * parse them to corresponding types.
 */
public class ConsoleReader {
    private Scanner scanner;

    /**
     * Default constructor, creates a Scanner for stdin.
     */
    public ConsoleReader() {
        scanner = new Scanner(System.in);
    }

    /**
     * Sets a different scanner to use for reading values.
     *
     * @param scanner - Scanner to use
     */
    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Returns currently set scanner
     *
     * @return current Scanner
     */
    public Scanner getScanner() {
        return scanner;
    }

    /**
     * Reads a string that is not null and not empty.
     * Repeat attempts until will receive a correct input,
     *
     * @param prefix - name for input hint
     * @return not null not empty string
     */
    public String readString(String prefix) {
        String s = "";

        while (s.isEmpty()) {
            System.out.println("Enter value for '" + prefix + "'. Value should not be empty or null");
            s = scanner.nextLine();
        }

        return s;
    }

    /**
     * Reads an integer [from; to]
     *
     * @param prefix - name for input hint
     * @param from - minimal accepted value
     * @param to - maximal accepted value
     * @return int which is   >= `from` && <= `to`
     */
    public int readInt(String prefix, int from, int to) {
        while (true) {
            System.out.println("Enter value for '" + prefix + "'. Value should not be in range '" + from + "' to '" + to + "'");
            String s = scanner.nextLine();
            try {
                int i = Integer.parseInt(s);
                if (i >= from && i <= to) {
                    return i;
                }
            } catch (NumberFormatException e) {}
        }
    }

    /**
     * Reads a long value [from; to]
     *
     * @param prefix - name for input hint
     * @param from - minimal accepted value
     * @param to - maximal accepted value
     * @return long which is   >= `from` && <= `to`
     */
    public long readLong(String prefix, long from, long to) {
        while (true) {
            System.out.println("Enter value for '" + prefix + "'. Value should not be in range '" + from + "' to '" + to + "'");
            String s = scanner.nextLine();
            try {
                long l = Long.parseLong(s);
                if (l >= from && l <= to) {
                    return l;
                }
            } catch (NumberFormatException e) {}
        }
    }

    /**
     * Reads a float value [from; to]
     *
     * @param prefix - name for input hint
     * @param from - minimal accepted value
     * @param to - maximal accepted value
     * @return float which is   >= `from` && <= `to`
     */
    public float readFloat(String prefix, float from, float to) {
        while (true) {
            System.out.println("Enter value for '" + prefix + "'. Value should not be in range '" + from + "' to '" + to + "'");
            String s = scanner.nextLine();
            try {
                float f = Float.parseFloat(s);
                if (f >= from && f <= to) {
                    return f;
                }
            } catch (NumberFormatException e) {}
        }
    }

    /**
     * Reads a double value [from; to]
     *
     * @param prefix - name for input hint
     * @param from - minimal accepted value
     * @param to - maximal accepted value
     * @return double which is   >= `from` && <= `to`
     */
    public double readDouble(String prefix, double from, double to) {
        while (true) {
            System.out.println("Enter value for '" + prefix + "'. Value should not be in range '" + from + "' to '" + to + "'");
            String s = scanner.nextLine();
            try {
                double d = Double.parseDouble(s);
                if (d >= from && d <= to) {
                    return d;
                }
            } catch (NumberFormatException e) {}
        }
    }

    /**
     * Reads a StudyGroup recursively filling all the fields (that is not auto generated)
     * asking user (or any input stream set in Scanner) for the input.
     *
     * @return StudyGroup, not null, filled with values.
     */
    public StudyGroup readStudyGroup() {
        StudyGroup studyGroup = new StudyGroup();

        studyGroup.setName(readString("StudyGroup.name"));
        studyGroup.setCoordinates(readCoordinates("StudyGroup"));
        studyGroup.setStudentsCount(readLong("StudyGroup.studentsCount", 1, Long.MAX_VALUE));
        studyGroup.setShouldBeExpelled(readLong("StudyGroup.shouldBeExpelled", 1, Long.MAX_VALUE));
        studyGroup.setFormOfEducation(readFormOfEducation("StudyGroup"));
        studyGroup.setSemester(readSemester("StudyGroup"));
        studyGroup.setGroupAdmin(readPerson("StudyGroup.groupAdmin"));

        return studyGroup;
    }

    /**
     * Reads coordinate object
     *
     * @param prefix - prefix to show the parent object in the path inside the user hint.
     * @return Coordinates objects, not null, with values.
     */
    public Coordinates readCoordinates(String prefix) {
        Coordinates coordinates = new Coordinates();

        coordinates.setX(readFloat(String.join(".", prefix, "Coordinates.x"), Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY));
        coordinates.setY(readDouble(String.join(".", prefix, "Coordinates.y"), Double.NEGATIVE_INFINITY, 2));

        return coordinates;
    }

    /**
     * Reads readFormOfEducation enum value
     *
     * @param prefix - prefix to show the parent object in the path inside the user hint.
     * @return FormOfEducation enum value
     */
    public FormOfEducation readFormOfEducation(String prefix) {
        var values = FormOfEducation.values();

        System.out.println(String.join(".", prefix, "formOfEducation"));
        for (var value : values) {
            System.out.println(value.ordinal() + ": " + value.name());
        }

        int from = values[0].ordinal();
        int to = values[values.length - 1].ordinal();
        int selected = -1;
        while (selected < from || selected > to) {
            selected = readInt("option", from, to);
        }

        return FormOfEducation.valueOf(values[selected].name());
    }

    /**
     * Reads Semester enum value
     *
     * @param prefix - prefix to show the parent object in the path inside the user hint.
     * @return Semester enum value
     */
    public Semester readSemester(String prefix) {
        var values = Semester.values();

        System.out.println(String.join(".", prefix, "semester"));
        for (var value : values) {
            System.out.println(value.ordinal() + ": " + value.name());
        }

        int from = values[0].ordinal();
        int to = values[values.length - 1].ordinal();
        int selected = -1;
        while (selected < from || selected > to) {
            selected = readInt("option", from, to);
        }

        return Semester.valueOf(values[selected].name());
    }

    /**
     * Reads person object
     *
     * @param prefix - prefix to show the parent object in the path inside the user hint.
     * @return Person objects, not null, with values.
     */
    public Person readPerson(String prefix) {
        Person p = new Person();

        p.setName(readString(String.join(".", prefix, "name")));
        p.setWeight(readDouble(String.join(".", prefix, "weight"), Double.MIN_VALUE, Double.MAX_VALUE));
        p.setEyeColor(readColor(String.join(".", prefix, "eyeColor"), true));
        p.setHairColor(readColor(String.join(".", prefix, "hairColor"), false));
        p.setNationality(readCountry(String.join(".", prefix, "nationality")));

        return p;
    }

    /**
     * Reads Color enum value, possibility of a `null` value is determined by `notNull` parameter.
     *
     * @param prefix - prefix to show the parent object in the path inside the user hint.
     * @param notNull - if set return value cannot be null, can be otherwise.
     * @return Color enum value or null.
     */
    public Color readColor(String prefix, boolean notNull) {
        var values = Color.values();

        System.out.println(String.join(".", prefix, ""));
        for (var value : values) {
            System.out.println(value.ordinal() + ": " + value.name());
        }

        int from = values[0].ordinal();
        int to = values[values.length - 1].ordinal();

        if (notNull) {
            int selected = -1;
            while (selected < from || selected > to) {
                selected = readInt("option", from, to);
            }
            return Color.valueOf(values[selected].name());
        }

        int intSelected = -1;
        while (intSelected < from || intSelected > to) {
            String selected = scanner.nextLine();
            if (selected.isEmpty()) {
                return null;
            } else {
                try {
                    intSelected = Integer.parseInt(selected);
                } catch (NumberFormatException e) {
                    System.out.println("Not a number");
                }
            }
        }

        return Color.valueOf(values[intSelected].name());
    }

    /**
     * Reads Country enum value
     *
     * @param prefix - prefix to show the parent object in the path inside the user hint.
     * @return Country enum value
     */
    public Country readCountry(String prefix) {
        var values = Country.values();

        System.out.println(String.join(".", prefix, ""));
        for (var value : values) {
            System.out.println(value.ordinal() + ": " + value.name());
        }

        int from = values[0].ordinal();
        int to = values[values.length - 1].ordinal();
        int selected = -1;
        while (selected < from || selected > to) {
            selected = readInt("option", from, to);
        }

        return Country.valueOf(values[selected].name());
    }
}
