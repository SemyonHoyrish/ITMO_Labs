package itmo.semyonh.lab8.commands;

import itmo.semyonh.lab8.types.StudyGroup;

import java.util.LinkedHashSet;


/**
 * ExitCommand (exit) exits the program
 */
public class ExitCommand implements Command {
    @Override
    public void prepare() {
        // Exit on client application
        System.exit(0);
    }

    @Override
    public CommandResult execute(LinkedHashSet<StudyGroup> collection) {
        // Not allowed to be sent to the server

        return null;
    }

    @Override
    public Command with(String[] args) {
        return this;
    }

    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public String getDescription() {
        return "exit : завершить программу (без сохранения в файл)";
    }
}
