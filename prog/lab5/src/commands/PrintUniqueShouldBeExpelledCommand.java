package commands;

import types.StudyGroup;

import java.util.HashSet;
import java.util.LinkedHashSet;

public class PrintUniqueShouldBeExpelledCommand implements Command {
    @Override
    public void execute(LinkedHashSet<StudyGroup> collection) {
        HashSet<Long> s = new HashSet<>();

        for (StudyGroup studyGroup : collection) {
            s.add(studyGroup.getShouldBeExpelled());
        }

        for (var ss : s) {
            System.out.println(ss);
        }
    }

    @Override
    public Command with(String[] args) {
        return this;
    }

    @Override
    public String getName() {
        return "print_unique_should_be_expelled";
    }
}
