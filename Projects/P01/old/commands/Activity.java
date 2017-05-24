package commands;

import client.Client;
import communication.Command;
import communication.Communication.Encoder;
import server.Server.Worker;

import java.util.ArrayList;

public class Activity implements Command {

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
        return null;
    }

    @Override
    public ArrayList<Command> get_responses() {
        return null;
    }
}
