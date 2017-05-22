import server.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BankGate implements Server.Gate, Runnable {

    private Server server;
    private ServerSocket socket;

    @Override
    public void handle(Socket client) {
        BankWorker worker = new BankWorker();
        BankCommunication com = new BankCommunication();
        com.execute(client);
        worker.execute(com);
        this.server.register(worker);
    }

    @Override
    public void execute(Server server, int port) {
        try {
            this.socket = new ServerSocket(port);
        } catch (IOException e) { }

        this.server = server;
        new Thread(this).start();
    }

    @Override
    public void run() {
        System.out.println("BankGate > Online");

        while(this.server.check()) {
            try {
                Socket client = this.socket.accept();
                this.handle(client);
            } catch (IOException e) { }
        }

        System.out.println("BankGate > Offline");
    }
}
