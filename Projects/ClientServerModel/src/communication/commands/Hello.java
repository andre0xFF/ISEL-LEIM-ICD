package communication.commands;

import client.Client;
import communication.Protocol.Command;
import communication.Protocol.CommandEncoder;
import communication.Protocol.Encoder;
import communication.encoders.XML;

import java.util.HashMap;

public class Hello implements Command {

    private static final String NAME = "hello";

    private Encoder encoder;
    HashMap<Class, CommandEncoder> encoders = new HashMap<Class, CommandEncoder>() {{
       put(XML.class, new XMLHello());
    }};

    @Override
    public void execute(Client client) {
        client.set_encoder(this.encoder);
        client.send(new Ok());
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
    public Command get_reponse() {
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
