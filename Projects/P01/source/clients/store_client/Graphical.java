package clients.store_client;

public class Graphical implements mvc.View, View, Runnable {

    private final static String TITLE = "Graphical user interface";
    private Controller controller;

    @Override
    public void set(mvc.Controller controller) {

        this.controller = (Controller) controller;
    }

    @Override
    public String get_title() {

        return Graphical.TITLE;
    }

    public Graphical(Client model) {

        this.set(new Controller(model));
    }

    @Override
    public void run() {

    }
}
