package models;

public interface CommunicationProtocol {

    public void send(Command cmd);
    public void close();
}
