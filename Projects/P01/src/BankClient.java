import client.Client;

import java.io.IOException;
import java.net.Socket;

class BankClient extends Client {

    public BankClient() throws IOException {
        super(
                new BankProtocol(new Socket("127.0.0.1", 5555)));
    }

    @Override
    public void on_ping() { }

    @Override
    public void on_pong() { }

    @Override
    public void on_login_success() {

    }

    @Override
    public void on_disconnect() {

    }

    @Override
    public void on_connect() {

    }

    public static void main(String[] args) {
        try {
            BankClient client = new BankClient();

        } catch (IOException e) { }
    }
}
