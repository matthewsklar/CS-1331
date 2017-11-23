/**
 * Represents a king.
 *
 * @author msklar3
 * @version 1.0
 * @since 1.0
 */
public class King extends Piece {
    /**
     * Constructor for the King piece object.
     *
     * @param color color of the king piece
     */
    public King(Color color) {
        super(color);
    }

    /**
     * Get the algebraic name of the king piece "K".
     *
     * @return "K"
     */
    @Override
    public String algebraicName() {
        return "K";
    }

    /**
     * Get the name of the king piece in FEN. The piece will be the letter "K",
     * and will be capitalized if white and lowercase if black.
     *
     * @return "K" if white, "k" if black
     */
    @Override
    public String fenName() {
        if (getColor() == Color.WHITE) {
            return "K";
        }

        return "k";
    }

    /**
     * Get all the squares that the king can move to from it's current square
     * if it is the only piece on the board. First, create an array that
     * assumes that the king can move to 8 squares. Then, check every square
     * surrounding the king and check if the king could move there (not on edge
     * of board). If the king can move to the square, add the square to the
     * array and add to the counter of the amount of squares in the array.
     * Return a trimmed version of the array.
     *
     * @param square the current square of the king
     * @return the squares that the piece can move to
     */
    @Override
    public Square[] movesFrom(Square square) {
        Square[] tempSquares = new Square[8];

        int file = square.getFile() - 96;
        int rank = square.getRank() - 48;

        int squareCount = 0;

        for (int f = file - 1; f <= file + 1; f++) {
            if (f != 0 && f != 9) {
                for (int r = rank - 1; r <= rank + 1; r++) {
                    if (r != 0 && r != 9 && (f != file || r != rank)) {
                        tempSquares[squareCount++] = new Square((char) (f + 96),
                            (char) (r + 48));
                    }
                }
            }
        }

        return trimArray(squareCount, 8, tempSquares);
    }
}
