package communications;

import commands.Ping;
import models.CommunicationProtocol;
import models.Server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class BankCOM implements CommunicationProtocol {

    private boolean validated;
    private PrintWriter writer;
    private Socket socket;
    private BufferedReader reader;

    public BankCOM(Socket socket) {
        this.socket = socket;

        try {
            this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.writer = new PrintWriter(this.socket.getOutputStream(), true);
        } catch (IOException e) {
            this.validated = false;
            return;
        }
    }

    @Override
    public void send(Command cmd) {
        this.writer.print(cmd.get());
    }

    @Override
    public void close() {
        try {
            this.writer.close();
            this.reader.close();
            this.socket.close();
        } catch (IOException e) {}
    }

    public boolean test() {
        String destination = this.socket.getRemoteSocketAddress().toString();
        this.send(new Ping());
        // TODO
        return true;
    }
}
