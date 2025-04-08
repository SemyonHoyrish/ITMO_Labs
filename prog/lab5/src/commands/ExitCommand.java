package commands;

import types.StudyGroup;

import java.util.LinkedHashSet;

public class ExitCommand implements Command {
    @Override
    public void execute(LinkedHashSet<StudyGroup> collection) {
        System.exit(0);
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
