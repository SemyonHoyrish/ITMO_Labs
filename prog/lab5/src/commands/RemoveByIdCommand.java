package commands;

import types.StudyGroup;

import javax.naming.Name;
import java.util.LinkedHashSet;

public class RemoveByIdCommand implements Command {
    private int id = 0;

    @Override
    public void execute(LinkedHashSet<StudyGroup> collection) {
        if (id == 0) {
            System.out.println("Could not call " + getName() + " command without an id arg");
            return;
        }

        collection.removeIf(g -> g.getId() == id);
    }

    @Override
    public Command with(String[] args) {
        RemoveByIdCommand n = new RemoveByIdCommand();

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
        return "remove_by_id";
    }

    @Override
    public String getDescription() {
        return "remove_by_id id : удалить элемент из коллекции по его id";
    }
}
