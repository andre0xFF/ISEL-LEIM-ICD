package client;

import commands.Ping;
import commands.Terminate;
import models.Client;
import communications.BankCOM;
import models.CommunicationProtocol.*;

public class BankClient implements Client {

    public static void main(String[] args) {
        new BankClient().execute();
    }

    private BankCOM com;

    public void execute() {
        this.com = Client.connect(Client.SERVER_HOSTNAME, Client.SERVER_PORT);

        // Ping test
        System.out.println("Ping!");
        this.com.send(new Ping());
        Command cmd = this.com.receive();

        System.out.println(cmd.get_name());

        this.com.send(new Terminate());
    }
}
