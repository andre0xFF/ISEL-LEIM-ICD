package commands;

import models.Command;

public class Ping implements Command {

    private String name = "Ping";
    private Command response = new Pong();

    @Override
    public void execute() {
        return;
    }

    @Override
    public String get_name() {
        return this.name;
    }
}

class Pong implements Command {

    private String name = "Pong!";

    @Override
    public void execute() {
        return;
    }

    @Override
    public String get_name() {
        return this.name;
    }
}
