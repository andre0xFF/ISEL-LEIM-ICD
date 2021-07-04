package patterns.behavioral.chain;

public interface Handler<T> {
    
    public void setNextHandler(Handler<T> handler);

    public void handle(T request);
}
