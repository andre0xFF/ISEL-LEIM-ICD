package models;

import communications.encoders.XML;
import models.Server.InternalClient;

import java.net.Socket;
import java.util.HashMap;

public interface CommunicationProtocol {

    boolean open(Socket socket);
    void close();
    void send(String msg);
    void send(Command cmd);
    Command receive();

    interface Command {

        void execute(InternalClient client);
        String get_name();
        Command get_response();

        String encode(Encoder encoder);
        Command decode(String msg);

        static Command decode(HashMap<String, EncodedCommand> encoders, String msg) {
            for (EncodedCommand encoder : encoders.values()) {
                Command new_cmd = encoder.decode(msg);

                if (new_cmd != null) {
                    return new_cmd;
                }
            }

            return null;
        }

        interface EncodedCommand {

            String encode(Command cmd);
            Command decode(String msg);
        }

    }

    interface Encoder {

        Encoder DEFAULT_ENCODING = new XML();
        String get_name();
        String encode(Command cmd);
        Command decode(String msg);

        static Command decode(Command[] cmds, String msg) {
            for (Command cmd : cmds) {
                Command new_cmd = cmd.decode(msg);

                if (new_cmd != null) {
                    return new_cmd;
                }
            }

            return null;
        }
    }
}
