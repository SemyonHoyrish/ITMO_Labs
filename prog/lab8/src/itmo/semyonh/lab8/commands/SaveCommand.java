package itmo.semyonh.lab8.commands;

import itmo.semyonh.lab8.types.StudyGroup;

import java.util.LinkedHashSet;

/**
 * Represent a special case command. It's logic located inside itmo.semyonh.lab5.CollectionManager.
 */
public class SaveCommand implements Command {
    @Override
    public void prepare() {
        return;
    }

    @Override
    public CommandResult execute(LinkedHashSet<StudyGroup> collection) {
        return null;
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
