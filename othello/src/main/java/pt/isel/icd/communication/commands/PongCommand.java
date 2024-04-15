package pt.isel.icd.communication.commands;

import java.time.LocalDateTime;

/**
 * A pong command is a command that is sent to the client to check if it is still alive.
 */
public record PongCommand(LocalDateTime dateTime) implements Command {
}
