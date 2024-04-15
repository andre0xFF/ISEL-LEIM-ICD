package pt.isel.icd.communication.commands;

import java.time.LocalDateTime;

/**
 * A ping command is a command that is sent to the server to check if it is still alive.
 */
public record PingCommand(LocalDateTime dateTime) implements Command {
}
