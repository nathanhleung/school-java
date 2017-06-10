/**
 * @author Nathan Leung
 * isPalindrome Project
 * 16 February 2017
 */

import java.util.ArrayList;
import java.util.Scanner;

public class PalindromeTrawler {
    public static void main(String[] args) {
    	try (Scanner input = new Scanner(System.in)) {
	    	System.out.print(
	    		"How many strings will you be " +
	    		"analyzing for palindromes? "
	    	);
	    	int numOfStrings = input.nextInt();
	    	ArrayList<String> strings = new ArrayList<String>();
	    	for (int i = 0; i < numOfStrings; i++) {
	    		System.out.print(
	    			"Enter your " + makeOrdinalNumber(i + 1) + " string: "
	    		);
	    		String nextString = input.next();
	    		strings.add(nextString);
	    	}
	        for (String string : strings) {
	            printPalindromes(string);
	        }
    	}
    }
    
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
    
    public static String removeMultipleChars(String str, char[] chs) {
        String newString = str;
        for (char ch : chs) {
            newString = removeChar(newString, ch);
        }
        return newString;
    }
    
    public static String normalize(String str) {
        // Normalized so we don't have to worry about
        // case, spaces, punctuation, etc
        char[] charsToRemove = { ' ', '\'', '.', ',', '-', '?', '"', '!' };
        String normalized =
            removeMultipleChars(str, charsToRemove).toLowerCase();
        return normalized;
    }
    
    public static boolean isPalindrome(String str) {
        // Normalized so we don't have to worry about
        // case, spaces, punctuation, etc
        String normalized = normalize(str);
       
        // Reverse for loop is faster
        int length = normalized.length();
        for (int i = length - 1; i >= 0; i--) {
            int current = normalized.charAt(i);
            int opposite = normalized.charAt(length - 1 - i);
            if (current != opposite) {
                return false;
            }
        }
        return true;
    }
    
    public static void printPalindromes(String str) {
    	int nPalindromes = 0;
        String normalized = normalize(str);
        // Add newline for formatting purposes
        System.out.println();
        System.out.println("Original String: \"" + str + "\"");
        System.out.println("Normalized String: \"" + normalized + "\"");
        int length = normalized.length();
        // The starting character can be any character in
        // the string up to 3 before the end
        for (int i = 0; i <= length - 3; i++) {
        	// The ending character starts at least 3 characters after
        	// the beginning character, and goes until the end
            for (int j = i + 3; j <= length; j++) {
                // Create increasing substrings incrementally
                String substr = normalized.substring(i, j);
                if (isPalindrome(substr)) {
                	nPalindromes++;
                    System.out.println("At index " + i + ": " + substr);
                }
            }
        }
        if (nPalindromes == 0) {
        	System.out.println("No palindromes found in this string.");
        }
    }
    
    public static String makeOrdinalNumber(int i) {
    	String[] ones = {
    		"zeroth",
    		"first",
    		"second",
    		"third",
    		"fourth",
    		"fifth",
    		"sixth",
    		"seventh",
    		"eighth",
    		"ninth",
    		"tenth",
    		"eleventh",
    		"twelfth",
    		"thirteenth",
    		"fourteenth",
    		"fifteenth",
    		"sixteenth",
    		"seventeenth",
    		"eighteenth",
    		"nineteenth",
    	};
    	String[] bareTens = {
    		"twentieth",
    		"thirtieth",
    		"fourtieth",
    		"fiftieth",
    		"sixtieth",
    		"seventieth",
    		"eightieth",
    		"ninetieth",
    	};
    	String[] tens = {
    		"twenty",
    		"thirty",
    		"forty",
    		"fifty",
    		"sixty",
    		"seventy",
    		"eighty",
    		"ninety",
    	};
    	if (i <= 19) {
    		return ones[i];
    	} else if (i % 10 == 0 && i < 100) {
    		int tensIndex = (i / 10) - 2;
    		return bareTens[tensIndex];
    	} else if (i < 100) {
    		int tensIndex = (i / 10) - 2;
        	int onesIndex = i % 10;
        	return tens[tensIndex] + "-" + ones[onesIndex];
    	} else {
    		return "next";
    	}
    }
}