import database.Database;
import protocol.Protocol;
import protocol.encodings.XML;
import server.Worker;

public final class BankWorker extends Worker {

    public BankWorker(Protocol protocol, Database database) {
        super(protocol, new XML(), database);
    }

    @Override
    public void on_ping() {

    }

    @Override
    public void on_pong() {

    }

    @Override
    public void on_login_request() {

    }

    @Override
    public void on_logout_request() {

    }

    @Override
    public void on_disconnect() {

    }

    @Override
    public void on_connect() {

    }
}
