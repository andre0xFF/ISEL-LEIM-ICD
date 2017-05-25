package commands;

import protocol.Encoding;

import java.util.HashMap;

public abstract class Command implements Encoding.Encoder {

    public abstract String name();
    public abstract Command[] responses();
    public abstract void execute(ClientCommandHandler client);
    public abstract void execute(ServerCommandHandler worker);

    public static void main(String[] args) {
        Login a = new Login("xpto", "cool");
        Encoding encoding = new Encoding.XML();
        String m = encoding.encode(a.name(), a.attributes());

        HashMap<String, String> t = encoding.decode(a.name(), m);
        Login b = new Login(null, null);
        b.attributes(t);

        String wrap = encoding.wrap("command", a.name(), m);
        String unwrap = encoding.unwrap("command", wrap);

        new Login(null).search("ok");
    }

    public Command search(String command_name) {
        if (this.name().equals(command_name)) {
            return this;
        }

        Command[] responses = this.responses();

        if (responses != null) {
            for (Command response : responses) {
                return response.search(command_name);
            }
        }

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

        void on_hello(Encoding encoder);
        void on_login_request();
        void on_logout_request();
        void send(Command command);
        void terminate();
    }
}
