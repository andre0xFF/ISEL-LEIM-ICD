package controllers.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import controllers.ClientController;

public class PongCommand implements ClientControllerCommand {
    private ClientController controller;

    @JsonCreator
    public PongCommand() {

    }

    @Override
    public void execute() {
        System.out.println("Pong!");
    }

    @Override
    public void setController(ClientController controller) {
        this.controller = controller;
    }
}
