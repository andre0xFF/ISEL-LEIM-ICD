import protocol.Protocol;

import java.net.Socket;

class BankProtocol extends Protocol {

    public BankProtocol(Socket socket) {
        super(socket);
    }
}
