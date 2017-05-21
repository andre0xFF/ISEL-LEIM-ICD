import communication.BankCommunication;
import communication.Communication;
import communication.commands.Ping;
import server.Server;
import server.Server.Worker;

import java.io.IOException;
import java.net.Socket;

public class BankWorker implements Worker, Runnable {

    private boolean active;
    private BankCommunication com = new BankCommunication();

    public static void main(String[] args) {
        BankWorker worker = new BankWorker();
        try {
            worker.execute(new Socket("127.0.0.1", Server.Gate.DEFAULT_PORT));
        } catch (IOException e) { }
    }

    @Override
    public void run() {
        while (this.check()) {
            Communication.Command command = this.com.receive();

            if (command != null) {
                command.execute(this);
            }
        }

        this.terminate();
    }

    @Override
    public void execute(Socket socket) {
        this.com.execute(socket);
        this.active = true;
        new Thread(this).start();
    }

    @Override
    public boolean check() {
        return this.active && this.com.check();
    }

    @Override
    public void terminate() {
        this.active = false;
        this.com.terminate();
    }

    @Override
    public Communication get_communication() {
        return this.com;
    }
}
