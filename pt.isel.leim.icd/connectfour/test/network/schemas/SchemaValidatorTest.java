package network.schemas;

import network.messages.MessageTest;
import network.messages.MessagesTestContent;
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
        assertThrows(SAXException.class, () -> schemaValidator.validate(MessagesTestContent.getEmptyMessageContent()));
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
    void shouldValidateAskSignUpMessage() {

        assertDoesNotThrow(() -> schemaValidator.validate(MessagesTestContent.getAskSignUpMessageContent()));
    }

    @Test
    void shouldValidateGiveSignUpAcceptedMessage() {
        assertDoesNotThrow(() -> schemaValidator.validate(MessagesTestContent.getGiveSignUpAcceptedMessageContent()));
    }

    @Test
    void shouldValidateAskLogInMessage() {
        assertDoesNotThrow(() -> schemaValidator.validate(MessagesTestContent.getAskLogInMessageContent()));
    }

    @Test
    void shouldValidateGiveLogInAcceptedMessage() {
        assertDoesNotThrow(() -> schemaValidator.validate(MessagesTestContent.getGiveLogInAcceptedMessageContent()));
    }

    @Test
    void shouldValidateAskUpdateProfileMessage() {
        assertDoesNotThrow(() -> schemaValidator.validate(MessagesTestContent.getAskUpdateProfileMessageContent()));
    }

    @Test
    void shouldValidateGiveUpdatedProfileMessage() {
        assertDoesNotThrow(() -> schemaValidator.validate(MessagesTestContent.getGiveUpdateProfileMessageContent()));
    }

    @Test
    void shouldValidateGameOverMessage() {
        assertDoesNotThrow(() -> schemaValidator.validate(MessagesTestContent.getGameOverMessageContent()));
    }

    @Test
    void shouldValidateAskQueueGameMessage() {
        assertDoesNotThrow(() -> schemaValidator.validate(MessagesTestContent.getAskQueueGameMessageContent()));
    }

    @Test
    void shouldValidateAskQueueGameCancelMessage() {
        assertDoesNotThrow(() -> schemaValidator.validate(MessagesTestContent.getAskQueueGameCancelMessageContent()));
    }

    @Test
    void shouldValidateGiveOpponentFoundMessage() {
        assertDoesNotThrow(() -> schemaValidator.validate(MessagesTestContent.getGiveOpponentFoundMessageContent()));
    }

    @Test
    void shouldValidateAskGamesStatsMessage() {
        assertDoesNotThrow(() -> schemaValidator.validate(MessagesTestContent.getAskGamesStatesMessageContent()));
    }

    @Test
    void shouldValidateGiveGamesStatsMessage() {
        assertDoesNotThrow(() -> schemaValidator.validate(MessagesTestContent.getGiveGamesStatsMessageContent()));
    }

    @Test
    void shouldValidatePlayTurnMessage() {
        assertDoesNotThrow(() -> schemaValidator.validate(MessagesTestContent.getPlayTurnMessageContent()));
    }


}
