package pt.isel.icd.communication;

public interface Middleware {

    boolean handle(ConnectionCommand<?> command);
}
