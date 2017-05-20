package models;

import models.CommunicationProtocol.Command;
import models.CommunicationProtocol.Encoder;

import java.net.Socket;

public interface Server {

    // Fields
    int DEFAULT_PORT = 5555;

    // Functions
    void execute(Gate gate);
    boolean add(InternalClient client);
    void update();

    // Modules
    interface InternalClient {
        void connect(Socket socket);
        void disconnect();
        void set_active(Boolean active);
        void set_encoding(Encoder encoder);
        boolean is_active();
        void send(Command cmd);
        Command receive();
    }

    interface Gate {
        void handle(Socket client_socket);
        boolean execute(Server server, int port);
        boolean is_active();
    }
}
