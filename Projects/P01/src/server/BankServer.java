package server;

import models.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

class BankServer implements Server {

    BankGate gate = new BankGate();

    public static void main(String[] args) {
        System.out.println("Hello world! This is BankServer.");
        new BankServer();
    }

    public BankServer() {
        System.out.println("Executing server..");
        Server.execute(this.gate);
    }
}

class BankGate implements Server.Gate, Runnable {

    private boolean active;
    private ServerSocket server;
    ArrayList<Server.InternalClient> clients = new ArrayList<>();

    @Override
    public void run() {
        System.out.println("Hello world! This is BankGate.");

        while (this.active) {
            try {
                this.handle(server.accept());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void handle(Socket client_socket) {
        BankInternalClient client = new BankInternalClient();
        client.execute(client_socket);
        this.clients.add(client);
    }

    @Override
    public void execute(ServerSocket server_socket) {
        this.server = server_socket;
        this.active = true;
        new Thread(this).start();
    }

    @Override
    public boolean is_active() {
        return this.active;
    }
}

class BankInternalClient implements Server.InternalClient, Runnable {

    @Override
    public void run() {
        System.out.println("Hello world! This is BankInternalClient");
    }

    @Override
    public boolean execute(Socket socket) {
        new Thread(this).start();
        return true;
    }
}
