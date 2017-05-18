package models;

import communications.BankCOM;
import models.CommunicationProtocol.*;
import java.io.IOException;
import java.net.Socket;

public interface Client {

    public final static String SERVER_HOSTNAME = "127.0.0.1";
    public final static int SERVER_PORT = Server.DEFAULT_PORT;
    public final static Encoding DEFAULT_ENCONDIG = new PlainText();

    public static BankCOM connect(String server_ip, int server_port) {
        BankCOM com = new BankCOM(Client.DEFAULT_ENCONDIG);

        try {
            Socket socket = new Socket(server_ip, server_port);
            com.open(socket);
        } catch (IOException e) { }

        return com;
    }
}
