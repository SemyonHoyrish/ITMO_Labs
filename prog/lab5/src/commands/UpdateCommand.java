package commands;

import helpers.ConsoleReader;
import types.StudyGroup;

import java.util.LinkedHashSet;
import java.util.List;

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

        for (StudyGroup studyGroup : collection) {
            if (studyGroup.getId() == id) {
                StudyGroup newGroup = reader.readStudyGroup();
                collection.remove(studyGroup);
                collection.add(newGroup);
            }
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
}
