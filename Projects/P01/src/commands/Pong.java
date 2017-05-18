package commands;

import models.CommunicationProtocol.*;

public class Pong implements Command {

    public static final String NAME = "Pong";

    @Override
    public void execute() { }

    @Override
    public String get_name() {
        return Pong.NAME;
    }

    @Override
    public Command get_response() {
        return null;
    }
}
