/**
 * 
 * @author Nathan Leung
 * 14 February 2017
 * Strings in Java Project
 */

public class StringAPIPractice {

	public static void main(String[] args) {
		System.out.println("STRINGS IN JAVA PROJECT");
		String myString = "Eduardo";
        System.out.println("This is my string: \"" + myString + "\"");
        
        // length
        System.out.println("\nLENGTH");
        int numberOfChars = myString.length();
        System.out.println("My string has " + numberOfChars + " characters.");
        
        // charAt
        System.out.println("\nCHAR AT");
        char secondToLastChar = myString.charAt(numberOfChars - 2);
        System.out.println(
        	"The second to last character in my string is \"" + secondToLastChar + "\"."
        );
        
        // compareTo
        System.out.println("\nCOMPARE TO");
        String anotherString = "Dustin";
        String aThirdString = "Mark";
        String[] myStrings = { myString, anotherString, aThirdString };
        System.out.println("Let's say we have these three strings:");
        for (String string : myStrings) {
        	System.out.print("\"" + string + "\" ");
        }
        System.out.println();
        System.out.println(
        	"If we ordered these according to ASCII values, this would be the result."
        );
        // Start with placeholder values
        String first = myString;
        String last = anotherString;
        for (String string: myStrings) {
        	if (string.compareTo(first) < 0) {
        		first = string;
        	}
        	if (string.compareTo(last) > 0) {
        		last = string;
        	}
        }
        String middle = "";
        for (String string: myStrings) {
        	if (string != first && string != last) {
        		middle = string;
        		break;
        	}
        }
        System.out.println("\"" + first + "\" \"" + middle + "\" \"" + last + "\"");
        
        // concat
        System.out.println("\nCONCAT");
        System.out.println("Here's what happens when we concatenate the first and last ASCII ordered string.");
        System.out.println("\"" + first.concat(last) + "\"");
        
        // equals
        System.out.println("\nEQUALS");
        String testString = middle;
        System.out.println("The test string is \"" + testString + "\".");
        for (String string : myStrings) {
        	String verb = "contains";
        	if (!string.equals(testString)) {
        		verb = "does not contain";
        	}
        	System.out.println("The test string " + verb + " the same characters as \"" + string + "\".");
        }
        
        // indexOf
        System.out.println("\nINDEX OF");
        System.out.println(
        	"The first instance of the character \"a\" occurs at the following indexes " + 
        	"in the following strings:"
        );
        for (String string : myStrings) {
        	int index = string.indexOf("a");
        	System.out.print("\"" + string + "\": ");
        	if (index >= 0) {
        		System.out.println(index);
        	} else {
        		System.out.println("not found");
        	}
        }
        
        System.out.println("\nREPLACE");
        System.out.println(
        	"Here are the strings with all the o's changed to 0's and the i's changed to 1's."
        );
        for (String string : myStrings) {
        	System.out.print("\"" + string.replace('o', '0').replace('i', '1') + "\" ");
        }
        System.out.println();
        
        System.out.println("\nSUBSTRING - ONE ARG");
        System.out.println(
        	"Here are the last three characters of each string."
        );
        for (String string : myStrings) {
        	System.out.print("\"" + string.substring(string.length() - 3) + "\" ");
        }
        System.out.println();
        
        System.out.println("\nSUBSTRING - TWO ARGS");
        System.out.println(
        	"Here are the second and third characters of each string."
        );
        for (String string : myStrings) {
        	System.out.print("\"" + string.substring(1, 3) + "\" ");
        }
        System.out.println();
        
        System.out.println("\nTO LOWER CASE");
        System.out.println(
        	"Here are the strings, all turned to lower case."
        );
        for (String string : myStrings) {
        	System.out.print("\"" + string.toLowerCase() + "\" ");
        }
        System.out.println();
        
        System.out.println("\nTO UPPER CASE");
        System.out.println(
        	"Here are the strings, all turned to upper case."
        );
        for (String string : myStrings) {
        	System.out.print("\"" + string.toUpperCase() + "\" ");
        }
        System.out.println();
        
        System.out.println("\nEQUALS IGNORE CASE");
        System.out.println(
        	"Let's see if these strings are equal, if we're disregarding case."
        );
        for (String string : myStrings) {
        	String string2 = string.toLowerCase();
        	if (Math.random() >= 0.5) {
        		string2 = string2.toUpperCase();
        	}
        	if (string.equalsIgnoreCase(string2)) {
        		System.out.println("\"" + string + "\" is case-insensitive equals to \"" + string2 + "\"");
        	} else {
        		System.out.println("This isn't supposed to happen.");
        	}
        }
        System.out.println();
	}
}
