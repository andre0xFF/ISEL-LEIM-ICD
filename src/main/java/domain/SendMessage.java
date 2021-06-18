package domain;

import application.Channel;
import application.Function;
import application.Result;
import application.User;

public class SendMessage implements Function {

    private User sender;
    private Channel receiver;
    private Message message;
    private Result result;

    public SendMessage(Message message) {
        this.message = message;
        this.sender = message.getSender();
        this.receiver = message.getReceiver();
    }

    public User getSender() {
        return sender;
    }

    public Channel getReceiver() {
        return receiver;
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
