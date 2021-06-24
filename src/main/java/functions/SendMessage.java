package domain;

import application.Function;
import application.Result;
import application.User;
import resources.Channel;

public class SendMessage implements Function {

    // @JsonSerialize(using = UserSerializer.class)
    // @JsonDeserialize(using = UserDeserializer.class)
    private User sender;
    private Channel receiver;
    private Message message;
    private Result result;

    public SendMessage() {
    }

    public SendMessage(User sender, Channel receiver, Message message) {
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
    public Result getResult() {
        return this.result;
    }

    @Override
    public void setResult(Result result) {
        this.result = result;
    }
}
