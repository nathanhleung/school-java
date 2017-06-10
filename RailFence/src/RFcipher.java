/**
 * Rail Fence Cipher Project
 * <p>
 * A class that encrypts and decrypts text using the rail-fence cipher.
 * <p>
 * 
 * @version 1 March 2017
 * @author 18nleung
 */
public class RFcipher {
	private String plaintext;
	
	/**
	 * Constructs a new RFcipher when no arguments are provided.
	 */
	public RFcipher() {
		this(null);
	}
	
	/**
	 * Constructs a new RFcipher when a String argument is provided.
	 * @param s the string to use in the constructor
	 */
	public RFcipher(String s) {
		if (s == null) {
			plaintext = "";
		} else {
			plaintext = s;
		}
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
        // char[] charsToRemove = { ' ', '\'', '.', ',', '-', '?', '"', '!' };
    	// just remove spaces
    	char[] charsToRemove = { ' ' };
        String normalized =
            removeMultipleChars(str, charsToRemove).toUpperCase();
        return normalized;
    }
	
	/**
	 * Encrypts a string using a two-level rail-fence cipher
	 * @param raw the original string
	 * @return the encrypted string
	 */
	public String encrypt(String raw) {
		String s = normalize(raw);
		String firstHalf = "";
		String secondHalf = "";
		// Iterate and build the first layer and second
		// layer of the fence
		for (int i = 0; i <  s.length(); i++) {
			if (i % 2 == 0) {
				firstHalf += s.charAt(i);
			} else {
				secondHalf += s.charAt(i);
			}
		}
		return firstHalf + secondHalf;
	}
	
	/**
	 * Decrypt a string that was encrypted using a two-level rail-fence cipher
	 * @param raw the encrypted string
	 * @return the decrypted string
	 */
	public String decrypt(String raw) {
		String s = normalize(raw);
		// Int division, so it'll round down
		int length = s.length();
		int halfway = length / 2;
		// Check if number is odd, if so, add 1 to halfway point because
		// in the encryption algorithm the top row of the fence gets
		// preference
		if (length % 2 == 1) {
			halfway += 1;
		}
		String firstHalf = s.substring(0, halfway);
		String secondHalf = s.substring(halfway);
		String result = "";
		for (int i = 0; i < length; i++) {
			if (i % 2 == 0) {
				result += firstHalf.charAt(i / 2);
			} else {
				if (i < length)
					result += secondHalf.charAt(i / 2);
			}
		}
		return result;
	}
	
	/**
	 * Generates a string representation of the current instance state
	 * @return string representation of current instance
	 */
	public String toString() {
		String encrypted = encrypt(plaintext);
		return
			"\nInitial String: " + plaintext +
			"\nNormalized: " + normalize(plaintext) +
			"\nEncrypted: " + encrypted +
			"\nDecrypted: " + decrypt(encrypted);
	}
	
	/**
	 * Runs tests to ensure the integrity of the class.
	 */
	public static void tests() {
		String testString = "Where in the world is Carmen SanDiego?";
		String expected = "WEENHWRDSAMNADEOHRITEOLICRESNIG?";
		String recovered = "WHEREINTHEWORLDISCARMENSANDIEGO?";
		
		RFcipher tester = new RFcipher();
		String testEncrypted = tester.encrypt(testString);
		if (testEncrypted.equals(expected)) {
			System.out.println("Encryption Test 1 passed!");
		} else {
			System.out.println("Encryption Test 1 failed!");
		}
		String testDecrypted = tester.decrypt(testEncrypted);
		if (testDecrypted.equals(recovered)) {
			System.out.println("Decryption Test 1 passed!");
		} else {
			System.out.println("Decryption Test 1 failed!");
			System.out.println("Expected: " + recovered);
			System.out.println("Got: " + testDecrypted);
		}
		
		String testString2 = "How much wood could a wood chuck, chuck?";
		String expected2 = "HWUHODOLAODHC,HC?OMCWOCUDWOCUKCUK";
		
		System.out.println();
	}
}
