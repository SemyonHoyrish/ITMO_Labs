package itmo.semyonh.lab8.commands;

import itmo.semyonh.lab8.types.StudyGroup;

import java.util.ArrayList;
import java.util.LinkedHashSet;


public class RetrieveCommand implements Command {

    @Override
    public void prepare() {

    }

    @Override
    public CommandResult execute(LinkedHashSet<StudyGroup> collection) {
        var l = new ArrayList<StudyGroup>(collection);
        return new CommandResult(CommandResultType.ItemList, l);
    }

    @Override
    public Command with(String[] args) {
        return this;
    }

    @Override
    public String getName() {
        return "retrieve";
    }

    @Override
    public String getDescription() {
        return "retrieves items from the server";
    }
}
