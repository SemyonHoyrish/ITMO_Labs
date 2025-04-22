package itmo.semyonh.lab6;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {

        var mode = System.getenv("MODE");
        var host = System.getenv("HOST");
        var portString = System.getenv("PORT");

        var port = Integer.parseInt(portString);

        if (mode != null && mode.equals("server")) {
            var server = new Server(port);
            server.run();
        } else {
            var client = new Client(host, port);
            try {
                client.run();
            } catch (InterruptedException e) {
                System.out.println("Client was interrupted");
            }
        }

    }
}