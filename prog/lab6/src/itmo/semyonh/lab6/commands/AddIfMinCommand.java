package itmo.semyonh.lab6.commands;

import itmo.semyonh.lab6.helpers.ConsoleReader;
import itmo.semyonh.lab6.types.StudyGroup;

import java.util.Collections;
import java.util.LinkedHashSet;


/**
 * AddIfMinCommand (add_if_min) reads StudyGroup from the reader
 * and adds it to the collection in case it less that minimal instance in the collection.
 */
public class AddIfMinCommand implements Command {
    private ConsoleReader reader;

    public AddIfMinCommand(ConsoleReader reader) {
        this.reader = reader;
    }

    @Override
    public void execute(LinkedHashSet<StudyGroup> collection) {
        StudyGroup group = reader.readStudyGroup();

        StudyGroup min = Collections.min(collection);

        if (group.compareTo(min) < 0) {
            collection.add(group);
        }
    }

    @Override
    public Command with(String[] args) {
        return this;
    }

    @Override
    public String getName() {
        return "add_if_min";
    }

    @Override
    public String getDescription() {
        return "add_if_min {element} : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции";
    }
}
