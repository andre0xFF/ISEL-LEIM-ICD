package pt.isel.icd.game.logic;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents the Dots and Boxes game board.
 *
 * The board is a grid of dots with dimensions rows x cols.
 * Lines can be drawn between adjacent dots (horizontal or vertical).
 * Boxes are the 1x1 squares formed between four adjacent dots.
 *
 * For a "3x3 board" we mean 3x3 dots = 2x2 boxes.
 */
public class Board {

    private final int rows; // number of dot rows
    private final int cols; // number of dot columns
    private final Set<Line> lines;
    private final PlayerMarker[][] boxOwners; // (rows-1) x (cols-1)

    public Board(int rows, int cols) {
        if (rows < 2 || cols < 2) {
            throw new IllegalArgumentException(
                "Board must have at least 2x2 dots"
            );
        }
        this.rows = rows;
        this.cols = cols;
        this.lines = new HashSet<>();
        this.boxOwners = new PlayerMarker[rows - 1][cols - 1];
    }

    public int rows() {
        return rows;
    }

    public int cols() {
        return cols;
    }

    public int boxRows() {
        return rows - 1;
    }

    public int boxCols() {
        return cols - 1;
    }

    /**
     * Checks if a line is valid (within bounds and not already placed).
     */
    public boolean isLineValid(Line line) {
        if (line == null) {
            return false;
        }
        if (lines.contains(line)) {
            return false;
        }

        return isDotInBounds(line.dot1()) && isDotInBounds(line.dot2());
    }

    private boolean isDotInBounds(Dot dot) {
        return (
            dot.row() >= 0 &&
            dot.row() < rows &&
            dot.col() >= 0 &&
            dot.col() < cols
        );
    }

    /**
     * Places a line on the board and returns how many boxes were completed.
     *
     * @param line   the line to place
     * @param marker the player's marker who placed the line
     * @return the number of boxes completed by this line (0, 1, or 2)
     * @throws IllegalArgumentException if the line is not valid
     */
    public int placeLine(Line line, PlayerMarker marker) {
        if (!isLineValid(line)) {
            throw new IllegalArgumentException("Invalid line: " + line);
        }

        lines.add(line);
        int boxesClosed = 0;

        // dot1 is always the top-left dot (normalized by Line)
        int row = line.dot1().row();
        int col = line.dot1().col();

        if (line.isHorizontal()) {
            // A horizontal line at (row, col)-(row, col+1) is:
            // - the top side of box (row, col)
            // - the bottom side of box (row-1, col)
            if (isBoxComplete(row, col)) {
                boxOwners[row][col] = marker;
                boxesClosed++;
            }
            if (isBoxComplete(row - 1, col)) {
                boxOwners[row - 1][col] = marker;
                boxesClosed++;
            }
        } else if (line.isVertical()) {
            // A vertical line at (row, col)-(row+1, col) is:
            // - the left side of box (row, col)
            // - the right side of box (row, col-1)
            if (isBoxComplete(row, col)) {
                boxOwners[row][col] = marker;
                boxesClosed++;
            }
            if (isBoxComplete(row, col - 1)) {
                boxOwners[row][col - 1] = marker;
                boxesClosed++;
            }
        } else {
            // This should never happen due to Line validation
            throw new IllegalStateException(
                "Line is neither horizontal nor vertical: " + line
            );
        }

        return boxesClosed;
    }

    /**
     * Checks if box at (boxRow, boxCol) has all 4 sides drawn.
     * A box at position (r, c) needs:
     * - Top:    horizontal line (r,c)-(r,c+1)
     * - Bottom: horizontal line (r+1,c)-(r+1,c+1)
     * - Left:   vertical line (r,c)-(r+1,c)
     * - Right:  vertical line (r,c+1)-(r+1,c+1)
     */
    private boolean isBoxComplete(int boxRow, int boxCol) {
        if (
            boxRow < 0 || boxRow >= rows - 1 || boxCol < 0 || boxCol >= cols - 1
        ) {
            return false;
        }

        Line top = new Line(
            new Dot(boxRow, boxCol),
            new Dot(boxRow, boxCol + 1)
        );
        Line bottom = new Line(
            new Dot(boxRow + 1, boxCol),
            new Dot(boxRow + 1, boxCol + 1)
        );
        Line left = new Line(
            new Dot(boxRow, boxCol),
            new Dot(boxRow + 1, boxCol)
        );
        Line right = new Line(
            new Dot(boxRow, boxCol + 1),
            new Dot(boxRow + 1, boxCol + 1)
        );

        return (
            lines.contains(top) &&
            lines.contains(bottom) &&
            lines.contains(left) &&
            lines.contains(right)
        );
    }

    /**
     * Returns the owner of the box at (boxRow, boxCol), or null if unclaimed.
     */
    public PlayerMarker getBoxOwner(int boxRow, int boxCol) {
        return boxOwners[boxRow][boxCol];
    }

    /**
     * Checks if a line has already been placed.
     */
    public boolean hasLine(Line line) {
        return lines.contains(line);
    }

    /**
     * Returns the total number of lines placed so far.
     */
    public int totalLinesPlaced() {
        return lines.size();
    }

    /**
     * Returns the total number of possible lines on this board.
     */
    public int totalPossibleLines() {
        // Horizontal: rows * (cols - 1)
        // Vertical:   (rows - 1) * cols
        return rows * (cols - 1) + (rows - 1) * cols;
    }

    /**
     * Checks if all possible lines have been drawn (board is full).
     */
    public boolean isFull() {
        return totalLinesPlaced() >= totalPossibleLines();
    }

    /**
     * Returns a copy of the set of placed lines.
     */
    public Set<Line> getLines() {
        return new HashSet<>(lines);
    }
}
