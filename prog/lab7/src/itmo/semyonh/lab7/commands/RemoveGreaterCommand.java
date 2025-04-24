package itmo.semyonh.lab7.commands;

import itmo.semyonh.lab7.helpers.ConsoleReader;
import itmo.semyonh.lab7.types.StudyGroup;

import java.util.LinkedHashSet;

/**
 * RemoveGreaterCommand (remove_greater) reads StudyGroup from the reader
 * and removes all elements from collection, which are greater that read one.
 */
public class RemoveGreaterCommand implements Command {
    private transient ConsoleReader reader;
    private StudyGroup group;

    public RemoveGreaterCommand(ConsoleReader reader) {
        this.reader = reader;
    }

    @Override
    public void prepare() {
        group = reader.readStudyGroup();
    }

    @Override
    public CommandResult execute(LinkedHashSet<StudyGroup> collection) {
        collection.removeIf( g -> g.compareTo(group) > 0 );

        return new CommandResult(CommandResultType.None, null);
    }

    @Override
    public Command with(String[] args) {
        return this;
    }

    @Override
    public String getName() {
        return "remove_greater";
    }

    @Override
    public String getDescription() {
        return "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный";
    }
}
