package functions.server;

import application.Client;
import application.models.Function;
import resources.Message;

public class SendMessage implements Function<Client> {

    private Client receiver;
    private Message message;

    public SendMessage(Message message) {
        this.message = message;
    }

    @Override
    public void execute() {
        // TODO receive new message
        
    }

    @Override
    public void setReceiver(Client receiver) {
        this.receiver = receiver;
    }
    
}
