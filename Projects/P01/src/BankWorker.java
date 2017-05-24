import protocol.Encoder;
import protocol.Protocol;
import server.Worker;

public class BankWorker extends Worker {

    public BankWorker(Protocol protocol) {
        super(protocol, new Encoder.XML());
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
