import messages.Message;
import org.xml.sax.SAXException;
import schemas.SchemaValidator;
import network.Socket;

import java.io.IOException;

public class Client {
    private final Socket socket;
    Message.XMLSerializer XMLSerializer = new Message.XMLSerializer();
    SchemaValidator validator = new SchemaValidator();

    public Client() throws IOException {
        this(Socket.DEFAULT_HOSTNAME, Socket.DEFAULT_PORT);
    }

    public Client(int port) throws IOException {
        this(Socket.DEFAULT_HOSTNAME, port);
    }

    public Client(String hostname) throws IOException {
        this(hostname, Socket.DEFAULT_PORT);
    }

    public Client(String hostname, int port) throws IOException {
        socket = new Socket(hostname, port);
    }

    public boolean isConnected() {
        return socket.isConnected();
    }

    public void write(Message message) throws IOException, SAXException {
        String content = XMLSerializer.serialize(message);
        validator.validate(content);

        socket.write(content);
    }

    public Message read() throws IOException, SAXException {
        String content = socket.read();
        validator.validate(content);

        return XMLSerializer.deserialize(content);
    }

    public void close() throws IOException {
        socket.close();
    }
}
