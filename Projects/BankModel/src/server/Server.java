package server;

import java.net.Socket;
import java.util.ArrayList;

public abstract class Server implements Runnable {

    private ArrayList<Worker> workers = new ArrayList<>();
    private boolean active = true;
    protected abstract Worker worker(Socket socket);

    public Server() {
        new Thread(this).start();
    }

    public void register_worker(Socket socket) {
        if (!this.active) {
            return;
        }

        Worker worker = this.worker(socket);
        this.workers.add(worker);
    }

    public boolean check() {
        return this.active;
    }

    @Override
    public void run() {
        while(this.check()) {
            this.check_workers();

            try {
                Thread.sleep(1000);

            } catch (InterruptedException e) { }
        }
    }

    private void check_workers() {
        for (Worker worker : this.workers) {
            if (!worker.is_connected()) {
                this.workers.remove(worker);
            }
        }
    }
}