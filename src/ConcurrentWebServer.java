import java.io.*;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConcurrentWebServer extends Thread {

    private Socket client;

    private Path htdocsFolder;

    /**
     * The class ConcurrentWebServer is a subclass of Thread and it allows to manage the
     */
    public ConcurrentWebServer(Socket client) {

        this(client, Paths.get("C:", "www"));

    }

    public ConcurrentWebServer(Socket client, Path htdocsFolder) {

        this.client = client;
        this.htdocsFolder = htdocsFolder;

    }

    @Override
    public void run() {

        if (client != null) {

            try {

                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
                String request = in.readLine();

                if (request != null && request.length() > 0) {

                    WebPages pages = new WebPages(in, out);

                    Path requestPath = Paths.get(getRequestFile(request.trim()));

                    try {

                        if (requestPath.toString().equalsIgnoreCase("")) {

                            pages.printHelloWorld();

                        } else {

                            pages.printHtmlFile(Paths.get(htdocsFolder.toString(), requestPath.toString()));

                        }

                    } catch (FileNotFoundException fnfe) {

                        pages.print404ErrorPage(Paths.get(htdocsFolder.toString(), requestPath.toString()).toString());

                    }

                }

                out.close();
                in.close();

            } catch (IOException ioe) {

                System.out.println("Unable to read and/or write from/to the client.");

            }

        }

    }

    public String getRequestFile(String request) {

        if (request.equalsIgnoreCase("GET / HTTP/1.1")) {
            return "";
        }

        String[] split = request.split(" ");
        return split[1];

    }
}
