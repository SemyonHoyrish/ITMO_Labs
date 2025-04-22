package itmo.semyonh.lab6.commands;

import itmo.semyonh.lab6.types.StudyGroup;

import java.util.LinkedHashSet;

/**
 * HelpCommand (help) prints the description of all registered commands.
 */
public class HelpCommand implements Command {
    private CommandManager manager;

    public HelpCommand(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute(LinkedHashSet<StudyGroup> collection) {
        for (var cmd : manager.getCommands().values()) {
            System.out.println(cmd.getDescription());
        }
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
