package controllers;

import behavioral.command.Command;

public interface Controller {

    void acceptCommunication();
    void sendCommand(Command<?> command);
}
