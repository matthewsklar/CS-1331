/**
 * NameParser takes a single String-valued command line argument of
 * the form "last_name, first_name" and prints the first and last names
 * separately.
 */
public class NameParser {

    public static String extractLastName(String name) {
        int commaPos = name.indexOf(",");
        String lastName = name.substring(0, commaPos).trim();
        return lastName;
    }

    public static String extractFirstName(String name) {
        int commaPos = name.indexOf(",");
        int len = name.length();
        String firstName = name.substring(commaPos + 1, len).trim();
        return firstName;
    }

    public static String getInitials(String name) {
        String firstInitial = extractFirstName(name).substring(0, 1).trim();
        String lastInitial = extractLastName(name).substring(0, 1).trim();
        String output = firstInitial + lastInitial;
        return output.toUpperCase();
    }

    public static void main(String[] args) {
        String fullName = args[0];
        System.out.println(fullName);
        String lastName = extractLastName(fullName);
        String firstName = extractFirstName(fullName);
        System.out.println("First name:\t" + firstName);
        System.out.println("Last name:\t" + lastName);
        System.out.println("Initials:\t" + getInitials(fullName));
    }

}
