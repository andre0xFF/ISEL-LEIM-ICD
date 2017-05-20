package communication;

import client.Client;
import communication.commands.Hello;
import communication.commands.Ping;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface Protocol {

    boolean open(Socket socket);
    void close();

    boolean is_open();

    void send(String message);
    void send(Command command);

    Command receive();

    void set_encoder(Encoder encoder);

    interface Command {

        void execute(Client client);
        String encode(Encoder encoder);
        Command decode(String message);

        static Command decode(Collection<CommandEncoder> cmd_encoders, String message) {
            for (CommandEncoder cmd_encoder : cmd_encoders) {
                Command match = cmd_encoder.decode(message);

                if (match != null) {
                    return match;
                }
            }

            return null;
        }

        static ArrayList<Command> get() {
            return new ArrayList<Command>() {{
                add(new Hello());
                add(new Ping());
            }};
        }
    }

    interface Encoder {

        String encode(Command command);
        Command decode(String message);
        void add(Command command);
        void set(ArrayList<Command> commands);
        ArrayList<Command> get();

        static Command decode(List<Command> commands, String message) {
            for (Command command : commands) {
                Command match = command.decode(message);

                if (match != null) {
                    return match;
                }
            }

            return null;
        }
    }

    interface CommandEncoder {
        String encode(Command command);
        Command decode(String message);
    }
}
