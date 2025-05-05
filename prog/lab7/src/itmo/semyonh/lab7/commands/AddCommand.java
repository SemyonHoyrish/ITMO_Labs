package itmo.semyonh.lab7.commands;

import itmo.semyonh.lab7.CollectionManager;
import itmo.semyonh.lab7.helpers.ConsoleReader;
import itmo.semyonh.lab7.helpers.Database;
import itmo.semyonh.lab7.types.StudyGroup;

import java.sql.SQLException;
import java.util.LinkedHashSet;


/**
 * AddCommand (add) reads StudyGroup from the reader and adds it to collection.
 */
public class AddCommand implements Command {
    private transient ConsoleReader reader;
    private StudyGroup studyGroup;

    public AddCommand(ConsoleReader reader) {
        this.reader = reader;
    }

    @Override
    public void prepare() {
        studyGroup = reader.readStudyGroup();
    }

    @Override
    public CommandResult execute(LinkedHashSet<StudyGroup> collection) {
//        studyGroup.regenerate();
        try {
            if (Database.getInstance().insert(studyGroup, CollectionManager.accountID)) {
                collection.add(studyGroup);
                return new CommandResult(CommandResultType.None, null);
            } else {
                return new CommandResult(CommandResultType.Failed, "Was not added in database");
            }
        } catch (SQLException e) {
            return new CommandResult(CommandResultType.Failed, "Database error: " + e.getMessage());
        }
    }

    @Override
    public Command with(String[] args) {
        return this;
    }

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getDescription() {
        return "add {element} : добавить новый элемент в коллекцию";
    }
}
