package pt.isel.icd.game.logic;

/**
 * Represents a line segment between two adjacent dots on the board.
 * A line is defined by two dots that must be adjacent (horizontally or vertically).
 *
 * The dots are normalized so that dot1 is always the top-left dot:
 * - For horizontal lines: dot1 has the smaller column
 * - For vertical lines: dot1 has the smaller row
 */
public record Line(Dot dot1, Dot dot2) {
    public Line {
        if (dot1 == null || dot2 == null) {
            throw new IllegalArgumentException("Dots cannot be null");
        }

        // Validate adjacency
        int rowDiff = Math.abs(dot1.row() - dot2.row());
        int colDiff = Math.abs(dot1.col() - dot2.col());

        if (
            !((rowDiff == 1 && colDiff == 0) || (rowDiff == 0 && colDiff == 1))
        ) {
            throw new IllegalArgumentException(
                "Dots must be adjacent (horizontally or vertically): " +
                    dot1 +
                    " and " +
                    dot2
            );
        }

        // Normalize: dot1 should be the top-left dot
        if (
            dot1.row() > dot2.row() ||
            (dot1.row() == dot2.row() && dot1.col() > dot2.col())
        ) {
            Dot temp = dot1;
            dot1 = dot2;
            dot2 = temp;
        }
    }

    public boolean isHorizontal() {
        return dot1.row() == dot2.row();
    }

    public boolean isVertical() {
        return dot1.col() == dot2.col();
    }

    @Override
    public String toString() {
        return String.format("Line(%s -> %s)", dot1, dot2);
    }
}
