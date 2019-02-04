import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Paths;

public class ConcurrentServerManager {

    /**
     * Metodo
     *
     * @param args
     */
    public static void main(String[] args) {

        final int PORT = 80;

        try {

            ServerSocket server = new ServerSocket(PORT);
            System.out.println("Listening on port " + PORT);

            while (true) {

                Socket client = server.accept();
                System.out.println("Connection accepted from " + client.getRemoteSocketAddress());
                (new ConcurrentWebServer(client, Paths.get("C:", "www"))).start();

            }

        } catch (IOException ioe) {

            System.out.println("Could not bind port " + PORT + " to a server");

        }

    }

}
