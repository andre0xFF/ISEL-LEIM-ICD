package models.player;

import java.awt.*;

/**
 * A player is a person that plays the game
 */
public interface Player {

    /**
     * Gets the player's username
     *
     * @return the player's username
     */
    String username();

    /**
     * Adds a token to the player's tokens list
     *
     * @param token the token to add
     */
    void addToken(Token token);

    /**
     * Removes the first token from the player's tokens list
     *
     * @return the token that was removed
     */
    Token popToken();

    /**
     * Gets the number of tokens the player has
     *
     * @return the number of tokens the player has
     */
    int countTokens();

    /**
     * Gets the player's color
     *
     * @return the player's color
     */
    Color color();

    /**
     * Sets the player's tokens list
     *
     * @param tokensStack the tokens list
     */
    void tokens(TokensStack tokensStack);

    /**
     * Plays the player's turn
     */
    void playTurn();

    /**
     * Sets the game point view
     *
     * @param gamePlayView the game point view
     */
    void gamePlayView(GamePlayView gamePlayView);
}
