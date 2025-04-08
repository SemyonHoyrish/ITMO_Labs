package commands;

import java.util.Arrays;
import java.util.HashMap;

public class CommandManager {
    private HashMap<String, Command> commands;

    public CommandManager() {
        commands = new HashMap<>();
    }

    public void registerCommand(Command command) {
        commands.put(command.getName(), command);
    }

    public HashMap<String, Command> getCommands() {
        return commands;
    }

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
