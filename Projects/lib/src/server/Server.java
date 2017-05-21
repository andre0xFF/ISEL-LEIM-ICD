package server;

import java.net.Socket;

public interface Server {

    // TODO: can ask for resources
    boolean is_active();
    void set(boolean active);

    void execute();
    void register(Worker worker);

    interface Worker {
        void set(boolean active);
        boolean is_active();
    }
    interface Gate {
        int DEFAULT_PORT = 5555;
        void handle(Socket client);
        void execute(Server server, int port);
    }

}
