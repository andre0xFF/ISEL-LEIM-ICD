package communications.commands;

import communications.encoders.XML;
import models.CommunicationProtocol.Command;
import models.CommunicationProtocol.Encoder;
import models.Server.InternalClient;

import java.util.HashMap;

public class Ping implements Command {

    public static final String NAME = "Ping";
    private final Command rsp = new Pong();

    private final HashMap<String, EncodedCommand> encoders = new HashMap<String, EncodedCommand>() {{
       put(new XML().get_name(), new XMLPing());
    }};

    @Override
    public void execute(InternalClient client) {
        client.send(this.rsp);
    }

    @Override
    public String get_name() {
        return Ping.NAME;
    }

    @Override
    public Command get_response() {
        return this.rsp;
    }

    @Override
    public String encode(Encoder encoder) {
        EncodedCommand en_cmd = this.encoders.get(encoder.get_name());
        return en_cmd.encode(this);
    }

    @Override
    public Command decode(String msg) {
        return Command.decode(this.encoders, msg);
    }

    private class XMLPing implements EncodedCommand {

        private final String FORMAT = "<%s></%s>";
        private final String XML = String.format(this.FORMAT, Ping.NAME, Ping.NAME);

        @Override
        public String encode(Command cmd) {
            return XML;
        }

        @Override
        public Command decode(String msg) {
            return msg.equals(XML) ? new Ping() : null;
        }
    }

    private class JSONPing implements EncodedCommand {

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