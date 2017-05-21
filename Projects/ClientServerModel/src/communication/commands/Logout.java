package communication.commands;

import client.Client;
import communication.Protocol.Command;
import communication.Protocol.Encoder;

public class Logout implements Command {

    private static final String NAME = "Logout";

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
    public Command get_reponse() {
        return null;
    }
}
