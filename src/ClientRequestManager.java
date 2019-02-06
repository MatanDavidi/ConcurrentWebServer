import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * The ClientRequestManager class contains useful methods to write resources to an output stream
 */
public class ClientRequestManager {

    /**
     * The writer to use to write to the output stream
     */
    private BufferedWriter out;

    /**
     *
     * @param out
     */
    public ClientRequestManager(OutputStream out) {

        this(new BufferedWriter(new OutputStreamWriter(out)));

    }

    public ClientRequestManager(BufferedWriter out) {

        this.out = out;

    }

    public void returnIndex() throws IOException {

//        returnHttpHeader();
//
//        out.write("<head>");
//        out.write("<title>IT WORKS!</title>");
//        out.write("</head>");
//        out.write("<body>");
//        out.write("<h1>IT WORKS!</h1>");
//        out.write("</body>");
        returnFile(Paths.get("pages", "index.html"));

    }

    public void return404ErrorPage(/*String file*/) throws IOException {

        //HTTP Header
//        out.write("HTTP/1.1 404 Not Found\r\n");
//        out.write("\r\n");
//        out.write("<head>");
//        out.write("<title>Error 404</title>");
//        out.write("</head>");
//        out.write("<body>");
//        out.write("<h1>Error 404 - File not found</h1>");
//        out.write("<p>Resource <code>" + file + "</code> could not be found</p>");
//        out.write("</body>");
        returnFile(Paths.get("pages", "errors", "4xx", "404.html"));

    }

    private void printHttpHeader() throws IOException {

        out.write("HTTP/1.1 200 OK\r\n");
        out.write("\r\n");


    }

    public void returnFile(Path url) throws IOException {

        if (Files.exists(url) && !Files.notExists(url)) {

            List<String> lines = Files.readAllLines(url);

            for (String line : lines) {

                out.write(line);

            }

        } else {

            throw new FileNotFoundException("Unable to find file " + url.toString());

        }

    }

}
