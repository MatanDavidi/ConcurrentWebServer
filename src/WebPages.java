import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class WebPages {

    private BufferedReader in;
    private BufferedWriter out;

    public WebPages(BufferedReader in, BufferedWriter out) {

        this.in = in;
        this.out = out;

    }

    public void printHelloWorld() throws IOException {

        printHttpHeader();

        out.write("<head>");
        out.write("<title>IT WORKS!</title>");
        out.write("</head>");
        out.write("<body>");
        out.write("<h1>IT WORKS!</h1>");
        out.write("</body>");

    }

    public void print404ErrorPage(String file) throws IOException {

        //HTTP Header
        out.write("HTTP/1.1 404 Not Found\r\n");
        out.write("\r\n");
        out.write("<head>");
        out.write("<title>Error 404</title>");
        out.write("</head>");
        out.write("<body>");
        out.write("<h1>Error 404 - File not found</h1>");
        out.write("<p>Could not find file <code>" + file + "</code></p>");
        out.write("</body>");

    }

    private void printHttpHeader() throws IOException {

        out.write("HTTP/1.1 200 OK\r\n");
        out.write("\r\n");

    }

    public void printHtmlFile(Path url) throws IOException {

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
