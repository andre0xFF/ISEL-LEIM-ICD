package sessions;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;

    public void start() {
        try {
            serverSocket = new ServerSocket(Messenger.PORT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Messenger accept() {
        Socket socket;

        try {
            socket = serverSocket.accept();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Messenger messenger = new Messenger();
        messenger.start(socket);
        System.out.println("Server: Client accepted!");

        return messenger;
    }

    public void stop() throws IOException {
        serverSocket.close();
        System.out.println("Server: Stopped!");
    }
}
