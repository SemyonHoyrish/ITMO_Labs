package itmo.semyonh.lab6;

import itmo.semyonh.lab6.commands.*;
import itmo.semyonh.lab6.helpers.Converter;
import itmo.semyonh.lab6.helpers.ConsoleReader;
import itmo.semyonh.lab6.net.Request;
import itmo.semyonh.lab6.net.Response;
import itmo.semyonh.lab6.net.ResponseType;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.*;

public class Client {
    private InetSocketAddress serverAddress;
    private DatagramChannel channel;
    private long commandID = 0;

    public Client(String host, int port) {
        serverAddress = new InetSocketAddress(host, port);
    }

    public void run() throws InterruptedException {
        try {
            channel = DatagramChannel.open();
            channel.configureBlocking(false);
        } catch (IOException e) {
            System.out.println("Cannot open channel: " + e.getMessage());
            return;
        }

        CommandManager commandManager = new CommandManager();

        ConsoleReader reader = new ConsoleReader();

        commandManager.registerCommand(new HelpCommand(commandManager));
        commandManager.registerCommand(new InfoCommand(new Date()));
        commandManager.registerCommand(new ShowCommand());
        commandManager.registerCommand(new AddCommand(reader));
        commandManager.registerCommand(new UpdateCommand(reader));
        commandManager.registerCommand(new RemoveByIdCommand());
        commandManager.registerCommand(new ClearCommand());
        commandManager.registerCommand(new ExecuteScriptCommand(reader, commandManager));
        commandManager.registerCommand(new ExitCommand());
        commandManager.registerCommand(new AddIfMinCommand(reader));
        commandManager.registerCommand(new RemoveGreaterCommand(reader));
        commandManager.registerCommand(new HistoryCommand(new ArrayList<>()));
        commandManager.registerCommand(new MaxByCoordsCommand());
        commandManager.registerCommand(new PrintUniqueShouldBeExpelledCommand());
        commandManager.registerCommand(new PrintFieldDescFormOfEducationCommand());


        var waitingForResponse = new HashMap<Long, Command>();

        Scanner scanner = new Scanner(System.in);
        ArrayDeque<Command> parsedCommands = new ArrayDeque<>();
        new Thread(() -> {
            while (true) {
                String current = scanner.nextLine();
                var cmd = commandManager.parseCommand(current);
                if (cmd == null) {
                    System.out.println("Unknown command");
                } else {
                    cmd.prepare();
                    parsedCommands.addLast(cmd);
                }
            }
        }).start();

        while (true) {
            ByteBuffer buff = ByteBuffer.allocate(64 * 1024);
            SocketAddress sa = null;

            try {
                sa = channel.receive(buff);
            } catch (IOException e) {
                System.out.println("Error receiving response: " + e.getMessage());
            }

            if (sa != null) {
                buff.flip();
                var bytes = new byte[buff.remaining()];
                buff.get(bytes);
                String content = new String(bytes);

                Response resp = Converter.responseFromJson(content);

                if (waitingForResponse.containsKey(resp.requestID())) {
                    var req = waitingForResponse.get(resp.requestID());
                    System.out.println("Response for '" + req.getName() + "' received, status: " + resp.status());
                    displayResponse(resp.type(), resp.response());
                    waitingForResponse.remove(resp.requestID());
                }
            } else if (!parsedCommands.isEmpty()) {
                var cmd = parsedCommands.removeFirst();
                var r = new Request(commandID++, cmd.getName(), Converter.commandToJson(cmd));
                try {
                    channel.send(ByteBuffer.wrap(Converter.requestToJson(r).getBytes()), serverAddress);
                    waitingForResponse.put(commandID-1, cmd);
                } catch (IOException e) {
                    System.out.println("Error sending request: " + e.getMessage());
                }
            }
        }
    }

    private void displayResponse(ResponseType t, String resp) {
        switch (t) {
            case PlainText -> {
                System.out.println(resp);
            }
            case Item -> {
                System.out.println(Converter.studyGroupFromJson(resp));
            }
            case ItemList -> {
                Converter.studyGroupListFromJson(resp).stream().forEach(System.out::println);
            }
        }
    }
}
