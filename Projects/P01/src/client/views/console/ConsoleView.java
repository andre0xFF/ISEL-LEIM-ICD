package client.views.console;

import client.Client;
import client.ClientController;
import client.ClientView;
import mvc.Controller;
import mvc.View;

public class ConsoleView implements View, ClientView, Runnable {

    private ClientController controller;

    public ConsoleView(Client client) {
        this.set(new ClientController(client));
    }

    @Override
    public void run() {

    }

    @Override
    public void set(Controller controller) {
        this.controller = (ClientController) controller;
    }
}
