package communication;

import communication.commands.Hello;
import communication.commands.Ping;
import communication.encoders.XML;
import server.Client;
import server.Server.Worker;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface Communication {

    void send(String message);
    void send(Command command);
    void set_encoder(Encoder encoder);
    Command receive();

    boolean check();
    void terminate();
    boolean execute(Socket socket);

    static boolean execute(Communication com) {
        if (!com.check()) {
            return false;
        }

        ArrayList<Command> commands = Command.get();
        Communication.Encoder encoder = new XML();
        encoder.set(commands);
        com.set_encoder(encoder);

        return true;
    }

    interface Command {

        void execute(Worker worker);
        void execute(Client client);
        String encode(Encoder encoder);
        String get_name();
        Command decode(String message);
        Command get_response();

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

        void add(Command command);
        void set(ArrayList<Command> commands);
        String encode(Command command);
        Command decode(String message);
        ArrayList<Command> get_commands();

        static Command decode(List<Command> commands, String message) {
            for (Command command : commands) {
                Command match = command.decode(message);

                if (match != null) {
                    return match;
                }

                Command response = command.get_response();

                if (response == null) {
                    continue;
                }

                List responses = new ArrayList<Command>() {{ add(response); }};
                match = Encoder.decode(responses, message);

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
