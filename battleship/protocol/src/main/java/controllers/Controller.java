package controllers;

import behavioral.command.Command;
import controllers.commands.CommandController;

public interface Controller {

    void acceptCommunication();
    void sendCommand(CommandController command);
}
