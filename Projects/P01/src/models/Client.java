package models;

import communications.BankCOM;

import java.io.IOException;
import java.net.Socket;

public interface Client {

    public final static String SERVER_HOSTNAME = "127.0.0.1";
    public final static int SERVER_PORT = Server.DEFAULT_PORT;

    public static BankCOM connect(String hostname, int port) {
        try {
            return new BankCOM(new Socket(hostname, port));
        } catch (IOException e) {
            return null;
        }
    }

    public static BankCOM connect() {
        return Client.connect(Client.SERVER_HOSTNAME, Client.SERVER_PORT);
    }
}
