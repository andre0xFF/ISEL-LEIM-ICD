package server;

import java.net.Socket;

public interface Server {

    void execute(Gate gate);
    void register(Worker worker);

    interface Worker {
        // TODO: can ask for resources
    }

    interface Gate {

        int DEFAULT_PORT = 5555;
        void handle(Socket client);
        boolean is_active();
        void set(Server server);
    }

}
