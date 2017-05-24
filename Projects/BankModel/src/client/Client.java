package client;

import commands.Command;
import commands.Hello;
import commands.Ping;
import protocol.Encoder;
import protocol.Endpoint;
import protocol.Protocol;

public abstract class Client extends Endpoint implements
        Command.CommonCommandHandler,
        Command.ClientCommandHandler {


    private final Encoder encoder;

    public Client(Protocol protocol) {
        super(protocol);
        this.encoder = new Encoder.XML();

        // Send Hello command so that the server knowns
        // which encoding to use
        super.send(new Hello());
        super.send(new Ping());
    }

    @Override
    protected Command[] commands() {
        return new Command[] {
                new Ping(),
        };
    }


    @Override
    public void on_command_received(Command command) {
        command.execute(this);
    }

    @Override
    protected Encoder encoder() {
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