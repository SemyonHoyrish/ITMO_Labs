package itmo.semyonh.lab5.commands;

import itmo.semyonh.lab5.types.StudyGroup;

import java.util.LinkedHashSet;


/**
 * Command interface describes command being used for manipulate CollectionManager
 */
public interface Command {
    /**
     * Method that actually manipulate the collection (if needed)
     *
     * @param collection - collection provided by CollectionManager
     */
    void execute(LinkedHashSet<StudyGroup> collection);

    /**
     * Method that return new instance of this Command with specific arguments,
     * or if this command does not upport argument, returns reference to `this`.
     *
     * @param args - list of arguments in string form.
     * @return return new Command or reference to `this`.
     */
    Command with(String[] args);

    /**
     * Returns name of the command
     *
     * @return command name
     */
    String getName();

    /**
     * Returns description of the command
     *
     * @return command description
     */
    String getDescription();
}
