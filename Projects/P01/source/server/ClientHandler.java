package server;

public class ClientHandler implements Runnable {

    private Server server;

    public ClientHandler(Server server) {

        this.server = server;

        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {

    }
}
