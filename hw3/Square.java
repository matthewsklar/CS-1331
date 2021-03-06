/**
 * Represents a square.
 *
 * @author msklar3
 * @version 1.1
 * @since 1.0
 */
public class Square {
    private char file, rank;

    /**
     * Constructor for Square object. Used when given file and rank chars.
     *
     * @param file file of the square
     * @param rank rank of the square
     */
    public Square(char file, char rank) {
        if ((int) file >= (int) 'a' && (int) file <= (int) 'h'
                && (int) rank > (int) '0' && (int) rank < (int) '9') {
            this.file = file;
            this.rank = rank;
        } else {
            throw new InvalidSquareException("" + file + rank);
        }
    }

    /**
     * Constructor for Square object. Used When given String of position.
     *
     * @param name name of the square
     */
    public Square(String name) {
        this(name.charAt(0), name.charAt(1));

        if (name.length() != 2) {
            throw new InvalidSquareException("" + name);
        }
    }

    /**
     * Get the file of the square.
     *
     * @return the file of the square
     */
    public char getFile() {
        return file;
    }

    /**
     * Get the rank of the square.
     *
     * @return the rank of the square
     */
    public char getRank() {
        return rank;
    }

    /**
     * Convert the object to a string.
     *
     * @return information about the object in the form of a String
     */
    public String toString() {
        return "" + getFile() + getRank();
    }

    /**
     * Define if another object is considered equal to this Square object.
     * First, if the other square is null, return false, and if it is this
     * Square object, return true. Then check if the other object is a Square.
     * If is not, return false. Then, convert the object to a Square and check
     * if the other Square has the same rank and file as this Square. Return
     * true if that do.
     *
     * @param Other other object that will be compared to this Square
     * @return whether the other object is considered equal to this Square
     *         object
     */
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (other == this) {
            return true;
        }

        if (!(other instanceof Square)) {
            return false;
        }

        Square that = (Square) other;

        return (getFile() == that.getFile() && getRank() == that.getRank());
    }
}
