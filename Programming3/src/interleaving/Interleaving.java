package interleaving;

import java.io.FileWriter;
import java.util.ArrayList;

/**
 * @author Hunter R. Caskey
 * 
 * Interleaving - This class implements an algorithm that decides if a given string, s,  is an
 * interleaving of two substrings, x and y. This algorithm has a linear O(XYS) running time. 
 * A test suite is run within the main routine to drive the program and to collect metrics
 * on the number of CPU comparisons made within the algorithm for analysis purposes. 
 * Consequently, execution of the main function results in the output for the tests to be 
 * written to a file named "programming3_tests.log".
 */
public class Interleaving {

	// For testing purposes: Defined the threshold number of log lines to write to file for
	private final static int MAX_CONSOLE_LOGS = 0;

	// For analysis purposes: Tracks the number of comparisons conducted in the execution of an algorithm.
	private static int comparisons;

	/**
	 * The entrypoint into the program.
	 * 
	 * @param args Command line arguments for the main driver of this program.
	 */
	public static void main(String[] args) {
		ArrayList<String> logger = new ArrayList<String>();
		runTest(logger, "1000110101", "101", "0");
		runTest(logger, "101", "101", "0");
		runTest(logger, "0", "101", "0");
		runTest(logger, "100000000000000000000000000000000000000000000000000001", "101", "0");
		runTest(logger, "aaa1010101aaa", "101", "0");
		runTest(logger, "aba1010101", "101", "0");
		runTest(logger, "1010101aba", "101", "0");
		outputTestResults(logger, "programming3_tests.log");
		
	}
	
	/**
	 * runTest is a utility methods that runs a test for a given set of s, x, and y and creates
	 * output messages that describe the execution of the algorithm.
	 * 
	 * @param logger The ArrayList to write log strings to.
	 * @param s The string that will be analyzed for the presence of an interleaving. 
	 * @param x One of two substrings that may form an interleaving.
	 * @param y One of two substrings that may form an interleaving.
	 */
	public static void runTest(ArrayList<String> logger, String s, String x, String y) {
		boolean result = isInterleave(s, x, y);
		if(result) {
			logger.add("String '" + s + "' is an interleaving of x='" + x + "' and y='" + y + "'.");
		}
		else {
			logger.add("String '" + s + "' is not an interleaving of x='" + x + "' and y='" + y + "'.");
		}
		logger.add("The worst case number of comparisons required to execute the algorithm was: " + comparisons + ".\n");
	}

	/**
	 * isInterleave constructs a variety of different permutations of s and calls recursiveInterleave on each
	 * in order to determine whether a given string s is an interleaving of substrings x and y. The permutations
	 * of s are created by removing characters from the beginning of the string, end of the string, and both
	 * the beginning and end of the string. This method returns true as soon as the first viable permutation has 
	 * been calculated. Additionally, this method, along with bookkeeping from recurseInterleave, keeps track
	 * of the number of CPU comparisons for the purposes of analyzing the execution of the algorithm. 
	 * 
	 * @param s The string to see examine for an interleaving of x and y. 
	 * @param x A substring that along with y may form an interleaving in string s. 
	 * @param y A substring that along with x may form an interleaving in string s. 
	 * @return True if an interleaving of x and y exists in  s, false otherwise.
	 */
	public static boolean isInterleave(String s, String x, String y) {
		comparisons = 0;
		for(int i = 0; i <= s.length(); i++) {
			for(int j = 0; j <= s.length(); j++) {
				String s1 = s.substring(i);
				String s2 = s.substring(0, s.length() - j);
				 if (recurseInterleave(s1,x,y,x,y, false, false) || recurseInterleave(s2,x,y,x,y, false, false)) {
					 return true;
				 }
				 else if(i < s.length() - j) {
						String s3 = s.substring(i, s.length() - j);
						if(recurseInterleave(s3,x,y,x,y, false, false)) {
							 return true;
						}
				 }
			}
		}
		return false;
	}

	/**
	 * recurseInterleave recursively reduced the original value passed in as s by one character on each invocation. 
	 * As the function recurses, it checks if the beginning of s is equal to the beginning of x or y. If it is, a character 
	 * will be stripped off of either x or y and the next character within the substring will be searched for during the subsequent 
	 * recursive calls. Eventually, the recursive calls will analyze all symbols in s, and if the substrings x and y have both
	 * been seen during that execution, the method will return true. However, if the conditions are not met the method will return false.
	 * If we encounter a symbol from s that is the next search symbol for x AND y, then a two separate recursion paths
	 * are created and the result of both are OR'd to rejoin the paths on the tail end of that source recursive call.
	 * 
	 * @param s The string to see examine for an interleaving of x and y, loses a character on each recursive call.
	 * @param x A substring of _x that is used in the determination of an interleaving.
	 * @param y A substring of _y that is used in the determination of an interleaving.
	 * @param _x The original copy of the substring x, used to reset x once all characters have been found.
	 * @param _y The original copy of the substring y, used to reset y once all characters have been found.
	 * @param xPresent A boolean indicating the substring x validly exists within s at least once (min requirement for interleaving)
	 * @param yPresent A boolean indicating the substring y validly exists within s at least once (min requirement for interleaving)
	 * @return True if the original value of s only consists of repetitions of substrings _x and _y, false otherwise.
	 */
	public static boolean recurseInterleave(String s, String x, String y, String _x, String _y, boolean xPresent, boolean yPresent)
	{
		// If all symbols in _x have been found within s
		comparisons++;
		if (x.length() == 0) {
			xPresent = true;
			x = _x;
		}
		
		// If all symbols in _y have been found within s
		comparisons++;
		if(y.length() == 0) {
			// Assign original value
			yPresent = true;
			y = _y;
		}
		
		// Check s is empty and an interleaving has been found
		if (s.length() == 0 && xPresent && yPresent) {
			comparisons += 3;
			return true;
		}
		else {
			comparisons += 2;
		}
		
		// If there are symbols in x or y and s is empty
		comparisons++;
		if (s.length() == 0) {
			return false;
		}

		comparisons += 4;
		return ((s.charAt(0) == x.charAt(0) && recurseInterleave(s.substring(1), x.substring(1), y, _x,_y, xPresent, yPresent)) || 
				(s.charAt(0) == y.charAt(0) && recurseInterleave(s.substring(1),x, y.substring(1), _x,_y, xPresent, yPresent)));
		
	}

	/**
	 * Outputs the given log statements to the command line if the number of lines to print is less than 30, otherwise
	 * writes the lines to the specified file.
	 * 
	 * @param logger ArrayList containing all of the log statements to output.
	 * @param fileName The name of the file that logs should be written to, null value indicates that the output should be printed to the console.
	 */
	public static void outputTestResults(ArrayList<String> logger, String fileName) {
		if(logger.size() < MAX_CONSOLE_LOGS) {
			for(String msg : logger) {
				System.out.println(msg);
			}
		}
		else {
			FileWriter writer;
			try {
				writer = new FileWriter(fileName);
				for(String msg : logger) {
					writer.write(msg + System.lineSeparator());
				}
				writer.close();
			}
			catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

}
