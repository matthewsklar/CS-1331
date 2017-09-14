import java.nio.file.Files;
import java.nio.file.Paths;

public class PgnReader {
    public static String[][] boardState;
    
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
	boardState = createBoardState();
	
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
	    {"Rw", "Nw", "Bw", "Qw", "Kw", "Bw", "Nw", "Rw"},
	    {"Pw", "Pw", "Pw", "Pw", "Pw", "Pw", "Pw", "Pw"},
	    {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
	    {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
	    {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
	    {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "},
	    {"Pb", "Pb", "Pb", "Pb", "Pb", "Pb", "Pb", "Pb"},
	    {"Rb", "Nb", "Bb", "Qb", "Kb", "Bb", "Nb", "Rb"}
	};

	return boardState;
    }

    /**
     * Update boardState by moving the piece to its new location.
     *
     * @param piece The piece that is being moved in the form Pc.
     * @param startFile The piece's file (x-axis) before moving.
     * @param startRank The piece's rank (y-axis) before moving.
     * @param endFile The piece's file (x-axis) after moving.
     * @param endRank The piece's rank (y-axis) after moving.
     */
    public static void move(String piece, int startFile, int startRank, int endFile, int endRank) {
	boardState[startRank][startFile] = "  ";
	boardState[endRank][endFile] = piece;
    }
    
    /**
     * Get the status of the square on the board.
     *
     * @param file The file (x-axis) of the board.
     * @param rank The rank (y-axis) of the board.
     *
     * @return A string representing the value of the piece on the square.
     * "  " = no piece
     * "Pc" = Piece color
     */
    public static String getBoardStateSquare(int file, int rank) {
	return boardState[rank][file];
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
     * @param move The algebraic notation of the move.
     * @param color The color of the piece moved: 'w' = white; 'b' = black.
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
	int endRank = move.charAt(startIndex) - 49;
	
	int startFile = getStartPosition(piece, color, endFile, endRank, start, capture);
	
	// System.out.println("" + piece + start nnn+ (capture ? "x" : "")  + endFile + endRank);
    }

    /**
     *
     */
    public static int getStartPosition(char piece, char color, int endFile, int endRank, char start, boolean capture) {
	int startFile = 0;
	int startRank = 0;

	String piecePlusColor = Character.toString(piece) + Character.toString(color);
	
	if (Character.isLetter(start)) {
	    startFile = start;
	} else if (Character.isDigit(start)) {
	    startRank = start;
	}

	if (piece == 'P') {
	    int[][] piecePos = getPiecePositions(piecePlusColor, startFile, startRank);

	    int[] startPos = getPawnStart(piecePos, color, endFile, endRank, capture);
	    move(piecePlusColor, startPos[0], startPos[1], endFile, endRank);
	} else if (piece == 'R') {
	    
	} else if (piece == 'N') {

	} else if (piece == 'B') {
	    
	} else if (piece == 'Q') {

	} else if (piece == 'K') {

	}

	return 0;
    }

    /**
     * Get the positions of instances of piece. If start exists
     * then only get instances of the piece that fit the start position.
     *
     * @param piece The piece to get the positions of in the format Pc.
     * @param startFile The start file if it is already known.
     * @param startRank The start rank if it is already known.
     *
     * @return A double integer array containing each the rank and file 
     * for every position of the piece.
     */
    public static int[][] getPiecePositions(String piece, int startFile, int startRank) {
	// TODO: Take into account starting position if it matters
	int numPiecePos = 0;
	
	for (String[] rank: boardState) {
	    for (String square: rank) {
		if (square.equals(piece)) {
		    numPiecePos++;
		}
	    }
	}

	int[][] piecePos = new int[numPiecePos][2];
	int pieceIndex = 0;

	for (int r = 0; r < boardState.length; r++) {
	    for (int f = 0; f < boardState[r].length; f++) {
		if (boardState[r][f].equals(piece)) {
		    piecePos[pieceIndex][0] = f;
		    piecePos[pieceIndex][1] = r;

		    pieceIndex++;
		}
	    }
	}

	return piecePos;
    }

    /**
     * Handle the movement of pawns. If it is a capture _______. If it isnt a capture, 
     * check for a pawn on the same file. If the pawn is 2 spaces away, check if there
     * is a pawn in the middle space, if there is one then that pawn is moved, otherwise
     * the pawn 2 spaces away is moved.
     *
     * @param piecePos The positions of all pawns of the correct color.
     * @param color The color of the pawns.
     * @param endFile The file of the pawn after moving.
     * @param endRank The rank of the pawn after moving.
     * @param capture If the pawn is capturing a piece.
     *
     * @return An integer array storing the position of the pawn that will be moved.
     */
    public static int[] getPawnStart(int[][] piecePos, char color, int endFile, int endRank, boolean capture) {
	if (capture) {
	    return null;
	}
	
	for (int[] pos: piecePos) {
	    if (pos[0] == endFile) {
		if (Math.abs(endRank - pos[1]) == 2) {
		    if (getBoardStateSquare(endFile, (endRank + pos[1]) / 2) == "P" + Character.toString(color)) {
			int[] startPos = {endFile, (endRank + pos[1]) / 2};

			return startPos;
		    }

		    int[] startPos = {endFile, pos[1]};

		    return startPos;
		}
	    }
	}

	return null;
    }

    /**
     *
     */
    public static int[] getRookStart(int[][] piecePos, char color, int endFile, int endRank) {

    }

    /**
     *
     */
    public static int[] getKnightStart(int[][] piecePos, char color, int endFile, int endRank) {
	
    }

    /**
     *
     */
    public static int[] getBishopStart(int[][] piecePos, char color, int endFile, int endRank) {
       
    }

    /**
     *
     */
    public static int[] getQueenStart(int[][] piecePos, char color, int endFile, int endRank) {

    }

    /**
     *
     */
    public static int[] getKingStart(int[][] piecePos, char color, int endFile, int endRank) {

    }
}
