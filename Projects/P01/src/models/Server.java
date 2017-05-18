package models;

import java.net.Socket;

public interface Server {

    // Fields
    public final static int DEFAULT_PORT = 5555;

    // Functions
    public void execute(Gate gate);
    public boolean add(InternalClient client);
    public void update();

    // Modules
    public interface InternalClient {
        public void execute(Socket socket);
        public void set_active(Boolean active);
        public boolean is_active();
    }

    public interface Gate {
        public void handle(Socket client_socket);
        public boolean execute(Server server, int port);
        public boolean is_active();
    }
}
