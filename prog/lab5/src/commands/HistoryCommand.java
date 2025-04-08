package commands;

import types.StudyGroup;

import java.util.LinkedHashSet;
import java.util.List;

/**
 * HistoryCommand (history) prints last 7 commands executed.
 */
public class HistoryCommand implements Command {
    private List<Command> commandsHistory;

    public HistoryCommand(List<Command> history) {
        commandsHistory = history;
    }

    @Override
    public void execute(LinkedHashSet<StudyGroup> collection) {
        for (int i = Math.max(commandsHistory.size() - 7, 0); i < commandsHistory.size(); i++) {
            System.out.println(commandsHistory.get(i).getName());
        }
    }

    @Override
    public Command with(String[] args) {
        return this;
    }

    @Override
    public String getName() {
        return "history";
    }

    @Override
    public String getDescription() {
        return "history : вывести последние 7 команд (без их аргументов)";
    }
}
