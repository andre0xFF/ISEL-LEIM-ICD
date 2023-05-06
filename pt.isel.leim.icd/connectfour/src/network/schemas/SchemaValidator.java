package network.schemas;

import org.junit.jupiter.api.function.Executable;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SchemaValidator {
    public static final String DEFAULT_XSD_SCHEMAS_PATH = "res/schemas/Message.xsd";
    private final Path xsdPath;

    /**
     * Creates a new SchemaValidator that validates against the default xsd schema
     */
    public SchemaValidator() {
        this(Paths.get(DEFAULT_XSD_SCHEMAS_PATH));
    }

    /**
     * Creates a new SchemaValidator that validates against the given xsd schema
     *
     * @param xsdPath the path to the xsd schema
     */
    public SchemaValidator(Path xsdPath) {
        this.xsdPath = xsdPath;
    }

    /**
     * Validates the given xml content against the xsd schema
     *
     * @param xmlContent the xml content to validate
     * @return null if the xml content is valid, otherwise an Executable that throws an exception
     * @throws IOException  if the xml content is not valid
     * @throws SAXException if the xml content is not valid
     */
    public Executable validate(String xmlContent) throws IOException, SAXException {
        File schemaFile = new File(xsdPath.toString());

        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(schemaFile);

        Validator validator = schema.newValidator();
        validator.validate(new SAXSource(new InputSource(new StringReader(xmlContent))));

        return null;
    }
}
