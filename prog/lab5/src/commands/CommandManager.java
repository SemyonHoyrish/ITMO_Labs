package commands;

import java.util.Arrays;
import java.util.HashMap;

/**
 * CommandManager handles registration of commands,
 * their parsing and inline arguments parsing.
 */
public class CommandManager {
    private HashMap<String, Command> commands;

    /**
     * Default constructor to initialize internal fields.
     */
    public CommandManager() {
        commands = new HashMap<>();
    }

    /**
     * Registers a command, that will be in parse options.
     *
     * @param command - command to register
     */
    public void registerCommand(Command command) {
        commands.put(command.getName(), command);
    }

    /**
     * Gets map of registered commands (command_name, command)
     *
     * @return HashMap of command name and command object.
     */
    public HashMap<String, Command> getCommands() {
        return commands;
    }

    /**
     * Parses the input line, determining whether if it was a command or not,
     * and in case it is a command, is there any arguments for it.
     *
     * @param input - one line of user input
     * @return Command with arguments, Command, or null.
     */
    public Command parseCommand(String input) {
        var parts = input.split(" ");
        for (var c : commands.keySet()) {
            if (parts[0].equals(c)) {
                var cmd = commands.get(c);
                if (parts.length > 1) {
                    var args = Arrays.copyOfRange(parts, 1, parts.length);
                    return cmd.with(args);
                } else {
                    return cmd;
                }
            }
        }
        return null;
    }
}
