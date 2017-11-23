/**
 * Represents a queen.
 *
 * @author msklar3
 * @version 1.0
 * @since 1.0
 */
public class Queen extends Piece {
    /**
     * Constructor for the Queen piece object.
     *
     * @param color color of the queen piece
     */
    public Queen(Color color) {
        super(color);
    }

    /**
     * Get the algebraic name of the queen piece "Q".
     *
     * @return "Q"
     */
    @Override
    public String algebraicName() {
        return "Q";
    }

    /**
     * Get the name of the queen piece in FEN. The piece will be the letter "Q",
     * and will be capitalized if white and lowercase if black.
     *
     * @return "Q" if white, "q" if black
     */
    @Override
    public String fenName() {
        if (getColor() == Color.WHITE) {
            return "Q";
        }

        return "q";
    }

    /**
     * Get all the squares that the queen can move to from it's current square
     * if it is the only piece on the board. First, create an array that
     * assumes that the queen can move to 27 squares. Then, iterate through
     * each file. Find the distance between the file being tested and the
     * queen's file. If it is 0, then add a square for each rank other than
     * the queen's rank to the array. Otherwise, check if the queen's rank
     * plus the distance is between 0 and 9 (exclusive). Add a square for
     * each one that is to the array. Repeat this but subtract the distance
     * from the queen's rank. Return a trimmed version of the moves array.
     *
     * @param square the current square of the queen
     * @return the squares that the queen can move to
     */
    @Override
    public Square[] movesFrom(Square square) {
        Square[] tempSquares = new Square[27];

        int file = square.getFile() - 96;
        int rank = square.getRank() - 48;

        int squareCount = 0;

        for (int f = 1; f < 9; f++) {
            int distance = f - file;

            if (distance == 0) {
                for (int r = 1; r < 9; r++) {
                    if (r == rank) {
                        continue;
                    }

                    tempSquares[squareCount++] = new Square(square.getFile(),
                                                            (char) (r + 48));
                }

                continue;
            }

            tempSquares[squareCount++] = new Square((char) (f + 96),
                                                    (char) (rank + 48));

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

        return trimArray(squareCount, 27, tempSquares);
    }
}
