import client.Client;
import communication.Command;
import communication.Command.CommandEventHandler;
import communication.Communication;
import communication.commands.Hello;
import communication.commands.Login;
import communication.commands.Logout;
import communication.commands.Ping;
import server.Server;

import java.io.IOException;
import java.net.Socket;

public class BankClient implements Client, Runnable, CommandEventHandler {

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

        Login login = new Login("Ex01", "qazwsx");
        this.com.send(login);

        while (this.check()) {
            Command command = this.com.receive();

            if (command != null) {
                command.execute((CommandEventHandler) this);

                String log = String.format("%s > %s", "Client", command.get_name());
                System.out.println(log);
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

    @Override
    public void on_login_success() {

    }

    @Override
    public void on_login_denied() {

    }
}
