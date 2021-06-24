package functions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import application.models.Function;
import resources.Channel;
import resources.Message;
import resources.models.User;

public class SendMessage implements Function {

    // @JsonSerialize(using = UserSerializer.class)
    // @JsonDeserialize(using = UserDeserializer.class)
    private User sender;
    private Channel receiver;
    private Message message;
    private Result<SendMessage> result;

    @JsonCreator()
    public SendMessage(
        @JsonProperty(value = "sender") User sender, 
        @JsonProperty(value = "receiver") Channel receiver, 
        @JsonProperty(value = "message") Message message
    ) {
        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
    }

    public User getSender() {
        return this.sender;
    }

    public Channel getReceiver() {
        return this.receiver;
    }

    public Message getMessage() {
        return message;
    }

    @Override
    public Result<SendMessage> getResult() {
        return this.result;
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        
    }

}
