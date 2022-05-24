package xml;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class SchemaManager {

    public static Boolean validateSchema(File inputfile, File xsdfile){

        // create a SchemaFactory capable of understading wxs schemas
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

        StreamSource schemafile = new StreamSource(xsdfile);
        // loads a wxs schema, represented by a Schema instance

        try {
            // Parses the specified source and returns it as a schema.
            Schema schema = factory.newSchema(schemafile);

            // Creates new Validator for the schema
            Validator validator = schema.newValidator();

            // validates xml file
            validator.validate(new StreamSource(inputfile));

            System.out.println("Xml file valid.");
            return true;
        } catch (SAXException | IOException e) {
            System.out.println("Xml file invalid");
            return false;
        }
    }
    public static void main(String[] args) throws IOException {
        File xmlfile = new File("chatAPP/WebContent/xml/SignIn.xml");
        File xsdfile = new File("chatAPP/WebContent/xml/SignIn.xsd");

        SchemaManager.validateSchema(xmlfile, xsdfile);
        System.out.println();
    }
}
