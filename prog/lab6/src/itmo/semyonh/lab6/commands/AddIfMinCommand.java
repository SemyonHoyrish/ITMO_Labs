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
    private transient ConsoleReader reader;
    private StudyGroup group;

    public AddIfMinCommand(ConsoleReader reader) {
        this.reader = reader;
    }

    @Override
    public void prepare() {
        group = reader.readStudyGroup();
    }

    @Override
    public CommandResult execute(LinkedHashSet<StudyGroup> collection) {
        StudyGroup min = Collections.min(collection);

        if (group.compareTo(min) < 0) {
            group.regenerate();
            collection.add(group);
        }

        return new CommandResult(CommandResultType.None, null);
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
