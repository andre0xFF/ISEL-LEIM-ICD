package server;

import models.Client;
import models.Server;
import java.util.ArrayList;

class BankServer implements Server {

    ArrayList<Server.InternalClient> clients = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Hello world! This is BankServer");
        new BankServer().execute();
    }

    @Override
    public void connect(Client client) {

    }

    @Override
    public void execute() {

    }
}

class BankInternalClient implements Server.InternalClient, Runnable {

    @Override
    public void run() {

    }
}
