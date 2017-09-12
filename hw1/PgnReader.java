import java.nio.file.Files;
import java.nio.file.Paths;

public class PgnReader {
    // http://cs1331.gatech.edu/fall2017/hw1/hw1-pgn-reader.html
    public static void main(String[] args) {
	String pgn = "NOT GIVEN";
	
	try {
	    pgn = new String(Files.readAllBytes(Paths.get("fegatello.pgn")));
	} catch (Exception e) {
	    e.printStackTrace();
	}
	
	tagValue("Event", pgn);
    }

    /*
     * Get the value corresponding to the tag name in the pgn file.
     *
     * @param name The name of the tag
     * @param pgn The pgn file
     *q
     * @return A String containing the value of the tag with the given name in the pgn file.
     * If the tag is not found, it returns the String "NOT GIVEN".
     */
    public static void tagValue(String name, String pgn) {
	System.out.println(pgn);
    }

    public static void finalPosition(String pgn) {

    }
}
