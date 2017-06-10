/**
 * Frequency Analysis
 * A Java implementation of frequency analysis in text
 * @author 18nleung
 * @version 28 March 2017
 */

import java.util.Map;
import java.util.Optional;
import java.util.HashMap;
import java.util.ArrayList;

public class FrequencyAnalysis {
	private String str;
	private Map<Character, Integer> frequencies;
	private char[] frequencyList;
	
	/**
	 * Constructs a new instance of the FrequencyAnalysis class
	 * and counts the letter frequency of the provided string
	 * @param str the string to count characters in
	 */
	public FrequencyAnalysis(String str) {
		// Normalize string before storing it (done in setString)
		setString(str);
		// Letters in English ordered by frequency
		frequencyList = new char[]{
			'E', 'T', 'A', 'O', 'I',
			'N', 'S', 'R', 'H', 'D',
			'L', 'U', 'C', 'M', 'F',
			'Y', 'W', 'G', 'P', 'B',
			'V', 'K', 'X', 'Q', 'J',
			'Z',
		};
	}
	
	/**
	 * Runs a test suite on the class to ensure that
	 * it is working correctly
	 */
	public void test() {
		String oldString = str;
		int failedTests = 0;
		Map<String, String> tests = new HashMap<String, String>();
		
		// Test cases are set up here
		// The first character in the value part is the target
		// character, and the number is the target count
		tests.put("FIRE MISSILE", "I3");
		tests.put("Genius without education is like silver in the mine", "T4");
		
		// Iterates over each test case
		for (Map.Entry<String, String> entry : tests.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			
			// Get the character
			char ch = value.charAt(0);
			// Get the number of instances
			int times = new Integer(value.substring(1));

			setString(key);
			
			int output = frequencies.get(ch);
			if (output != times) {
				System.out.println("Frequency test failed! Got: " + output + ", Expected: " + times);
				failedTests++;
			}
		}
		if (failedTests > 0) {
			System.out.println(failedTests + " test(s) failed, see above for details.");
		} else {
			System.out.println("All FrequencyAnalysis tests passing!");
		}
		setString(oldString);
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
        // Normalized so we don't have to worry about
        // case, spaces, punctuation, etc
        char[] charsToRemove = { ' ', '\'', '.', ',', '-', '?', '"', '!', '\n' };
        String normalized =
            removeMultipleChars(str, charsToRemove).toUpperCase();
        return normalized;
    }
	
	/**
	 * Counts the frequencies of letters in the provided string
	 * and stores the counts in the frequencies HashMap
	 */
	private void countFrequency() {
		for (int i = 0; i < str.length(); i++) {
			char currChar = str.charAt(i);
			int currCount;
			if (frequencies.get(currChar) != null) {
				currCount = frequencies.get(currChar);
			} else {
				currCount = 0;
			}
			frequencies.put(currChar, currCount + 1);
		}
	}
	
	/**
	 * Get the currently analyzed string
	 * @return the currently analyzed string
	 */
	public String getString() {
		return str;
	}
	
	/**
	 * Set the new string to analyze and analyze it
	 * @param str the string to analyze
	 * @return the new string
	 */
	public String setString(String str) {
		this.str = normalize(str);
		frequencies = new HashMap<Character, Integer>();
		countFrequency();
		return this.str;
	}
	
	/**
	 * Get the map of the frequencies
	 * @return a Map containing letter frequencies
	 */
	public Map<Character, Integer> getFrequencies() {
		return frequencies;
	}
	
	/**
	 * Guesses the shift based on which letter was most common in the table
	 * @param skip how many letters to skip (for example, 1 would skip E) - only used if E didn't work
	 * @return the guess for a key
	 */
	public int guessKey(int skip) {
		Map<Character, Integer> frequenciesCopy = new HashMap<Character, Integer>(frequencies);
		// Start it at A
		char mostCommon = 'A';
		do {
			int max = 0;
			for (Map.Entry<Character, Integer> entry : frequenciesCopy.entrySet()) {
				int value = entry.getValue();
				if (value > max) {
					max = value;
					mostCommon = entry.getKey();
				}
			}
			frequenciesCopy.remove(mostCommon);
			skip -= 1;
		} while (skip >= 0);
		// Get the shift between the most common
		// letter in our frequency chart and the letter E
		// which is the most common letter in English
		int shift = (int) mostCommon - (int) 'E';
		if (shift < 0) {
			shift += 26;
		}
		return shift;
	}
	
	/**
	 * Creates a string representation of the current state of the class
	 */
	public String toString() {
		String result = "";
		for (Map.Entry<Character, Integer> entry : frequencies.entrySet()) {
			result += (entry.getKey() + ": " + entry.getValue() + "\n");
		}
		return "Frequency Analysis" + 
			"\nOriginal String: " + str + 
			"\nCharacter Counts:" +
			"\n" + result +
			"\nFrequency Table Source: " +
			"https://www.math.cornell.edu/~mec/2003-2004/cryptography/subs/frequencies.html";
	}
}
