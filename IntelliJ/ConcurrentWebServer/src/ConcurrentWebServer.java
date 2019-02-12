import java.io.*;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * The ConcurrentWebServer class is a subclass of Thread and it manages requests coming from a client offering to
 * display a certain html page based on the request as the response
 */
public class ConcurrentWebServer extends Thread {

    /**
     * The client that is connected to this server
     */
    private Socket client;

    /**
     * The folder in which the server has to look for html files to offer e.g. www
     */
    private Path htdocsFolder;

    /**
     * The name of the file to open by default when no file is specified e.g. index.html
     */
    private String defaultFile;

    /**
     * Instantiates new objects of type ConcurrentWebServer with a default value for the field htdocsFolder
     *
     * @param client the client that is connected to this server
     */
    public ConcurrentWebServer(Socket client) {

        this(client, Paths.get("pages"), "index.html");

    }

    /**
     * Instantiates new objects of type ConcurrentWebServer allowing to specify a value for the fields client and
     * htdocsFolder
     *
     * @param client       the client that is connected to this server
     * @param htdocsFolder the folder in which the server has to look for html files to offer
     */
    public ConcurrentWebServer(Socket client, Path htdocsFolder) {

        this(client, htdocsFolder, "index.html");

    }

    public ConcurrentWebServer(Socket client, Path htdocsFolder, String defaultFile) {

        this.defaultFile = defaultFile;
        this.client = client;
        this.htdocsFolder = htdocsFolder;

    }

    /**
     * Reads the client's request and replies with an appropriate html page
     */
    @Override
    public void run() {

        if (client != null) {

            try {

                //Get the client's input and output streams
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

                //Read the client's request
                String request = in.readLine();

                //Create a new object of type ClientResponseManager
                ClientResponseManager pages = new ClientResponseManager(client.getOutputStream());

                //If the request exists, meaning that the client actually sent something and is valid
                if (request != null && request.length() > 0 && request.startsWith("GET") && request.endsWith("HTTP/1.1")) {

                    //Reads the resource the client is looking for (if any)
                    Path requestPath = Paths.get(getRequestFile(request.trim()));

                    try {

                        //If the requested resource is empty, change it to index.html
                        if (requestPath.toString().equals("")) {

                            requestPath = Paths.get(defaultFile);

                        }

                        //Print requested html page
                        pages.returnFile(Paths.get(htdocsFolder.toString(), requestPath.toString()));

                        //If the resource was not found
                    } catch (FileNotFoundException fnfe) {

                        //Print a 404 error page
                        pages.return404ErrorPage(/*Paths.get(htdocsFolder.toString(), requestPath.toString()).toString()*/);

                    }

                } else {

                    //If the request wasn't validated print a Bad Request page
                    pages.return400ErrorPage();

                }

                //Close connection resources
                out.close();
                in.close();
                client.close();

            } catch (IOException ioe) {

                //If there is a problem with the input/output from/to the client
                System.out.println("Unable to read and/or write from/to the client.");

            }

        }

    }

    /**
     * Gets the requested resource from a request string
     *
     * @param request the string from which to extrapolate the requested resource
     * @return a String containing the name of the requested resource
     */
    public String getRequestFile(String request) {

        if (request.equalsIgnoreCase("GET / HTTP/1.1")) {
            return "";
        }

        String[] split = request.split(" ");
        return split[1];

    }
}
