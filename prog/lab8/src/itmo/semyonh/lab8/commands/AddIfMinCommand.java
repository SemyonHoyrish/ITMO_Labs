package itmo.semyonh.lab8.commands;

import itmo.semyonh.lab8.CollectionManager;
import itmo.semyonh.lab8.helpers.ConsoleReader;
import itmo.semyonh.lab8.helpers.Database;
import itmo.semyonh.lab8.types.StudyGroup;

import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedHashSet;


/**
 * AddIfMinCommand (add_if_min) reads StudyGroup from the reader
 * and adds it to the collection in case it less that minimal instance in the collection.
 */
public class AddIfMinCommand implements Command {
    private transient ConsoleReader reader;
    private StudyGroup group;

    public AddIfMinCommand(ConsoleReader reader) {
        this.reader = reader;
    }

    @Override
    public void prepare() {
        group = reader.readStudyGroup();
    }

    @Override
    public CommandResult execute(LinkedHashSet<StudyGroup> collection) {
        if (collection.isEmpty() || group.compareTo(Collections.min(collection)) < 0) {
//            group.regenerate();
            try {
                if (Database.getInstance().insert(group, CollectionManager.accountID)) {
                    collection.add(group);
                } else {
                    return new CommandResult(CommandResultType.Failed, "Was not added in database");
                }
            } catch (SQLException e) {
                return new CommandResult(CommandResultType.Failed, "Database error: " + e.getMessage());
            }
        }
        return new CommandResult(CommandResultType.None, null);
    }

    @Override
    public Command with(String[] args) {
        return this;
    }

    @Override
    public String getName() {
        return "add_if_min";
    }

    @Override
    public String getDescription() {
        return "add_if_min {element} : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции";
    }
}
