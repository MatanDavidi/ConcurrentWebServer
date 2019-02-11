import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * The ClientResponseManager class contains useful methods to write resources to an output stream.
 */
public class ClientResponseManager {

    /**
     * The writer to use to write to the output stream.
     */
    private OutputStream out;

    /**
     * Instantiates new objects of type ClientResponseManager.
     *
     * @param out the output stream to write to.
     */
    public ClientResponseManager(OutputStream out) {

        this.out = out;

    }

    /**
     * Writes a 400 error page to the output stream.
     *
     * @throws IOException in case there's an error while writing to the output stream.
     */
    public void return400ErrorPage() throws IOException {

        returnFile(Paths.get("pages", "errors", "4xx", "400.html"));

    }

    /**
     * Writes a 404 error page to the output stream.
     *
     * @throws IOException in case there's an error while writing to the output stream.
     */
    public void return404ErrorPage(/*String file*/) throws IOException {

        returnFile(Paths.get("pages", "errors", "4xx", "404.html"));

    }

    /**
     * Writes the HTTP header to the output stream.
     *
     * @throws IOException in case there's an error while writing to the output stream.
     */
    private void returnHttpHeader() throws IOException {

        returnHttpHeader("HTTP/1.1 200 OK\r\n", "");


    }

    /**
     * Writes the HTTP header to the output stream allowing to specify a certain file type.
     *
     * @param fileType the type of the file to write to the output stream.
     * @throws IOException in case there's an error while writing to the output streams.
     */
    private void returnHttpHeader(String fileType) throws IOException {

        returnHttpHeader("HTTP/1.1 200 OK", fileType);


    }

    private void returnHttpHeader(String response, String fileType) throws IOException {

        if (!response.endsWith("\r\n")) {
            response += "\r\n";
        }

        if (!fileType.equals("") && !fileType.endsWith("\r\n")) {
            fileType += "\r\n";
        }

        out.write(response.getBytes());
        out.write(("Content-Type: " + fileType).getBytes());
        out.write("\r\n".getBytes());

    }

    /**
     * Writes the contents of a file to the output stream.
     *
     * @param url the path to the file of which to write the contents.
     * @throws IOException in case there's an error while reading the file's contents or writing to the output stream.
     */
    public void returnFile(Path url) throws IOException {

        //Check if the file exists
        if (Files.exists(url) && !Files.notExists(url)) {

            //Write a different file type depending on the file's extension
            String lowerUrl = url.toString().toLowerCase();
            String fileType = getFileType(lowerUrl);

            //Write the HTTP header e.g. HTTP/1.1 200 OK
            returnHttpHeader(fileType);

            //Read the file's data
            byte[] fileContent = Files.readAllBytes(url);

            //Send the data to the client
            out.write(fileContent);

        } else {

            //If the file was not found, throw a FileNotFoundException
            throw new FileNotFoundException("Unable to find file " + url.toString());

        }

    }

    private String getFileType(String lowerUrl) {

        String re = "";

        if (lowerUrl.endsWith(".html")) {

            re = "text/html";

        } else if (lowerUrl.endsWith(".css")) {

            re = "text/css";

        } else if (lowerUrl.endsWith(".js")) {

            re = "application/javascript";

        } else if (lowerUrl.endsWith(".jpg") || lowerUrl.endsWith(".jpeg")) {

            re = "image/jpeg";

        } else if (lowerUrl.endsWith(".png")) {

            re = "image/png";

        } else if (lowerUrl.endsWith(".bmp")) {

            re = "image/bmp";

        }

        return re;

    }

}
