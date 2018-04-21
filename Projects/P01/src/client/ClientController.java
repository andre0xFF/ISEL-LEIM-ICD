package client;

import mvc.Controller;
import mvc.Model;

public class ClientController implements Controller {

    private Client client;

    public ClientController(Client client) {
        this.set(client);
    }

    @Override
    public void set(Model model) {
        this.client = (Client) model;
    }
}
