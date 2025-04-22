package itmo.semyonh.lab6.commands;

import itmo.semyonh.lab6.types.StudyGroup;

import java.util.LinkedHashSet;


/**
 * RemoveByIdCommand (remove_by_id id) removes collection entry that has id that was provided as an argument,
 * if there are no such entry, does noting.
 */
public class RemoveByIdCommand implements Command {
    private int id = 0;

    @Override
    public void prepare() {

    }

    @Override
    public CommandResult execute(LinkedHashSet<StudyGroup> collection) {
        if (id == 0) {
            return new CommandResult(CommandResultType.Failed, "Could not call " + getName() + " command without an id arg");
        }

        collection.removeIf(g -> g.getId() == id);

        return new CommandResult(CommandResultType.None, null);
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
