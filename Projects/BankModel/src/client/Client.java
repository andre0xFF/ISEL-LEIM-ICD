package client;

import commands.Command;
import commands.Login;
import commands.Ping;
import protocol.Encoding;
import protocol.Endpoint;
import protocol.Protocol;

public abstract class Client extends Endpoint implements
        Command.CommonCommandHandler,
        Command.ClientCommandHandler {


    private final Encoding encoder;

    public Client(Protocol protocol) {
        super(protocol);
        this.encoder = new Encoding.XML();

//        super.send(new Ping());
        super.send(new Login("xpto", "pass"));
        super.send(new Login.Logout("xpto"));
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