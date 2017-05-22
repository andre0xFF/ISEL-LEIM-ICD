package communication;

import communication.encoders.XML;

import java.net.Socket;
import java.util.ArrayList;
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
        Encoder encoder = new XML();
        encoder.set(commands);
        com.set_encoder(encoder);

        return true;
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

                ArrayList<Command> responses = command.get_responses();

                if (responses == null) {
                    continue;
                }

                match = Encoder.decode(responses, message);

                if (match != null) {
                    return match;
                }
            }

            return null;
        }
    }
}
