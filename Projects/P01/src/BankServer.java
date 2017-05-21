import server.Server;

import java.util.ArrayList;

public class BankServer implements Server {

    public static void main(String[] args) {
        BankGate gate = new BankGate();
        BankServer server = new BankServer();

        gate.execute(server, Gate.DEFAULT_PORT);
        server.execute();
    }

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
    public void execute() {
        this.active = true;

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
    }

    @Override
    public void register(Worker worker) {
        this.workers.add(worker);
    }
}