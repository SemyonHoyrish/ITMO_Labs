package itmo.semyonh.lab8;


public class Main {
    public static void main(String[] args) {
        var mode = System.getenv("MODE");
        var host = System.getenv("HOST");
        var portString = System.getenv("PORT");

        var port = Integer.parseInt(portString);

        if (mode != null && mode.equals("server")) {
            var server = new Server(port);
            server.run();
        } else if (mode != null && mode.equals("cli")) {
            var client = new Client(host, port);
            try {
                client.run();
            } catch (InterruptedException e) {
                System.out.println("Client was interrupted");
            }
        } else {
            GUICLient.launch_gui();
        }
    }
}