package server;

import communications.BankCOM;
import models.Client;
import models.CommunicationProtocol;
import models.Server;

import java.net.Socket;

public class BankInternalClient implements Server.InternalClient, Runnable {

    private BankCOM com;
    private CommunicationProtocol.Encoding encoding = Client.DEFAULT_ENCONDIG;
    private boolean active;

    @Override
    public void set_active(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean is_active() {
        return this.active;
    }

    @Override
    public void run() {
        System.out.println("Hello world! This is BankInternalClient");

        while(this.active) {
            CommunicationProtocol.Command cmd = this.com.receive();

            if (cmd == null) {
                break;
            }

            System.out.println(cmd.get_name());
            cmd.execute();
            CommunicationProtocol.Command rsp = cmd.get_response();

            if (rsp == null) {
                continue;
            }

            this.com.send(rsp);
        }

        this.com.close();
        System.out.println("Client disconnected");
    }

    @Override
    public void execute(Socket socket) {
        this.com = new BankCOM(this.encoding);
        this.com.open(socket);
        this.active = true;
        new Thread(this).start();
    }
}
