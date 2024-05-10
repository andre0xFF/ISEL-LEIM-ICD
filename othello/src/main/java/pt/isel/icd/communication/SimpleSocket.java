package pt.isel.icd.communication;

import com.fasterxml.jackson.core.JsonProcessingException;
import pt.isel.icd.patterns.command.Receiver;
import pt.isel.icd.serialization.Serializer;

import java.io.*;
import java.net.Socket;
import java.util.UUID;

/**
 * A simple wrapper around java.net.Socket with read and write functionality.
 */
public class SimpleSocket implements Closeable {
    public static final int DEFAULT_PORT = 8000;
    public static final String DEFAULT_HOSTNAME = "localhost";
    private final Socket socket;
    private final PrintWriter writer;
    private final BufferedReader reader;
    private final UUID identifier = UUID.randomUUID();
    private final Serializer serializer;

    /**
     * Creates a new socket.
     *
     * @throws IOException If an I/O error occurs when creating the socket.
     */
    public SimpleSocket(Serializer existingSerializer) throws IOException {
        this(existingSerializer, DEFAULT_HOSTNAME, DEFAULT_PORT);
    }

    /**
     * Creates a new socket.
     *
     * @param port The port.
     * @throws IOException If an I/O error occurs when creating the socket.
     */
    public SimpleSocket(Serializer existingSerializer, int port) throws IOException {
        this(existingSerializer, DEFAULT_HOSTNAME, port);
    }

    /**
     * Creates a new socket.
     *
     * @param hostname The hostname.
     * @throws IOException If an I/O error occurs when creating the socket.
     */
    public SimpleSocket(Serializer existingSerializer, String hostname) throws IOException {
        this(existingSerializer, hostname, DEFAULT_PORT);
    }

    /**
     * Creates a new socket.
     *
     * @param hostname The hostname.
     * @param port     The port.
     * @throws IOException If an I/O error occurs when creating the socket.
     */
    public SimpleSocket(Serializer existingSerializer, String hostname, int port) throws IOException {
        this(existingSerializer, new Socket(hostname, port));
    }

    /**
     * Creates a new socket.
     *
     * @param existingSocket The socket.
     * @throws IOException If an I/O error occurs when creating the socket.
     */
    public SimpleSocket(Serializer existingSerializer, Socket existingSocket) throws IOException {
        serializer = existingSerializer;
        socket = existingSocket;
        writer = new PrintWriter(socket.getOutputStream(), true);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    /**
     * Writes a message to the socket.
     *
     * @param message The message to be written.
     */
    public void write(String message) {
        writer.println(message);
    }

    public void write(SimpleSocketCommand<?> command) throws JsonProcessingException {
        writer.println(serializer.serialize(command));
    }

    /**
     * Reads a message from the socket.
     *
     * @return The message read, or null if the end of the stream has been reached.
     * @throws IOException If an I/O error occurs when reading from the socket.
     */
    public String readLine() throws IOException {
        return reader.readLine();
    }

    public SimpleSocketCommand<Receiver> read() throws IOException {
        String line = readLine();

        if (line == null) {
            // The end of the stream has been reached.
            return null;
        }

        // TODO validate schema.

        return serializer.deserialize(line, SimpleSocketCommand.class);
    }

    /**
     * Checks if the socket is connected.
     *
     * @return True if the socket is connected, false otherwise.
     */
    public boolean isConnected() {
        return socket.isConnected() && !socket.isClosed();
    }

    /**
     * Checks if the socket is closed.
     *
     * @return True if the socket is closed, false otherwise.
     */
    public boolean isClosed() {
        return socket.isClosed();
    }

    /**
     * Closes the socket.
     *
     * @throws IOException If an I/O error occurs when closing the socket.
     */
    @Override
    public void close() throws IOException {
        socket.close();
        writer.close();
        reader.close();
    }

    /**
     * Returns the hostname.
     *
     * @return The hostname.
     */
    public String hostname() {
        return socket.getInetAddress().getHostName();
    }

    /**
     * Returns the port.
     *
     * @return The port.
     */
    public int port() {
        return socket.getPort();
    }

    public UUID identifier() {
        return identifier;
    }
}
