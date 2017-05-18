package models;

import commands.*;

import java.net.Socket;
import java.util.HashMap;

public interface CommunicationProtocol {

    public boolean open(Socket socket);
    public void close();
    public void send(Command cmd);
    public Command receive();

    public interface Command {
        public void execute();
        public String get_name();
        public Command get_response();

        public static HashMap<String, Command> list = new HashMap<String, Command>() {{
            put(new Ping().get_name(), new Ping());
            put(new Pong().get_name(), new Pong());
            put(new Terminate().get_name(), new Terminate());
            put(new Hello().get_name(), new Hello());
        }};
    }

    public interface Encoding {

        public String get_name();
        public String encode(Command cmd);
        public Command decode(String msg);
        public static Encoding DEFAULT_ENCODING = Encoding.list.get("XML");

        public static HashMap<String, Encoding> list = new HashMap<String, Encoding>() {{
            put(new PlainText().get_name(), new PlainText());
            put(new XML().get_name(), new XML());
            put(new JSON().get_name(), new JSON());
        }};
    }

    public class PlainText implements Encoding {

        @Override
        public String get_name() {
            return "PlainText";
        }

        @Override
        public String encode(Command cmd) {
            return cmd.get_name();
        }

        @Override
        public Command decode(String msg) {
            return Command.list.get(msg.split(" ")[0]);
        }

    }

    public class XML implements Encoding {

        @Override
        public String get_name() {
            return "XML";
        }

        @Override
        public String encode(Command cmd) {
            return null;
        }

        @Override
        public Command decode(String msg) {
            return null;
        }
    }

    public class JSON implements Encoding {

        @Override
        public String get_name() {
            return "JSON";
        }

        @Override
        public String encode(Command cmd) {
            return null;
        }

        @Override
        public Command decode(String msg) {
            return null;
        }
    }
}
