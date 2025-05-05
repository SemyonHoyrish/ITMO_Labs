package itmo.semyonh.lab7;

import com.google.gson.Gson;
import itmo.semyonh.lab7.commands.Command;
import itmo.semyonh.lab7.commands.CommandResult;
import itmo.semyonh.lab7.types.StudyGroup;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import java.io.*;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;


/**
 * The CollectionManager class is a central entity that controls the collection `data`,
 * additional data related to that (like `initData`),
 * and handles command execution, that affects underlying data or manager itself (data load/store to a file).
 */
public class CollectionManager {

    private LinkedHashSet<StudyGroup> data;
    private Date initDate;
    private List<Command> history;

    private ReentrantLock lock;
    public static int accountID = -1;

    /**
     * A default constructor to initialize components of the class.
     */
    public CollectionManager() {
        data = new LinkedHashSet<>();
        history = new ArrayList<>();
        initDate = new Date();
        lock = new ReentrantLock();
    }

    public void fill(Iterable<StudyGroup> entries) {
        lock.lock();
        for (var e : entries) {
            data.add(e);
        }
        lock.unlock();
    }

    /**
     * Executes commands on `data`,
     * also handles special cases (`save` command).
     *
     * @param c Command to execute
     */
    public CommandResult executeCommand(Command c, int accountID) {
        lock.lock();
        CollectionManager.accountID = accountID;
        history.add(c);

        var res = c.execute(data);
        CollectionManager.accountID = -1;
        lock.unlock();
        return res;
    }

    /**
     * Returns date of underlying collection initialization,
     * does not designed to be changed outside.
     *
     * @return Date object of collection initialization time.
     */
    public Date getInitDate() {
        return initDate;
    }

    /**
     * Returns list of commands history.
     * Currently unlimited (may change).
     *
     * @return List of Command in order of execution.
     */
    public List<Command> getHistory() {
        return history;
    }
}
