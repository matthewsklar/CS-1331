/**
 * Represents a pawn.
 *
 * @author msklar3
 * @version 1.0
 * @since 1.0
 */
public class Pawn extends Piece {
    /**
     * Constructor for the Pawn piece object.
     *
     * @param color color of the pawn piece
     */
    public Pawn(Color color) {
        super(color);
    }

    /**
     * Get the algebraic name of the pawn piece "P".
     *
     * @return ""
     */
    @Override
    public String algebraicName() {
        return "";
    }

    /**
     * Get the name of the pawn piece in FEN. The piece will be the letter "P",
     * and will be capitalized if white and lowercase if black.
     *
     * @return "P" if white, "p" if black
     */
    @Override
    public String fenName() {
        if (getColor() == Color.WHITE) {
            return "P";
        }

        return "p";
    }

    /**
     * Get all the squares that the pawn can move to from it's current square
     * if it is the only piece an the board. The pawn can move forward 1 rank
     * and also 2 ranks if it is on its starting file.
     *
     * @param square the current square of the pawn
     * @return the squares that the pawn can move to
     */
    @Override
    public Square[] movesFrom(Square square) {
        Square[] tempSquares = new Square[2];

        int rank = square.getRank() - 48;
        int direction = getColor() == Color.WHITE ? 1 : -1;
        int squareCount = 0;

        char nextSquare = (char) (rank + direction + 48);

        if (nextSquare != '0' && nextSquare != '9') {
            tempSquares[squareCount++] = new Square(square.getFile(),
                                                    (char) (nextSquare));

            if ((getColor() == Color.WHITE && rank == 2)
                    || (getColor() == Color.BLACK && rank == 7)) {
                tempSquares[squareCount++] = new Square(square.getFile(),
                    (char) (rank + direction * 2 + 48));
            }
        }

        return trimArray(squareCount, 2, tempSquares);
    }
}
