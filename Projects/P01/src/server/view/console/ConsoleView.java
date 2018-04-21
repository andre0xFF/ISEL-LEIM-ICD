package server.view.console;

import mvc.Controller;
import mvc.View;
import server.Server;
import server.ServerController;
import server.ServerView;

public class ConsoleView implements View, ServerView, Runnable {

    private ServerController controller;

    public ConsoleView(Server server) {

        this.set(new ServerController(server));

        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void set(Controller controller) {

        this.controller = (ServerController) controller;
    }

    @Override
    public void run() {

    }
}
