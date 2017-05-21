import server.Server;

import java.util.ArrayList;

public class BankServer implements Server {

    public static void main(String[] args) {
        new BankServer().execute();
    }

    private ArrayList<Worker> workers = new ArrayList<>();
    private boolean active;

    @Override
    public boolean is_active() {
        return this.active;
    }

    @Override
    public void set(boolean active) {
        this.active = active;
    }

    @Override
    public void execute() {
        System.out.println("Hello this is BankServer");
        this.set(true);
        BankGate gate = new BankGate();
        gate.execute(this, Gate.DEFAULT_PORT);

        while(this.is_active()) {
            for (Worker worker : this.workers) {
                if (!worker.is_active()) {
                    worker.set(false);
                    this.workers.remove(worker);
                }
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) { }
        }
    }

    @Override
    public void register(Worker worker) {
        this.workers.add(worker);
    }
}