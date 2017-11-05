import java.util.*;

public class Tester {
    public static SquareSet<Square> squareSet = new SquareSet();
    public static SquareSet<Square> squareSet2;
    public static SquareSet<Square> squareSetPre;

    public static void main(String[] args) {
        squareSet2 = new SquareSet();

        Square[] squares = { new Square("h1"), new Square("h2") };
        squareSetPre = new SquareSet(Arrays.asList(squares));

        debugSection("Add/Size");
        System.out.println(squareSet.size());
        add(new Square("h2"));
        add(new Square("h1"));
        add(new Square("h1"));
        add(null);
        add(new Square("h2"));
        add(new Square("a4"));

        debugSection("Contain");
        contain(new Square("h2"));
        contain(new Square("h8"));

        debugSection("Contains All");
        squareSet2.add(new Square("h2"));
        squareSet2.add(new Square("h1"));
        containsAll(squareSet2);

        debugSection("Equals");
        equal(squareSet);
        equal(squareSet2);
        squareSet2.add(new Square("a4"));
        equal(squareSet2);
        squareSet2.add(new Square("a4"));
        equal(squareSet2);
        squareSet2.add(new Square("a1"));
        equal(squareSet2);

        debugSection("Hashcode");

        debugSection("Is Empty");
        empty();

        debugSection("Iterator");
        for (Square s : squareSet) {
            System.out.println(s);
        }

        debugSection("toArray");
        System.out.println(squareSet.toArray()[0]);
        toArray(squareSet);
        toArray(squareSet2);
        toArray(squareSet);
    }

    public static void debugSection(String title) {
        System.out.println("---------------------" +
                           title + "---------------------");
    }

    public static void add(Square o) {
        try {
            squareSet.add(o);

            System.out.println(squareSet.size());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void contain(Object o) {
        System.out.println(squareSet.contains(o));
    }

    public static void containsAll(Collection<?> c) {
        try {
            System.out.println(squareSet.contains(Arrays.asList(c)));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void equal(Object o) {
        try {
            System.out.println(squareSet.equals(o));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void hashC() {

    }

    public static void empty() {
        System.out.println(squareSet.isEmpty());
    }

    public static void toArray(SquareSet s) {
        try {
            System.out.println(s.toArray());
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
