package server;

import communication.Communication;

import java.net.Socket;

public interface Server {

    // TODO: can ask for resources
    boolean check();
    void terminate();

    void execute();
    void register(Worker worker);

    interface Gate {
        int DEFAULT_PORT = 5555;
        void handle(Socket client);
        void execute(Server server, int port);
    }

    interface Worker {
        void execute(Socket socket);
        void terminate();
        boolean check();
        Communication get_communication();
    }

}
