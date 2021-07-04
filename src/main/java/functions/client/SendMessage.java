package functions.client;

import com.fasterxml.jackson.annotation.JsonProperty;

import resources.Message;
import application.Server;
import application.models.Function;

public class SendMessage implements Function<Server> {

    private Server receiver;

    @JsonProperty
    private Message message;

    public SendMessage(Message message) {
        this.message = message;
    }

    @Override
    public void execute() {
        // todo: write in database

        this.receiver.invoke(
            new functions.server.SendMessage(this.message)
        );
    }

    @Override
    public void setReceiver(Server receiver) {
        this.receiver = receiver;
    }
    
}
