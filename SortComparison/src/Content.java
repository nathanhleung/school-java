/**
 * Alphabet Soup Project: Content Class
 * A class representing the content to write using our bowls of alphabet soup.
 * @author Nathan Leung
 * @version 16 April 2017
 */

import java.util.ArrayList;
import java.util.Arrays;

class Content {
  private ArrayList<String> words;

  /**
   * Constructs a new instance of Content with the provided text
   * @param text the text to initialize Content with
   */
  public Content(String text) {
    setContent(text);
  }

  /**
	 * Removes all instances of a certain character from a string
	 * @param str the original string
	 * @param ch the character to remove from the string
	 * @return the new string
	 */
	public static String removeChar(String str, char ch) {
        int length = str.length();
        String newString = "";
        for (int i = 0; i < length; i++) {
            char current = str.charAt(i);
            if (current != ch) {
                newString += current;
            }
        }
        return newString;
    }

	/**
	 * Removes all instances of the provided characters from a string
	 * @param str the original string
	 * @param chs an array of characters to return
	 * @return the new string
	 */
    public static String removeMultipleChars(String str, char[] chs) {
        String newString = str;
        for (int i = 0; i < chs.length; i++) {
            newString = removeChar(newString, chs[i]);
        }
        return newString;
    }

    /**
     * Removes spaces from a string for the purposes of cleaning it before encryption/decryption
     * @param str the original string
     * @return the normalized string
     */
    public static String normalize(String str) {
        // Remove every puncutation mark except for spaces
        char[] charsToRemove = { '\'', '.', ',', '?', '"', '!' };
        String normalized =
            removeMultipleChars(str, charsToRemove).toUpperCase();
        String noEmDashes = "";
        // Handle em dashes - gettysburg specific
        int length = normalized.length();
        for (int i = 0; i < length; i++) {
        	// Checks if there is an instance of -- and it's not the last character
        	if (normalized.charAt(i) == '-' && normalized.charAt(i + 1) == '-' && i < length - 1) {
        		// Replace "--" with " "
        		noEmDashes += " ";
        	} else {
        		noEmDashes += normalized.charAt(i);
        	}
        }
        // Finally, remove the dashes
        return removeChar(noEmDashes, '-');
    }

    /**
     * Gets the words of the content
     * @return the words of the content
     */
    public ArrayList<String> getWords() {
      return words;
    }

    /**
     * Sets the content (normalizes, etc)
     * @param text the content to set
     * @return the content that was set
     */
    public ArrayList<String> setContent(String text) {
      // Normalize text before saving, by removing all punctuation
      // except for spaces and then splitting the remaining text
      // by spaces (into component words)
      words = new ArrayList<String>(Arrays.asList(normalize(text).split("\\s+")));
      return words;
    }

    /**
     * Creates a string representation of the content
     */
    public String toString() {
      return String.join(" ", words);
    }
}
