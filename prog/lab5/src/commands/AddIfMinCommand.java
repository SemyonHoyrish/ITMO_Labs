package commands;

import helpers.ConsoleReader;
import types.StudyGroup;

import java.util.Collections;
import java.util.LinkedHashSet;

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
}
