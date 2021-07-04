package patterns.behavioral.command;

/**
 * Stores the actual command.
 */
public final class Invoker {

    private Command<?> command;

    /**
     * Sets the command.
     * @param command
     */
    public void set(Command<?> command) {
        this.command = command;
    }

    /**
     * Executes the command.
     */
    public void execute() {
        this.command.execute();
    }
}
