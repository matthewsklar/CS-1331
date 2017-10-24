/**
 * Exception for when an invalid square location is searched for.
 *
 * The exception is unchecked because the moveTo method in Piece creates
 * squares during runtime.
 *
 * @author msklar3
 * @version 1.1
 * @since 1.1
 */
public class InvalidSquareException extends RuntimeException {
    public InvalidSquareException(String message) {
        super(message);
    }
}
