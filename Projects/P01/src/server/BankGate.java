package server;

import models.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BankGate implements Server.Gate, Runnable {

    private boolean active;
    private ServerSocket server_socket;
    private Server server;

    @Override
    public boolean is_active() {
        return this.active;
    }

    @Override
    public void run() {
        System.out.println("Hello world! This is BankGate.");

        while (this.active) {
            try {
                Socket new_socket = this.server_socket.accept();
                this.handle(new_socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void handle(Socket client_socket) {
        BankInternalClient client = new BankInternalClient();
        client.execute(client_socket);
        this.server.add(client);
    }

    @Override
    public boolean execute(Server server, int port) {
        this.server = server;

        try {
            this.server_socket = new ServerSocket(port);
        } catch (IOException e) {
            return false;
        }

        this.active = true;
        new Thread(this).start();
        return true;
    }
}
