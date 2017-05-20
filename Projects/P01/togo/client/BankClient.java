package client;

import communications.commands.Hello;
import communications.commands.Ping;
import communications.commands.Pong;
import communications.commands.Terminate;
import communications.encoders.XML;
import models.Client;
import communications.BankCOM;
import models.CommunicationProtocol;
import models.CommunicationProtocol.*;

import java.io.IOException;
import java.net.Socket;

public class BankClient implements Client {

    public static void main(String[] args) {
        BankClient client  = new BankClient();

        client.connect(Client.SERVER_HOSTNAME, Client.SERVER_PORT);
        client.test();
    }

    private BankCOM com;

    public BankClient() {
        Command[] cmds = new Command[] {
                new Hello(null),
                new Ping(),
                new Pong(),
        };

        Encoder encoder = new XML(cmds);
    }

    @Override
    public void connect(String hostname, int port) {
        try {
            Socket socket = new Socket(hostname, port);
            this.com.open(socket);
        } catch (IOException e) { }
    }

    @Override
    public void disconnect() {
        this.com.close();
    }

    public void test() {
        // Ping test
        System.out.println("Hello!");
        this.com.send(new Hello(new XML()));
        Command cmd = this.com.receive();

        System.out.println(cmd.get_name());

//        this.com.send(new Terminate());
    }
}
