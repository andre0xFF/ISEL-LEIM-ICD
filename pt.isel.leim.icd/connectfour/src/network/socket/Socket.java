package network.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * A simple wrapper around java.net.Socket with thread functionality.
 */
public class Socket implements java.io.Closeable, Runnable {
    /**
     * The default port.
     */
    public static final int DEFAULT_PORT = 8000;
    /**
     * The default hostname.
     */
    public static final String DEFAULT_HOSTNAME = "localhost";

    private final java.net.Socket socket;
    private final PrintWriter writer;
    private final BufferedReader reader;
    private Listener<String> listener;
    private Thread thread;

    /**
     * Creates a new socket. The hostname is set to "localhost" and the port is set to 8000.
     * @throws IOException If an I/O error occurs when creating the socket.
     */
    public Socket() throws IOException {
        this(DEFAULT_HOSTNAME, DEFAULT_PORT);
    }

    /**
     * Creates a new socket. The hostname is set to "localhost".
     * @param port The port.
     * @throws IOException If an I/O error occurs when creating the socket.
     */
    public Socket(int port) throws IOException {
        this(DEFAULT_HOSTNAME, port);
    }

    /**
     * Creates a new socket. The port is set to 8000.
     * @param hostname The hostname.
     * @throws IOException If an I/O error occurs when creating the socket.
     */
    public Socket(String hostname) throws IOException {
        this(hostname, DEFAULT_PORT);
    }

    /**
     * Creates a new socket.
     * @param hostname The hostname.
     * @param port The port.
     * @throws IOException If an I/O error occurs when creating the socket.
     */
    public Socket(String hostname, int port) throws IOException {
        this(new java.net.Socket(hostname, port));
    }

    /**
     * Creates a new socket.
     * @param socket The socket.
     * @throws IOException If an I/O error occurs when creating the socket.
     */
    public Socket(java.net.Socket socket) throws IOException {
        writer = new PrintWriter(socket.getOutputStream(), true);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        this.socket = socket;
    }

    /**
     * Writes a message to the socket.
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
     * @return True if the socket is connected, false otherwise.
     */
    public boolean isConnected() {
        return this.socket.isConnected() && !this.socket.isClosed();
    }

    /**
     * Checks if the socket is closed.
     * @return True if the socket is closed, false otherwise.
     */
    public boolean isClosed() {
        return this.socket.isClosed();
    }

    /**
     * Closes the socket.
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
     * @return The hostname.
     */
    public String hostname() {
        return this.socket.getInetAddress().getHostName();
    }

    /**
     * Returns the port.
     * @return The port.
     */
    public int port() {
        return this.socket.getPort();
    }

    /**
     * Starts listening for messages.
     * @param listener The listener to be called when a message is received.
     */
    public void listen(Listener<String> listener) {
        this.listener = listener;

        if (this.thread != null) {
            return;
        }
        
        this.thread = new Thread(this);
        this.thread.start();
    }

    /**
     * Stops listening for messages.
     */
    public void ignore() {
        this.listener = null;
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

                // The listener may have called ignore() while we were reading the message.
                if (this.listener != null) {
                    this.listener.onMessage(message);
                }
            }
        } catch (IOException e) {
            // TODO
            // e.printStackTrace();
        }
    }
}
