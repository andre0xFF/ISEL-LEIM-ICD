import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private final Socket socket;
    private final PrintWriter writer;
    private final BufferedReader reader;
    Message.Serializer serializer = new Message.Serializer();
    SchemaValidator validator = new SchemaValidator();

    public Client() {
        this(8000);
    }

    public Client(int port) {
        try {
            socket = new Socket("localhost", port);
            writer = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isConnected() {
        return socket.isConnected();
    }

    public void write(Message message) throws IOException, XPathExpressionException, ParserConfigurationException, SAXException {
        String content = serializer.serialize(message);
        boolean valid = validator.validate(content);

        if (valid) {
            writer.println(content);
        }
    }

    public void read() throws IOException, XPathExpressionException, ParserConfigurationException, SAXException {
        String content = reader.readLine();
        boolean valid = validator.validate(content);

        if (valid) {
            Message message = serializer.deserialize(content);
        }
    }
}
