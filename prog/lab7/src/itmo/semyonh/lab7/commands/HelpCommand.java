package itmo.semyonh.lab7.commands;

import itmo.semyonh.lab7.types.StudyGroup;

import java.util.LinkedHashSet;

/**
 * HelpCommand (help) prints the description of all registered commands.
 */
public class HelpCommand implements Command {
    private transient CommandManager manager;

    public HelpCommand(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void prepare() {
        for (var cmd : manager.getCommands().values()) {
            System.out.println(cmd.getDescription());
        }
    }

    @Override
    public CommandResult execute(LinkedHashSet<StudyGroup> collection) {
        // No point of this on the server

        return null;
    }

    @Override
    public Command with(String[] args) {
        return this;
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "help : вывести справку по доступным командам";
    }
}
