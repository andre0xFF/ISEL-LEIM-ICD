package pt.isel.icd.communication;

import java.util.List;

/**
 * Handles communication requests and responses.
 * Each controller declares the commands it can handle.
 */
public interface Controller {
    List<Class<? extends Command<?>>> commandsList();
}
