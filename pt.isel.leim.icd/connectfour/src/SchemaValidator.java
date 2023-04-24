import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

public class SchemaValidator {

    private static final String xpathExpression = "/Message/@type";

    public boolean validate(String xmlContent) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        String type = getType(xmlContent);
        String xsdFilePath = String.format("res/schemas/%s.xsd", type);

        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdFilePath));

            Validator validator = schema.newValidator();
            validator.validate(new SAXSource(new InputSource(new StringReader(xmlContent))));

            return true;
        } catch (SAXException e) {
            return false;
        }
    }

    private String getType(String xmlContent) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Document doc = dbf.newDocumentBuilder().parse(new InputSource(new StringReader(xmlContent)));
        XPathFactory xpf = XPathFactory.newInstance();
        XPath xpath = xpf.newXPath();

        Node node = (Node) xpath.evaluate(xpathExpression, doc, XPathConstants.NODE);

        return node.getNodeValue();
    }
}
