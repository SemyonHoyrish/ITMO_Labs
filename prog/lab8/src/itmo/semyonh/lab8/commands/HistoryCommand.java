package itmo.semyonh.lab8.commands;

import itmo.semyonh.lab8.types.StudyGroup;

import java.util.LinkedHashSet;
import java.util.List;

/**
 * HistoryCommand (history) prints last 7 commands executed.
 */
public class HistoryCommand implements Command {
    private static List<Command> commandsHistory;

    public HistoryCommand(List<Command> history) {
        commandsHistory = history;
    }

    @Override
    public void prepare() {

    }

    @Override
    public CommandResult execute(LinkedHashSet<StudyGroup> collection) {
        StringBuilder result = new StringBuilder();
        for (int i = Math.max(commandsHistory.size() - 7, 0); i < commandsHistory.size(); i++) {
            result.append(commandsHistory.get(i).getName()).append("\n");
        }

        return new CommandResult(CommandResultType.PlainText, result.toString());
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
