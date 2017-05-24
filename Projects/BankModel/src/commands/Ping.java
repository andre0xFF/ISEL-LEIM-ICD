package commands;

import protocol.Encoder;
import protocol.Encoder.Encoding;

public class Ping extends Command {

    @Override
    protected Encoding[] encodings() {
        return new Encoding[] {
                new XMLPing(),
        };
    }

    @Override
    public Command response() {
        return new Pong();
    }

    @Override
    public void execute(ClientCommandHandler client) {
        client.send(this.response());
        System.out.println("Client: Ping!");
    }

    @Override
    public void execute(ServerCommandHandler server) {
        server.send(this.response());
        System.out.println("Server: Ping!");
    }

    @Override
    public String name() {
        return "Ping";
    }

    class XMLPing implements Encoding {


        @Override
        public String encode(Command command) {
            return "<ping/>";
        }

        @Override
        public Encoder encoder() {
            return new Encoder.XML();
        }

        @Override
        public Command command() {
            return new Ping();
        }
    }
}

class Pong extends Command {

    @Override
    protected Encoding[] encodings() {
        return new Encoding[] {
                new XMLPong(),
        };
    }

    @Override
    protected Command response() {
        return null;
    }

    @Override
    public void execute(ClientCommandHandler client) {
        System.out.println("Client: Pong.");
    }

    @Override
    public void execute(ServerCommandHandler server) {
        System.out.println("Server: Pong.");
    }

    @Override
    public String name() {
        return "Pong";
    }

    class XMLPong implements Encoding {

        @Override
        public String encode(Command command) {
            return "<pong/>";
        }

        @Override
        public Encoder encoder() {
            return new Encoder.XML();
        }

        @Override
        public Command command() {
            return new Pong();
        }
    }
}