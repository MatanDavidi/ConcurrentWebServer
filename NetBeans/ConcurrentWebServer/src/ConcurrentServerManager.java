
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The ConcurrentServerManager class manages simultaneous clients connecting to
 * the server by creating a unique thread for each client
 */
public class ConcurrentServerManager {

    /**
     * Method to verify the correct functioning of the program
     *
     * @param args the arguments passed via command line
     */
    public static void main(String[] args) {

        final int PORT = 80;

        try {

            //Create the server
            ServerSocket server = new ServerSocket(PORT);
            System.out.println("Listening on port " + PORT);

            while (true) {

                //Accept a connection from the client
                Socket client = server.accept();
                System.out.println("Connection accepted from " + client.getRemoteSocketAddress());

                //Create a server thread
                (new ConcurrentWebServer(client)).start();

            }
            //If there's a problem with the server's creation
        } catch (IOException ioe) {

            System.out.println("Could not bind port " + PORT + " to a server");

        }

    }

}
