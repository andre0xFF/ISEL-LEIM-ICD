package commands;

import models.CommunicationProtocol.*;

public class Ping implements Command {

    public static final String NAME = "Ping";
    private final Command rsp;
    private final Message msg;

    public Ping() {
        this.rsp = new Pong();
        this.msg = new TextPing();
    }

    @Override
    public void execute() { }

    @Override
    public String get_name() {
        return Ping.NAME;
    }

    @Override
    public Command get_response() {
        return this.rsp;
    }

    @Override
    public Message get() {
        this.msg.encode();
        return this.msg;
    }

    class Pong implements Command {

        public static final String NAME = "Pong";
        private final Command rsp = null;
        private final TextPing msg;

        public Pong() {
            this.msg = new TextPing();
        }

        @Override
        public void execute() { }

        @Override
        public String get_name() {
            return Pong.NAME;
        }

        @Override
        public Command get_response() {
            return this.rsp;
        }

        @Override
        public Message get() {
            this.msg.encode();
            return this.msg;
        }
    }
}

class XMLPing implements Message, Message.XML {

    @Override
    public String encode() {
        return null;
    }

    @Override
    public XMLPing decode(String msg) {
        return null;
    }
}

class TextPing implements Message, Message.PlainText {

    @Override
    public String encode() {
        return String.format("%s", "");
    }

    @Override
    public TextPing decode(String msg) {
        String[] strings = msg.split(" ");
        return new TextPing(strings[0], strings[1]);
    }
}
