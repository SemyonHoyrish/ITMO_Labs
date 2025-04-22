package itmo.semyonh.lab6.commands;

import itmo.semyonh.lab6.types.StudyGroup;

import java.util.Date;
import java.util.LinkedHashSet;

/**
 * InfoCommand (info) prints information about collection.
 */
public class InfoCommand implements Command {
    private Date collectionInitDate;

    public InfoCommand(Date collectionInitDate) {
        this.collectionInitDate = collectionInitDate;
    }

    @Override
    public void execute(LinkedHashSet<StudyGroup> collection) {
        System.out.println(
                "class: " + collection.getClass() + "\n"
                        + "init date: " + collectionInitDate + "\n"
                        + "size: " + collection.size() + "\n"
                        + "hash: " + collection.hashCode());
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
