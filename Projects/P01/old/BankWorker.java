import communication.Communication;
import server.Database;
import server.Server;
import server.Server.Worker;

import java.io.IOException;
import java.net.Socket;

public class BankWorker implements Worker, Runnable {

    private boolean active;
    private Database db;
    private Communication com;

    public static void main(String[] args) {
        BankWorker worker = new BankWorker();

        try {
            BankCommunication com = new BankCommunication();
            com.execute(new Socket("127.0.0.1", Server.Gate.DEFAULT_PORT));
            worker.execute(com);
        } catch (IOException e) { }
    }

    @Override
    public void run() {
        while (this.check()) {
            Communication.Command command = this.com.receive();

            if (command != null) {
                command.execute(this);

                String log = String.format("%s > %s", "Worker", command.get_name());
                System.out.println(log);
            }
        }

        this.terminate();
    }

    @Override
    public void execute(Communication com) {
        this.com = com;
        this.active = true;
        new Thread(this).start();
    }

    @Override
    public void attach(Database database) {
        this.db = database;
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

    @Override
    public Database get_database() {
        return this.db;
    }
}
