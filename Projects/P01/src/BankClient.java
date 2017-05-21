import communication.BankCommunication;
import communication.Communication;
import communication.commands.Hello;
import communication.commands.Logout;
import communication.commands.Ping;
import server.Client;
import server.Server;

import java.io.IOException;
import java.net.Socket;

public class BankClient implements Client, Runnable {

    private BankCommunication com = new BankCommunication();
    private boolean active;

    public static void main(String[] args) {
        BankClient client = new BankClient();

        try {
            client.execute(new Socket("127.0.0.1", Server.Gate.DEFAULT_PORT));
        } catch (IOException e) { }
    }

    @Override
    public void run() {
        this.com.send(new Hello());
        this.com.send(new Ping());

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
        this.com.send(new Logout());
        this.com.terminate();
    }

    @Override
    public Communication get_communication() {
        return this.com;
    }
}
