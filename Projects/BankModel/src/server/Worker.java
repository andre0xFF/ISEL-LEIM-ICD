package server;

import commands.Command;
import commands.Hello;
import commands.Logout;
import commands.Ping;
import protocol.Encoder;
import protocol.Endpoint;
import protocol.Protocol;

public abstract class Worker extends Endpoint implements
        Command.CommonCommandHandler,
        Command.ServerCommandHandler {

    private Encoder encoder;

    public Worker(Protocol protocol, Encoder encoder) {
        super(protocol);
        this.encoder = encoder;
    }

    @Override
    protected Command[] commands() {
        return new Command[] {
                new Hello(),
                new Ping(),
                new Logout(),
        };
    }

    @Override
    public void on_command_received(Command command) {
        command.execute(this);
    }

    @Override
    protected Encoder encoder() {
        return this.encoder;
    }

    @Override
    public void on_hello(Encoder encoder) {
        this.encoder = encoder;
    }
}
