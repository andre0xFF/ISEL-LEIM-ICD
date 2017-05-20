package communications;

import communications.commands.Hello;
import communications.commands.Ping;
import communications.commands.Pong;
import communications.commands.Terminate;
import communications.commands.account.*;
import models.CommunicationProtocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class BankCOM implements CommunicationProtocol {

    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private Encoder encoder;

    public BankCOM(Encoder encoder) {
        this.encoder = encoder;
    }

    public boolean open(Socket socket) {
        this.socket = socket;

        try {
            this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.writer = new PrintWriter(this.socket.getOutputStream(), true);
        } catch (IOException e) {
            return false;
        }

        // TODO Hello with encoding
        return true;
    }

    @Override
    public void close() {
        this.send(new Terminate());

        try {
            this.writer.close();
            this.reader.close();
            this.socket.close();
        } catch (IOException e) { }
    }

    @Override
    public void send(String msg) {
        this.writer.println(msg);
    }

    @Override
    public void send(Command cmd) {
        String msg = this.encoder.encode(cmd);
        this.send(msg);
    }

    @Override
    public Command receive() {
        String msg;

        try {
            msg = this.reader.readLine();
        } catch (IOException e) {
            return null;
        }

        return msg == null ? null : this.encoder.decode(msg, this.commands);
    }
}
