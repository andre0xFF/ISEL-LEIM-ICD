package patterns.behavioral.chain;

public final class BaseHandler<T> implements Handler<T> {

    private Handler<T> next;

    @Override
    public void setNextHandler(Handler<T> handler) {
        this.next = handler;
    }

    @Override
    public void handle(T request) {
        if (this.next == null) {
            return;
        }

        this.next.handle(request);
    }
}
