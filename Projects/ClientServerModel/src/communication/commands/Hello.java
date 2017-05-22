package communication.commands;

import client.Client;
import communication.Command;
import communication.Communication;
import communication.Communication.Encoder;
import communication.encoders.XML;
import server.Server.Worker;

import java.util.ArrayList;
import java.util.HashMap;

public class Hello implements Command {

    private Encoder encoder;
    HashMap<Class, CommandEncoder> encoders = new HashMap<Class, CommandEncoder>() {{
       put(XML.class, new XMLHello());
    }};

    @Override
    public void execute(Worker client) {
        Communication com = client.get_communication();
        com.set_encoder(this.encoder);
        com.send(new LoginSuccess());
    }

    @Override
    public void execute(Client client) {
        // The server is not supposed to send Hello
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
        return "hello";
    }

    @Override
    public ArrayList<Command> get_responses() {
        return null;
    }

    public void set_encoder(Encoder encoder) {
        this.encoder = encoder;
    }


    private class XMLHello implements CommandEncoder {

        @Override
        public String encode(Command command) {
            Hello hello = (Hello) command;
            return String.format(this.get_format(), hello.get_name());
        }

        @Override
        public Command decode(String message) {
            if (message != null && message.contains("hello")) {
                Hello hello = new Hello();
                hello.set_encoder(new XML());
                return hello;
            }

            return null;
        }

        @Override
        public String get_format() {
            return "<%s/>";
        }
    }
}
