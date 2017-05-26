package protocol.commands;

import protocol.Command;

import java.util.HashMap;

public final class Ping extends Command {

    @Override
    public Command[] responses() {
        return new Pong[]{ new Pong() };
    }

    @Override
    public void execute(ClientCommandHandler client) {
        client.send(new Pong());
        System.out.println("Client: Ping!");
    }

    @Override
    public void execute(ServerCommandHandler server) {
        server.send(new Pong());
        System.out.println("Server: Ping!");
    }

    @Override
    public String name() {
        return "ping";
    }

    @Override
    public HashMap<String, String> attributes() {
        return null;
    }

    @Override
    public void attributes(HashMap<String, String> attributes) {
        return;
    }

    private final class Pong extends Command {

        @Override
        public Command[] responses() {
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
            return "pong";
        }

        @Override
        public HashMap<String, String> attributes() {
            return null;
        }

        @Override
        public void attributes(HashMap<String, String> attributes) {
            return;
        }
    }
}
