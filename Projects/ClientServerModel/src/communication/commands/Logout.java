package communication.commands;

import communication.Communication.Command;
import communication.Communication.Encoder;
import server.Client;
import server.Server.Worker;

public class Logout implements Command {

    private static final String NAME = "Logout";

    @Override
    public void execute(Worker client) {

    }

    @Override
    public void execute(Client client) {

    }

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
        return Logout.NAME;
    }

    @Override
    public Command get_response() {
        return null;
    }
}
