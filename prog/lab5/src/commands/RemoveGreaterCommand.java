package commands;

import helpers.ConsoleReader;
import types.StudyGroup;

import java.util.LinkedHashSet;

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
}
