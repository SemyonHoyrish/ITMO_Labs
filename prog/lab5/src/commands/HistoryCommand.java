package commands;

import types.StudyGroup;

import java.util.LinkedHashSet;
import java.util.List;

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
}
