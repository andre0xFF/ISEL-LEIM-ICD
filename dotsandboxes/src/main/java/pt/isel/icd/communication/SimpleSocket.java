package pt.isel.icd.communication;

import java.io.*;
import java.net.Socket;
import java.util.UUID;
import org.xml.sax.SAXException;
import pt.isel.icd.serialization.CommandSerializer;

/**
 * A simple wrapper around java.net.Socket with read and write functionality.
 * Uses DOM-based XML serialization (no Jackson).
 * Optionally validates incoming XML against an XSD schema.
 */
public class SimpleSocket implements Closeable {

    public static final int DEFAULT_PORT = 8000;
    public static final String DEFAULT_HOSTNAME = "localhost";

    private final Socket socket;
    private final PrintWriter writer;
    private final BufferedReader reader;
    private final UUID identifier = UUID.randomUUID();
    private final CommandSerializer commandSerializer;
    private final SchemaValidator schemaValidator;

    public SimpleSocket(CommandSerializer serializer) throws IOException {
        this(serializer, null, DEFAULT_HOSTNAME, DEFAULT_PORT);
    }

    public SimpleSocket(CommandSerializer serializer, SchemaValidator validator)
        throws IOException {
        this(serializer, validator, DEFAULT_HOSTNAME, DEFAULT_PORT);
    }

    public SimpleSocket(
        CommandSerializer serializer,
        SchemaValidator validator,
        int port
    ) throws IOException {
        this(serializer, validator, DEFAULT_HOSTNAME, port);
    }

    public SimpleSocket(
        CommandSerializer serializer,
        SchemaValidator validator,
        String hostname
    ) throws IOException {
        this(serializer, validator, hostname, DEFAULT_PORT);
    }

    public SimpleSocket(
        CommandSerializer serializer,
        SchemaValidator validator,
        String hostname,
        int port
    ) throws IOException {
        this(serializer, validator, new Socket(hostname, port));
    }

    public SimpleSocket(
        CommandSerializer serializer,
        SchemaValidator validator,
        Socket existingSocket
    ) throws IOException {
        commandSerializer = serializer;
        schemaValidator = validator;
        socket = existingSocket;
        writer = new PrintWriter(socket.getOutputStream(), true);
        reader = new BufferedReader(
            new InputStreamReader(socket.getInputStream())
        );
    }

    public void write(String message) {
        writer.println(message);
    }

    public void write(SimpleSocketCommand<?> command) {
        writer.println(commandSerializer.serialize(command));
    }

    public String readLine() throws IOException {
        return reader.readLine();
    }

    /**
     * Reads a command from the socket. If a {@link SchemaValidator} is present,
     * the raw XML is validated against the XSD schema before deserialization.
     *
     * @return the deserialized command, or {@code null} if the stream has ended
     * @throws IOException if an I/O error occurs
     */
    public SimpleSocketCommand<?> read() throws IOException, SAXException {
        String line = readLine();
        if (line == null) {
            return null;
        }

        if (schemaValidator != null) {
            schemaValidator.validate(line);
        }

        return commandSerializer.deserialize(line);
    }

    public boolean isConnected() {
        return socket.isConnected() && !socket.isClosed();
    }

    public boolean isClosed() {
        return socket.isClosed();
    }

    @Override
    public void close() throws IOException {
        reader.close();
        writer.close();
        socket.close();
    }

    public String hostname() {
        return socket.getInetAddress().getHostName();
    }

    public int port() {
        return socket.getPort();
    }

    public UUID identifier() {
        return identifier;
    }
}
