package protocol;

public abstract class Command implements Protocol.Encoder {

    public abstract void execute(ClientCommandHandler client);
    public abstract void execute(ServerCommandHandler worker);
    public abstract String name();
    public abstract Command[] responses();

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

    public abstract class Response extends Command {
        public abstract Command master();
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
        void send(Response response);
        void terminate();
    }

    public interface ServerCommandHandler {

        void on_login_request();
        void on_logout_request();
        void send(Command command);
        void send(Response response);
        void terminate();
    }
}
