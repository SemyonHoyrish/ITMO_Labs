package commands;

import types.StudyGroup;

import java.util.LinkedHashSet;


/**
 * ShowCommand (show) prints all elements of the collection.
 */
public class ShowCommand implements Command {

    @Override
    public void execute(LinkedHashSet<StudyGroup> collection) {
        for (StudyGroup studyGroup : collection) {
            System.out.println(studyGroup);
        }
    }

    @Override
    public Command with(String[] args) {
        return this;
    }

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String getDescription() {
        return "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }
}
