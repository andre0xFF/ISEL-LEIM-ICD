package server;

import models.Server;

import java.util.ArrayList;

public class BankServer implements Server {

    public static void main(String[] args) {
        new BankServer().execute(new BankGate());
    }

    private BankGate gate;
    private ArrayList<InternalClient> clients = new ArrayList<>();

    @Override
    public void execute(Gate gate) {
        this.gate = (BankGate) gate;
        System.out.println("Hello world! This is BankServer.");
        this.gate.execute(this, Server.DEFAULT_PORT);
    }

    @Override
    public boolean add(InternalClient client) {
        this.clients.add(client);
        return true;
    }

    @Override
    public void update() {
        for (InternalClient client : this.clients) {
            if (!client.is_active()) {
                this.clients.remove(client);
            }
        }
    }
}
