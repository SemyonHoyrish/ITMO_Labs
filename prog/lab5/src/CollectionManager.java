import commands.Command;
import types.StudyGroup;

import java.util.ArrayDeque;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

public class CollectionManager {

    private LinkedHashSet<StudyGroup> data;
    private Date initDate;
    private List<Command> history;


    public CollectionManager() {
        data = new LinkedHashSet<>();
        initDate = new Date();
    }

    public void executeCommand(Command c) {
        history.add(c);
        c.execute(data);
    }

    public Date getInitDate() {
        return initDate;
    }

    public List<Command> getHistory() {
        return history;
    }
}
