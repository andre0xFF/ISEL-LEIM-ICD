package pt.isel.icd.game.logic;

/**
 * Represents a line segment between two adjacent dots on the board.
 * A line is defined by its row, column, and orientation (horizontal or vertical).
 *
 * For a grid of rows x cols dots:
 * - Horizontal lines: row in [0, rows-1], col in [0, cols-2]
 * - Vertical lines:   row in [0, rows-2], col in [0, cols-1]
 */
public record Line(int row, int col, Orientation orientation) {
    public enum Orientation {
        HORIZONTAL,
        VERTICAL,
    }

    @Override
    public String toString() {
        return String.format("Line(%s, row=%d, col=%d)", orientation, row, col);
    }
}
