package clients.store_client;

public class Controller implements mvc.Controller {

    private Client client;

    public Controller(Client client) {

        this.set(client);
    }

    @Override
    public void set(mvc.Model model) {

        this.client = (Client) model;
    }
}
