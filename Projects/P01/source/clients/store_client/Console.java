package clients.store_client;

public class Console implements mvc.View, View, Runnable {

    private final static String TITLE = "Console user interface";
    private Controller controller;

    @Override
    public void set(mvc.Controller controller) {

        this.controller = (Controller) controller;
    }

    public Console(Client client) {

        this.set(new Controller(client));
    }

    @Override
    public void run() {

    }

    @Override
    public String get_title() {

        return Console.TITLE;
    }
}
