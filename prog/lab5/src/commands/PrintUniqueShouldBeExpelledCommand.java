package commands;

import types.StudyGroup;

import java.util.HashSet;
import java.util.LinkedHashSet;


/**
 * PrintUniqueShouldBeExpelledCommand (print_unique_should_be_expelled)
 * prints all unique values of shouldBeExpelled field of all items in the collection.
 */
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

    @Override
    public String getDescription() {
        return "print_unique_should_be_expelled : вывести уникальные значения поля shouldBeExpelled всех элементов в коллекции";
    }
}
