package pt.isel.icd.communication;

import pt.isel.icd.patterns.observer.Subscriber;

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

    /**
     * Creates a new socket. The hostname is set to "localhost" and the port is set to 8000.
     *
     * @throws IOException If an I/O error occurs when creating the socket.
     */
    public SimpleSocket() throws IOException {
        this(DEFAULT_HOSTNAME, DEFAULT_PORT);
    }

    /**
     * Creates a new socket. The hostname is set to "localhost".
     *
     * @param port The port.
     * @throws IOException If an I/O error occurs when creating the socket.
     */
    public SimpleSocket(int port) throws IOException {
        this(DEFAULT_HOSTNAME, port);
    }

    /**
     * Creates a new socket. The port is set to 8000.
     *
     * @param hostname The hostname.
     * @throws IOException If an I/O error occurs when creating the socket.
     */
    public SimpleSocket(String hostname) throws IOException {
        this(hostname, DEFAULT_PORT);
    }

    /**
     * Creates a new socket.
     *
     * @param hostname The hostname.
     * @param port     The port.
     * @throws IOException If an I/O error occurs when creating the socket.
     */
    public SimpleSocket(String hostname, int port) throws IOException {
        this(new Socket(hostname, port));
    }

    /**
     * Creates a new socket.
     *
     * @param existingSocket The socket.
     * @throws IOException If an I/O error occurs when creating the socket.
     */
    public SimpleSocket(Socket existingSocket) throws IOException {
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

    /**
     * Reads a message from the socket.
     *
     * @return The message read, or null if the end of the stream has been reached.
     * @throws IOException If an I/O error occurs when reading from the socket.
     */
    public synchronized String readLine() throws IOException {
        return reader.readLine();
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
