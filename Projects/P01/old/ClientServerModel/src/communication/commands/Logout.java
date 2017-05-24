package communication.commands;

import communication.Command;
import communication.Communication;
import communication.Communication.Encoder;
import client.Client;
import server.Server;
import server.Server.Worker;

import java.util.ArrayList;
import java.util.Collection;

public class Logout extends Command {


    @Override
    public void execute(Worker worker) {

    }

    @Override
    public void execute(Client client) {

    }

    @Override
    public void execute(CommandEventHandler event_handler) {

    }

    @Override
    public String encode(Encoder encoder) {
        return null;
    }

    @Override
    public String get_name() {
        return "logout";
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
