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
     * Called when it's the player's turn
     */
    void onPlayTurn();

    /**
     * Called when it's the other player's turn
     */
    void onWaitTurn();

    /**
     * Called when the player won the game
     */
    void onWin();

    /**
     * Called when the player lost the game
     */
    void onLose();

    /**
     * Called when the game is drawn
     */
    void onDraw();

    /**
     * Called when the player dropped a token
     *
     * @param column the column where the token was dropped
     * @param row    the row where the token was dropped
     */
    void onTokenDropped(int column, int row, Color color);

    /**
     * Called when the player dropped a token in a full column
     *
     * @param column the column where the player tried to drop the token
     */
    void onTokenNotDropped(int column);

    /**
     * Sets the game point view
     *
     * @param gamePlayView the game point view
     */
    void gamePlayView(GamePlayView gamePlayView);
}
