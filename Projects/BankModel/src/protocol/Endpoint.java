package protocol;

import commands.Command;

import java.util.HashMap;

public abstract class Endpoint implements Command.CommonCommandHandler, Protocol.ProtocolHandler {

    private Protocol protocol;
    private Encoding encoding = new Encoding.XML();
    protected abstract Command[] commands();
    protected abstract Encoding encoder();

    public Endpoint(Protocol protocol) {
        this.protocol = protocol;
        this.protocol.add_handler(this);
    }

    public boolean is_connected() {
        return this.protocol.is_connected();
    }

    public void terminate() {
        this.protocol.terminate();
    }

    public void send(Command command) {
        String message = this.encode(command);
        this.protocol.send(message);
    }

    private String encode(Command command) {
        String name = command.name();
        String message = this.encoding.encode(name, command.attributes());
        String wrapped_message = this.encoding.wrap("command", name, message);

        // Wrap the command encoding with command element
        return String.format("<command name=\"%s\">%s</command>", name, message);
    }

    private Command decode(String encoded_message) {
        encoded_message = this.encoding.unwrap("command", encoded_message);
        String command_name = this.encoding.decode(encoded_message);

        Command[] commands = this.commands();

        for (Command command : commands) {
            Command match = command.search(command_name);

            if (match != null) {
                HashMap<String, String> att = this.encoding.decode(match.name(), encoded_message);
                match.attributes(att);
                return match;
            }
        }

        return null;
    }

    @Override
    public void on_received_message(String message) {
        Command match = this.decode(message);

        if (match != null) {
            this.on_command_received(match);
        }
    }
}
