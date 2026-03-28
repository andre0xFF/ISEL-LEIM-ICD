package pt.isel.icd.serialization;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.*;
import java.nio.file.Path;

/**
 * DOM-based XML helper — no data-binding (Jackson/JAXB) used.
 * Provides methods to create, parse, serialize and validate XML documents.
 */
public class XmlHelper {

    private static final DocumentBuilderFactory DOC_FACTORY;
    private static final TransformerFactory TRANSFORMER_FACTORY;

    static {
        DOC_FACTORY = DocumentBuilderFactory.newInstance();
        TRANSFORMER_FACTORY = TransformerFactory.newInstance();
    }

    /**
     * Creates a new empty DOM Document.
     */
    public static Document createDocument() {
        try {
            DocumentBuilder builder = DOC_FACTORY.newDocumentBuilder();
            return builder.newDocument();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException("Failed to create XML document", e);
        }
    }

    /**
     * Parses an XML string into a DOM Document.
     */
    public static Document parse(String xml) {
        try {
            DocumentBuilder builder = DOC_FACTORY.newDocumentBuilder();
            return builder.parse(new InputSource(new StringReader(xml)));
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse XML", e);
        }
    }

    /**
     * Parses an XML file into a DOM Document.
     */
    public static Document parse(File file) {
        try {
            DocumentBuilder builder = DOC_FACTORY.newDocumentBuilder();
            return builder.parse(file);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse XML file: " + file.getPath(), e);
        }
    }

    /**
     * Serializes a DOM Document to an XML string (single line, no XML declaration).
     */
    public static String serialize(Document doc) {
        try {
            Transformer transformer = TRANSFORMER_FACTORY.newTransformer();
            transformer.setOutputProperty(javax.xml.transform.OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "no");
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            return writer.toString().trim();
        } catch (TransformerException e) {
            throw new RuntimeException("Failed to serialize XML", e);
        }
    }

    /**
     * Serializes a DOM Document to a file with XML declaration.
     */
    public static void serialize(Document doc, File file) {
        try {
            Transformer transformer = TRANSFORMER_FACTORY.newTransformer();
            transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(new DOMSource(doc), new StreamResult(file));
        } catch (TransformerException e) {
            throw new RuntimeException("Failed to serialize XML to file", e);
        }
    }

    /**
     * Gets text content of the first child element with the given tag name.
     */
    public static String getChildText(Element parent, String tagName) {
        NodeList nodes = parent.getElementsByTagName(tagName);
        if (nodes.getLength() == 0) return null;
        return nodes.item(0).getTextContent().trim();
    }

    /**
     * Adds a child element with text content.
     */
    public static Element addChildElement(Document doc, Element parent, String tagName, String textContent) {
        Element child = doc.createElement(tagName);
        if (textContent != null) {
            child.setTextContent(textContent);
        }
        parent.appendChild(child);
        return child;
    }

    /**
     * Validates an XML string against an XSD schema file.
     */
    public static void validate(String xmlContent, Path xsdPath) throws SAXException, IOException {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(xsdPath.toFile());
        Validator validator = schema.newValidator();
        validator.validate(new SAXSource(new InputSource(new StringReader(xmlContent))));
    }
}
