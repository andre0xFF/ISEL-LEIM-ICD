package network;

import network.messages.Message;
import network.socket.Listener;
import org.xml.sax.SAXException;
import schemas.SchemaValidator;
import network.socket.Socket;

import java.io.IOException;

/**
 * Represents a client.
 */
public class Client implements Listener<String> {
    private final Socket socket;
    private final Message.XMLSerializer XMLSerializer = new Message.XMLSerializer();
    private final SchemaValidator schemaValidator = new SchemaValidator();
    private Listener<Message> listener;

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
        this(new Socket(hostname, port));
    }

    public Client(Socket socket) {
        this.socket = socket;
    }

    /**
     * Checks if the client is connected.
     * @return True if the client is connected, false otherwise.
     */
    public boolean isConnected() {
        return this.socket.isConnected();
    }

    /**
     * Writes a message.
     * @param message The message to be written.
     * @throws IOException If an I/O error occurs when writing.
     * @throws SAXException If an error occurs when validating the message.
     */
    public void write(Message message) throws IOException, SAXException {
        String content = XMLSerializer.serialize(message);
        schemaValidator.validate(content);

        socket.write(content);
    }

    /**
     * Reads a message.
     * @return The message read.
     * @throws IOException If an I/O error occurs when reading.
     * @throws SAXException If an error occurs when validating the message.
     */
    public Message read() throws IOException, SAXException {
        return read(socket.read());
    }

    private Message read(String content) throws IOException, SAXException {
        schemaValidator.validate(content);

        return XMLSerializer.deserialize(content);
    }

    /**
     * Closes the client.
     * @throws IOException If an I/O error occurs when closing.
     */
    public void close() throws IOException {
        socket.close();
    }

    /**
     * Called when a message is received.
     *
     * @param content The message.
     */
    @Override
    public void onMessage(String content) {
        try {
            Message message = read(content);
            this.listener.onMessage(message);
        } catch (IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    public void listen(Listener<Message> listener) {
        this.socket.listen(this);
        this.listener = listener;
    }
}
