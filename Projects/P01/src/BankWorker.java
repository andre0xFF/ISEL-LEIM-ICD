import client.Client;
import communication.Protocol;
import communication.encoders.XML;
import server.Server;

import java.net.Socket;
import java.util.ArrayList;

public class BankWorker implements Client, Server.Worker {

    private BankProtocol protocol = new BankProtocol();

    public BankWorker() {
        ArrayList<Protocol.Command> commands = Protocol.Command.get();

        Protocol.Encoder encoder = new XML();
        encoder.set(commands);

        this.protocol.set_encoder(encoder);
    }

    @Override
    public void connect(Socket socket) {
        this.protocol.open(socket);
    }

    @Override
    public void disconnect() {
        this.protocol.close();
    }

    @Override
    public boolean is_active() {
        return this.protocol.is_open();
    }

    @Override
    public void send(Protocol.Command cmd) {
        this.protocol.send(cmd);
    }

    @Override
    public Protocol.Command receive() {
        return this.protocol.receive();
    }

    @Override
    public void set_encoder(Protocol.Encoder encoder) {
        this.protocol.set_encoder(encoder);
    }
}
