package database;

import lib.XMLDoc;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;


public abstract class Database {

    private final Document document;
    private final String xsd_path;

    public Database(Document document, String xsd_path) {
        this.document = document;
        this.xsd_path = xsd_path;
    }

    public NodeList get_nodes(String xpath) {
        NodeList list;

        synchronized (this.document) {
            list = XMLDoc.getXPath(xpath, this.document);
        }

        return list;
    }

    public String get_value(String xpath) {
        String result;

        synchronized (this.document) {
            result = XMLDoc.getXPathV(xpath, this.document);
        }

        return result;
    }
}