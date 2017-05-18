package communications;

import commands.*;
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
    private Encoding encoding;

    public BankCOM(Encoding encoding) {
        this.encoding = encoding;
    }

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
        this.send(new Terminate());
        try {
            this.writer.close();
            this.reader.close();
            this.socket.close();
        } catch (IOException e) {}
    }

    @Override
    public void send(Command cmd) {
        String msg = this.encoding.encode(cmd);
        this.writer.println(msg);
    }

    @Override
    public Command receive() {
        try {
            String msg = this.reader.readLine();

            if (msg == null) {
                return null;
            }

            return this.encoding.decode(msg);
        } catch (IOException e) {
            return null;
        }
    }
}
