package behavioral.command;

/**
 * An object, like a dialog button, that invokes the actual command.
 */
public interface Invoker {
    void setCommand(Command<?> command);

    void execute();
}
