package communications.commands;

import communications.encoders.XML;
import models.CommunicationProtocol;
import models.CommunicationProtocol.Command;
import models.CommunicationProtocol.Encoder;
import models.Server;

import java.util.HashMap;

public class Pong implements Command {

    public static final String NAME = "Pong";

    private HashMap<String, EncodedCommand> encoders = new HashMap<String, EncodedCommand>() {{
        put(new XML().get_name(), new Pong.XMLPong());
    }};

    @Override
    public void execute(Server.InternalClient client) { }

    @Override
    public String get_name() {
        return Pong.NAME;
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

    private class XMLPong implements EncodedCommand {

        private final String FORMAT = "<%s></%s>";
        private final String XML = String.format(this.FORMAT, Pong.NAME, Pong.NAME);

        @Override
        public String encode(Command cmd) {
            return XML;
        }

        @Override
        public Command decode(String msg) {
            return msg.equals(XML) ? new Pong() : null;
        }
    }

    private class JSONPong implements EncodedCommand {

        @Override
        public String encode(Command cmd) {
            return null;
        }

        @Override
        public Command decode(String msg) {
            return null;
        }
    }
}
