package communication.commands;

import client.Client;
import communication.Protocol.Command;
import communication.Protocol.CommandEncoder;
import communication.Protocol.Encoder;
import communication.encoders.XML;

import java.util.HashMap;

public class Ping implements Command {

    private static final Object NAME = "ping";
    HashMap<Class, CommandEncoder> encoders = new HashMap<Class, CommandEncoder>() {{
        put(XML.class, new XMLPing());
    }};

    @Override
    public void execute(Client client) {
        Pong pong = new Pong();
        pong.add(this.encoders);
        client.send(pong);
    }

    @Override
    public String encode(Encoder encoder) {
        return this.encoders.get(encoder.getClass()).encode(this);
    }

    @Override
    public Command decode(String message) {
        return Command.decode(this.encoders.values(), message);
    }

    private class XMLPing implements CommandEncoder {

        private final String FORMAT = String.format("<%s/>", Ping.NAME);

        @Override
        public String encode(Command command) {
            return this.FORMAT;
        }

        @Override
        public Command decode(String message) {
            return message.equals(this.FORMAT) ? new Ping() : null;
        }
    }
}

class Pong implements Command {

    private static final String NAME = "pong";
    private HashMap<Class, CommandEncoder> encoders;

    @Override
    public void execute(Client client) { }

    protected void add(HashMap<Class, CommandEncoder> encoders) {
        this.encoders = encoders;
    }

    @Override
    public String encode(Encoder encoder) {
        return this.encoders.get(encoder.getClass()).encode(this);
    }

    @Override
    public Command decode(String message) {
        return Command.decode(this.encoders.values(), message);
    }

    private class XMLPong implements CommandEncoder {

        private final String FORMAT = String.format("<%s/>", Pong.NAME);

        @Override
        public String encode(Command command) {
            return this.FORMAT;
        }

        @Override
        public Command decode(String message) {
            return message.equals(this.FORMAT) ? new Pong() : null;
        }
    }
}
