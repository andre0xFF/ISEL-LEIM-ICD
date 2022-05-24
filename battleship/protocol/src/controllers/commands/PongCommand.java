package controllers.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import controllers.Controller;

public class PongCommand implements CommandController {
    private Controller controller;

    @JsonCreator
    public PongCommand() {

    }

    @Override
    public void execute() {
        System.out.println("Pong!");
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }
}
