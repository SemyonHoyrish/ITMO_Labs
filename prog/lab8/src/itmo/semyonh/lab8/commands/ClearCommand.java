package itmo.semyonh.lab8.commands;

import itmo.semyonh.lab8.CollectionManager;
import itmo.semyonh.lab8.helpers.Database;
import itmo.semyonh.lab8.types.StudyGroup;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;

/**
 * ClearCommand (clear) clears the collection
 */
public class ClearCommand implements Command {

    @Override
    public void prepare() {

    }

    @Override
    public CommandResult execute(LinkedHashSet<StudyGroup> collection) {
        var confirmed = new ArrayList<Integer>();
        for(var g : collection) {
            try {
                var res = Database.getInstance().remove(g.getId(), CollectionManager.accountID);
                if (res) {
                    confirmed.add(g.getId());
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
        return "clear";
    }

    @Override
    public String getDescription() {
        return "clear : очистить коллекцию";
    }
}
