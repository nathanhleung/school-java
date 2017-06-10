/**
 * Caesar Driver
 * A driver for the Caesar Cipher and Frequency Analysis classes
 * @author 18nleung
 * @version 28 March 2017
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Arrays;
import java.util.List;

public class CaesarDriver {
	/**
	 * Runs our code, main method
	 * @param args args for the program
	 */
	public static void main(String[] args) {
		String encrypted = readFromFile("text.txt");
		
		// Construct classes
		CaesarCipher cipher = new CaesarCipher(13);
		FrequencyAnalysis analysis = new FrequencyAnalysis(encrypted);
		
		// Run tests
		cipher.test();
		analysis.test();

		// Demo class capabilities
		System.out.println("Original text: " + encrypted);
		System.out.println(analysis);
		// For it to work with this text (attached), we need to skip 1
		int guess = analysis.guessKey(1);
		System.out.println("Key Guess: " + guess);
		cipher.setShift(guess);
		String decrypted = cipher.decrypt(encrypted);
		System.out.println(decrypted);
		
		// Write decrypted text to file
		writeToFile("decrypted.txt", decrypted);
	}
	
	/**
	 * Reads data from a file
	 * @param filename the file to read from (relative to class location)
	 * @return the data that was read
	 */
	public static String readFromFile(String filename) {
		URL path = CaesarDriver.class.getResource(filename);
		File file = new File(path.getFile());
		String contents = "";
		try {
			Scanner sc = new Scanner(file);
			while (sc.hasNextLine()) {
				contents += sc.nextLine() + "\n";
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		}
		return contents;
	}
	
	/**
	 * Writes data to a file with the specified filename
	 * @param filename the filename to use (relative to class)
	 * @param data the data to write
	 */
	public static void writeToFile(String filename, String data) {
		// From http://stackoverflow.com/questions/2885173/how-do-i-create-a-file-and-write-to-it-in-java
		List<String> lines = Arrays.asList(data);
		// Write to the same place where we got our encrypted text from
		Path file = Paths.get(System.getProperty("user.dir") + "/src/" + filename);
		try {
			Files.write(file, lines, Charset.forName("UTF-8"));
			System.out.println("Wrote result to " + file + "!");
		} catch (IOException e) {
			System.out.println("An error occurred while writing to file.");
		}
	}
}
