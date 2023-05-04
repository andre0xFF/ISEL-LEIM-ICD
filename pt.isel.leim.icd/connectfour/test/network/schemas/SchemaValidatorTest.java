package network.schemas;

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
    void shouldValidateAskSignUpMessage() {
        assertDoesNotThrow(() -> schemaValidator.validate("<Message><AskSignUpMessage><image>12314</image><username>André</username><password>1234</password><nationality>PT</nationality><age>48</age></AskSignUpMessage></Message>"));
    }

    @Test
    void shouldValidateGiveSignUpAcceptedMessage() {
        assertDoesNotThrow(() -> schemaValidator.validate("<Message><GiveSignUpAcceptedMessage></GiveSignUpAcceptedMessage></Message>"));
    }

    @Test
    void shouldValidateAskLogInMessage() {
        assertDoesNotThrow(() -> schemaValidator.validate("<Message><AskLogInMessage><username>player1</username><password>pass</password></AskLogInMessage></Message>"));
    }

    @Test
    void shouldValidateGiveLogInAcceptedMessage() {
        assertDoesNotThrow(() -> schemaValidator.validate("<Message><GiveLogInAcceptedMessage></GiveLogInAcceptedMessage></Message>"));
    }

    @Test
    void shouldValidateAskUpdateProfileMessage() {
        assertDoesNotThrow(() -> schemaValidator.validate("<Message><AskUpdateProfileMessage><image>12314</image><username>André</username><password>1234</password><nationality>PT</nationality><age>48</age></AskUpdateProfileMessage></Message>"));
    }

    @Test
    void shouldValidateGiveUpdatedProfileMessage() {
        assertDoesNotThrow(() -> schemaValidator.validate("<Message><GiveUpdatedProfileMessage><image>12314</image><username>André</username><password>1234</password><nationality>PT</nationality><age>48</age></GiveUpdatedProfileMessage></Message>"));
    }

    @Test
    void shouldValidateGameOverMessage() {
        assertDoesNotThrow(() -> schemaValidator.validate("<Message><GameOverMessage><info>Game Over</info></GameOverMessage></Message>"));
    }

    @Test
    void shouldValidateAskQueueGameMessage() {
        assertDoesNotThrow(() -> schemaValidator.validate("<Message><AskQueueGameMessage></AskQueueGameMessage></Message>"));
    }

    @Test
    void shouldValidateAskQueueGameCancelMessage() {
        assertDoesNotThrow(() -> schemaValidator.validate("<Message><AskQueueGameCancelMessage></AskQueueGameCancelMessage></Message>"));
    }

    @Test
    void shouldValidateGiveOpponentFoundMessage() {
        assertDoesNotThrow(() -> schemaValidator.validate("<Message><GiveOpponentFoundMessage><opponentusername>XpTo</opponentusername></GiveOpponentFoundMessage></Message>"));
    }

    @Test
    void shouldValidateAskGameStatsMessage() {
        assertDoesNotThrow(() -> schemaValidator.validate("<Message><AskGameStatsMessage></AskGameStatsMessage></Message>"));
    }

    @Test
    void shouldValidateGiveGamesStatsMessage() {
        assertDoesNotThrow(() -> schemaValidator.validate("<Message><GiveGamesStatsMessage><GamesStats><GameStat><id>Gamexpto</id><result>win</result><time>12:53:30</time></GameStat><GameStat><id>War2</id><result>Loss</result><time>12:53:30</time></GameStat></GamesStats></GiveGamesStatsMessage></Message>"));
    }

    @Test
    void shouldValidatePlayTurnMessage() {
        assertDoesNotThrow(() -> schemaValidator.validate("<Message><PlayTurnMessage/></Message>"));
    }


}
