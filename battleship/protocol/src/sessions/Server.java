package sessions;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;

    public void start() {
        try {
            serverSocket = new ServerSocket(Communication.PORT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Communication accept() {
        Socket socket;

        try {
            socket = serverSocket.accept();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Communication communication = new Communication();
        communication.start(socket);
        System.out.println("Server: Client accepted!");

        return communication;
    }

    public void stop() throws IOException {
        serverSocket.close();
        System.out.println("Server: Stopped!");
    }
}
