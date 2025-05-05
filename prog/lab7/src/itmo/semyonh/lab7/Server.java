package itmo.semyonh.lab7;

import itmo.semyonh.lab7.commands.*;
import itmo.semyonh.lab7.helpers.ConsoleReader;
import itmo.semyonh.lab7.helpers.Converter;
import itmo.semyonh.lab7.net.Request;
import itmo.semyonh.lab7.net.Response;
import itmo.semyonh.lab7.net.ResponseType;
import itmo.semyonh.lab7.net.Status;
import itmo.semyonh.lab7.types.StudyGroup;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server {
    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    private int port;
    private DatagramSocket socket;

    private CommandManager commandManager;
    private CollectionManager collectionManager;

    private ExecutorService readPool;
    private ExecutorService procPool;
    private ExecutorService sendPool;


    public Server(int port) {
        logger.info("Set server port to " + port);
        this.port = port;

        String filename = System.getenv("DATA_FILENAME");
        if (filename != null && !filename.isEmpty()) {
            collectionManager.setFilename(filename);
        } else {
            logger.info("Filename was not specified, using default one");
        }

        int READ_THREADS = System.getenv("READ_THREADS") != null
                ? Integer.parseInt(System.getenv("READ_THREADS"))
                : 10;

        int SEND_THREADS = System.getenv("SEND_THREADS") != null
                ? Integer.parseInt(System.getenv("SEND_THREADS"))
                : 10;


        readPool = Executors.newFixedThreadPool(READ_THREADS);
        procPool = Executors.newCachedThreadPool();
        sendPool = Executors.newFixedThreadPool(SEND_THREADS);
    }

    public void run() {
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }

        collectionManager = new CollectionManager();
        commandManager = new CommandManager();

        try {
            collectionManager.readFile();
        } catch (FileNotFoundException e) {
            logger.warn("File '" + collectionManager.getFilename() + "' was not found.");
//            System.out.println("File '" + collectionManager.getFilename() + "' was not found.");
        }

        ConsoleReader reader = null;
        commandManager.registerCommand(new HelpCommand(commandManager));
        commandManager.registerCommand(new InfoCommand(collectionManager.getInitDate()));
        commandManager.registerCommand(new ShowCommand());
        commandManager.registerCommand(new AddCommand(reader));
        commandManager.registerCommand(new UpdateCommand(reader));
        commandManager.registerCommand(new RemoveByIdCommand());
        commandManager.registerCommand(new ClearCommand());
        commandManager.registerCommand(new ExecuteScriptCommand(reader, commandManager));
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
            logger.info("Waiting for a packet");
            socket.receive(packet);
        } catch (IOException e) {
            logger.error("Cannot receive packet: " + e.getMessage());
//            System.out.println("Cannot receive packet: " + e.getMessage());
        }

        readPool.execute(() -> {
            var contentBytes = new byte[packet.getLength()];
            System.arraycopy(packet.getData(), 0, contentBytes, 0, packet.getLength());
            String content = new String(contentBytes);

            logger.info("Deserializing the request");
            Request r = Converter.requestFromJson(content);

            procPool.execute(() -> {
                var registered = commandManager.getCommands();
                if (!registered.containsKey(r.commandName())) {
                    logger.info("Unknown command from client: " + r.commandName());
//            System.out.println("Unknown command from client: " + r.commandName());
                    sendResponseUC(r.id(), packet.getAddress(), packet.getPort());
                }

                logger.info("Deserializing the command");
                Command cmd = Converter.commandFromJson(r.commandJson(), registered.get(r.commandName()).getClass());
                logger.info("Executing command " + cmd.getName());
                var result = collectionManager.executeCommand(cmd);

                logger.info("Constructing response based on command result");
                sendResponseFromResult(r.id(), packet.getAddress(), packet.getPort(), result);
            });
        });
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

        sendPool.execute(() -> {
            DatagramPacket packet = new DatagramPacket(bytes, bytes.length, host, port);
            try {
                logger.info("Sending a packet");
                socket.send(packet);
            } catch (IOException e) {
                logger.error("Cannot send packet: " + e.getMessage());
//            System.out.println("Cannot send packet: " + e.getMessage());
            }
        });
    }
}
