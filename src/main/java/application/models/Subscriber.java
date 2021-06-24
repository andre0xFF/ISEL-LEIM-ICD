package application.models;

public interface Subscriber<TOPIC> {
    
    public void update(TOPIC context);
}
