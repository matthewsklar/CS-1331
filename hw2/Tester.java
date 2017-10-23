public class Tester {
    public static void main(String[] args) {
	Piece king = new King(Color.WHITE);
	Piece queen = new Queen(Color.BLACK);
	Piece bishop = new Bishop(Color.BLACK);
	Piece knight = new Knight(Color.WHITE);
	Piece rook = new Rook(Color.WHITE);
	Piece pawn = new Pawn(Color.WHITE);

        Test(knight);
    }

    public static void Test(Piece p) {
	System.out.println("C: " + p.getColor() +
			   "\nA: " + p.algebraicName() +
			   "\nF: " + p.fenName());

	Square start = new Square("h3");
        Square other = new Square('c', '3');
        System.out.println(start + ", " + other + ", " + start.equals(other));

	System.out.println("Square: " + start);

	Square[] moves = p.movesFrom(start);

	System.out.println("Length: " + moves.length);

	for (Square s : moves) {
	    System.out.println(s);
	    // System.out.println(s.equals(s));
	}
    }
}
