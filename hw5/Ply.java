import java.util.Optional;

/**
 * One players move.
 *
 * @author msklar3
 * @version 1.3
 * @since 1.3
 */
public class Ply {
    private Piece piece;
    private Square from, to;
    private Optional<String> comment;

    /**
     * Constructor for Ply
     *
     * @param piece Piece moved
     * @param from start Square
     * @param to end Square
     * @param comment optional comment on move
     */
    public Ply(Piece piece, Square from, Square to, Optional<String> comment) {
        this.piece = piece;
        this.from = from;
        this.to = to;
        this.comment = comment;
    }

    /**
     * Getter for piece.
     *
     * @return piece moved
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * Getter for from.
     *
     * @return Square move starts on
     */
    public Square getFrom() {
        return from;
    }

    /**
     * Gettor for to.
     *
     * @return Square move ends on
     */
    public Square getTo() {
        return to;
    }

    /**
     * Getter for comment.
     *
     * @return move commentthe comment
     */
    public Optional<String> getComment() {
        return comment;
    }
}
