import java.nio.file.Files;
import java.nio.file.Paths;

public class PgnReader {
    private static String[][] boardState;

    /**
     * Getter for board state.
     */
    public static String[][] getBoardState() {
        return boardState;
    }

    /**
     * Setter for board state.
     *
     * @param bs Value to set board state as.
     */
    public static void setBoardState(String[][] bs) {
        boardState = bs;
    }

    /**
     * Get the status of the square on the board.
     *
     * @param file The file (x-axis) of the square on the board.
     * @param rank The rank (y-axis) of the square on the board.
     *
     * @return The value of the piece on the square.
     *     "  " = no piece
     *     "Pc" = Piece color
     */
    public static String getBoardStateSquare(int file, int rank) {
        return boardState[rank][file];
    }

    /**
     * Set the status of a square on the board.
     *
     * @param file The file (x-axis) of the square on the board.
     * @param rank The rank (y-axis) of the square on the board.
     * @param piece The piece to be set on the square in the format Pc.
     */
    public static void setBoardStateSquare(int file, int rank, String piece) {
        boardState[rank][file] = piece;
    }

    /**
     * The main method of the program. Get the pgn content from the pgn file
     * and use the String as a variable to pass to methods to get the tagValues
     * and the final position.
     */
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
     * @return The value of the tag with the given name in the pgn file.
     * If the tag is not found, it returns the String "NOT GIVEN".
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
     * @return The FEN of the final position in the pgn file.
     */
    public static String finalPosition(String pgn) {
        setBoardState(createBoardState());

        moves(pgn);

        return pgnToFen();
    }

    /**
     * Create the inital board state of the chess board.
     *
     * R = Rook; B = Bishop; N = Knight; K = King; Q = Queen; P = Pawn
     *
     * @return The initial state of the chess board with each piece in
     * the form Pc.
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
    public static void move(String piece, int startFile, int startRank,
                             int endFile, int endRank) {
        setBoardStateSquare(startFile, startRank, "  ");
        setBoardStateSquare(endFile, endRank, piece);
    }

    /**
     * Determine the moves in the chess game based on the pgn.
     * Individually send the moves to be handled.
     *
     * @param pgn The pgn file.
     */
    public static void moves(String pgn) {
        pgn = pgn.substring(pgn.lastIndexOf("]"), pgn.length());
        String pgnMoves = pgn.substring(pgn.indexOf("1."));

        int lastMoveIndex = 0;
        int startIndex = 0;

        while (startIndex != -1) {
            startIndex = pgnMoves.indexOf(".", startIndex);
            int endIndex = pgnMoves.indexOf(".", startIndex + 1);

            String[] moves;

            if (endIndex == -1) {
                moves = pgnMoves.substring(startIndex + 1).trim().split(" ");
            } else {
                moves = pgnMoves.substring(startIndex + 1, endIndex - 1).trim()
                    .split(" ");
            }

            processMove(moves[0], 'w');

            if (moves.length >= 2) {
                processMove(moves[1], 'b');
            }

            startIndex = endIndex;
        }
    }

    /**
     * Obtain information from algebraic notation of move. First, remove all
     * annotations from the end of the notation. After, if the move is a
     * castle, call the relevant method. If the move is not a castle,
     * retrieve relevent information from the notation, removing the
     * information from the notation after storing it. Use the information
     * to get the starting position of the piece moved and move the piece.
     *
     * @param move The algebraic notation of the move.
     * @param color The color of the piece moved: 'w' = white; 'b' = black.
     */
    public static void processMove(String move, char color) {
        char lastChar = move.charAt(move.length() - 1);

        if (lastChar == '+' || lastChar == '#') {
            move = move.substring(0, move.length() - 1);
        }

        // Castling
        if (move.equals("O-O")) {
            castleKingSide(color);

            return;
        } else if (move.equals("O-O-O")) {
            castleQueenSide(color);

            return;
        }

        char piece = Character.isUpperCase(move.charAt(0)) ? move.charAt(0)
            : 'P';

        int promoteIndex = move.indexOf('=');
        boolean promote = promoteIndex != -1;

        int xIndex = move.indexOf('x');
        boolean capture = xIndex != -1;

        if (piece != 'P') {
            move = move.substring(1, move.length());
        }

        String promotion = promote ? Character.toString(
            move.charAt(promoteIndex + 1))
            + Character.toString(color) : null;

        if (promote) {
            move = move.substring(0, promoteIndex);
        }

        if (capture) {
            move = move.replace("x", "");
        }

        int endFile = move.charAt(move.length() - 2) - 97;
        int endRank = move.charAt(move.length() - 1) - 49;

        move = move.substring(0, move.length() - 2);

        int startFile = -1;
        int startRank = -1;

        if (!move.equals("")) {
            for (int i = 0; i < move.length(); i++) {
                char c = move.charAt(i);

                if (Character.isLetter(c)) {
                    startFile = c - 97;
                } else {
                    startRank = c - 49;
                }
            }

            move = "";
        }

        String piecePlusColor =
            Character.toString(piece) + Character.toString(color);
        String endPiece = promote ? promotion : piecePlusColor;

        int[] startPos = getStartPosition(piece, color, piecePlusColor,
                                          startFile, startRank, endFile,
                                          endRank, capture);

        move(endPiece, startPos[0], startPos[1], endFile, endRank);
    }

