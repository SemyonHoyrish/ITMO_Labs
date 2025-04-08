import commands.AddCommand;
import commands.CommandManager;
import helpers.ConsoleReader;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CollectionManager collectionManager = new CollectionManager();
        CommandManager commandManager = new CommandManager();

        ConsoleReader reader = new ConsoleReader();
        commandManager.registerCommand(new AddCommand(reader));

        Scanner scanner = new Scanner(System.in);
        String current = "";
        while (!current.equals("exit")) {
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