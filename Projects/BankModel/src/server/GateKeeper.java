package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class GateKeeper implements Runnable {

    public static final int DEFAULT_PORT = 5555;

    private Server server;
    private ServerSocket gate_socket;

    public GateKeeper(Server server, int port) {
        this.server = server;

        try {
            this.gate_socket = new ServerSocket(port);

        } catch (IOException e) { }

        new Thread(this).start();
    }

    @Override
    public void run() {
        this.receive();
    }

    public void receive() {
        while(this.server.check()) {
            try {
                Socket client = this.gate_socket.accept();
                this.server.register_worker(client);

            } catch (IOException e) { }
        }
    }
}
