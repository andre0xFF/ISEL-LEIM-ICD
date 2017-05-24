import lib.XMLDoc;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import server.Database;

public class BankDatabase implements Database {

    private String xml_path;
    private String xsd_path;
    private Document xml;

    public static void main(String[] args) {
        BankDatabase db = new BankDatabase();
        db.execute("./P01/db/database.xml", "./P01/db/database.xsd");
        db.check();
        String res = db.get_value("//user[@login=\"Ex01\"]/@password");
        System.out.println(res);
    }

    @Override
    public void execute(String xml_path, String xsd_path) {
        this.xml_path = xml_path;
        this.xsd_path = xsd_path;
        this.xml = XMLDoc.parseFile(xml_path);
    }

    @Override
    public boolean check() {
        return XMLDoc.validDocXSD(this.xml_path, this.xsd_path);
    }

    @Override
    public void terminate() { }

    @Override
    public NodeList get_nodes(String xpath) {
        NodeList list;

        synchronized (this.xml) {
            list = XMLDoc.getXPath(xpath, this.xml);
        }

        return list;
    }

    @Override
    public String get_value(String xpath) {
        String result;

        synchronized (this.xml) {
            result = XMLDoc.getXPathV(xpath, this.xml);
        }

        return result;
    }
}
