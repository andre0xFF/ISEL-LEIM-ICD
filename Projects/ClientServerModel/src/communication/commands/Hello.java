package communication.commands;

import communication.Communication;
import communication.Communication.Command;
import communication.Communication.CommandEncoder;
import communication.Communication.Encoder;
import communication.encoders.XML;
import server.Client;
import server.Server.Worker;

import java.util.HashMap;

public class Hello implements Command {

    private static final String NAME = "hello";

    private Encoder encoder;
    HashMap<Class, CommandEncoder> encoders = new HashMap<Class, CommandEncoder>() {{
       put(XML.class, new XMLHello());
    }};

    @Override
    public void execute(Worker client) {
        Communication com = client.get_communication();
        com.set_encoder(this.encoder);
        com.send(new Ok());
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
        return Hello.NAME;
    }

    @Override
    public Command get_response() {
        return null;
    }

    public void set_encoder(Encoder encoder) {
        this.encoder = encoder;
    }


    private class XMLHello implements CommandEncoder {

        private final String FORMAT = String.format("<%s/>", Hello.NAME);

        @Override
        public String encode(Command command) {
            return this.FORMAT;
        }

        @Override
        public Command decode(String message) {
            if (message != null && message.equals(this.FORMAT)) {
                Hello hello = new Hello();
                hello.set_encoder(new XML());
                return hello;
            }

            return null;
        }
    }
}
