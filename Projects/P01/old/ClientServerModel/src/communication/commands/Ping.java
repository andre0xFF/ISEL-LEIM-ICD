package communication.commands;

import client.Client;
import communication.Command;
import communication.Communication.Encoder;
import communication.encoders.XML;
import server.Server.Worker;

import java.util.ArrayList;
import java.util.HashMap;

public class Ping implements Command {

    private static final String NAME = "ping";
    HashMap<Class, CommandEncoder> encoders = new HashMap<Class, CommandEncoder>() {{
        put(XML.class, new XMLPing());
    }};

    @Override
    public void execute(Worker worker) {
        worker.get_communication().send(new Pong());
    }

    @Override
    public void execute(Client client) {
        client.get_communication().send(new Pong());
    }

    @Override
    public void execute(CommandEventHandler event_handler) { }

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
    public ArrayList<Command> get_responses() {
        return new ArrayList<Command>() {{
            add(new Pong());
        }};
    }

    private class XMLPing implements CommandEncoder {

        @Override
        public String encode(Command command) {
            return String.format(this.get_format(), Ping.NAME);
        }

        @Override
        public Command decode(String message) {
            return message != null && message.contains("ping") ? new Ping() : null;
        }

        @Override
        public String get_format() {
            return "<%s/>";
        }
    }
}

class Pong implements Command {

    private static final String NAME = "pong";
    HashMap<Class, CommandEncoder> encoders = new HashMap<Class, CommandEncoder>() {{
        put(XML.class, new XMLPong());
    }};

    @Override
    public void execute(Worker worker) { }

    @Override
    public void execute(Client client) { }

    @Override
    public void execute(CommandEventHandler event_handler) { }

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
    public ArrayList<Command> get_responses() {
        return null;
    }

    private class XMLPong implements CommandEncoder {

        @Override
        public String encode(Command command) {
            Pong pong = (Pong) command;
            return String.format(this.get_format(), pong.get_name());
        }

        @Override
        public Command decode(String message) {
            return message != null && message.contains("pong") ? new Pong() : null;
        }

        @Override
        public String get_format() {
            return "<%s/>";
        }
    }
}
