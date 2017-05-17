package models;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public interface Server {

    // Fields
    public final static int DEFAULT_PORT = 5555;

    // Functions
    public static void execute(Gate gate) {
        Server.execute(Server.DEFAULT_PORT, gate);
    }

    public static boolean execute(int port, Gate gate) {
        try {
            ServerSocket socket = new ServerSocket(port);
            gate.execute(socket);
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    // Modules
    public interface InternalClient {
        public boolean execute(Socket socket);
    }

    public interface Gate {
        public void handle(Socket client_socket);
        public void execute(ServerSocket server_socket);
        public boolean is_active();
    }
}
