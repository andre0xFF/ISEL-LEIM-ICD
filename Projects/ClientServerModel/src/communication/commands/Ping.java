package communication.commands;

import client.Client;
import communication.Protocol.Command;
import communication.Protocol.CommandEncoder;
import communication.Protocol.Encoder;
import communication.encoders.XML;

import java.util.HashMap;

public class Ping implements Command {

    private static final String NAME = "ping";
    HashMap<Class, CommandEncoder> encoders = new HashMap<Class, CommandEncoder>() {{
        put(XML.class, new XMLPing());
    }};

    @Override
    public void execute(Client client) {
        client.send(new Pong());
    }

    @Override
    public String encode(Encoder encoder) {
        return this.encoders.get(encoder.getClass()).encode(this);
    }

    @Override
    public Command decode(String message) {
        return Command.decode(this.encoders.values(), message);
    }

    @Override
    public String get_name() {
        return Ping.NAME;
    }

    @Override
    public Command get_reponse() {
        return new Pong();
    }

    private class XMLPing implements CommandEncoder {

        private final String FORMAT = String.format("<%s/>", Ping.NAME);

        @Override
        public String encode(Command command) {
            return this.FORMAT;
        }

        @Override
        public Command decode(String message) {
            return message != null && message.equals(this.FORMAT) ? new Ping() : null;
        }
    }
}

class Pong implements Command {

    private static final String NAME = "pong";
    HashMap<Class, CommandEncoder> encoders = new HashMap<Class, CommandEncoder>() {{
        put(XML.class, new XMLPong());
    }};

    @Override
    public void execute(Client client) { }

    @Override
    public String encode(Encoder encoder) {
        return this.encoders.get(encoder.getClass()).encode(this);
    }

    @Override
    public Command decode(String message) {
        return Command.decode(this.encoders.values(), message);
    }

    @Override
    public String get_name() {
        return Pong.NAME;
    }

    @Override
    public Command get_reponse() {
        return null;
    }

    private class XMLPong implements CommandEncoder {

        private final String FORMAT = String.format("<%s/>", Pong.NAME);

        @Override
        public String encode(Command command) {
            return this.FORMAT;
        }

        @Override
        public Command decode(String message) {
            return message != null && message.equals(this.FORMAT) ? new Pong() : null;
        }
    }
}
