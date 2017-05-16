package models;

import communications.BankCOM;

public interface Client {

    public final static String SERVER_HOSTNAME = "127.0.0.1";
    public final static int SERVER_PORT = Server.DEFAULT_PORT;

    public static BankCOM connect(String hostname, int port) {
        return new BankCOM(hostname, port);
    }

    public static BankCOM connect() {
        return Client.connect(Client.SERVER_HOSTNAME, Client.SERVER_PORT);
    }

    public static boolean validate(String host, int port) {
        return new Server.Host(host, port).validate();
    }


}
