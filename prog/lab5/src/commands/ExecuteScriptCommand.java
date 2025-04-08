package commands;

import types.StudyGroup;

import java.util.LinkedHashSet;

public class ExecuteScriptCommand implements Command {

    @Override
    public void execute(LinkedHashSet<StudyGroup> collection) {
//TODO
    }

    @Override
    public Command with(String[] args) {
        return null;//TODO
    }

    @Override
    public String getName() {
        return "execute_script";
    }
}
