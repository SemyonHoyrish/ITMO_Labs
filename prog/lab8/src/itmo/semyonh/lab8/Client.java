package itmo.semyonh.lab8;

import itmo.semyonh.lab8.commands.*;
import itmo.semyonh.lab8.helpers.Converter;
import itmo.semyonh.lab8.helpers.ConsoleReader;
import itmo.semyonh.lab8.net.Credentials;
import itmo.semyonh.lab8.net.Request;
import itmo.semyonh.lab8.net.Response;
import itmo.semyonh.lab8.net.ResponseType;

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
    private Credentials credentials;
    private boolean initialized = false;

    public Client(String host, int port) {
        serverAddress = new InetSocketAddress(host, port);
    }

    public boolean init() {
        try {
            channel = DatagramChannel.open();
            channel.configureBlocking(false);
        } catch (IOException e) {
            System.out.println("Cannot open channel: " + e.getMessage());
            return false;
        }
        initialized = true;
        return true;
    }

    public void run() throws InterruptedException {
        if (!initialized) {
            if (!init()) {
                return;
            }
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
        commandManager.registerCommand(new RegisterAccountCommand());


        var waitingForResponse = new HashMap<Long, Command>();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter your login: ");
        String login = scanner.nextLine();
        System.out.println("Please enter your password: ");
        String password = scanner.nextLine();
        credentials = new Credentials(login, password);

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
            var resp = recieve();

            if (resp != null) {
                if (waitingForResponse.containsKey(resp.requestID())) {
                    var req = waitingForResponse.get(resp.requestID());
                    System.out.println("Response for '" + req.getName() + "' received, status: " + resp.status());
                    displayResponse(resp.type(), resp.response());
                    waitingForResponse.remove(resp.requestID());
                }
            } else if (!parsedCommands.isEmpty()) {
                var cmd = parsedCommands.removeFirst();

                if (!splitExecuteCommand(cmd, waitingForResponse)) {
                    var r = new Request(commandID++, cmd.getName(), Converter.commandToJson(cmd), credentials);
                    if (send(r)) {
                        waitingForResponse.put(commandID-1, cmd);
                    }
                }

            }
        }
    }

    Response recieve() {
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
            return resp;
        }

        return null;
    }

    private boolean splitExecuteCommand(Command command, HashMap<Long, Command> wfr) {
        if (command.getClass() == ExecuteScriptCommand.class) {
            for (var c : ((ExecuteScriptCommand) command).commandsToExecute) {
                if (!splitExecuteCommand(c, wfr)) {
                    var rr = new Request(commandID++, c.getName(), Converter.commandToJson(c), credentials);
                    if (send(rr)) {
                        wfr.put(commandID - 1, c);
                    }
                }
            }
            return true;
        }
        return false;
    }

    boolean send(Request r) {
        try {
            channel.send(ByteBuffer.wrap(Converter.requestToJson(r).getBytes()), serverAddress);
            return true;
        } catch (IOException e) {
            System.out.println("Error sending request: " + e.getMessage());
        }
        return false;
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
