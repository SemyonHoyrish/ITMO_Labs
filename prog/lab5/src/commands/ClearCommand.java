package commands;

import types.StudyGroup;

import java.util.LinkedHashSet;

/**
 * ClearCommand (clear) clears the collection
 */
public class ClearCommand implements Command {

    @Override
    public void execute(LinkedHashSet<StudyGroup> collection) {
        collection.clear();
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
