package application;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

import application.protocol.Format;
import application.protocol.Request;
import application.protocol.formats.XML;
import application.protocol.requests.Echo;
import application.protocol.requests.Disconnect;

public class Server {

    //
    // Functionality
    //
    ArrayList<ServerThread> threads = new ArrayList<>();
    boolean isActive;
    int port = 9999;
    ServerSocket socket;
    Format format = new XML();

    private void stop() {
        this.isActive = false;
    }

    private void start() {
        try {
            this.socket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.isActive = true;

        while(isActive) {
            try {
                this.threads.add(new ServerThread(this, socket.accept()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //
    // Interface
    //
    public Server() {
        this.format.register(new Echo());
        this.format.register(new Disconnect());
        
        this.start();
    }

    public void send(Request request, User user) {
        for (ServerThread thread : this.threads) {
            if (thread.get_user().equals(user)) {
                thread.send(request);
            }
        }
    }

    protected Format get_format() {
        return this.format;
    }
}
