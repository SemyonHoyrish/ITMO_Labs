package itmo.semyonh.lab5;

import itmo.semyonh.lab5.commands.*;
import itmo.semyonh.lab5.helpers.ConsoleReader;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        CollectionManager collectionManager = new CollectionManager();
        CommandManager commandManager = new CommandManager();

        String filename = System.getenv("DATA_FILENAME");
        if (filename != null && !filename.isEmpty()) {
            collectionManager.setFilename(filename);
        }
        try {
            collectionManager.readFile();
        } catch (FileNotFoundException e) {
            System.out.println("File '" + filename + "' was not found.");
        }


        ConsoleReader reader = new ConsoleReader();
        commandManager.registerCommand(new HelpCommand(commandManager));
        commandManager.registerCommand(new InfoCommand(collectionManager.getInitDate()));
        commandManager.registerCommand(new ShowCommand());
        commandManager.registerCommand(new AddCommand(reader));
        commandManager.registerCommand(new UpdateCommand(reader));
        commandManager.registerCommand(new RemoveByIdCommand());
        commandManager.registerCommand(new ClearCommand());
        commandManager.registerCommand(new SaveCommand());
        commandManager.registerCommand(new ExecuteScriptCommand(reader, commandManager));
        commandManager.registerCommand(new ExitCommand());
        commandManager.registerCommand(new AddIfMinCommand(reader));
        commandManager.registerCommand(new RemoveGreaterCommand(reader));
        commandManager.registerCommand(new HistoryCommand(collectionManager.getHistory()));
        commandManager.registerCommand(new MaxByCoordsCommand());
        commandManager.registerCommand(new PrintUniqueShouldBeExpelledCommand());
        commandManager.registerCommand(new PrintFieldDescFormOfEducationCommand());

        Scanner scanner = new Scanner(System.in);
        String current = "";
        while (true) {
            System.out.print("@> ");
            current = scanner.nextLine();

            var cmd = commandManager.parseCommand(current);
            if (cmd == null) {
                System.out.println("Unknown command");
            } else {
                collectionManager.executeCommand(cmd);
            }
        }
    }
}