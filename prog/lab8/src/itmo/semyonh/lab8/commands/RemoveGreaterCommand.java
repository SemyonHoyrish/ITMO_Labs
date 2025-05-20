package itmo.semyonh.lab8.commands;

import itmo.semyonh.lab8.CollectionManager;
import itmo.semyonh.lab8.helpers.ConsoleReader;
import itmo.semyonh.lab8.helpers.Database;
import itmo.semyonh.lab8.types.StudyGroup;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;

/**
 * RemoveGreaterCommand (remove_greater) reads StudyGroup from the reader
 * and removes all elements from collection, which are greater that read one.
 */
public class RemoveGreaterCommand implements Command {
    private transient ConsoleReader reader;
    private StudyGroup group;

    public RemoveGreaterCommand(ConsoleReader reader) {
        this.reader = reader;
    }

    public RemoveGreaterCommand(StudyGroup group) {
        this.group = group;
    }

    @Override
    public void prepare() {
        group = reader.readStudyGroup();
    }

    @Override
    public CommandResult execute(LinkedHashSet<StudyGroup> collection) {
        var toRemove = new ArrayList<Integer>();
        for (var g : collection) {
            if (g.compareTo(group) > 0) {
                toRemove.add(g.getId());
            }
        }

        var confirmed = new ArrayList<Integer>();
        for (int id : toRemove) {
            try {
                var res = Database.getInstance().remove(id, CollectionManager.accountID);
                if (res) {
                    confirmed.add(id);
                }
            } catch (SQLException e) {
            }
        }

        collection.removeIf(g -> confirmed.contains(g.getId()));

        return new CommandResult(CommandResultType.None, null);
    }

    @Override
    public Command with(String[] args) {
        return this;
    }

    @Override
    public String getName() {
        return "remove_greater";
    }

    @Override
    public String getDescription() {
        return "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный";
    }
}
