package schemas;

import messages.MessageTest;
import messages.PingMessageTest;
import messages.PongMessageTest;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import static org.junit.jupiter.api.Assertions.*;

class SchemaValidatorTest {

    @Test
    void shouldValidatePingMessage() {
        SchemaValidator schemaValidator = new SchemaValidator();

        assertDoesNotThrow(() -> schemaValidator.validate(PingMessageTest.serializedContent));
    }

    @Test
    void shouldValidatePongMessage() {
        SchemaValidator schemaValidator = new SchemaValidator();

        assertDoesNotThrow(() -> schemaValidator.validate(PongMessageTest.serializedContent));
    }

    @Test
    void shouldNotValidateEmptyMessage() throws IOException, SAXException {
        SchemaValidator schemaValidator = new SchemaValidator();

        assertThrows(SAXException.class, () -> schemaValidator.validate(MessageTest.messageEmptyContent));
    }
}
