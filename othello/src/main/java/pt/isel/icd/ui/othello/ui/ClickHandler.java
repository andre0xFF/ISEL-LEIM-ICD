package pt.isel.icd.othello.ui;

import pt.isel.icd.othello.BoardCharacter;

public interface ClickHandler {
    void Clicked(int row, int col, BoardCharacter state);
}