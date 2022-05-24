package controllers.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import controllers.Controller;
import controllers.ServerController;

public class PingCommand implements ServerControllerCommand {

    private Controller controller;

    @JsonCreator
    public PingCommand() {

    }

    @Override
    public void execute() {
        System.out.println("Ping!");
        controller.sendCommand(new PongCommand());
    }

    @Override
    public void setController(ServerController controller) {
        this.controller = controller;
    }
}

