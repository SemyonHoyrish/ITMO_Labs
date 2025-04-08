package commands;

import types.StudyGroup;

import java.util.LinkedHashSet;

/**
 * Represent a special case command. It's logic located inside CollectionManager.
 */
public class SaveCommand implements Command {
    @Override
    public void execute(LinkedHashSet<StudyGroup> collection) {
        return;
    }

    @Override
    public Command with(String[] args) {
        return this;
    }

    @Override
    public String getName() {
        return "save";
    }

    @Override
    public String getDescription() {
        return "save : сохранить коллекцию в файл";
    }
}
