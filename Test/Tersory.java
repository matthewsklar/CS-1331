public class Tersory {
    public static void main(String[] args) {
	int a = 7;

        String fav = (++a % 2 == 0) ? "GT" : "UGA";
        System.out.printf("Fav: %s \n", fav);
    }
}
