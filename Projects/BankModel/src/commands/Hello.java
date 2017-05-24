package commands;

import protocol.Encoder;

public class Hello extends Command {

    private Encoder encoder = new Encoder.XML();

    @Override
    protected Encoder.Encoding[] encodings() {
        return new Encoder.Encoding[] {
                new XMLHello(),
        };
    }

    @Override
    protected Command response() {
        return null;
    }

    protected void set_encoder(Encoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public void execute(ClientCommandHandler client) {

    }

    @Override
    public void execute(ServerCommandHandler server) {
        server.on_hello(this.encoder);
    }

    @Override
    public String name() {
        return "Hello";
    }

    private class XMLHello implements Encoder.Encoding {

        @Override
        public Encoder encoder() {
            return new Encoder.XML();
        }

        @Override
        public Command command() {
            return new Hello();
        }

        @Override
        public String encode(Command command) {
            return "<hello/>";
        }
    }
}
