package commands;

import helpers.ConsoleReader;
import types.StudyGroup;

import java.util.LinkedHashSet;

/**
 * RemoveGreaterCommand (remove_greater) reads StudyGroup from the reader
 * and removes all elements from collection, which are greater that read one.
 */
public class RemoveGreaterCommand implements Command {
    private ConsoleReader reader;

    public RemoveGreaterCommand(ConsoleReader reader) {
        this.reader = reader;
    }

    @Override
    public void execute(LinkedHashSet<StudyGroup> collection) {
        StudyGroup group = reader.readStudyGroup();

        collection.removeIf( g -> g.compareTo(group) > 0 );
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
