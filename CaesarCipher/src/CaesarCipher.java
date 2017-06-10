/**
 * Caesar Cipher
 * A Java implementation of the Caesar cipher
 * @author 18nleung
 * @version 28 March 2017
 */

import java.util.Map;
import java.util.HashMap;

public class CaesarCipher {
	private int shift;
	
	/**
	 * Constructs a new instance of the CaesarCipher class
	 * @param shift the shift to use for the Caesar cipher
	 */
	public CaesarCipher(int shift) {
		this.shift = shift;
	}
	
	/**
	 * Runs a test suite on the class to ensure that
	 * it is working correctly
	 */
	public void test() {
		int oldShift = shift;
		int failedTests = 0;
		Map<String, String> tests = new HashMap<String, String>();
		
		// Test cases are set up here
		// The first integer in the value part is the shift
		tests.put("FIRE MISSILE", "03ILUHP LVVLO H");
		tests.put("Genius without education is like silver in the mine", "13TRAVH FJVGU BHGRQ HPNGV BAVFY VXRFV YIREV AGURZ VAR");
		
		// Iterates over each test case
		for (Map.Entry<String, String> entry : tests.entrySet()) {
			String key = entry.getKey();
			// Normalize the key for checking against decryption
			// since the decrypted text will also have been
			// normalized
			String normalizedKey = groupLetters(normalize(key));
			String value = entry.getValue();
			// Get the shift
			shift = new Integer(value.substring(0, 2));
			// Remove the shift from the value
			value = value.substring(2);
			String encrypted = encrypt(key);
			String decrypted = decrypt(value);
			// Group the letters because the decryption algorithm
			// doesn't know how the letters originally were
			if (!normalizedKey.equals(decrypted)) {
				System.out.println("Encryption test failed! Got: " + decrypted + ", Expected: " + normalizedKey);
				failedTests++;
			}
			if (!value.equals(encrypted)) {
				System.out.println("Decryption test failed! Got: " + encrypted + ", Expected: " + value);
				failedTests++;
			}
		}
		if (failedTests > 0) {
			System.out.println(failedTests + " test(s) failed, see above for details.");
		} else {
			System.out.println("All CaesarCipher tests passing!");
		}
		shift = oldShift;
	}
	
	/**
	 * Groups the letters of the provided string into blocks of 5
	 * @return the string with grouped blocks of letters
	 */
	public static String groupLetters(String str) {
		String result = "";
		for (int i = 0; i < str.length(); i++) {
			// If we're on the fifth letter and it's
			// not the first letter, add a space
			if (i % 5 == 0 && i != 0) {
				result += " ";
			}
			result += str.charAt(i);
		}
		return result;
	}
	
	/**
	 * Gets the currently set shift value
	 * @return the current shift value
	 */
	public int getShift() {
		return shift;
	}
	
	/**
	 * Sets the new shift value
	 * @param newShift the new shift value for the cipher
	 * @return the new shift value
	 */
	public int setShift(int newShift) {
		shift = newShift;
		return shift;
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
        char[] charsToRemove = { ' ', '\'', '.', ',', '-', '?', '"', '!' };
        String normalized =
            removeMultipleChars(str, charsToRemove).toUpperCase();
        return normalized;
    }
	
	/**
	 * Encrypts the provided raw string using the Caesar cipher
	 * @param raw the string to encrypt
	 * @return the encrypted string
	 */
	public String encrypt(String raw) {
		// Normalize string so we don't have to worry about punctuation etc
		String plaintext = normalize(raw);
		String encrypted = "";
		for (int i = 0; i < plaintext.length(); i++) {
			// Turns 65 - 90 (A - Z in ASCII) to 0 - 25
			int normalizedCharIndex = ((int) plaintext.charAt(i)) - 65;
			int shifted = (normalizedCharIndex + shift) % 26;
			// Turns the shifted int back into a char
			char shiftedChar = (char) (shifted + 65);
			encrypted += shiftedChar;
		}
		return groupLetters(encrypted);
	}
	
	/**
	 * Decrypts the provided raw string using the Caesar cipher
	 * @param raw the string to decrypt
	 * @return the decrypted string
	 */
	public String decrypt(String raw) {
		String encrypted = normalize(raw);
		String plaintext = "";
		for (int i = 0; i < encrypted.length(); i++) {
			// Turns 65 - 90 (A - Z in ASCII) to 0 - 25
			int normalizedCharIndex = ((int) encrypted.charAt(i)) - 65;
			// We add 26 this time because if the number is negative
			// we'll get a weird result
			int shifted = (normalizedCharIndex - shift + 26) % 26;
			// Turns the shifted int back into a char
			char shiftedChar = (char) (shifted + 65);
			plaintext += shiftedChar;
		}
		return groupLetters(plaintext);
	}
	
	/**
	 * Creates a string representation of the current CaesarCipher instance
	 */
	public String toString() {
		return "Caesar Cipher" + 
				"\nShift: " + shift;
	}
}
