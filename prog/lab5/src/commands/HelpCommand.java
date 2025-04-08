package commands;

import types.StudyGroup;

import java.util.LinkedHashSet;

public class HelpCommand implements Command {

    @Override
    public void execute(LinkedHashSet<StudyGroup> collection) {
        // TODO
    }

    @Override
    public Command with(String[] args) {
        return this;
    }

    @Override
    public String getName() {
        return "help";
    }
}
