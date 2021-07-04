package patterns.behavioral.observer;

public interface Subscriber<T> {

    /**
     * Callback for context publish.
     * @param context
     */
    public void onPublish(T context);
}
