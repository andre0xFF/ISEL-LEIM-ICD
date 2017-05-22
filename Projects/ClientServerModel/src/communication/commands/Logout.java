package communication.commands;

import communication.Command;
import communication.Communication.Encoder;
import client.Client;
import server.Server.Worker;

import java.util.ArrayList;

public class Logout implements Command {

    @Override
    public void execute(Worker worker) {
        worker.terminate();
    }

    @Override
    public void execute(Client client) { }

    @Override
    public String encode(Encoder encoder) {
        return null;
    }

    @Override
    public Command decode(String message) {
        return null;
    }

    @Override
    public String get_name() {
        return "logout";
    }

    @Override
    public ArrayList<Command> get_responses() {
        return null;
    }
}
