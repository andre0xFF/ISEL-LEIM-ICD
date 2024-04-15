package pt.isel.icd.communication;

import org.xml.sax.SAXException;
import pt.isel.icd.communication.commands.Command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pt.isel.icd.patterns.observer.Subscriber;
import pt.isel.icd.serialization.XMLSerializer;

/**
 * Representation of a connection that has the ability to read from and write to a socket. It ensures the messages adhere to a certain schema and contains an inbuilt router to direct messages to the correct subscribers.
 */
public class Connection implements ConnectionPublisher, Subscriber<String> {
    private final SocketFacade socket;
    private final XMLSerializer<Command> serializer = new XMLSerializer<>();
    private final SchemaValidator schemaValidator = new SchemaValidator();
    private final Map<Class<? extends Command>, ConnectionSubscriber> connectionSubscribers = new HashMap<>();
    private final ArrayList<Subscriber<Command>> defaultSubscribers = new ArrayList<>();
    private Command lastReadCommand;
    private final static boolean VALIDATE_SCHEMAS = false;

    public Connection() throws IOException {
        this(SocketFacade.DEFAULT_HOSTNAME, SocketFacade.DEFAULT_PORT);
    }

    public Connection(int port) throws IOException {
        this(SocketFacade.DEFAULT_HOSTNAME, port);
    }

    public Connection(String hostname) throws IOException {
        this(hostname, SocketFacade.DEFAULT_PORT);
    }

    public Connection(String hostname, int port) throws IOException {
        this(new SocketFacade(hostname, port));
    }

    public Connection(SocketFacade existingSocket) {
        socket = existingSocket;

        existingSocket.subscribe(this);
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
     * Checks if the client is closed.
     *
     * @return True if the client is closed, false otherwise.
     */
    public boolean isClosed() {
        return socket.isClosed();
    }

    /**
     * Writes a message.
     *
     * @param command The message to be written.
     * @throws IOException If an I/O error occurs when writing.
     */
    public void write(Command command) throws IOException {
        String content = serializer.serialize(command);

        try {
            schemaValidator.validate(content);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }

        socket.write(content);
    }

    /**
     * Reads a message.
     *
     * @return The message read.
     * @throws IOException If an I/O error occurs when reading.
     */
    public Command read() throws IOException {
        return read(socket.read());
    }

    private Command read(String content) throws IOException {
        try {
            if (VALIDATE_SCHEMAS) {
                schemaValidator.validate(content);
            }
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }

        lastReadCommand = serializer.deserialize(content, Command.class);

        return lastReadCommand;
    }

    /**
     * Closes the client.
     *
     * @throws IOException If an I/O error occurs when closing.
     */
    public void close() throws IOException {
        socket.close();
    }

    /**
     * Subscribes a subscriber. Default subscribers are notified of all messages.
     *
     * @param subscriber The subscriber to be subscribed.
     */
    @Override
    public void subscribe(Subscriber<Command> subscriber) {
        defaultSubscribers.add(subscriber);
    }

    /**
     * Unsubscribes a subscriber.
     *
     * @param subscriber The subscriber to be unsubscribed.
     */
    @Override
    public void unsubscribe(Subscriber<Command> subscriber) {
        defaultSubscribers.remove(subscriber);
    }

    /**
     * Subscribes a connection subscriber. Connection subscribers are notified of messages of specific types.
     *
     * @param subscriber The subscriber to be subscribed.
     */
    @Override
    public void subscribe(ConnectionSubscriber subscriber) {
        for (Class<? extends Command> messageType : subscriber.commandTypes()) {
            connectionSubscribers.put(messageType, subscriber);
        }
    }

    /**
     * Unsubscribes a connection subscriber.
     *
     * @param subscriber The subscriber to be unsubscribed.
     */
    @Override
    public void unsubscribe(ConnectionSubscriber subscriber) {
        for (Class<? extends Command> messageType : subscriber.commandTypes()) {
            connectionSubscribers.remove(messageType);
        }
    }

    /**
     * Returns the total number of subscribers.
     *
     * @return The total number of subscribers.
     */
    @Override
    public int connectionSubscribersTotal() {
        return connectionSubscribers.size();
    }

    /**
     * Publishes the last read message.
     */
    @Override
    public void publish() {
        if (lastReadCommand == null) {
            return;
        }

        route(lastReadCommand);

        lastReadCommand = null;
    }

    private void route(Command command) {
        ConnectionSubscriber connectionSubscriber = connectionSubscribers.get(command.getClass());

        if (connectionSubscriber != null) {
            connectionSubscriber.update(command);
        }

        for (Subscriber<Command> subscribers : defaultSubscribers) {
            subscribers.update(command);
        }
    }

    /**
     * Connection update. Reads the message and publishes it.
     *
     * @param context The context.
     */
    @Override
    public void update(String context) {
        try {
            read(context);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        publish();
    }
}
