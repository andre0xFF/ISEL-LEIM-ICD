package pt.isel.icd.othello;

public record MakeMoveCommand(int row, int column, BoardCharacter playerCharacter) {
    public MakeMoveCommand {
        if (!validate()) {
            throw new IllegalArgumentException("Invalid row or column");
        }
    }

    private boolean validate() {
        return row >= 0 && row < Othello.BOARD_SIZE && column >= 0 && column < Othello.BOARD_SIZE;
    }
}
