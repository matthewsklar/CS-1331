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

	System.out.println("");

	for (int i = 7; i >= 0; i--) {
	    for (int j = 0; j < 8; j++) {
		System.out.print(boardState[i][j]);
	    }

	    System.out.println("");
	}
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
	String pgnMoves = pgn.substring(pgn.indexOf("1."));

	int currentMove = 1;
	int moveIndex = pgnMoves.indexOf(currentMove + ". ");

	while (moveIndex != -1) {
	    currentMove++;
	    int nextIndex = pgnMoves.indexOf(currentMove + ". ");

	    int moveDigits = (int) Math.log10(currentMove - 1) + 1;

	    if (nextIndex == -1) {
	        String[] moves = pgnMoves.substring(moveIndex + 2 + moveDigits).split(" ");

		processMove(moves[0], 'w');

		if (moves.length == 2) {
		    processMove(moves[1], 'b');
		}
	    } else {
		String[] moves = pgnMoves.substring(moveIndex + 2 + moveDigits, nextIndex).split(" ");

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
	
	int[] startPos = getStartPosition(piece, color, endFile, endRank, start, capture);
	
	// System.out.println("" + piece + start nnn+ (capture ? "x" : "")  + endFile + endRank);
    }

    /**
     *
     */
    public static int[] getStartPosition(char piece, char color, int endFile, int endRank, char start, boolean capture) {
	int startFile = -1;
	int startRank = -1;

	String piecePlusColor = Character.toString(piece) + Character.toString(color);

	if (Character.isLetter(start)) {
	    startFile = start - 97;
	} else if (Character.isDigit(start)) {
	    startRank = start - 49;
	}

	int[][] piecePos = getPiecePositions(piecePlusColor, startFile, startRank);
	
	if (piece == 'P') {
    	    int direction = color == 'w' ? 1 : -1;
	    int[] startPos = getPawnStart(piecePos, color, endFile, endRank, direction, capture);
	    move(piecePlusColor, startPos[0], startPos[1], startPos[2], startPos[3]);

	    return startPos;
	} else if (piece == 'R') {
	    int[] startPos = getRookStart(piecePos, endFile, endRank);
	    move(piecePlusColor, startPos[0], startPos[1], endFile, endRank);

	    return startPos;
	} else if (piece == 'N') {
	    int[] startPos = getKnightStart(piecePos, endFile, endRank);
	    move(piecePlusColor, startPos[0], startPos[1], endFile, endRank);

	    return startPos;
	} else if (piece == 'B') {
	    int[] startPos = getBishopStart(piecePos, endFile, endRank);
	    move(piecePlusColor, startPos[0], startPos[1], endFile, endRank);

	    return startPos;
	} else if (piece == 'Q') {
	    int[] startPos = piecePos[0];
	    move(piecePlusColor, startPos[0], startPos[1], endFile, endRank);

	    return startPos;
	} else if (piece == 'K') {
	    int[] startPos = piecePos[0];
	    move(piecePlusColor, startPos[0], startPos[1], endFile, endRank);

	    return startPos;
	}

	return null;
    }

    // TODO: Add support for specified start of position
    
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

	int[][] piecePos;
	
	if (startFile != -1) {
	    for (int r = 0; r < 8; r++) {
		if (getBoardStateSquare(startFile, r).equals(piece)) {
		    numPiecePos++;
		}
	    }

	    piecePos = new int[numPiecePos][2];

	    int pieceIndex = 0;
	    
	    for (int rank = 0; rank < 8; rank++) {
		if (getBoardStateSquare(startFile, rank).equals(piece)) {
		    piecePos[pieceIndex][0] = startFile;
		    piecePos[pieceIndex][1] = rank;

		    pieceIndex++;
		}
	    }

	    return piecePos;
	} else if (startRank != -1) {
	    for (int f = 0; f < 8; f++) {
		if (getBoardStateSquare(startFile, f).equals(piece)) {
		    numPiecePos++;
		}

		piecePos = new int[numPiecePos][2];

		int pieceIndex = 0;
	    
		for (int file = 0; file < 8; file++) {
		    if (getBoardStateSquare(file, startRank).equals(piece)) {
			piecePos[pieceIndex][0] = file;
			piecePos[pieceIndex][1] = startRank;

			pieceIndex++;
		    }
		}

		return piecePos;
	    }
	}
	
	for (String[] rank: boardState) {
	    for (String square: rank) {
		if (square.equals(piece)) {
		    numPiecePos++;
		}
	    }
	}

	piecePos = new int[numPiecePos][2];

	int pieceIndex = 0;

	for (int r = 0; r < boardState.length; r++) {
	    for (int f = 0; f < boardState[r].length; f++) {
		if (getBoardStateSquare(f, r).equals(piece)) {
		    piecePos[pieceIndex][0] = f;
		    piecePos[pieceIndex][1] = r;

		    pieceIndex++;
		}
	    }
	}

	return piecePos;
    }

    /**
     * Handle the movement of pawns. If it is a capture determine direction of the capture
     * and check if the pawn is in the correct position to capture. If no pawn is in 
     * position, check for an en passant. If it isnt a capture, check for a pawn on the 
     * same file. If the pawn is 2 spaces away, check if is a pawn in the middle
     * space, if there is one then that pawn is moved, the pawn 2 spaces away is moved.
     *
     * @param piecePos The positions of all pawns of the correct color.
     * @param color The color of the pawns.
     * @param endFile The file of the pawn after moving.
     * @param endRank The rank of the pawn after moving.
     * @param direction The direction the pawn moves ('w' = 1; 'b' = -1).
     * @param capture If the pawn is capturing a piece.
     *
     * @return An integer array storing the position of the pawn that will be moved and its
     * end position.
     */
    public static int[] getPawnStart(int[][] piecePos, char color, int endFile, int endRank, int direction, boolean capture) {
	if (capture) {
	    // En Passant
	    if (getBoardStateSquare(endFile, endRank) == "  ") {
		for (int[] pos: piecePos) {
		    if (endRank == pos[1] + direction) {
			int[] posInfo = {pos[0], pos[1], endFile, endRank};
			move("  ", endFile, endRank, endFile, pos[1]);

			return posInfo;
		    }
		}

	    }
	    
	    for (int[] pos: piecePos) {
		if (endRank == pos[1] + direction) {
		    System.out.println(endRank + ", " + pos[1] + "+" + direction);
		    int[] posInfo = {pos[0], pos[1], endFile, endRank};

		    return posInfo;
		}
	    }
	}
	
	for (int[] pos: piecePos) {
	    if (pos[0] == endFile) {
       		if (Math.abs(endRank - pos[1]) == 2) {
		    if (getBoardStateSquare(endFile, (endRank + pos[1]) / 2) == "P" + Character.toString(color)) {
			int[] posInfo = {endFile, (endRank + pos[1]) / 2, endFile, endRank};

			return posInfo;
		    }
		}

		int[] posInfo = {pos[0], pos[1], endFile, endRank};

		return posInfo;
	    }
	}

	return null;
    }

    /**
     * Handle the movement of rooks. Find the rook that shares the same end 
     * file location. If it exists, check if there are any pieces on the same file
     * on a rank between the start and end rank. If there is or if no rook is on the
     * same file, check if the rook shares the same end rank location. If it does, check
     * if there are any pieces on the same rank on a file between the start and end file.
     *
     * @param piecePos The positions of all rooks of the correct color.
     * @param endFile The file of the rook after moving.
     * @param endRank The rank of the rook after moving.
     *
     * @return An integer array storing the position of the rook that will be moved.
     */
    public static int[] getRookStart(int[][] piecePos, int endFile, int endRank) {
	// TODO: If no specifed start rank or file
	for (int[] pos: piecePos) {
	    boolean correct = true;
	    
	    if (pos[0] == endFile) {
		int direction = (endRank - pos[1]) / Math.abs(endRank - pos[1]);
		
		for (int r = 0; r < Math.abs(pos[1] - endRank) - 1; r++) {
		    if (getBoardStateSquare(endFile, Math.abs(r + direction * pos[1] + 1)) != "  ") {
			correct = false;
			
			break;
		    }
		}

		if (correct) {
		    int[] startPos = {pos[0], pos[1]};
		    
		    return startPos;
		}
	    }

	    if (pos[1] == endRank) {
		correct = true;

		int direction = (endFile - pos[0]) / Math.abs(endFile - pos[1]);

		for (int f = 0; f < Math.abs(pos[0] - endFile) - 1; f++) {
		    if (getBoardStateSquare(Math.abs(f + direction * pos[0] + 1), endRank) != "  ") {
			correct = false;
			
			break;
		    }
		}

		if (correct) {
		    int[] startPos = {pos[0], pos[1]};
		    
		    return startPos;
		}
	    }
	}

	return null;
    }

    /**
     * Handle the movement of knigths. If the knight's file is one away from the final
     * file, then the knight's rank is either two away from the final rank or the knight
     * is not able to move there. If the knight's rank is one away from the final rank,
     * then the knight's file is either two away from the final file or the knight is
     * not able to move there.
     *
     * @param piecePos The positions of all knights of the correct color.
     * @param endFile The file of the knight after moving.
     * @param endRank The rank of the knight after moving.
     *
     * @return An integer array storing the position of the knight that will be moved.
     */
    public static int[] getKnightStart(int[][] piecePos, int endFile, int endRank) {
	for (int[] pos: piecePos) {
	    if ((pos[0] + 1 == endFile || pos[0] - 1 == endFile) && (pos[1] + 2 == endRank || pos[1] - 2 == endRank)) {
		int[] startPos = {pos[0], pos[1]};

		return startPos;
	    } else if ((pos[0] + 2 == endFile || pos[0] - 2 == endFile) && (pos[1] + 1 == endRank || pos[1] - 1 == endRank)) {
		int[] startPos = {pos[0], pos[1]};

		return startPos;
	    }
	}

	return null;
    }
    
    /**
     * Handle the movement of bishops. Determine if the bishop to be moved is dark 
     * squared or light squared using modulo on the end square and find the bishop 
     * that matches.
     *
     * @param piecePos The positions of all bishops of the correct color.
     * @param endFile The file of the bishop after moving.
     * @param endRank The rank of the bishop after moving.
     *
     * @return An integer array storing the starting position of the bishop that will be moved.
     */
    public static int[] getBishopStart(int[][] piecePos, int endFile, int endRank) {
	int bishopColor = (endFile + endRank) % 2;

	for (int[] pos: piecePos) {
	    if ((pos[0] + pos[1]) % 2 == bishopColor) {
		int[] startPos = {pos[0], pos[1]};

		return startPos;
	    }
	}

	return null;
    }
}
