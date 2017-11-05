public class Tester {
    public static SquareSet<Square> squareSet = new SquareSet();
    public static SquareSet squareSet2;

    public static void main(String[] args) {
        System.out.println(squareSet.size());

        add(new Square("h2"));
        add(new Square("h1"));
        add(new Square("h2"));

        for (Square s : squareSet) {
            System.out.println(s);
        }
    }

    public static void add(Square o) {
        squareSet.add(o);

        System.out.println(squareSet.size());
    }
}
