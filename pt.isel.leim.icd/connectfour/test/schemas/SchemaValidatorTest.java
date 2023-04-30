package schemas;

import network.messages.MessageTest;
import network.messages.PingMessageTest;
import network.messages.PongMessageTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SchemaValidatorTest {

    private SchemaValidator schemaValidator;

    @BeforeEach
    void setUp() {
        this.schemaValidator = new SchemaValidator();
    }

    @Test
    void shouldNotValidateEmptyMessage() {
        assertThrows(SAXException.class, () -> schemaValidator.validate(MessageTest.messageEmptyContent));
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
    void shouldValidateAskGameHistoryMessage() {
        assertDoesNotThrow(() -> schemaValidator.validate("<Message><AskGameHistoryMessage/></Message>"));
    }

    @Test
    void shouldValidatePlayTurnMessage() {
        assertDoesNotThrow(() -> schemaValidator.validate("<Message><PlayTurnMessage/></Message>"));
    }

    @Test
    void shouldValidateLoginMessage() {
        assertDoesNotThrow(() -> schemaValidator.validate("<Message><LoginMessage><username>player1</username><password>pass</password></LoginMessage></Message>"));
    }
}
