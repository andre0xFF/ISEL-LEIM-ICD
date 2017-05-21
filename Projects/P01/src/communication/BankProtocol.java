package communication;

import communication.commands.Hello;
import communication.commands.Logout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class BankProtocol implements Protocol {

    private Encoder encoder;
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    @Override
    public boolean open(Socket socket) {
        this.socket = socket;

        try {
            this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.writer = new PrintWriter(this.socket.getOutputStream(), true);
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    @Override
    public void close() {
        this.send(new Logout());

        try {
            this.writer.close();
            this.reader.close();
            this.socket.close();
        } catch (IOException e) { }
    }

    @Override
    public boolean is_open() {
        return this.socket.isConnected();
    }

    @Override
    public void send(String message) {
        this.writer.println(message);
    }

    @Override
    public void send(Command command) {
        String encoded_msg = this.encoder.encode(command);
        this.writer.println(encoded_msg);
    }

    @Override
    public Command receive() {
        String message = "";

        try {
            message = this.reader.readLine();
            return this.encoder.decode(message);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public void set_encoder(Encoder encoder) {
        if (this.encoder != null) {
            encoder.set(this.encoder.get());
        }

        this.encoder = encoder;
    }
}
