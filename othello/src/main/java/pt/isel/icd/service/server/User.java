package pt.isel.icd.service.server;

import pt.isel.icd.communication.Connection;
import pt.isel.icd.communication.commands.Command;

import java.io.IOException;

public class User {
    private final Connection connection;
    private boolean isAuthenticated = false;

    public User(Connection existingConnection) {
        connection = existingConnection;
    }

    public void write(Command command) throws IOException {
        connection.write(command);
    }

    public Command read() throws IOException {
        return connection.read();
    }

    public boolean isConnected() {
        return connection.isConnected();
    }
}
