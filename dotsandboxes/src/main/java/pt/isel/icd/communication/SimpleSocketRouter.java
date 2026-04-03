package pt.isel.icd.communication;

import java.util.HashMap;
import java.util.logging.Logger;
import pt.isel.icd.user.management.Authenticator;

public class SimpleSocketRouter {

    private static final Logger logger = Logger.getLogger(
        SimpleSocketRouter.class.getName()
    );

    private final HashMap<
        Class<? extends SimpleSocketCommand<?>>,
        Object
    > controllers = new HashMap<>();
    private Authenticator authenticator;
    private SimpleSocketCommand<Object> command;

    public void addReceiver(
        Class<? extends SimpleSocketCommand<?>> commandType,
        Object controller
    ) {
        controllers.put(commandType, controller);
    }

    public void removeReceiver(
        Class<? extends SimpleSocketCommand<?>> commandType
    ) {
        controllers.remove(commandType);
    }

    public void setAuthenticator(Authenticator authenticator) {
        this.authenticator = authenticator;
    }

    @SuppressWarnings("unchecked")
    public void route(SimpleSocketCommand<?> newCommand) {
        command = (SimpleSocketCommand<Object>) newCommand;

        Class<?> commandType = command.getClass();
        Object receiver = controllers.get(commandType);

        if (receiver == null) {
            return;
        }

        if (command.requiresAuthentication() && authenticator != null) {
            boolean isAuthenticated = authenticator.isAuthenticated(
                command.socketId()
            );
            if (!isAuthenticated) {
                logger.warning(
                    String.format(
                        "Command %s from socket %s is not authenticated",
                        command.getClass().getSimpleName(),
                        command.socketId()
                    )
                );
                return;
            }
        }

        command.setReceiver(receiver);
        command.execute();
    }
}
