import models.player.GamePlayView;
import models.player.Player;
import models.player.Token;
import models.player.TokensStack;

import java.awt.*;

public class LocalOpponentPlayer implements Player {

    @Override
    public String username() {
        return null;
    }

    @Override
    public void addToken(Token token) {

    }

    @Override
    public Token popToken() {
        return null;
    }

    @Override
    public int countTokens() {
        return 0;
    }

    @Override
    public Color color() {
        return null;
    }

    @Override
    public void tokens(TokensStack tokensStack) {

    }

    @Override
    public void onPlayTurn() {

    }

    @Override
    public void onWaitTurn() {

    }

    @Override
    public void onWin() {

    }

    @Override
    public void onLose() {

    }

    @Override
    public void onDraw() {

    }

    @Override
    public void onTokenDropped(int column, int row, Color color) {

    }

    @Override
    public void onTokenNotDropped(int column) {

    }

    @Override
    public void gamePlayView(GamePlayView gamePlayView) {

    }
}
