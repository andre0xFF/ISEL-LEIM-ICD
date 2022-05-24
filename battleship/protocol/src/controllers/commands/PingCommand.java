package controllers.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import controllers.Controller;

public class PingCommand implements CommandController {

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
    public void setController(Controller controller) {
        this.controller = controller;
    }
}

