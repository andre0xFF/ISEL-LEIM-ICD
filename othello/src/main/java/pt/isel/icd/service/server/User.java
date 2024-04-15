package pt.isel.icd.service.server;

import pt.isel.icd.messaging.Connection;
import pt.isel.icd.messaging.messages.Message;

import java.io.IOException;

public class User {
    private final Connection connection;
    private boolean isAuthenticated = false;

    public User(Connection existingConnection) {
        connection = existingConnection;
    }

    public void write(Message message) throws IOException {
        connection.write(message);
    }

    public Message read() throws IOException {
        return connection.read();
    }

    public boolean isConnected() {
        return connection.isConnected();
    }
}
