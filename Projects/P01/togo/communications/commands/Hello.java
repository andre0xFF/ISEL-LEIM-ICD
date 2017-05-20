package communications.commands;

import communications.encoders.Text;
import communications.encoders.XML;
import models.CommunicationProtocol.Command;
import models.CommunicationProtocol.Encoder;
import models.Server.InternalClient;

import java.util.HashMap;

public class Hello implements Command {

    public final static String NAME = "HELLO";
    private final HashMap<String, EncodedCommand> encoders = new HashMap<String, EncodedCommand>() {{
        put(new Text().get_name(), new TextHello());
    }};

    private final Encoder encoder;

    public Hello(Encoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public void execute(InternalClient client) {
        client.set_encoding(this.encoder);
    }

    @Override
    public String get_name() {
        return Hello.NAME;
    }

    @Override
    public Command get_response() {
        return null;
    }

    @Override
    public String encode(Encoder encoder) {
        return this.encoders.get(encoder.get_name()).encode(this);
    }

    @Override
    public Command decode(String msg) {
        return Command.decode(this.encoders, msg);
    }

    private class TextHello implements EncodedCommand{

        private final String TEXT = String.format("%s", Hello.NAME);

        @Override
        public String encode(Command cmd) {
            return TEXT;
        }

        @Override
        public Command decode(String msg) {
            return msg.contains(TEXT) ? new Hello(new Text()) : null;
        }
    }

    private class XMLHello implements EncodedCommand {

        private final String XML = String.format("<%s/>", Hello.NAME);

        @Override
        public String encode(Command cmd) {
            return XML;
        }

        @Override
        public Command decode(String msg) {
            return msg.equals(XML) ? new Hello(new XML()) : null;
        }
    }
}
