package functions;

import application.Client;
import application.models.Function;

public class TestFunction2 implements Function<Client> {

    private Client client;

    @Override
    public void execute() {
        System.out.println(this.client);
    }

    @Override
    public void setReceiver(Client receiver) {
        this.client = receiver;
    }
    
}
