package commands;

import types.StudyGroup;

import java.util.LinkedHashSet;
import java.util.List;


public interface Command {
    void execute(LinkedHashSet<StudyGroup> collection);
    Command with(String[] args);
    String getName();
}
