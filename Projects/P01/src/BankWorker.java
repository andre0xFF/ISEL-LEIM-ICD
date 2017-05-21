import client.Client;
import communication.BankProtocol;
import communication.Protocol;
import communication.encoders.XML;
import server.Server;

import java.net.Socket;
import java.util.ArrayList;

public class BankWorker implements Client, Server.Worker, Runnable {

    private BankProtocol protocol = new BankProtocol();
    private boolean active;

    public BankWorker() {
        ArrayList<Protocol.Command> commands = Protocol.Command.get_default_commands();

        Protocol.Encoder encoder = new XML();
        encoder.set(commands);

        this.protocol.set_encoder(encoder);
    }

    @Override
    public void connect(Socket socket) {
        this.protocol.open(socket);
        new Thread(this).start();
    }

    @Override
    public void disconnect() {
        this.protocol.close();
    }

    @Override
    public boolean is_active() {
        return this.protocol.is_open() && this.active;
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

    @Override
    public void run() {
        this.set(true);

        while (this.is_active()) {
            Protocol.Command command = this.protocol.receive();

            if (command == null) {
                break;
            }

            command.execute(this);
        }

        System.out.println("Worker terminated");
    }

    @Override
    public void set(boolean active) {
        this.active = active;

        if (!this.active) {
            this.disconnect();
        }
    }
}
