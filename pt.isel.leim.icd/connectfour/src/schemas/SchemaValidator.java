package schemas;

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
import java.nio.file.Path;
import java.nio.file.Paths;

public class SchemaValidator {
    public static final String DEFAULT_XSD_SCHEMAS_PATH = "res/schemas";
    private final Path xsdSchemas;

    public SchemaValidator() {
        this(Paths.get(DEFAULT_XSD_SCHEMAS_PATH));
    }

    public SchemaValidator(Path xsdSchemas) {
        this.xsdSchemas = xsdSchemas;
    }


    public boolean validate(String xmlContent) throws IOException {

        try{
            Path schemaPath = xsdSchemas.resolve("Message.xsd");
            File schemaFile = new File(schemaPath.toString());

            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(schemaFile);

            Validator validator = schema.newValidator();
            validator.validate(new SAXSource(new InputSource(new StringReader(xmlContent))));

            return true;
        }catch (SAXException e){
            return false;
        }

    }
}
