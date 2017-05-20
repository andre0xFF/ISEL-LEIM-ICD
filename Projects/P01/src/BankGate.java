import server.Server;

import java.net.Socket;

public class BankGate implements Server.Gate {

    private Server server;

    @Override
    public void handle(Socket client) {
        BankWorker worker = new BankWorker();
        worker.connect(client);
        this.server.register(worker);
    }

    @Override
    public boolean is_active() {
        return false;
    }

    @Override
    public void set(Server server) {
        this.server = server;
    }
}
