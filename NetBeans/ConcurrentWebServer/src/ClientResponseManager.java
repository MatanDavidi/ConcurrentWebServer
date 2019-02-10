
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * The ClientResponseManager class contains useful methods to write resources to
 * an output stream.
 */
public class ClientResponseManager {

    /**
     * The writer to use to write to the output stream.
     */
    private OutputStream out;

    /**
     * Istantiates new objects of type ClientResponseManager.
     *
     * @param out the output stream to write to.
     */
    public ClientResponseManager(OutputStream out) {

        this.out = out;

    }

    /**
     * Writes a 400 error page to the output stream.
     *
     * @throws IOException in case there's an error while writing to the output
     * stream.
     */
    public void return400ErrorPage() throws IOException {

        returnFile(Paths.get("/pages", "errors", "4xx", "400.html"));

    }

    /**
     * Writes a 404 error page to the output stream.
     *
     * @throws IOException in case there's an error while writing to the output
     * stream.
     */
    public void return404ErrorPage(/*String file*/) throws IOException {
        
        returnFile(Paths.get("/pages", "errors", "4xx", "404.html"));

    }

    /**
     * Writes the HTTP header to the output stream.
     *
     * @throws IOException in case there's an error while writing to the output
     * stream.
     */
    private void returnHttpHeader() throws IOException {

        out.write("HTTP/1.1 200 OK\r\n".getBytes());
        out.write("\r\n".getBytes());

    }

    /**
     * Writes the HTTP header to the output stream allowing to specify a certain
     * file type.
     *
     * @param fileType the type of the file to write to the output stream.
     * @throws IOException in case there's an error while writing to the output
     * streams.
     */
    private void returnHttpHeader(String fileType) throws IOException {

        out.write("HTTP/1.1 200 OK\r\n".getBytes());
        out.write((fileType + "\r\n").getBytes());
        out.write("\r\n".getBytes());

    }

    /**
     * Writes the contents of a file to the output stream.
     *
     * @param url the path to the file of which to write the contents.
     * @throws IOException in case there's an error while reading the file's
     * contents or writing to the output stream.
     */
    public void returnFile(Path url) throws IOException {

        //Check if the file exists
        if (Files.exists(url) && !Files.notExists(url)) {

            //Write a different file type depending on the file's extension
            String lowerUrl = url.toString().toLowerCase();
            if (lowerUrl.endsWith(".html")) {

                returnHttpHeader("text/html");

            } else if (lowerUrl.endsWith(".css")) {

                returnHttpHeader("text/css");

            } else if (lowerUrl.endsWith(".js")) {

                returnHttpHeader("application/javascript");

            } else {

                returnHttpHeader();

            }

            //Read the file's data
            byte[] fileContent = Files.readAllBytes(url);

            //Send the data to the client
            out.write(fileContent);

        } else {

            //If the file was not found, throw a FileNotFoundException
            throw new FileNotFoundException("Unable to find file " + url.toString());

        }

    }

}