    /**
     * Handle king side castling. King side castling is annotated as "O-O" and
     * moves the king to g1 for white and g8 for black and the rook to f1 for
     * white and f8 for black. This method moves the king and rook to the
     * correct square.
     *
     * @param color The color that is castling.
     */
    public static void castleKingSide(char color) {
        String colorString = Character.toString(color);

        String king = "K" + colorString;
        String rook = "R" + colorString;

        int rank = color == 'w' ? 0 : 7;

        move(king, 4, rank, 6, rank);
        move(rook, 7, rank, 5, rank);
    }

    /**
     * Handle queen side castling. Queen side castling is annotated as "O-O-O"
     * and moves the king to c1 for white and c8 for black and the rook to d1
     * for white and d8 for black. This method moves the king and rook to the
     * correct square.
     *
     * @param color The color that is castling.
     */
    public static void castleQueenSide(char color) {
        String colorString = Character.toString(color);

        String king = "K" + colorString;
        String rook = "R" + colorString;

        int rank = color == 'w' ? 0 : 7;

        move(king, 4, rank, 2, rank);
        move(rook, 0, rank, 3, rank);
    }

    /**
     * Get the start position of the moved piece. Get pieces with the correct
     * start positions. Each piece has different movement, so call the method
     * that corresponds with the piece and returs the start position of the
     * piece.
     *
     * @param piece The piece that was moved.
     * @param color The color of the piece that was moved.
     * @param piecePlusColor A string combining the piece that was moved and
     *        its color.
     * @param startFile The file the piece starts on (-1 if unknown).
     * @param startRank The rank the piece starts on (-1 if unknown).
     * @param endFile The file the piece ends on.
     * @param endRank The rank the piece ends on.
     * @param capture If the piece is a pawn that captured a piece this turn.
     *
     * @return The start position of the piece.
     */
    public static int[] getStartPosition(char piece, char color,
                                           String piecePlusColor, int startFile,
                                           int startRank, int endFile,
                                           int endRank, boolean capture) {
        int[][] piecePos = getPiecePositions(piecePlusColor, startFile,
                                             startRank);
        int[] startPos = null;

        if (piece == 'P') {
            startPos = getPawnStart(piecePos, color, endFile, endRank, capture);
        } else if (piece == 'R') {
            startPos = getRookStart(piecePos, endFile, endRank);
        } else if (piece == 'N') {
            startPos = getKnightStart(piecePos, endFile, endRank);
        } else if (piece == 'B') {
            startPos = getBishopStart(piecePos, endFile, endRank);
        } else if (piece == 'Q') {
            startPos = getQueenStart(piecePos, endFile, endRank);
        } else if (piece == 'K') {
            startPos = piecePos[0];
        }

        return startPos;
    }

