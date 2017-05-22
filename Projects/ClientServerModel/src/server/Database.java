package server;

import org.w3c.dom.NodeList;

public interface Database {
    void execute(String xml_path, String xsd_path);
    boolean check();
    void terminate();
    NodeList get_nodes(String xpath);
    String get_value(String xpath);
}
