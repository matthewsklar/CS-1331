/**
 * Represents a piece.
 *
 * @author msklar3
 * @version 1.0
 * @since 1.0
 */
public abstract class Piece {
    private Color color;

    /**
     * Constructor for Piece object. Used when given color of piece.
     *
     * @param color color of the piece
     */
    public Piece(Color color) {
        this.color = color;
    }

    /**
     * Get the color of the piece.
     *
     * @return the color of the piece
     */
    public Color getColor() {
        return color;
    }

    /**
     * Trim an array by checking if it can do the maximum amount of moves.
     * If it can, return itself, otherwise trim it to the amount of moves
     * that it can make.
     *
     * @param squareCount amount of moves the piece count make
     * @param maxMoves maximum amount of moves the piece could make in any
     *        situation
     * @param square moves the piece can make
     * @return the moves the piece can make
     */
    public Square[] trimArray(int squareCount, int maxMoves, Square[] square) {
        if (squareCount == maxMoves) {
            return square;
        }

        Square[] squares = new Square[squareCount];

        for (int i = 0; i < squareCount; i++) {
            squares[i] = square[i];
        }

        return squares;
    }

    /**
     * Get the algebraic name of the piece.
     *
     * @return the algebraic name of the piece. "" is used for pawns
     */
    public abstract String algebraicName();

    /**
     * Get the FEN name of the piece.
     *
     * @return the FEN name of the piece. If the piece is white, then it is the
     *         piece capitalized, and if the piece is black, then it is the
     *         piece in lowercase.
     */
    public abstract String fenName();

    /**
     * Get all the squares that the piece can move to from it's current square
     * if it is the only piece on the board.
     *
     * @param square the current square of the piece
     * @return the squares that the piece can move to
     */
    public abstract Square[] movesFrom(Square square);
}
