package client;

import communication.Protocol;

import java.net.Socket;

public interface Client {

    void connect(Socket server);
    void disconnect();
    boolean is_active();
    void send(Protocol.Command cmd);
    Protocol.Command receive();
    void set_encoder(Protocol.Encoder encoder);
}

