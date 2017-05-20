import server.Server;

import java.util.ArrayList;

public class BankServer implements Server {

    private ArrayList<Worker> workers = new ArrayList<>();

    @Override
    public void execute(Gate gate) {

    }

    @Override
    public void register(Worker worker) {
        this.workers.add(worker);
    }
}