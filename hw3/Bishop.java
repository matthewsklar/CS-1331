/**
 * Represents a bishop.
 *
 * @author msklar3
 * @version 1.0
 * @since 1.0
 */
public class Bishop extends Piece {
    /**
     * Constructor for the Bishop piece object.
     *
     * @param color color of the bishop piece
     */
    public Bishop(Color color) {
        super(color);
    }

    /**
     * Get the algebraic name of the bishop piece "B".
     *
     * @return "B"
     */
    @Override
    public String algebraicName() {
        return "B";
    }

    /**
     * Get the name of the bishop piece in FEN. The piece will be the letter
     * "B", and will be capitalized if white and lowercase if black.
     *
     * @return "B" if white, "b" if black
     */
    @Override
    public String fenName() {
        if (getColor() == Color.WHITE) {
            return "B";
        }

        return "b";
    }

    /**
     * Get all the squares that the bishop can move to from it's current square
     * if it is the only piece on the board. First, create an array that that
     * assumes that the bishop can move to 13 square. Then, iterate through each
     * file. Find the distance between the file being tested and the bishop's
     * file. If it is 0, then skip the iteration. Otherwise check if the
     * bishop's rank plus that distance is between 0 and 9 (exclusive). Add a
     * square for each one that is to the array. Repeat this but subtract the
     * distance from the bishop's rank. Return a trimmed version of the moves
     * array.
     *
     * @param square the current square of the bishop
     * @return the squares that the bishop can move to
     */
    @Override
    public Square[] movesFrom(Square square) {
        Square[] tempSquares = new Square[13];

        int file = square.getFile() - 96;
        int rank = square.getRank() - 48;

        int squareCount = 0;

        for (int f = 1; f < 9; f++) {
            int distance = f - file;

            if (distance == 0) {
                continue;
            }

            int rankPlus = rank + distance;
            int rankMinus = rank - distance;

            if (rankPlus > 0 && rankPlus < 9) {
                tempSquares[squareCount++] = new Square((char) (f + 96),
                                                        (char) (rankPlus + 48));
            }

            if (rankMinus > 0 && rankMinus < 9) {
                tempSquares[squareCount++] = new Square((char) (f + 96),
                    (char) (rankMinus + 48));
            }
        }

        return trimArray(squareCount, 13, tempSquares);
    }
}
