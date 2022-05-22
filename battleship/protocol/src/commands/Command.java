package commands;

import controllers.Controller;

public interface Command {

    CommandResult execute(Controller controller);
}
