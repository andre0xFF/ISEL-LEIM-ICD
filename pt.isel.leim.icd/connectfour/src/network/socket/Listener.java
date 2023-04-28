package network.socket;

/**
 * Represents a listener.
 * @param <T> The type of the message.
 */
public interface Listener<T> {

    /**
     * Called when a message is received.
     * @param message The message.
     */
    void onMessage(T message);
}
