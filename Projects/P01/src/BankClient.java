import client.Client;
import communication.Protocol;
import communication.commands.Hello;
import communication.encoders.XML;
import server.Server;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class BankClient implements Client {

    private final BankProtocol com = new BankProtocol();

    public BankClient() {
        ArrayList<Protocol.Command> commands = Protocol.Command.get();

        Protocol.Encoder encoder = new XML();
        encoder.set(commands);

        this.com.set_encoder(encoder);
    }

    @Override
    public void connect(Socket server) {
        this.com.open(server);
    }

    @Override
    public void disconnect() {
        this.com.close();
    }

    @Override
    public boolean is_active() {
        return this.com.is_open();
    }

    @Override
    public void send(Protocol.Command cmd) {
        this.send(cmd);
    }

    @Override
    public Protocol.Command receive() {
        return this.com.receive();
    }

    @Override
    public void set_encoder(Protocol.Encoder encoder) {
        this.com.set_encoder(encoder);
    }

    public static void main(String[] args) {
        BankClient client = new BankClient();

        try {
            client.connect(new Socket("127.0.0.1", Server.Gate.DEFAULT_PORT));
        } catch (IOException e) { }

        client.send(new Hello());
    }
}
