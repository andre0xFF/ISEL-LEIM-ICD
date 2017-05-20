package models;

public interface Client {

    String SERVER_HOSTNAME = "127.0.0.1";
    int SERVER_PORT = Server.DEFAULT_PORT;
    void connect(String hostname, int port);
    void disconnect();
}
