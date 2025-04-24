package itmo.semyonh.lab7.commands;

import itmo.semyonh.lab7.types.StudyGroup;

import java.util.LinkedHashSet;


/**
 * ShowCommand (show) prints all elements of the collection.
 */
public class ShowCommand implements Command {

    @Override
    public void prepare() {

    }

    @Override
    public CommandResult execute(LinkedHashSet<StudyGroup> collection) {
        StringBuilder result = new StringBuilder();
        for (StudyGroup studyGroup : collection) {
            result.append(studyGroup.toString()).append("\n");
        }

        return new CommandResult(CommandResultType.PlainText, result.toString());
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
