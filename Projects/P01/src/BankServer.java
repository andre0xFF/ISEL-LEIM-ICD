import lib.XMLDoc;
import org.w3c.dom.NodeList;
import server.Server;
import server.Worker;

import java.net.Socket;

public class BankServer extends Server {

    protected static final String XML_PATH = "./P01/db/database.xml";
    protected static final String XSD_PATH = "./P01/db/database.xsd";

    private BankDatabase database = new BankDatabase(XMLDoc.parseFile(XML_PATH), XSD_PATH);

    @Override
    protected Worker worker(Socket socket) {
        return new BankWorker(
                new BankProtocol(socket),
                new BankDatabase(XMLDoc.parseFile(XML_PATH), XSD_PATH)
        );
    }

    public static void main(String[] args) {
        BankServer server = new BankServer();
        BankGate gate = new BankGate(server, 5555);


//        String res = this.database.get_value("//user[@username=\"Ex01\"]/@password");
//        System.out.println(res);
//        NodeList nodes = XMLDoc.getXPath("<commands><command name=\"ping\"><ping/></command><command name=\"pong\"><pong/></command></commands>", "//command/@name");
    }
}
