package pt.isel.icd.communication;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.xml.XMLConstants;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class SchemaValidator {

    public static final String DEFAULT_XSD_SCHEMAS_PATH =
        "src/main/resources/schemas/Commands.xsd";
    private final Validator validator;

    public SchemaValidator() {
        this(Paths.get(DEFAULT_XSD_SCHEMAS_PATH));
    }

    public SchemaValidator(Path xsdPath) {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(
            XMLConstants.W3C_XML_SCHEMA_NS_URI
        );
        java.io.File schemaFile = xsdPath.toFile();

        try {
            Schema schema = schemaFactory.newSchema(schemaFile);
            validator = schema.newValidator();
        } catch (SAXException e) {
            throw new RuntimeException(
                "Failed to load XSD schema: " + xsdPath,
                e
            );
        }
    }

    public void validate(String xmlContent) throws IOException, SAXException {
        validator.validate(
            new SAXSource(new InputSource(new StringReader(xmlContent)))
        );
    }
}
