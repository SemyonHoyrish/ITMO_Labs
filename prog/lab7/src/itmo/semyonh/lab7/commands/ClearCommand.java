package itmo.semyonh.lab7.commands;

import itmo.semyonh.lab7.types.StudyGroup;

import java.util.LinkedHashSet;

/**
 * ClearCommand (clear) clears the collection
 */
public class ClearCommand implements Command {

    @Override
    public void prepare() {

    }

    @Override
    public CommandResult execute(LinkedHashSet<StudyGroup> collection) {
        collection.clear();

        return new CommandResult(CommandResultType.None, null);
    }

    @Override
    public Command with(String[] args) {
        return this;
    }

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getDescription() {
        return "clear : очистить коллекцию";
    }
}
