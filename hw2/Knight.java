/**
 * Represents a knight.
 *
 * @author msklar3
 * @version 1.0
 * @since 1.0
 */
public class Knight extends Piece {
    /**
     * Constructor for the Knight piece object.
     *
     * @param color color of the knight piece
     */
    public Knight(Color color) {
        super(color);
    }

    /**
     * Get the algebraic name of the knight piece "N".
     *
     * @return "N"
     */
    @Override
    public String algebraicName() {
        return "N";
    }

    /**
     * Get the name of the knight piece in FEN. The piece will be the
     * letter "N", and will be capitalized if white and lowercase if black.
     *
     * @return "N" if white, "n" if black
     */
    @Override
    public String fenName() {
        if (getColor() == Color.WHITE) {
            return "N";
        }

        return "n";
    }

    /**
     * Get all the squares that the knight can move to from it's current square
     * if it is teh only piece on the board. First, create at array that assumes
     * that the knight can move to 8 squares. Then, check each location that it
     * could move to if the file and rank are within the board bounds (1 - 8)
     * after the movement is considered. Add a square for each square that is
     * in range. Return a trimmed version of the moves array.
     *
     * @param square the current square of the knight
     * @return the squares that the knight can move to
     */
    @Override
    public Square[] movesFrom(Square square) {
        Square[] tempSquares = new Square[8];

        int file = square.getFile() - 96;
        int rank = square.getRank() - 48;

        int squareCount = 0;

        if (file > 1) {
            int f = file - 1;

            if (rank + 2 < 9) {
                tempSquares[squareCount++] = new Square((char) (f + 96),
                                                        (char) (rank + 50));
            }

            if (rank - 2 > 0) {
                tempSquares[squareCount++] = new Square((char) (f + 96),
                                                        (char) (rank + 46));
            }

            if (file > 2) {
                f = file - 2;

                if (rank + 1 < 9) {
                    tempSquares[squareCount++] = new Square((char) (f + 96),
                                                            (char) (rank + 49));
                }

                if (rank - 1 > 0) {
                    tempSquares[squareCount++] = new Square((char) (f + 96),
                                                            (char) (rank + 47));
                }
            }
        }

        if (file < 8) {
            int f = file + 1;

            if (rank + 2 < 9) {
                tempSquares[squareCount++] = new Square((char) (f + 96),
                                                        (char) (rank + 50));
            }

            if (rank - 2 > 0) {
                tempSquares[squareCount++] = new Square((char) (f + 96),
                                                        (char) (rank + 46));
            }

            if (file < 7) {
                f = file + 2;

                if (rank + 1 < 9) {
                    tempSquares[squareCount++] = new Square((char) (f + 96),
                                                            (char) (rank + 49));
                }

                if (rank - 1 > 0) {
                    tempSquares[squareCount++] = new Square((char) (f + 96),
                                                            (char) (rank + 47));
                }
            }
        }

        return trimArray(squareCount, 8, tempSquares);
    }
}