    /**
     * Get the positions of instances of piece. If information about a starting
     * position exists, then only get instances of the piece that fit the
     * information about the start position.
     *
     * @param piece The piece to get the positions of in the format Pc.
     * @param startFile The start file if it is already known.
     * @param startRank The start rank if it is already known.
     *
     * @return The rank and file for every position of the piece that fits the
     *         starting parameters.
     */
    public static int[][] getPiecePositions(String piece, int startFile,
                                            int startRank) {
        int numPiecePos = 0;

        int[][] piecePos;

        if (startFile != -1 && startRank != -1) {
            piecePos = new int[1][2];

            piecePos[0][0] = startFile;
            piecePos[0][1] = startRank;
        } else if (startFile != -1) {
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
        } else if (startRank != -1) {
            for (int f = 0; f < 8; f++) {
                if (getBoardStateSquare(f, startRank).equals(piece)) {
                    numPiecePos++;
                }
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
        } else {
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
                for (int f = 0; f < getBoardState()[r].length; f++) {
                    if (getBoardStateSquare(f, r).equals(piece)) {
                        piecePos[pieceIndex][0] = f;
                        piecePos[pieceIndex][1] = r;

                        pieceIndex++;
                    }
                }
            }
        }

        return piecePos;
    }

    /**
     * Handle the movement of pawns. If it is a capture determine direction
     * of the capture. If the end position does not contain a piece, then
     * en passant. Otherwise check if the pawn is in the correct position
     * to capture. If it isnt a capture, check for a pawn on the same file.
     * If the pawn is 2 spaces away, check if is annn pawn in the middle
     * space, if there is one then that pawn is moved, the pawn 2 spaces
     * away is moved.
     *
     * @param piecePos The positions of all pawns of the correct color.
     * @param color The color of the pawns.
     * @param endFile The file of the pawn after moving.
     * @param endRank The rank of the pawn after moving.
     * @param capture If the pawn is capturing a piece.
     *
     * @return The position of the pawn that will be moved.
     */
    public static int[] getPawnStart(int[][] piecePos, char color, int endFile,
                                     int endRank, boolean capture) {
        if (capture) {
            // En Passant
            int direction = color == 'w' ? 1 : -1;

            if (getBoardStateSquare(endFile, endRank).equals("  ")) {
                for (int[] pos: piecePos) {
                    if (endRank == pos[1] + direction) {
                        int[] posInfo = {pos[0], pos[1]};
                        move("  ", endFile, endRank, endFile, pos[1]);

                        return posInfo;
                    }
                }

            }

            for (int[] pos: piecePos) {
                if (endRank == pos[1] + direction) {
                    int[] posInfo = {pos[0], pos[1]};

                    return posInfo;
                }
            }
        }

        for (int[] pos: piecePos) {
            if (pos[0] == endFile) {
                //if (Math.abs(endRank - pos[1]) == 2) {
                if (Math.abs(endRank - pos[1]) == 1) {
                    int[] posInfo = {pos[0], pos[1]};

                    return posInfo;
                }

                int direction = (endRank - pos[1]) / Math.abs(endRank - pos[1]);

                boolean correct = true;

                for (int r = 0; r < Math.abs(pos[1] - endRank) - 1; r++) {
                    if (!getBoardStateSquare(endFile,
                                             Math.abs(
                                             r + direction * pos[1] + 1)
                                             ).equals("  ")) {
                        correct = false;

                        break;
                    }
                }

                if (correct) {
                    int[] posInfo = {pos[0], pos[1]};

                    return posInfo;
                }
            }
        }

        return null;
    }

