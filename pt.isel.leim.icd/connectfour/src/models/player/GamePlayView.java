package models.player;

public interface GamePlayView {

    /**
     * Drops a token in the specified column
     *
     * @param column the column to drop the token in
     * @return true if the token was dropped, false if the game is over or the column is full
     */
    boolean dropToken(int column);

    boolean
}
