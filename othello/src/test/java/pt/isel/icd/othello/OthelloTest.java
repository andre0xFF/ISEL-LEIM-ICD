package pt.isel.icd.othello;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;


class OthelloTest {
    // <MethodName><ConditionOrStateUnderTest><ExpectedBehavior>

    private Othello othello;

    @BeforeEach
    void setUp() {
        othello = new Othello();
    }

    @ParameterizedTest
    @CsvSource({
            "3, 3", "3, 4",
            "4, 3", "4, 4",
    })
    @DisplayName("When the board is initialized, the center pieces are player pieces")
    void initializeBoardCenterPiecesArePlayerPieces(int row, int column) {
        assertNotEquals(BoardCharacter.EMPTY, othello.getPiece(row, column));
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0", "0, 1", "0, 2", "0, 3", "0, 4", "0, 5", "0, 6", "0, 7",
            "1, 0", "1, 1", "1, 2", "1, 3", "1, 4", "1, 5", "1, 6", "1, 7",
            "2, 0", "2, 1", "2, 2", "2, 3", "2, 4", "2, 5", "2, 6", "2, 7",
            "3, 0", "3, 1", "3, 2", "3, 5", "3, 6", "3, 7",
            "4, 0", "4, 1", "4, 2", "4, 5", "4, 6", "4, 7",
            "5, 0", "5, 1", "5, 2", "5, 3", "5, 4", "5, 5", "5, 6", "5, 7",
            "6, 0", "6, 1", "6, 2", "6, 3", "6, 4", "6, 5", "6, 6", "6, 7",
            "7, 0", "7, 1", "7, 2", "7, 3", "7, 4", "7, 5", "7, 6", "7, 7"
    })
    @DisplayName("When the board is initialized, the outer center pieces are empty")
    void initializeBoardOuterCenterPiecesAreEmpty(int row, int column) {
        assertEquals(BoardCharacter.EMPTY, othello.getPiece(row, column));
    }

    @Test
    @DisplayName("When making a valid move, the move succeeds")
    void makeMoveValidMoveSucceeds() {
        assertTrue(othello.makeMove(2, 3, BoardCharacter.O));
    }

    @Test
    @DisplayName("When making an invalid move, the move fails")
    void makeMoveInvalidMoveFails() {
        assertFalse(othello.makeMove(0, 0, BoardCharacter.O));
    }

    @Test
    void checkEndGameBoardFullGameEnded() {
        // TODO: Implement test checkEndGameBoardFullGameEnded
    }

    @Test
    @DisplayName("When making a valid move, the opponent's pieces are flipped")
    void makeMoveValidMoveFlipsPieces() {
        othello.makeMove(2, 3, BoardCharacter.O);
        assertEquals(BoardCharacter.O, othello.getPiece(3, 3));
        assertEquals(BoardCharacter.O, othello.getPiece(4, 3));
    }
}