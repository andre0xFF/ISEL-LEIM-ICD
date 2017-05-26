package client;

import protocol.Command;
import protocol.commands.Login;
import protocol.commands.Ping;
import protocol.Protocol.Encoding;
import protocol.Endpoint;
import protocol.Protocol;
import protocol.encodings.XML;

public abstract class Client extends Endpoint implements
        Command.CommonCommandHandler,
        Command.ClientCommandHandler {


    private final Encoding encoder;

    public Client(Protocol protocol) {
        super(protocol);
        this.encoder = new XML();

        super.send(new Ping());
        super.send(new Login("xpto", "pass"));
    }

    @Override
    protected Command[] commands() {
        return new Command[] {
                new Login(null, null),
                new Ping(),
        };
    }


    @Override
    public void on_command_received(Command command) {
        command.execute(this);
    }

    @Override
    protected Encoding encoder() {
        return this.encoder;
    }

    protected interface ClientUserInterface {

        void login_interface();
        void client_interface();
    }
}

//class ClientGUI implements Client.ClientUserInterface {
//
//    BankClient client;
//
//    public static void main(String[] args) {
//        ClientGUI gui = new ClientGUI();
//    }
//
//    public ClientGUI() {
//        try {
//            this.client = new BankClient();
//
//        } catch (IOException e) { }
//    }
//
//    @Override
//    public void login_interface() {
//
//    }
//
//    @Override
//    public void client_interface() {
//
//    }
//
//}