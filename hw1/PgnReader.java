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
	
	System.out.println(tagValue("Event", pgn));
    }

    /*
     * Get the value corresponding to the tag name in the pgn file.
     *
     * @param name The name of the tag
     * @param pgn The pgn file
     *
     * @return A String containing the value of the tag with the given name in the pgn file.
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

	String value = pgn.substring(startIndex, endIndex);

	return value;
    }

    public static void finalPosition(String pgn) {

    }
}
