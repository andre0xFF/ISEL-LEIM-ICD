package pt.isel.icd.communication;

import pt.isel.icd.patterns.observer.Publisher;
import pt.isel.icd.patterns.observer.Subscriber;

import java.io.*;
import java.net.Socket;

/**
 * A simple wrapper around java.net.Socket with thread, read and write functionality.
 */
public class SocketFacade implements Publisher<String>, Closeable, Runnable {

    public static final int DEFAULT_PORT = 8000;
    public static final String DEFAULT_HOSTNAME = "localhost";
    private final Socket socket;
    private final PrintWriter writer;
    private final BufferedReader reader;
    private Subscriber<String> subscriber;
    private Thread thread;
    private String lastReadMessage;

    /**
     * Creates a new socket. The hostname is set to "localhost" and the port is set to 8000.
     *
     * @throws IOException If an I/O error occurs when creating the socket.
     */
    public SocketFacade() throws IOException {
        this(DEFAULT_HOSTNAME, DEFAULT_PORT);
    }

    /**
     * Creates a new socket. The hostname is set to "localhost".
     *
     * @param port The port.
     * @throws IOException If an I/O error occurs when creating the socket.
     */
    public SocketFacade(int port) throws IOException {
        this(DEFAULT_HOSTNAME, port);
    }

    /**
     * Creates a new socket. The port is set to 8000.
     *
     * @param hostname The hostname.
     * @throws IOException If an I/O error occurs when creating the socket.
     */
    public SocketFacade(String hostname) throws IOException {
        this(hostname, DEFAULT_PORT);
    }

    /**
     * Creates a new socket.
     *
     * @param hostname The hostname.
     * @param port     The port.
     * @throws IOException If an I/O error occurs when creating the socket.
     */
    public SocketFacade(String hostname, int port) throws IOException {
        this(new Socket(hostname, port));
    }

    /**
     * Creates a new socket.
     *
     * @param existingSocket The socket.
     * @throws IOException If an I/O error occurs when creating the socket.
     */
    public SocketFacade(Socket existingSocket) throws IOException {
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
    public String read() throws IOException {
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


    /**
     * Reads messages from the socket, and publishes them to the subscriber.
     */
    @Override
    public void run() {
        try {
            while (!socket.isClosed()) {
                String message = read();

                // The end of the stream has been reached.
                if (message == null) {
                    close();
                    break;
                }

                // Subscriber may have unsubscribed while reading a message.
                if (subscriber != null) {
                    lastReadMessage = message;
                    publish();
                }
            }
        } catch (IOException e) {
            // TODO
            // e.printStackTrace();
        }
    }

    /**
     * Starts reading messages from the socket in a separate thread, and subscribes the subscriber to the messages.
     *
     * @param newSubscriber The new subscriber.
     */
    @Override
    public void subscribe(Subscriber<String> newSubscriber) {
        subscriber = newSubscriber;

        if (thread != null) {
            return;
        }

        thread = new Thread(this);
        thread.start();
    }

    /**
     * Unsubscribes the subscriber from the messages.
     *
     * @param subscriber The subscriber.
     */
    @Override
    public void unsubscribe(Subscriber<String> subscriber) {
        subscriber = null;
    }

    /**
     * Publishes the last read message to the subscriber.
     */
    @Override
    public void publish() {
        subscriber.update(lastReadMessage);
    }
}
