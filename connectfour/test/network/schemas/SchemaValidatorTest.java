package network.schemas;

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
        assertThrows(SAXException.class, () -> schemaValidator.validate("<Message></Message>"));
    }

    @Test
    void shouldValidatePingMessage() {
        assertDoesNotThrow(() -> schemaValidator.validate("<Message><PingMessage><dateTime>2021-05-18T15:00:00</dateTime></PingMessage></Message>"));
    }

    @Test
    void shouldValidatePongMessage() {
        assertDoesNotThrow(() -> schemaValidator.validate("<Message><PongMessage><dateTime>2021-05-18T15:00:00</dateTime></PongMessage></Message>"));
    }

    @Test
    void shouldValidateAskSignUpMessage() {
        assertDoesNotThrow(() -> schemaValidator.validate("<Message><AskSignUpMessage><image>12314</image><username>André</username><password>1234</password><nationality>PT</nationality><age>48</age></AskSignUpMessage></Message>"));
    }

    @Test
    void shouldValidateGiveSignUpAcceptedMessage() {
        assertDoesNotThrow(() -> schemaValidator.validate("<Message><GiveSignUpAcceptedMessage/></Message>"));
    }

    @Test
    void shouldValidateAskLoginMessage() {
        assertDoesNotThrow(() -> schemaValidator.validate("<Message><AskLoginMessage><username>Joao</username><password>1234</password></AskLoginMessage></Message>"));
    }

    @Test
    void shouldValidateGiveLoginAcceptedMessage() {
        assertDoesNotThrow(() -> schemaValidator.validate("<Message><GiveLoginResultMessage><authenticated>true</authenticated></GiveLoginResultMessage></Message>"));
    }

    @Test
    void shouldValidateAskUpdateProfileMessage() {
        assertDoesNotThrow(() -> schemaValidator.validate("<Message><AskUpdateProfileMessage><image>12314</image><username>André</username><password>1234</password><nationality>PT</nationality><age>48</age></AskUpdateProfileMessage></Message>"));
    }

    @Test
    void shouldValidateGiveUpdatedProfileMessage() {
        assertDoesNotThrow(() -> schemaValidator.validate("<Message><GiveUpdatedProfileMessage><image>abcd</image><username>Joao</username><nationality>Portuguese</nationality><age>19</age></GiveUpdatedProfileMessage></Message>"));
    }

    @Test
    void shouldValidateGameOverMessage() {
        assertDoesNotThrow(() -> schemaValidator.validate("<Message><GameOverMessage><info>abcd</info></GameOverMessage></Message>"));
    }

    @Test
    void shouldValidateAskQueueGameMessage() {
        assertDoesNotThrow(() -> schemaValidator.validate("<Message><AskQueueGameMessage/></Message>"));
    }

    @Test
    void shouldValidateAskQueueGameCancelMessage() {
        assertDoesNotThrow(() -> schemaValidator.validate("<Message><AskQueueGameCancelMessage/></Message>"));
    }

    @Test
    void shouldValidateGiveOpponentFoundMessage() {
        assertDoesNotThrow(() -> schemaValidator.validate("<Message><GiveOpponentFoundMessage><opponentusername>João</opponentusername></GiveOpponentFoundMessage></Message>"));
    }

    @Test
    void shouldValidateAskGamesStatsMessage() {
        assertDoesNotThrow(() -> schemaValidator.validate("<Message><AskGamesStatsMessage/></Message>"));
    }

    @Test
    void shouldValidateGiveGamesStatsMessage() {
        assertDoesNotThrow(() -> schemaValidator.validate("<Message><GiveGamesStatsMessage><GamesStats><GameStat><id>Gamexpto</id><result>WIN</result><dateTime>2021-05-18T15:00:00</dateTime></GameStat><GameStat><id>War2</id><result>LOSS</result><dateTime>2021-05-19T15:00:00</dateTime></GameStat></GamesStats></GiveGamesStatsMessage></Message>"));
    }

    @Test
    void shouldValidatePlayTurnMessage() {
        assertDoesNotThrow(() -> schemaValidator.validate("<Message><OnPlayTurnMessage/></Message>"));
    }

    @Test
    void shouldValidateOnTokenDroppedMessage() {
        assertDoesNotThrow(() -> schemaValidator.validate("<Message><OnTokenDroppedMessage><column>1</column><row>1</row><color><red>255</red><green>0</green><blue>0</blue></color></OnTokenDroppedMessage></Message>"));
    }

    @Test
    void shouldValidateOnTokenNotDropped() {
        assertDoesNotThrow(() -> schemaValidator.validate("<Message><OnTokenNotDroppedMessage><column>1</column></OnTokenNotDroppedMessage></Message>"));
    }

    @Test
    void shouldValidateOnWaitTurnMessage() {
        assertDoesNotThrow(() -> schemaValidator.validate("<Message><OnWaitTurnMessage/></Message>"));
    }
}
