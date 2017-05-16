package communications;

import models.Command;
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
    private final Server.Host host;

    public BankCOM(String hostname, int port) {
        this.host = new Server.Host(hostname, port);

        try {
            this.socket = new Socket(hostname, port);
            this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.writer = new PrintWriter(this.socket.getOutputStream(), true);
        } catch (IOException e) {
            this.validated = false;
            return;
        }

        this.validated = this.host.validate();
    }

    @Override
    public void send(Command cmd) {

    }

    @Override
    public void close() {
        try {
            this.writer.close();
            this.reader.close();
            this.socket.close();
        } catch (IOException e) {}
    }
}
