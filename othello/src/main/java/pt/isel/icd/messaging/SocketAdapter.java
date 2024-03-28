package pt.isel.icd.messaging;

import pt.isel.icd.patterns.observer.Publisher;
import pt.isel.icd.patterns.observer.Subscriber;

import java.io.*;
import java.net.Socket;

/**
 * A simple wrapper around java.net.Socket with thread, read and write functionality.
 */
public class SocketAdapter implements Publisher<String>, Closeable, Runnable {
    
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
    public SocketAdapter() throws IOException {
        this(DEFAULT_HOSTNAME, DEFAULT_PORT);
    }

    /**
     * Creates a new socket. The hostname is set to "localhost".
     *
     * @param port The port.
     * @throws IOException If an I/O error occurs when creating the socket.
     */
    public SocketAdapter(int port) throws IOException {
        this(DEFAULT_HOSTNAME, port);
    }

    /**
     * Creates a new socket. The port is set to 8000.
     *
     * @param hostname The hostname.
     * @throws IOException If an I/O error occurs when creating the socket.
     */
    public SocketAdapter(String hostname) throws IOException {
        this(hostname, DEFAULT_PORT);
    }

    /**
     * Creates a new socket.
     *
     * @param hostname The hostname.
     * @param port     The port.
     * @throws IOException If an I/O error occurs when creating the socket.
     */
    public SocketAdapter(String hostname, int port) throws IOException {
        this(new Socket(hostname, port));
    }

    /**
     * Creates a new socket.
     *
     * @param socket The socket.
     * @throws IOException If an I/O error occurs when creating the socket.
     */
    public SocketAdapter(Socket socket) throws IOException {
        this.writer = new PrintWriter(socket.getOutputStream(), true);
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.socket = socket;
    }

    /**
     * Writes a message to the socket.
     *
     * @param message The message to be written.
     */
    public void write(String message) {
        this.writer.println(message);
    }

    /**
     * Reads a message from the socket.
     *
     * @return The message read, or null if the end of the stream has been reached.
     * @throws IOException If an I/O error occurs when reading from the socket.
     */
    public String read() throws IOException {
        return this.reader.readLine();
    }

    /**
     * Checks if the socket is connected.
     *
     * @return True if the socket is connected, false otherwise.
     */
    public boolean isConnected() {
        return this.socket.isConnected() && !this.socket.isClosed();
    }

    /**
     * Checks if the socket is closed.
     *
     * @return True if the socket is closed, false otherwise.
     */
    public boolean isClosed() {
        return this.socket.isClosed();
    }

    /**
     * Closes the socket.
     *
     * @throws IOException If an I/O error occurs when closing the socket.
     */
    @Override
    public void close() throws IOException {
        this.reader.close();
        this.writer.close();
        this.socket.close();
    }

    /**
     * Returns the hostname.
     *
     * @return The hostname.
     */
    public String hostname() {
        return this.socket.getInetAddress().getHostName();
    }

    /**
     * Returns the port.
     *
     * @return The port.
     */
    public int port() {
        return this.socket.getPort();
    }

    @Override
    public void run() {
        try {
            while (!this.socket.isClosed()) {
                String message = read();

                // The end of the stream has been reached.
                if (message == null) {
                    close();
                    break;
                }

                // Subscriber may have unsubscribed while reading a message.
                if (this.subscriber != null) {
                    this.lastReadMessage = message;
                    this.publish();
                }
            }
        } catch (IOException e) {
            // TODO
            // e.printStackTrace();
        }
    }

    @Override
    public void subscribe(Subscriber<String> subscriber) {
        this.subscriber = subscriber;

        if (this.thread != null) {
            return;
        }

        this.thread = new Thread(this);
        this.thread.start();
    }

    @Override
    public void unsubscribe(Subscriber<String> subscriber) {
        this.subscriber = null;
    }

    @Override
    public void publish() {
        this.subscriber.update(this.lastReadMessage);
    }
}
