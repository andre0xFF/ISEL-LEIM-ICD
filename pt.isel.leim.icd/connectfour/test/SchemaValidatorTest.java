import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SchemaValidatorTest {

    @Test
    void shouldValidatePingMessage() throws XPathExpressionException, ParserConfigurationException, IOException, SAXException {
        SchemaValidator schemaValidator = new SchemaValidator();

        boolean isValid = schemaValidator.validate(MessageTest.pingMessageContent);

        assertTrue(isValid);
    }

    @Test
    void shouldValidatePongMessage() throws XPathExpressionException, ParserConfigurationException, IOException, SAXException {
        SchemaValidator schemaValidator = new SchemaValidator();

        boolean isValid = schemaValidator.validate(MessageTest.pongMessageContent);

        assertTrue(isValid);
    }
}
