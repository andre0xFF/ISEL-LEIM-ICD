package protocol;

import commands.Command;
import commands.Logout;

public abstract class Endpoint implements Command.CommonCommandHandler, Protocol.ProtocolHandler {

    private Protocol protocol;
    protected abstract Command[] commands();
    protected abstract Encoder encoder();

    public Endpoint(Protocol protocol) {
        this.protocol = protocol;
        this.protocol.add_handler(this);
    }

    public boolean is_connected() {
        return this.protocol.is_connected();
    }

    public void terminate() {
        this.send(new Logout());
        this.protocol.terminate();
    }

    public void send(Command command) {
        Encoder encoder = this.encoder();
        String message = command.encode(encoder);

        if (message == null) {
            return;
        }

        String name = command.name();
        String wrap = encoder.wrap(message, name);

        this.protocol.send(wrap);
    }

    @Override
    public void on_received_message(String message) {
        Encoder encoder = this.encoder();
        String unwrap = encoder.unwrap(message);

        Command[] commands = this.commands();

        for (Command command : commands) {
            Command match = command.decode(unwrap);

            if (match != null) {
                this.on_command_received(match);
                break;
            }
        }
    }
}
