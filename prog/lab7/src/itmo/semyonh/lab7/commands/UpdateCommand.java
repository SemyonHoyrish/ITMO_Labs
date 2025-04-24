package itmo.semyonh.lab7.commands;

import itmo.semyonh.lab7.helpers.ConsoleReader;
import itmo.semyonh.lab7.types.StudyGroup;

import java.util.LinkedHashSet;

/**
 * UpdateCommand (update id) finds entry with given id,
 * if found reads StudyGroup from the reader,
 * and replaces item with provided one.
 */
public class UpdateCommand implements Command {
    private int id = 0;
    private transient ConsoleReader reader;
    private StudyGroup newGroup;

    public UpdateCommand(ConsoleReader reader) {
        this.reader = reader;
    }

    @Override
    public void prepare() {
        newGroup = reader.readStudyGroup();
    }

    @Override
    public CommandResult execute(LinkedHashSet<StudyGroup> collection) {
        if (id == 0) {
            return new CommandResult(CommandResultType.Failed, "Could not call " + getName() + " command without an id arg");
        }

        newGroup.regenerate();

        StudyGroup found = null;
        for (StudyGroup studyGroup : collection) {
            if (studyGroup.getId() == id) {
                found = studyGroup;
            }
        }

        if (found != null) {
            collection.remove(found);
            collection.add(newGroup);
        } else {
            return new CommandResult(CommandResultType.Failed, "Could not find study group with id " + id);
        }

        return new CommandResult(CommandResultType.None, null);
    }

    @Override
    public Command with(String[] args) {
        UpdateCommand n = new UpdateCommand(reader);

        try {
            n.id = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Incorrect arg '" + args[0] + "' for " + getName() + " command!");
            return null;
        }

        return n;
    }

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String getDescription() {
        return "update id {element} : обновить значение элемента коллекции, id которого равен заданному";
    }
}
