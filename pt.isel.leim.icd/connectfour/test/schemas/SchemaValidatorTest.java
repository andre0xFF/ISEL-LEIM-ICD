package schemas;

import messages.MessageTest;
import messages.PingMessageTest;
import messages.PongMessageTest;
import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import static org.junit.jupiter.api.Assertions.*;

class SchemaValidatorTest {

    private SchemaValidator schemaValidator;

    @BeforeEach
    void setUp() {
        this.schemaValidator = new SchemaValidator();
    }

    @Test
    void shouldValidatePingMessage() {
        assertDoesNotThrow(() -> schemaValidator.validate(PingMessageTest.serializedContent));
    }

    @Test
    void shouldValidatePongMessage() {
        assertDoesNotThrow(() -> schemaValidator.validate(PongMessageTest.serializedContent));
    }

    @Test
    void shouldNotValidateEmptyMessage() {
        assertThrows(SAXException.class, () -> schemaValidator.validate(MessageTest.messageEmptyContent));
    }
}
