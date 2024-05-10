package pt.isel.icd.communication;

public interface SimpleSocketMiddleware {

    boolean handle(SimpleSocketCommand<?> command);
}
