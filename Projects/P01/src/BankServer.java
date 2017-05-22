import server.Database;
import server.Server;

import java.util.ArrayList;

public class BankServer implements Server, Runnable {

    public static void main(String[] args) {
        BankGate gate = new BankGate();
        BankServer server = new BankServer();
        BankDatabase db = new BankDatabase();

        db.execute("P01/db/database.xml", "P01/db/database.xsd");
        server.execute(db);
        gate.execute(server, Gate.DEFAULT_PORT);
    }

    private Database db;
    private ArrayList<Worker> workers = new ArrayList<>();
    private boolean active;

    @Override
    public boolean check() {
        return this.active;
    }

    @Override
    public void terminate() {
        this.active = false;

        for (Worker worker : this.workers) {
            worker.terminate();
        }
    }

    @Override
    public void execute(Database db) {
        this.active = true;
        boolean check = db.check();

        if (!check) {
            return;
        }

        this.db = db;
        new Thread(this).start();
    }

    @Override
    public void register(Worker worker) {
        worker.attach(this.db);
        this.workers.add(worker);
    }

    @Override
    public void run() {
        System.out.println("Server > Online");

        while(this.check()) {
            for (int i = 0; i < this.workers.size(); i++) {
                Worker worker = this.workers.get(i);

                if (!worker.check()) {
                    worker.terminate();
                    this.workers.remove(worker);
                    i--;
                }
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) { }
        }

        System.out.println("BankServer > Offline");
    }
}