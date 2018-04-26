package server;

public class Console implements mvc.View, View, Runnable {

    private Controller controller;

    @Override
    public void set(mvc.Controller controller) {

        this.controller = (Controller) controller;
    }

    @Override
    public String get_title() {

        return "Console user interface";
    }

    public Console(Server server) {

        this.set(new Controller(server));

        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {

    }
}
