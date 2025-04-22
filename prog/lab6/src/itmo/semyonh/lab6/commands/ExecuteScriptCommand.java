package itmo.semyonh.lab6.commands;

import itmo.semyonh.lab6.helpers.ConsoleReader;
import itmo.semyonh.lab6.types.StudyGroup;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * ExecuteScriptCommand (execute_script file_name) reads content of the script file and
 * provide it as an input for the command parsing, and executes all commands.
 */
public class ExecuteScriptCommand implements Command {
    private String scriptName;
    private transient ConsoleReader reader;
    private transient CommandManager commandManager;
    private List<Command> commandsToExecute;

    private static ArrayDeque<String> scriptsInCall = new ArrayDeque<>();

    public ExecuteScriptCommand(ConsoleReader reader, CommandManager commandManager) {
        this.reader = reader;
        this.commandManager = commandManager;
        this.commandsToExecute = new ArrayList<>();
    }

    @Override
    public void prepare() {
        if (scriptName == null || scriptName.isEmpty()) {
            System.out.println(getName() + " command: scriptName cannot be null or empty");
            return;
        }

        if (scriptsInCall.contains(scriptName)) {
            System.out.println("Recursion in script " + scriptName);
            return;
        }

        Scanner sc;
        try {
            FileInputStream fs = new FileInputStream(scriptName);
            sc = new Scanner(fs);
        } catch (FileNotFoundException e) {
            System.out.println(getName() + " command: script " + scriptName + " not found");
            return;
        }

        scriptsInCall.addLast(scriptName);
        Scanner prevScanner = reader.getScanner();
        reader.setScanner(sc);

        while (sc.hasNext()) {
            String cmd_text = sc.nextLine();
            var cmd = commandManager.parseCommand(cmd_text);
            if (cmd == null) {
                System.out.println("'" + cmd_text + "' is not a command");
                continue;
            }
            cmd.prepare();
            commandsToExecute.add(cmd);
        }

        reader.setScanner(prevScanner);
        sc.close();
        var sname = scriptsInCall.removeLast();
        assert(sname.equals(scriptName));
    }

    @Override
    public CommandResult execute(LinkedHashSet<StudyGroup> collection) {
        List<CommandResult> results = new ArrayList<>();

        for (Command command : commandsToExecute) {
            var res = command.execute(collection);

            if (res.type() == CommandResultType.ResultList) {
                results.addAll((ArrayList<CommandResult>)res.result());
            } else {
                results.add(res);
            }
        }

        return new CommandResult(CommandResultType.ResultList, results);
    }

    @Override
    public Command with(String[] args) {
        ExecuteScriptCommand n = new ExecuteScriptCommand(reader, commandManager);
        n.scriptName = args[0];
        return n;
    }

    @Override
    public String getName() {
        return "execute_script";
    }

    @Override
    public String getDescription() {
        return "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.";
    }
}
