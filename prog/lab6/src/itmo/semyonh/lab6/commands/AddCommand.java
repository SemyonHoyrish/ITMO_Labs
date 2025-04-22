package itmo.semyonh.lab6.commands;

import itmo.semyonh.lab6.helpers.ConsoleReader;
import itmo.semyonh.lab6.types.StudyGroup;

import java.util.LinkedHashSet;


/**
 * AddCommand (add) reads StudyGroup from the reader and adds it to collection.
 */
public class AddCommand implements Command {
    private transient ConsoleReader reader;
    private StudyGroup studyGroup;

    public AddCommand(ConsoleReader reader) {
        this.reader = reader;
    }

    @Override
    public void prepare() {
        studyGroup = reader.readStudyGroup();
    }

    @Override
    public CommandResult execute(LinkedHashSet<StudyGroup> collection) {
        studyGroup.regenerate();
        collection.add(studyGroup);

        return new CommandResult(CommandResultType.None, null);
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
