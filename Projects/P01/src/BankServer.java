import server.Server;
import server.Worker;

import java.net.Socket;

public class BankServer extends Server {

    @Override
    protected Worker worker(Socket socket) {
        return new BankWorker(new BankProtocol(socket));
    }

    public static void main(String[] args) {
        BankServer server = new BankServer();
        BankGate gate = new BankGate(server, 5555);
    }
}
