package protocol.commands;

import protocol.Command;

import java.util.HashMap;

public class Login extends Command {

    private String username;
    private String password;

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Login(HashMap<String, String> attributes) {
        this.attributes(attributes);
    }

    @Override
    public Command[] responses() {
        return new Command[] {
                new Ok(this),
                new Denied(this),
        };
    }

    @Override
    public void execute(ClientCommandHandler client) {

    }

    @Override
    public void execute(ServerCommandHandler worker) {
        worker.send((Response) new Ok(this));
    }

    @Override
    public String name() {
        return "login";
    }

    @Override
    public HashMap<String, String> attributes() {
        HashMap<String, String> att = new HashMap<>();
        att.put("username", this.username);
        att.put("password", this.password);

        return att;
    }

    @Override
    public void attributes(HashMap<String, String> attributes) {
        if (attributes != null && attributes.size() > 0) {
            this.username = attributes.get("username");
            this.password = attributes.get("password");
        }
    }

    // The Logout command is not a response to the Login command
    // but logging out doesn't make sense without logging in
    public static final class Logout extends Command {

        private String username;

        public Logout(String username) {
            this.username = username;
        }

        @Override
        public Command[] responses() {
            return null;
        }

        @Override
        public void execute(ClientCommandHandler client) {

        }

        @Override
        public void execute(ServerCommandHandler worker) {

        }

        @Override
        public String name() {
            return "logout";
        }

        @Override
        public HashMap<String, String> attributes() {
            return null;
        }

        @Override
        public void attributes(HashMap<String, String> attributes) {
            if (attributes != null && attributes.size() > 0) {
                this.username = attributes.get("username");
            }
        }
    }

    private class Ok extends Response  {

        private Command master;

        public Ok(Command command) {
            master = command;
        }

        @Override
        public void execute(ClientCommandHandler client) {

        }

        @Override
        public void execute(ServerCommandHandler worker) {

        }

        @Override
        public String name() {
            return "ok";
        }

        @Override
        public Command[] responses() {
            return null;
        }

        @Override
        public HashMap<String, String> attributes() {
            return null;
        }

        @Override
        public void attributes(HashMap<String, String> attributes) {

        }

        @Override
        public Command master() {
            return this.master;
        }
    }

    private class Denied extends Response {

        private Command master;

        public Denied(Command command) {
            master = command;
        }

        @Override
        public void execute(ClientCommandHandler client) {

        }

        @Override
        public void execute(ServerCommandHandler worker) {

        }

        @Override
        public String name() {
            return "you_shall_not_pass";
        }

        @Override
        public Command[] responses() {
            return null;
        }

        @Override
        public HashMap<String, String> attributes() {
            return null;
        }

        @Override
        public void attributes(HashMap<String, String> attributes) {

        }

        @Override
        public Command master() {
            return this.master;
        }
    }
}
