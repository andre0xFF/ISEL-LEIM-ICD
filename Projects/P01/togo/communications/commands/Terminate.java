//package communications.commands;
//
//import communications.encoders.XML;
//import models.CommunicationProtocol;
//import models.CommunicationProtocol.Command;
//import models.CommunicationProtocol.Encoder;
//import models.Server;
//import models.Server.InternalClient;
//
//import java.util.HashMap;
//
//public class Terminate implements Command {
//
//
//    @Override
//    public void execute(InternalClient client) {
//        client.disconnect();
//    }
//
//    @Override
//    public String get_name() {
//        return "Terminate";
//    }
//
//    @Override
//    public Command get_response() {
//        return null;
//    }
//
//    @Override
//    public String encode(Encoder encoder) {
//        return null;
//    }
//
//    @Override
//    public Command decode(Encoder encoder, String msg) {
//        return null;
//    }
//
//    @Override
//    public HashMap<String, EncodedCommand> get_encodings() {
//        return null;
//    }
//}
