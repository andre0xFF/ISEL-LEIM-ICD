package models;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerTest {

    @Test
    void shouldAddToken() {
        Player player = new Player("Player 1");
        player.addToken(new Token(Color.ORANGE));

        assertEquals(1, player.tokensSize());
    }

    @Test
    void shoudPopToken() {
        Player player = new Player("Player 1");
        Token token = new Token(Color.ORANGE);

        player.addToken(token);

        assertEquals(token, player.popToken());
        assertEquals(0, player.tokensSize());
    }

    @Test
    void shouldHaveName() {
        Player player = new Player("Player 1");

        assertEquals("Player 1", player.username());
    }
}
