import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import commands.Command;
import types.StudyGroup;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import java.io.*;
import java.util.*;

public class CollectionManager {

    private LinkedHashSet<StudyGroup> data;
    private Date initDate;
    private List<Command> history;
    private String filename = "test.json";


    public CollectionManager() {
        data = new LinkedHashSet<>();
        history = new ArrayList<>();
        initDate = new Date();
    }

    public void executeCommand(Command c) {
        history.add(c);

        // handle special commands
        switch (c.getName()) {
            case "save":
                try {
                    writeFile();
                } catch (IOException e) {
                    System.out.println("Cannot write file: " + e.getMessage());
                }
                break;

            default:
                c.execute(data);
                break;
        }
    }

    public Date getInitDate() {
        return initDate;
    }

    public List<Command> getHistory() {
        return history;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void writeFile() throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filename));

        Gson gson = new Gson();
        var result = gson.toJson(data);

//        System.out.println(result);

        writer.write(result);

        writer.flush();
        writer.close();
    }

    public void readFile() throws FileNotFoundException {
        InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));

        Gson gson = new Gson();
        Type type = new TypeToken<LinkedHashSet<StudyGroup>>() {}.getType();
        data = gson.fromJson(reader, type);
    }
}
