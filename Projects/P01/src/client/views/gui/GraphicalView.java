package client.views.gui;

import client.Client;
import client.ClientController;
import mvc.Controller;
import mvc.View;

public class GraphicalView implements View, Runnable {

    private ClientController controller;

    public GraphicalView(Client client) {

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
