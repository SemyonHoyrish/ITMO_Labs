package itmo.semyonh.lab6.commands;

import itmo.semyonh.lab6.types.StudyGroup;

import java.util.Collections;
import java.util.LinkedHashSet;

/**
 * MaxByCoordsCommand (max_by_coordinates) prints any collection item that has maximal coordinates value.
 */
public class MaxByCoordsCommand implements Command {
    @Override
    public void prepare() {

    }

    @Override
    public CommandResult execute(LinkedHashSet<StudyGroup> collection) {
        var r = Collections.max(collection, (a, b) -> a.getCoordinates().compareTo(b.getCoordinates()));

        return new CommandResult(CommandResultType.Item, r);
    }

    @Override
    public Command with(String[] args) {
        return this;
    }

    @Override
    public String getName() {
        return "max_by_coordinates";
    }

    @Override
    public String getDescription() {
        return "max_by_coordinates : вывести любой объект из коллекции, значение поля coordinates которого является максимальным";
    }
}
