package pt.isel.icd.messaging;

import pt.isel.icd.messaging.messages.Message;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pt.isel.icd.patterns.observer.Publisher;
import pt.isel.icd.patterns.observer.Subscriber;
import pt.isel.icd.serialization.Serializer;
import pt.isel.icd.serialization.XMLSerializer;

/**
 * Represents a client.
 */
public class Client implements Publisher<Message>, Subscriber<String> {
    private final SocketFacade socket;
    private final Serializer<Message> serializer = new XMLSerializer<>();
    private final SchemaValidator schemaValidator = new SchemaValidator();
    private final Map<Class<? extends Message>, Subscriber<Message>> specificSubscribers = new HashMap<>();
    private final ArrayList<Subscriber<Message>> defaultSubscribers = new ArrayList<>();
    private Message lastReadMessage;

    public Client() throws IOException {
        this(SocketFacade.DEFAULT_HOSTNAME, SocketFacade.DEFAULT_PORT);
    }

    public Client(int port) throws IOException {
        this(SocketFacade.DEFAULT_HOSTNAME, port);
    }

    public Client(String hostname) throws IOException {
        this(hostname, SocketFacade.DEFAULT_PORT);
    }

    public Client(String hostname, int port) throws IOException {
        this(new SocketFacade(hostname, port));
    }

    public Client(SocketFacade socket) {
        this.socket = socket;

        socket.subscribe(this);
    }

    /**
     * Checks if the client is connected.
     *
     * @return True if the client is connected, false otherwise.
     */
    public boolean isConnected() {
        return socket.isConnected();
    }

    /**
     * Writes a message.
     *
     * @param message The message to be written.
     * @throws IOException  If an I/O error occurs when writing.
     * @throws SAXException If an error occurs when validating the message.
     */
    public void write(Message message) throws IOException, SAXException {
        String content = serializer.serialize(message);
        schemaValidator.validate(content);

        socket.write(content);
    }

    /**
     * Reads a message.
     *
     * @return The message read.
     * @throws IOException  If an I/O error occurs when reading.
     * @throws SAXException If an error occurs when validating the message.
     */
    public Message read() throws IOException, SAXException {
        return read(socket.read());
    }

    private Message read(String content) throws IOException, SAXException {
        schemaValidator.validate(content);

        return serializer.deserialize(content, Message.class);
    }

    /**
     * Closes the client.
     *
     * @throws IOException If an I/O error occurs when closing.
     */
    public void close() throws IOException {
        socket.close();
    }

    @Override
    public void subscribe(Subscriber<Message> subscriber) {
        defaultSubscribers.add(subscriber);
    }

    public void subscribe(Subscriber<Message> subscriber, Class<? extends Message> messageType) {
        specificSubscribers.put(messageType, subscriber);
    }

    @Override
    public void unsubscribe(Subscriber<Message> subscriber) {
        defaultSubscribers.remove(subscriber);
    }

    public void unsubscribe(Subscriber<Message> subscriber, Class<? extends Message> messageType) {
        specificSubscribers.remove(messageType);
    }

    @Override
    public void publish() {
        for (Subscriber<Message> subscriber : specificSubscribers) {
            if (lastReadMessage != null) {
                subscriber.update(lastReadMessage);
            }
        }
    }

    @Override
    public void update(String context) {
        lastReadMessage = read(context);

        publish();
    }
}
