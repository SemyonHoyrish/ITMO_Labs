package itmo.semyonh.lab6;

import itmo.semyonh.lab6.commands.*;
import itmo.semyonh.lab6.helpers.ConsoleReader;
import itmo.semyonh.lab6.helpers.Converter;
import itmo.semyonh.lab6.net.Request;
import itmo.semyonh.lab6.net.Response;
import itmo.semyonh.lab6.net.ResponseType;
import itmo.semyonh.lab6.net.Status;
import itmo.semyonh.lab6.types.StudyGroup;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

public class Server {
    private int port;
    private DatagramSocket socket;

    private CommandManager commandManager;
    private CollectionManager collectionManager;


    public Server(int port) {
        this.port = port;
    }

    public void run() {
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }

        collectionManager = new CollectionManager();
        commandManager = new CommandManager();

        String filename = System.getenv("DATA_FILENAME");
        if (filename != null && !filename.isEmpty()) {
            collectionManager.setFilename(filename);
        }
        try {
            collectionManager.readFile();
        } catch (FileNotFoundException e) {
            System.out.println("File '" + collectionManager.getFilename() + "' was not found.");
        }

        ConsoleReader reader = null;
        commandManager.registerCommand(new HelpCommand(commandManager));
        commandManager.registerCommand(new InfoCommand(collectionManager.getInitDate()));
        commandManager.registerCommand(new ShowCommand());
        commandManager.registerCommand(new AddCommand(reader));
        commandManager.registerCommand(new UpdateCommand(reader));
        commandManager.registerCommand(new RemoveByIdCommand());
        commandManager.registerCommand(new ClearCommand());
        commandManager.registerCommand(new ExitCommand());
        commandManager.registerCommand(new AddIfMinCommand(reader));
        commandManager.registerCommand(new RemoveGreaterCommand(reader));
        commandManager.registerCommand(new HistoryCommand(collectionManager.getHistory()));
        commandManager.registerCommand(new MaxByCoordsCommand());
        commandManager.registerCommand(new PrintUniqueShouldBeExpelledCommand());
        commandManager.registerCommand(new PrintFieldDescFormOfEducationCommand());

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                collectionManager.writeFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));

        while (true) {
            read();
        }
    }

    private void read() {
        int size = 64 * 1024;
        var buff = new byte[size];
        var packet = new DatagramPacket(buff, size);
        try {
            socket.receive(packet);
        } catch (IOException e) {
            System.out.println("Cannot receive packet: " + e.getMessage());
        }
        var contentBytes = new byte[packet.getLength()];
        System.arraycopy(packet.getData(), 0, contentBytes, 0, packet.getLength());
        String content = new String(contentBytes);

        Request r = Converter.requestFromJson(content);

        var registered = commandManager.getCommands();
        if (!registered.containsKey(r.commandName())) {
            System.out.println("Unknown command from client: " + r.commandName());
            sendResponseUC(r.id(), packet.getAddress(), packet.getPort());
        }

        Command cmd = Converter.commandFromJson(r.commandJson(), registered.get(r.commandName()).getClass());
        var result = collectionManager.executeCommand(cmd);

        sendResponseFromResult(r.id(), packet.getAddress(), packet.getPort(), result);
    }

    private void sendResponseFromResult(long id, InetAddress host, int port, CommandResult result) {
        Response resp = null;

        if (result == null) {
            resp = new Response(id, Status.UNKNOWN_COMMAND, ResponseType.None, null);
        } else {
            switch (result.type()) {
                case None -> {
                    resp = new Response(id, Status.PROCESSED, ResponseType.None, null);
                }
                case Failed -> {
                    resp = new Response(id, Status.FAILED, ResponseType.PlainText, (String)result.result());
                }
                case PlainText -> {
                    resp = new Response(id, Status.PROCESSED, ResponseType.PlainText, (String)result.result());
                }
                case Item -> {
                    resp = new Response(id, Status.PROCESSED, ResponseType.Item, Converter.studyGroupToJson((StudyGroup) result.result()));
                }
                case ItemList -> {
                    resp = new Response(id, Status.PROCESSED, ResponseType.ItemList,
                            Converter.studyGroupListToJson(
                                    new ArrayList<>(((ArrayList<StudyGroup>) result.result())
                                            .stream()
                                            .sorted((a, b) -> a.getCoordinates().compareTo(b.getCoordinates()))
                                            .toList())
                            )
                    );
                }
                case ResultList -> {
                    for (var res : (ArrayList<CommandResult>) result.result()) {
                        sendResponseFromResult(id, host, port, res);
                    }
                    resp = new Response(0, Status.PROCESSED, ResponseType.None, null);
                }
            }
        }

        send(host, port, resp);
    }

    private void sendResponseUC(long id, InetAddress host, int port) {
        var resp = new Response(id, Status.UNKNOWN_COMMAND, ResponseType.None, null);
        send(host, port, resp);
    }
    
    private void send(InetAddress host, int port, Response resp) {
        var json = Converter.responseToJson(resp);
        var bytes = json.getBytes();

        DatagramPacket packet = new DatagramPacket(bytes, bytes.length, host, port);
        try {
            socket.send(packet);
        } catch (IOException e) {
            System.out.println("Cannot send packet: " + e.getMessage());
        }
    }

}
