/**
 * Alphabet Soup Project: Driver Class
 * A class running the code for our alphabet soup project.
 * @author Nathan Leung
 * @version 16 April 2017
 */

import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.ArrayList;

class AlphabetSoupDriver {
  /**
   * The function in which all of our code is run
   */
  public static void main(String[] args) {
    System.out.println("Welcome to Alphabet Soup.");

    // Prompt user for a filename until a valid file is provided
    Scanner input = new Scanner(System.in);
    String content;
    System.out.println("\nWhat is the name of the file you want to load?");
    do {
      String filename = input.nextLine();
      content = readFromFile(filename);
      if (content.equals("")) {
        System.out.println("\nPlease enter another filename.");
      }
    } while (content.equals(""));

    // Create a new content object with the provided content
    Content userContent = new Content(content);

    // Start making bowls
    int bowlsNeeded = 0;
    Bowl bowl;

    // Start iterating with wordsLeft
    ArrayList<String> wordsLeft = userContent.getWords();

    while (wordsLeft.size() > 0) {
      bowlsNeeded += 1;
      System.out.println("Bowl #" + bowlsNeeded);
      bowl = new Bowl(wordsLeft);
      bowl.createWords();
      System.out.println(bowl);

      wordsLeft = bowl.getWordsLeft();

      System.out.println("Remaining letters will be thrown out.\n");
    }

    System.out.println("It took " + bowlsNeeded + " bowls to write your text.");
  }

  /**
   * Helper method to read data from a file
   * @param filename the file to read from (relative to class location)
   * @return the data that was read
   */
  private static String readFromFile(String filename) {
    String content;
    try {
      // From https://stackoverflow.com/questions/4716503/reading-a-plain-text-file-in-java
      content = new String(Files.readAllBytes(Paths.get(filename)));
    } catch (IOException e) {
      System.out.println("File not found.");
      content = "";
    }
    return content;
  }
}
