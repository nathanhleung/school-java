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
	 * Removes all instances of the provided characters from a string
	 * @param str the original string
	 * @param chs an array of characters to return
	 * @return the new string
	 */
    public static String removeMultipleChars(String str, char[] chs) {
        String newString = "";
        for (int i = 0; i < str.length(); i++) {
        	char curr = str.charAt(i);
        	if (!Arrays.asList(chs).contains(curr)) {
        		newString += curr;
        	}
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
        char[] charsToRemove = { '\'', '.', ',', '?', '"', '!', '-' };
        String normalized =
            removeMultipleChars(str, charsToRemove).toUpperCase();
        return normalized;
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
