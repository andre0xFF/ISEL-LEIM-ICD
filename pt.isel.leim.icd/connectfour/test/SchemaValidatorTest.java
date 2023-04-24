import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SchemaValidatorTest {

    @org.junit.jupiter.api.Test
    void shouldValidatePingMessage() throws XPathExpressionException, ParserConfigurationException, IOException, SAXException {
        String xmlContent = "<Message type=\"PingMessage\"><dateTime>2021-05-18T15:00:00</dateTime></Message>";
        SchemaValidator schemaValidator = new SchemaValidator();

        boolean isValid = schemaValidator.validate(xmlContent);

        assertTrue(isValid);
    }

    @org.junit.jupiter.api.Test
    void shouldValidatePongMessage() throws XPathExpressionException, ParserConfigurationException, IOException, SAXException {
        String xmlContent = "<Message type=\"PongMessage\"><dateTime>2021-05-18T15:00:00</dateTime></Message>";
        SchemaValidator schemaValidator = new SchemaValidator();

        boolean isValid = schemaValidator.validate(xmlContent);

        assertTrue(isValid);
    }
}
