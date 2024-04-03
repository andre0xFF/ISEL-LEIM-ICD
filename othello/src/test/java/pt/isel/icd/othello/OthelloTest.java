package pt.isel.icd.othello;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;


class OthelloTest {
    // test<MethodName>_<ConditionOrStateUnderTest>_<ExpectedBehavior>

    Othello othello;

    @BeforeEach
    void setUp() {
        othello = new Othello();
    }

    @ParameterizedTest
    @CsvSource({
            "3, 3", "3, 4",
            "4, 3", "4, 4",
    })
    void initializeBoard_CenterPieces_ArePlayerPieces(int row, int column) {
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
    void initializeBoard_OuterCenterPieces_AreEmpty(int row, int column) {
        assertEquals(BoardCharacter.EMPTY, othello.getPiece(row, column));
    }

    @Test
    void makeMove_ValidMove_Succeeds() {
        assertTrue(othello.makeMove(2, 3, BoardCharacter.X));
    }

    @Test
    void makeMove_InvalidMove_Fails() {
        assertFalse(othello.makeMove(0, 0, BoardCharacter.O));
    }

    @Test
    void checkEndGame_BoardFull_GameEnded() {
    }

    @Test
    void makeMove_ValidMove_FlipsPieces() {
        othello.makeMove(2, 3, BoardCharacter.X);
        assertEquals(BoardCharacter.X, othello.getPiece(2, 3));
        assertEquals(BoardCharacter.X, othello.getPiece(3, 3));
    }
}