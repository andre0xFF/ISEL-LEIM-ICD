package commands;

import protocol.Encoder;
import protocol.Encoder.Encoding;

public abstract class Command {

    protected abstract Encoding[] encodings();
    protected abstract Command response();
    public abstract void execute(ClientCommandHandler client);
    public abstract void execute(ServerCommandHandler worker);
    public abstract String name();

    public String encode(Encoder encoder) {
        Encoding encoding = this.parse(encoder);

        if (encoding != null) {
            return encoding.encode(this);
        }

        // The command does not contain the specified encoding
        return null;
    }

    public Command decode(String encoded_command, Encoder encoder) {
        Encoding encoding = this.parse(encoder);

        if (encoding != null) {
            return encoding.decode(encoded_command);
        }

        return null;
    }

    private Encoding parse(Encoder encoder) {
        // Loop all encodings to find an encoder match
        for (Encoding encoding : this.encodings()) {
            if (encoding.encoder().equals(encoder)) {
                return encoding;
            }
        }

        return null;
    }

    public Command decode(String encoded_command) {
        // Loop all command's encodings to see if any can
        // decode the message
        for (Encoding encoding : this.encodings()) {
            Command command = encoding.decode(encoded_command);

            if (command != null) {
                return command;
            }
        }

        // The message was not decode, then do the same
        // recursively for all possible responses
        Command response = this.response();

        if (response != null) {
            return response.decode(encoded_command);
        }

        // The command nor the command's response were
        // able to decode the message
        return null;
    }

    public interface CommonCommandHandler {

        void on_command_received(Command command);
        void on_ping();
        void on_pong();
        void terminate();
    }

    public interface ClientCommandHandler {

        void on_login_success();
        void send(Command command);
        void terminate();
    }

    public interface ServerCommandHandler {

        void on_hello(Encoder encoder);
        void on_login_request();
        void on_logout_request();
        void send(Command command);
        void terminate();
    }
}
