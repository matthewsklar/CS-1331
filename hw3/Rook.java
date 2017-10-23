/**
 * Represents a rook.
 *
 * @author msklar3
 * @version 1.0
 * @since 1.0
 */
public class Rook extends Piece {
    /**
     * Constructor for the Rook piece object.
     *
     * @param color color of the rook piece
     */
    public Rook(Color color) {
        super(color);
    }

    /**
     * Get the algebraic name of the rook piece "R".
     *
     * @return "R"
     */
    @Override
    public String algebraicName() {
        return "R";
    }

    /**
     * Get the name of the rook piece in FEN. The piece will be the letter "R",
     * and will be capitalized if white and lowercase if black.
     *
     * @return "R" if white, "r" if black
     */
    @Override
    public String fenName() {
        if (getColor() == Color.WHITE) {
            return "R";
        }

        return "r";
    }

    /**
     * Get all the squares that the rook can move to from it's current square
     * if it is the only piece on the board. First, add a square to the array
     * for every file other than it's current file at its current rank. Then
     * repeat this but with every rank.
     *
     * @param square the current square of the rook
     * @return the squares that the rook can move to
     */
    @Override
    public Square[] movesFrom(Square square) {
        Square[] squares = new Square[14];

        int file = square.getFile() - 96;
        int rank = square.getRank() - 48;

        int squareCount = 0;

        for (int f = 1; f < 9; f++) {
            if (f != file) {
                squares[squareCount++] = new Square((char) (f + 96),
                                                    square.getRank());
            }
        }

        for (int r = 1; r < 9; r++) {
            if (r != rank) {
                squares[squareCount++] = new Square(square.getFile(),
                                                    (char) (r + 48));
            }
        }

        return squares;
    }
}
