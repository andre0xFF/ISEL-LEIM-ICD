package schemas;

import messages.Message;
import messages.PingMessageTest;
import messages.PongMessageTest;
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

        boolean isValid = schemaValidator.validate(PingMessageTest.pingMessageContent, Message.XMLSerializer.DEFAULT_XPATH_EXPRESSION);

        assertTrue(isValid);
    }

    @Test
    void shouldValidatePongMessage() throws XPathExpressionException, ParserConfigurationException, IOException, SAXException {
        SchemaValidator schemaValidator = new SchemaValidator();

        boolean isValid = schemaValidator.validate(PongMessageTest.pongMessageContent, Message.XMLSerializer.DEFAULT_XPATH_EXPRESSION);

        assertTrue(isValid);
    }
}
