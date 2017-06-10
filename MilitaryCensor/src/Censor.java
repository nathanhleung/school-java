/**
 * @author 18nleung
 * Military Censor Project
 * 21 February 2017
 */

import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Censor {
	public static void main(String[] args) {
		String[] badWords = {
			"hermes",
			"bridge",
			"muddy",
			"river",
			"assault",
			"offensive",
		};
		
		boolean testing = true;
		if (testing) {
			Map<String, Boolean> tests = new HashMap<String, Boolean>();
			tests.put("I hope I survive the assault tomorrow.", false);
			tests.put("I want to talk to you about Bobby, but we’ll cross that bridge later.", false);
			tests.put("Tell sis and Larry that I’ll be Ok and I will see them in 6 months", true);
			tests.put("Your last letter was a little muddy on exactly what you meant.", false);
			tests.put("I see no point in us trying to take the hermes crossing.", false);
			runTests(tests, badWords);
			// Separate tests from the actual program
			System.out.println();
		}
		
		try (Scanner input = new Scanner(System.in)) {
			System.out.print(
	    		"How many sentences will you be " +
	    		"analyzing for bad words? "
	    	);
	    	int numOfStrings = input.nextInt();
	    	// Consume leftover newline from nextInt
	    	input.nextLine();
	    	for (int i = 0; i < numOfStrings; i++) {
	    		System.out.print(
	    			"Enter your " + makeOrdinalNumber(i + 1) + " string: "
	    		);
	    		String nextString = input.nextLine();
	    		if (sentenceIsOk(nextString, badWords)) {
	    			System.out.println("You're OK to send that.");
	    		} else {
	    			System.out.println("Unfortunately, your letter is REJECTED.");
	    		}
	    		// separator between each new string
	    		System.out.println();
	    	}
	    	System.out.println("Thanks for using the program!");
		}
	}
	
	public static boolean sentenceIsOk(String sentence, String[] badWords) {
		// Convert to lower case to make it case insensitive
		StringTokenizer st = new StringTokenizer(sentence.toLowerCase(), ", \n?'()");
		while (st.hasMoreTokens()) {
			String next = st.nextToken();
			for (String badWord : badWords) {
				if (next.equals(badWord)) {
					return false;
				}
			}
		}
		return true;
	}
	
	public static boolean runTests(Map<String, Boolean> tests, String[] badWords) {
		int failedTests = 0;
		for (Map.Entry<String, Boolean> testCase : tests.entrySet()) {
			System.out.println(
				"Expected: \"" + testCase.getKey() + "\" -> " + testCase.getValue()
			);
			boolean result = sentenceIsOk(testCase.getKey(), badWords);
			if (result == testCase.getValue()) {
				System.out.println("PASSED, got " + result);
			} else {
				System.out.println("FAILED, got " + result);
				failedTests++;
			}
			System.out.println();
		}
		if (failedTests == 0) {
			System.out.println("All tests passed.");
			return true;
		} else {
			System.out.println(failedTests + " failed tests.");
			return false;
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
