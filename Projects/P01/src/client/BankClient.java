package client;

import commands.Ping;
import models.Client;
import communications.BankCOM;

public class BankClient implements Client {

    private final BankCOM com;

    public static void main(String[] args) {
        System.out.println("Hello world! This is BankClient");
        new BankClient();
    }

    public BankClient() {
        this(Client.SERVER_HOSTNAME, Client.SERVER_PORT);
    }

    public BankClient(String server_ip, int server_port) {
        this.com = Client.connect(server_ip, server_port);
        this.com.send(new Ping());
    }
}
