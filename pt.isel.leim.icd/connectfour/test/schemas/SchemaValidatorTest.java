package schemas;

import messages.MessageTest;
import messages.PingMessageTest;
import messages.PongMessageTest;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SchemaValidatorTest {

    @Test
    void shouldValidatePingMessage() throws IOException {
        SchemaValidator schemaValidator = new SchemaValidator();

        boolean isValid = schemaValidator.validate(PingMessageTest.pingMessageContent);

        assertTrue(isValid);
    }

    @Test
    void shouldValidatePongMessage() throws IOException {
        SchemaValidator schemaValidator = new SchemaValidator();

        boolean isValid = schemaValidator.validate(PongMessageTest.pongMessageContent);

        assertTrue(isValid);
    }

    @Test
    void shouldInValidateEmptyMessage() throws IOException {
        SchemaValidator schemaValidator = new SchemaValidator();

        boolean isValid = schemaValidator.validate(MessageTest.messageEmptyContent);

        assertFalse(isValid);
    }
}
