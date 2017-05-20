package server;

import communications.BankCOM;
import communications.encoders.Text;
import models.CommunicationProtocol.Encoder;
import models.CommunicationProtocol.Command;
import models.Server.InternalClient;

import java.net.Socket;

public class BankInternalClient implements InternalClient, Runnable {

    private BankCOM com = new BankCOM(new Text());
    private boolean active;

    @Override
    public void set_active(Boolean active) {
        this.active = active;
    }

    @Override
    public void set_encoding(Encoder encoder) {
        this.com = new BankCOM(encoder);
    }

    @Override
    public boolean is_active() {
        return this.active;
    }

    @Override
    public void send(Command cmd) {

    }

    @Override
    public Command receive() {
        return null;
    }

    @Override
    public void run() {
        this.set_active(true);
        System.out.println("Hello world! This is BankInternalClient");

        while(this.active) {
            Command cmd = this.com.receive();

            if (cmd == null) {
                break;
            }

            cmd.execute(this);
        }

        this.com.close();
        System.out.println("Client disconnected");
    }

    @Override
    public void connect(Socket socket) {
        this.com.open(socket);
        new Thread(this).start();
    }

    @Override
    public void disconnect() {
        this.active = false;
        Thread.currentThread().interrupt();
        this.com.close();
    }
}
