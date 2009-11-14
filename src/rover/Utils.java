package rover;


/*
 * Miscellanous utilities that do not yet have a home.
 */
public class Utils {
	
	/*
	 * Splits up a string of whitespace separated strings
	 * Not stricty required for Rover problem.
	 * Added to avoid space vs tabe confusion
	 * @param description - a string of strings separated by whitespace
	 * @return the strings
	 */
	static String[] splitString(String description) {
		String[] parts = description.split("[ \t]");
		int n = 0;
		for (String p: parts) {
			if (p.length() > 0)
				++n;
		}
		String[] nonEmptyParts = new String[n];
		n = 0;
		for (String p: parts) {
			if (p.length() > 0) {
				nonEmptyParts[n++] = p;
			}
		}
		return nonEmptyParts;
	}
}
