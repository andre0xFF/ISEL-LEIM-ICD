package pt.isel.icd.communication;

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
    public static final String DEFAULT_XSD_SCHEMAS_PATH = "src/main/resources/schemas/Command.xsd";
    private final Validator validator;

    /**
     * Creates a new SchemaValidator that validates against the default XSD schema
     */
    public SchemaValidator() {
        this(Paths.get(DEFAULT_XSD_SCHEMAS_PATH));
    }

    /**
     * Creates a new SchemaValidator that validates against the given XSD schema
     *
     * @param xsdPath the path to the XSD schema
     */
    public SchemaValidator(Path xsdPath) {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        File schemaFile = new File(xsdPath.toString());

        Schema schema = null;

        try {
            schema = schemaFactory.newSchema(schemaFile);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }

        validator = schema.newValidator();
    }

    /**
     * Validates the given XML content against the XSD schema
     *
     * @param xmlContent the XML content to validate
     */
    public void validate(String xmlContent) throws IOException, SAXException {
        validator.validate(new SAXSource(new InputSource(new StringReader(xmlContent))));
    }
}
