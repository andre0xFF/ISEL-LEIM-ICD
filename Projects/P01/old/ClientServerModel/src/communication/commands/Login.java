package communication.commands;

import client.Client;
import communication.Command;
import communication.Communication;
import communication.Communication.Encoder;
import communication.encoders.XML;
import server.Server.Worker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Login implements Command {

    protected String username;
    protected String password;
    HashMap<Class, CommandEncoder> encoders = new HashMap<Class, CommandEncoder>() {{
        put(XML.class, new XMLLogin());
    }};

    public Login() { }

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public void execute(Worker worker) {
        String xpath = String.format("//user[@username=\"%s\"]/@password", this.username);
        String password = worker.get_database().get_value(xpath);

        Communication com = worker.get_communication();

        if (this.password.equals(password)) {
            com.send(new LoginSuccess());
        } else {
            com.send(new LoginDenied());
        }
    }

    @Override
    public void execute(Client client) { }

    @Override
    public void execute(CommandEventHandler event_handler) { }

    @Override
    public String encode(Encoder encoder) {
        return this.encoders.get(encoder.getClass()).encode(this);
    }

    @Override
    public String get_name() {
        return "login";
    }

    @Override
    public Collection<CommandEncoder> get_command_encoders() {
        return this.encoders.values();
    }

    @Override
    public ArrayList<Command> get_responses() {
        return new ArrayList< Command>() {{
           add(new LoginSuccess());
           add(new LoginDenied());
        }};
    }

    private class XMLLogin implements CommandEncoder {

        @Override
        public String encode(Command command) {
            Login login = (Login) command;
            return String.format(this.get_format(), login.get_name(), login.username, login.password);
        }

        @Override
        public Command decode(String message) {
            if (!message.contains("ping")) {
                return null;
            }

            String[] username = message.split("\"");
            return new Login(username[1], username[3]);
        }

        @Override
        public String get_format() {
            return "<%s username=\"%s\" password=\"%s\"/>";
        }
    }
}

class LoginSuccess implements Command {

    HashMap<Class, CommandEncoder> encoders = new HashMap<Class, CommandEncoder>() {{
        put(XML.class, new XMLOk());
    }};

    @Override
    public void execute(Worker worker) { }

    @Override
    public void execute(Client client) { }

    @Override
    public void execute(CommandEventHandler event_handler) {
        event_handler.on_login_success();
    }

    @Override
    public String encode(Encoder encoder) {
        return this.encoders.get(encoder.getClass()).encode(this);
    }

    @Override
    public String get_name() {
        return "login_success";
    }

    @Override
    public Collection<CommandEncoder> get_command_encoders() {
        return this.encoders.values();
    }

    @Override
    public ArrayList<Command> get_responses() {
        return null;
    }

    private class XMLOk implements CommandEncoder {

        @Override
        public String encode(Command command) {
            LoginSuccess ok = (LoginSuccess) command;
            return String.format(this.get_format(), ok.get_name());
        }

        @Override
        public Command decode(String message) {
            return message.contains("ok") ? new LoginSuccess() : null;
        }

        @Override
        public String get_format() {
            return "<%s/>";
        }
    }
}

class LoginDenied implements Command {

    @Override
    public void execute(Worker worker) { }

    @Override
    public void execute(Client client) { }

    @Override
    public void execute(CommandEventHandler event_handler) {
        event_handler.on_login_denied();
    }

    @Override
    public String encode(Encoder encoder) {
        return null;
    }

    @Override
    public String get_name() {
        return "login_denied";
    }

    @Override
    public Collection<CommandEncoder> get_command_encoders() {
        return null;
    }

    @Override
    public ArrayList<Command> get_responses() {
        return null;
    }
}