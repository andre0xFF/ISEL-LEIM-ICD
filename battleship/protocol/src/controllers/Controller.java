package controllers;

import controllers.commands.CommandController;
import sessions.Server;

public interface Controller {

    void acceptCommunication();
    void sendCommand(CommandController command);
}
