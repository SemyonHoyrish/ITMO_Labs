package commands;

import helpers.ConsoleReader;
import types.StudyGroup;

import java.util.LinkedHashSet;


/**
 * AddCommand (add) reads StudyGroup from the reader and adds it to collection.
 */
public class AddCommand implements Command {
    private ConsoleReader reader;

    public AddCommand(ConsoleReader reader) {
        this.reader = reader;
    }

    @Override
    public void execute(LinkedHashSet<StudyGroup> collection) {
        StudyGroup studyGroup = reader.readStudyGroup();

        collection.add(studyGroup);
    }

    @Override
    public Command with(String[] args) {
        return this;
    }

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getDescription() {
        return "add {element} : добавить новый элемент в коллекцию";
    }
}
