package itmo.semyonh.lab8.commands;

import itmo.semyonh.lab8.types.StudyGroup;

import java.util.Date;
import java.util.LinkedHashSet;

/**
 * InfoCommand (info) prints information about collection.
 */
public class InfoCommand implements Command {
    private static Date collectionInitDate;

    public InfoCommand(Date collectionInitDate) {
        InfoCommand.collectionInitDate = collectionInitDate;
    }

    @Override
    public void prepare() {

    }

    @Override
    public CommandResult execute(LinkedHashSet<StudyGroup> collection) {
        String result =
                "class: " + collection.getClass() + "\n"
                        + "init date: " + collectionInitDate + "\n"
                        + "size: " + collection.size() + "\n"
                        + "hash: " + collection.hashCode();

        return new CommandResult(CommandResultType.PlainText, result);
    }

    @Override
    public Command with(String[] args) {
        return this;
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
    }
}