    /**
     * Handle the movement of rooks. Find the rook that shares the same end
     * file location. If it exists, check if there are any pieces on the
     * same file on a rank between the start and end rank. If there is or if
     * no rook is on the same file, check if the rook shares the same end
     * rank location. If it does, check if there are any pieces on the same
     * rank on a file between the start and end file.
     *
     * @param piecePos The positions of all rooks of the correct color.
     * @param endFile The file of the rook after moving.
     * @param endRank The rank of the rook after moving.
     *
     * @return The position of the rook that will be moved.
     */
    public static int[] getRookStart(int[][] piecePos, int endFile,
                                     int endRank) {
        for (int[] pos: piecePos) {
            boolean correct = true;

            if (pos[0] == endFile) {
                int direction = (endRank - pos[1]) / Math.abs(endRank - pos[1]);

                for (int r = 0; r < Math.abs(pos[1] - endRank) - 1; r++) {
                    if (!getBoardStateSquare(endFile, Math.abs(
                                                    r + direction * pos[1] + 1))
                                                    .equals("  ")) {
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

                int direction = (endFile - pos[0]) / Math.abs(endFile - pos[0]);

                for (int f = 0; f < Math.abs(pos[0] - endFile) - 1; f++) {
                    if (!getBoardStateSquare(Math.abs(
                                             f + direction * pos[0] + 1
                                             ), endRank).equals("  ")) {
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
     * Handle the movement of knigths. If the knight's file is one away from the
     * final file, then the knight's rank is either two away from the final rank
     * or the knight is not able to move there. If the knight's rank is one away
     * from the final rank, then the knight's file is either two away from the
     * final file or the knight is not able to move there.
     *
     * @param piecePos The positions of all knights of the correct color.
     * @param endFile The file of the knight after moving.
     * @param endRank The rank of the knight after moving.
     *
     * @return The position of the knight that will be moved.
     */
    public static int[] getKnightStart(int[][] piecePos, int endFile,
                                       int endRank) {
        for (int[] pos: piecePos) {
            if ((pos[0] + 1 == endFile || pos[0] - 1 == endFile)
                && (pos[1] + 2 == endRank || pos[1] - 2 == endRank)) {
                int[] startPos = {pos[0], pos[1]};

                return startPos;
            } else if ((pos[0] + 2 == endFile || pos[0] - 2 == endFile)
                       && (pos[1] + 1 == endRank || pos[1] - 1 == endRank)) {
                int[] startPos = {pos[0], pos[1]};

                return startPos;
            }
        }

        return null;
    }

    /**
     * Handle the movement of bishops. Determine if the bishop to be moved is
     * dark squared or light squared using modulo on the end square and find
     * the bishop that matches.
     *
     * @param piecePos The positions of all bishops of the correct color.
     * @param endFile The file of the bishop after moving.
     * @param endRank The rank of the bishop after moving.
     *
     * @return The starting position of the bishop that will be moved.
     */
    public static int[] getBishopStart(int[][] piecePos, int endFile,
                                       int endRank) {
        int bishopColor = (endFile + endRank) % 2;

        for (int[] pos: piecePos) {
            if ((pos[0] + pos[1]) % 2 == bishopColor) {
                int fDir = (endFile - pos[0]) / Math.abs(endFile - pos[0]);
                int rDir = (endRank - pos[1]) / Math.abs(endRank - pos[1]);

                boolean correct = true;

                for (int i = 0; i < Math.abs(pos[0] - endFile) - 1; i++) {
                    int file = Math.abs(i + fDir * pos[0] + 1);
                    int rank = Math.abs(i + rDir * pos[1] + 1);

                    if (!getBoardStateSquare(file, rank).equals("  ")) {
                        correct = false;
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
     * Handle the movement of Queens. First, check if any queen is capable
     * of making a rook move. If one is, return its starting position;
     * otherwise, check if any queen is capble of making a bishop move.
     * If one is, return its starting position; otherwise, return null.
     *
     * @param piecePos The positions of all queens of the correct color.
     * @param endFile The file of the queen after moving.
     * @param endRank The rank of the queen after moving.
     *
     * @return The starting position of the queen that will be moved.
     */
    public static int[] getQueenStart(int[][] piecePos, int endFile,
                                      int endRank) {
        int[] rookMove = getRookStart(piecePos, endFile, endRank);

        if (rookMove != null) {
            return rookMove;
        }

        int[] bishopMove = getBishopStart(piecePos, endFile, endRank);

        if (bishopMove != null) {
            return bishopMove;
        }

        return null;
    }

    /**
     * Convert the game in pgn to fen. Get the current board state and
     * iterate through every rank to find the fen of each row. Combine
     * them together to form the complete fen.
     *
     * @return The fen of the board state corresponding to the pgn.
     */
    public static String pgnToFen() {
        String fen = "";

        for (int i = boardState.length - 1; i >= 0; i--) {
            String fenLine = "";

            int spaceStreak = 0;

            for (String file: getBoardState()[i]) {
                String piece = Character.toString(file.charAt(0));
                char color = file.charAt(1);

                if (color == ' ') {
                    spaceStreak++;

                    continue;
                } else if (spaceStreak > 0) {
                    fenLine += spaceStreak;

                    spaceStreak = 0;
                }

                String square = color == 'w' ? piece : piece.toLowerCase();

                fenLine += square;
            }

            if (spaceStreak > 0) {
                fenLine += spaceStreak;
            }

            fen += fenLine + (i != 0 ? "/" : "");
        }

        return fen;
    }
}
