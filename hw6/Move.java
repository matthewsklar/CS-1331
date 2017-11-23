/**
 * One turn.
 *
 * @author msklar3
 * @version 1.3
 * @since 1.3
 */
public class Move {
    private Ply whitePly, blackPly;

    /**
     * Constructor for Move.
     *
     * @param whitePly white players turn
     * @param blackPly black players turn
     */
    public Move(Ply whitePly, Ply blackPly) {
        this.whitePly = whitePly;
        this.blackPly = blackPly;
    }

    /**
     * Getter for whitePly.
     *
     * @return whitePly
     */
    public Ply getWhitePly() {
        return whitePly;
    }

    /**
     * Getter for blackPly.
     *
     * @return blackPly
     */
    public Ply getBlackPly() {
        return blackPly;
    }
}
