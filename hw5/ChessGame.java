import java.util.List;
import java.util.stream.Collectors;
import java.util.function.Predicate;

/**
 * Represents a sequence of Move objects.
 *
 * @author msklar3
 * @version 1.3
 * @since 1.3
 */
public class ChessGame {
    private class PiecePredicate implements Predicate<Move> {
        private Piece p;

        public PiecePredicate(Piece p) {
            this.p = p;
        }

        @Override
        public boolean test(Move move) {
            return move.whitePly().getPiece() == p
                || move.blackPly().getPiece() == p;
        }
    }

    private List<Move> moves;

    /**
     * Constructor for ChessGame.
     *
     * @param moves moves made in the chess game
     */
    public ChessGame(List<Move> moves) {
        this.moves = moves;
    }

    /**
     * Get the nth move.
     *
     * @param n index of the move to get
     * @return nth move
     */
    public Move getMove(int n) {
        return moves.get(n);
    }

    /**
     * Getter for moves.
     *
     * @return list of all the moves made in the chess game
     */
    public List<Move> getMoves() {
        return moves;
    }

    /**
     * Get a list filtered by the predicate.
     *
     * @param filter test applied to filter elements
     * @return list of Moves after being filtered
     */
    public List<Move> filter(Predicate<Move> filter) {
        return moves.stream()
            .filter(filter)
            .collect(Collectors.toList());
    }

    /**
     * Get a list of moves with comments. Must call filter with a lambda.
     *
     * @return list of Moves with comments
     */
    public List<Move> getMovesWithComment() {
        return filter((Move move) -> {
                return move.whitePly().getComment().isPresent()
                    || move.blackPly().getComment().isPresent();
            });
    }

    /**
     * Get a list of moves without comments. Must call filter with an anonymous
     * inner class.
     *
     * @return list of Moves without comments
     */
    public List<Move> getMovesWithoutComment() {
        return filter(new Predicate<Move>() {
                public boolean test(Move move) {
                    return !move.whitePly().getComment().isPresent()
                        && !move.blackPly().getComment().isPresent();
                }
            });
    }

    /**
     * Get a list of moves with a piece of the specified type. Must call filter
     * with an instance of an inner class.
     *
     * @param p Piece to check for
     * @return list of Moves with a piece of the specified type
     */
    public List<Move> getMovesWithPiece(Piece p) {
        return filter(new PiecePredicate(p));
    }
}
