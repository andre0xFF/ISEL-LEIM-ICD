package communication;

import client.Client;
import communication.commands.Hello;
import communication.commands.Login;
import communication.commands.Ping;
import communication.Communication.Encoder;
import server.Server.Worker;

import java.util.ArrayList;
import java.util.Collection;


public interface Command {

    default Command decode(String message) {
        Collection<CommandEncoder> command_encoders = this.get_command_encoders();

        for (CommandEncoder encoder : command_encoders) {
            Command match = encoder.decode(message);

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
            add(new Login());
        }};
    }

    void execute(Worker worker);
    void execute(Client client);
    void execute(CommandEventHandler event_handler);
    String encode(Encoder encoder);
    String get_name();
    Collection<CommandEncoder> get_command_encoders();
    ArrayList<Command> get_responses();

    interface CommandEncoder {

        String encode(Command command);
        Command decode(String message);
        String get_format();
    }

    interface CommandEventHandler {

        void on_login_success();
        void on_login_denied();
    }
}
