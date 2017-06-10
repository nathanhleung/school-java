/**
 * Alphabet Soup Project: Bowl Class
 * A class representing a bowl of alphabet soup.
 * @author Nathan Leung
 * @version 16 April 2017
 */

import java.util.ArrayList;
import java.util.Random;

class Bowl {
  private ArrayList<String> wordsLeft;
  private ArrayList<String> wordsCreated;
  private ArrayList<Character> lettersInBowl;

  /**
   * Constructs a new instance of the Bowl class with the provided words to be made
   * @param words the words to make out of the bowl
   */
  public Bowl(ArrayList<String> words) {
    lettersInBowl = createAlphabet();
    wordsLeft = words;
    wordsCreated = new ArrayList<String>();
  }

  /**
   * Creates letters to hydrate the lettersInBowl list
   * @return an ArrayList of 60 random letters
   */
  private static ArrayList<Character> createAlphabet() {
    ArrayList<Character> letters = new ArrayList<Character>();
    // ASCII codes
    final int ASCII_OFFSET = 65;
    Random rand = new Random();
    
    for (int i = 0; i < 60; i++) {
      // Generates a random number between 1 and 25
      int n = rand.nextInt(26);
      // Turns the number into a letter
      char letter = (char)(n + ASCII_OFFSET);
      letters.add(letter);
    }
    return letters;
  }

  /**
   * Creates words out of the current letters from the provided text
   */
  public void createWords() {
    for (int i = 0; i < wordsLeft.size(); i++) {
      String word = wordsLeft.get(i);
      // Create a copy of the lettersLeft for each new word
      ArrayList<Character> lettersInMemory = new ArrayList<Character>(lettersInBowl);
      boolean stopSearching = false;
      for (int j = 0; j < word.length() && !stopSearching; j++) {
        // See if letter of word is in the letter bank of bowl
        int indexOfLetter = lettersInMemory.indexOf(word.charAt(j));
        if (indexOfLetter != -1) {
          lettersInMemory.remove(indexOfLetter);
        } else {
          stopSearching = true;
        }
        // If we've made it to the end and have every letter,
        // then transfer the lettersInMemory to the actual
        // lettersLeft and then add the created word to
        // the wordsCreated list. Also remove the word
        // fromt the wordsLeft list
        if (j == word.length() - 1 && !stopSearching) {
          lettersInBowl = new ArrayList<Character>(lettersInMemory);
          wordsCreated.add(word);
          int indexOfWord = wordsLeft.indexOf(word);
          wordsLeft.remove(indexOfWord);
        }
      }
    }
  }

  // Note about the two following getters: they do not have setters
  // because that would compromise the proper functioning of the program

  /**
   * Gets the words left to be created
   * @return the words left to be created
   */
  public ArrayList<String> getWordsLeft() {
    return wordsLeft;
  }

  /**
   * Gets the words created from the bowl
   * @return the words created from the bowl
   */
  public ArrayList<String> getWordsCreated() {
    return wordsCreated;
  }

  /**
   * Gets the letters in the bowl
   * @return the letters in the bowl
   */
  public ArrayList<Character> getLettersInBowl() {
    return lettersInBowl;
  }

  /**
   * Sets the letters in the bowl
   * @param letters the new letters in the bowl
   * @return the newly set letters in the bowl
   */
  public ArrayList<Character> setLettersInBowl(ArrayList<Character> letters) {
    lettersInBowl = letters;
    return lettersInBowl;
  }

  /**
   * Creates a String representation of the bowl
   */
  public String toString() {
    return "Words Left: " + wordsLeft +
      "\nWords Created: " + wordsCreated +
      "\nLetters In Bowl: " + lettersInBowl;
  }
}
