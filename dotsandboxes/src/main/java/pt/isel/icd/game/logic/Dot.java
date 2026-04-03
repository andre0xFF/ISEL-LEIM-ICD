package pt.isel.icd.game.logic;

/**
 * Represents a dot (point) on the Dots and Boxes board.
 */
public record Dot(int row, int col) {
    public Dot {
        if (row < 0 || col < 0) {
            throw new IllegalArgumentException(
                "Row and col must be non-negative: (" + row + ", " + col + ")"
            );
        }
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", row, col);
    }
}
