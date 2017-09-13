import java.nio.file.Files;
import java.nio.file.Paths;

public class PgnReader {
    // http://cs1331.gatech.edu/fall2017/hw1/hw1-pgn-reader.html
    public static void main(String[] args) {
        String pgn = "";

        try {
            pgn = new String(Files.readAllBytes(Paths.get(args[0])));
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Event: " + tagValue("Event", pgn));
        System.out.println("Site: " + tagValue("Site", pgn));
        System.out.println("Date: " + tagValue("Date", pgn));
        System.out.println("Round: " + tagValue("Round", pgn));
        System.out.println("White: " + tagValue("White", pgn));
        System.out.println("Black: " + tagValue("Black", pgn));
        System.out.println("Result: " + tagValue("Result", pgn));
        System.out.println("Final Position: " + finalPosition(pgn));
    }

    /**
     * Get the value corresponding to the tag name in the pgn file.
     *
     * @param name The name of the tag.
     * @param pgn The pgn file.
     *
     * @return A String containing the value of the tag with the given name
     * in the pgn file. If the tag is not found, it returns the String
     * "NOT GIVEN".
     */
    public static String tagValue(String name, String pgn) {
        name = "[" + name;

        int startIndex = pgn.indexOf(name);

        if (startIndex == -1) {
            return "NOT GIVEN";
        }

        startIndex += name.length() + 2;

        int endIndex = pgn.indexOf("]", startIndex) - 1;

        return pgn.substring(startIndex, endIndex);
    }

    /**
     * Get the FEN of the final position in the pgn file.
     *
     * @param pgn The pgn file.
     *
     * @return A String containing the FEN of the final position in the pgn
     * file.
     */
    public static String finalPosition(String pgn) {
	String[][] boardState = createBoardState();
	moves(pgn);
	return "TODO: Complete this";
    }

    /**
     * Create the inital board state of the chess board.
     *
     * R = Rook; B = Bishop; N = Knight; K = King; Q = Queen; P = Pawn
     *
     * @return A double Strain garray containing the initial 
     * state of the chess board with each piece in the form Pc.
     */
    public static String[][] createBoardState() {
	String[][] boardState = {
	    {"Rw", "Bw", "Nw", "Qw", "Kw", "Nw", "Kw", "Rw"},
	    {"Pw", "Pw", "Pw", "Pw", "Pw", "Pw", "Pw", "Pw"},
	    {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
	    {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
	    {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
	    {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
	    {"Pb", "Pb", "Pb", "Pb", "Pb", "Pb", "Pb", "Pb"},
	    {"Rb", "Bb", "Nb", "Qb", "Kb", "Nb", "Kb", "Rb"},
	};

	return boardState;
    }

    /**
     * Determine the moves in the chess game based on the pgn.
     * Individually send the moves to be handled.
     *
     * @param pgn The pgn file.
     */
    public static void moves(String pgn) {
	String pgnMoves = pgn.substring(pgn.indexOf("1. "));

	int currentMove = 1;
	int moveIndex = pgnMoves.indexOf(currentMove + ". ");

	while (moveIndex != -1) {
	    currentMove++;

	    int nextIndex = pgnMoves.indexOf(currentMove + ". ");

	    if (nextIndex == -1) {
	        processMove(pgnMoves.substring(moveIndex + 3), 'w');
	    } else {
		String moves[] = pgnMoves.substring(moveIndex + 3, nextIndex).split(" ");

	        processMove(moves[0], 'w');
		processMove(moves[1], 'b');
	    }

	    moveIndex = nextIndex;
	}
    }

    /**
     * Obtain information from algebraic notation of move.
     *
     * @param move A String containing the algebraic notation of the move.
     * @param color A char containingc the color of the piece moved: 'w' = white; 'b' = black.
     */
    public static void processMove(String move, char color) {
	char piece = Character.isUpperCase(move.charAt(0)) ? move.charAt(0) : 'P';

	int xIndex = move.indexOf('x');
	boolean capture = xIndex != -1;

	int startIndex = piece == 'P' ? 0 : 1;
	char start = '\0';

	if (capture) {
	    if (move.charAt(startIndex) != 'x') {
		start = move.charAt(startIndex);
	    }

	    startIndex = xIndex + 1;
	}

       	int endFile = move.charAt(startIndex++) - 97;
	int endRank = move.charAt(startIndex) - 48; // TODO: -49 if starts at 0

	
	int startFile = startPosition(piece, color, endFile, endRank, start);
	
	// System.out.println("" + piece + start + (capture ? "x" : "")  + endFile + endRank);
    }

    /**
     *
     */
    public static int startPosition(char piece, char color, int endFile, int endRank, char start) { // TODO: Return tuple of position
	int startFile = 0;
	int startRank = 0;

	String piecePlusColor = Character.toString(piece) + Character.toString(color);
	
	if (Character.isLetter(start)) {
	    startFile = start;
	} else if (Character.isDigit(start)) {
	    startRank = start;
	}

	if (piece == 'P') {
	    
	} else if (piece == 'R') {

	} else if (piece == 'B') {

	} else if (piece == 'N') {

	} else if (piece == 'Q') {

	} else if (piece == 'K') {

	}

	return 0;
    }
}
