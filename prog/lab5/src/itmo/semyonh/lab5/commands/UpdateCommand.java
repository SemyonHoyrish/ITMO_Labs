package itmo.semyonh.lab5.commands;

import itmo.semyonh.lab5.helpers.ConsoleReader;
import itmo.semyonh.lab5.types.StudyGroup;

import java.util.LinkedHashSet;

/**
 * UpdateCommand (update id) finds entry with given id,
 * if found reads StudyGroup from the reader,
 * and replaces item with provided one.
 */
public class UpdateCommand implements Command {
    private int id = 0;
    private ConsoleReader reader;

    public UpdateCommand(ConsoleReader reader) {
        this.reader = reader;
    }

    @Override
    public void execute(LinkedHashSet<StudyGroup> collection) {
        if (id == 0) {
            System.out.println("Could not call " + getName() + " command without an id arg");
            return;
        }

        StudyGroup found = null;
        for (StudyGroup studyGroup : collection) {
            if (studyGroup.getId() == id) {
                found = studyGroup;
            }
        }

        if (found != null) {
            StudyGroup newGroup = reader.readStudyGroup();
            collection.remove(found);
            collection.add(newGroup);
        }
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
