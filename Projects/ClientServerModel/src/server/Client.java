package server;

import communication.Communication;

import java.net.Socket;

public interface Client {
    void execute(Socket socket);
    boolean check();
    void terminate();
    Communication get_communication();
}
