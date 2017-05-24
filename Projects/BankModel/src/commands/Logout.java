package commands;

import protocol.Encoder;
import protocol.Encoder.Encoding;

public class Logout extends Command {

    @Override
    protected Encoding[] encodings() {
        return new Encoding[] {
                new XMLLogout(),
        };
    }

    @Override
    protected Command response() {
        return null;
    }

    @Override
    public void execute(ClientCommandHandler client) { }

    @Override
    public void execute(ServerCommandHandler worker) {
        worker.terminate();
    }

    @Override
    public String name() {
        return "Logout";
    }

    private class XMLLogout implements Encoding {

        @Override
        public Encoder encoder() {
            return new Encoder.XML();
        }

        @Override
        public Command command() {
            return new Logout();
        }

        @Override
        public String encode(Command command) {
            return "<logout/>";
        }
    }
}
