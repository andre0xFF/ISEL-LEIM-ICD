package server;

import protocol.Command;
import protocol.commands.Login;
import protocol.commands.Ping;
import protocol.Protocol.Encoding;
import database.Database;
import protocol.Endpoint;
import protocol.Protocol;

public abstract class Worker extends Endpoint implements
        Command.CommonCommandHandler,
        Command.ServerCommandHandler {

    private Encoding encoder;
    private Database database;

    public Worker(Protocol protocol, Encoding encoder, Database database) {
        super(protocol);
        this.encoder = encoder;
        this.database = database;
    }

    @Override
    protected Command[] commands() {
        return new Command[] {
                new Ping(),
                new Login(null, null),
                new Login.Logout(null),
        };
    }

    @Override
    public void on_command_received(Command command) {
        command.execute(this);
    }

    @Override
    protected Encoding encoder() {
        return this.encoder;
    }
}
