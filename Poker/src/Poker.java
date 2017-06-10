/**
 * Poker class
 * A class which implements a poker game in Java
 * @author 18nleung
 * @version 17 March 2017
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Poker {
	private static String[] handNames = {
		"Nothing",
		"Pair of Jacks or Better",
		"Two Pair",
		"Three of a Kind",
		"Straight",
		"Flush",
		"Full House",
		"Four of a Kind",
		"Straight Flush",
		"Royal Flush",
	};
	
	private ArrayList<String> deck;
	private ArrayList<String> hand;
	private int handSize;
	
	/**
	 * Constructs a new instance of the Poker class
	 */
	public Poker() {
		resetDeck();
		shuffleDeck();
		// In case we want to play 7 card or something
		// Logic isn't done for that yet, though, so
		// keep it at 5
		handSize = 5;
		hand = drawCards(handSize);
	}
	
	/**
	 * Converts shorthand card notation (i.e. c3) into a full
	 * card name, 3 of Clubs.
	 * @param card the shorthand card notation
	 * @return the full card name
	 */
	private static String cardToString(String card) {
		// Map the shorthand suits to their full names
		Map<String, String> suits = new HashMap<String, String>();
		suits.put("c", "Clubs");
		suits.put("d", "Diamonds");
		suits.put("h", "Hearts");
		suits.put("s", "Spades");
		
		// In our shorthand format, the suit is one letter and it precedes
		// the value of the card - for example, h3 is the 3 of hearts
		String shortSuit = card.substring(0, 1);
		String suit = suits.get(shortSuit);
		
		// Cast the card value to an integer so we can
		// use it in the switch
		int num = new Integer(card.substring(1));
		// Set the card "name" initially to simply its value
		String name = "" + num;
		
		// If the card is a face card, apply that name
		switch (num) {
			case 1:
				name = "Ace";
				break;
			case 11:
				name = "Jack";
				break;
			case 12:
				name = "Queen";
				break;
			case 13:
				name = "King";
				break;
		}
		
		return name + " of " + suit;
	}
	
	/**
	 * Orders the provided hand by card number
	 * @param hand the hand to order
	 * @return the ordered hand
	 */
	private static ArrayList<String> orderHand(ArrayList<String> hand) {
		hand.sort((card1, card2) -> {
			int num1 = new Integer(card1.substring(1));
			int num2 = new Integer(card2.substring(1));
			// If num1 is less than num2, it will come before
			return num1 - num2;
		});
		return hand;
	}
	
	/**
	 * Gets the payout of the provided hand
	 * @param handName the name of the hand
	 * @return the payout
	 */
	public static int getPayout(String handName) {
		int[] payouts = {
			0, 1, 2, 3, 4, 5, 6, 25, 50, 250,
		};
		// If the handName provided is invalid, just return 0
		int index = Arrays.asList(handNames).indexOf(handName);
		if (index == -1) {
			return 0;
		}
		return payouts[index];
	}
	
	/**
	 * Generates the initial deck of 52 cards if no deck exists,
	 * or resets the incomplete deck to the initial 52 cards
	 */
	private void resetDeck() {
		// Blanks the deck
		deck = new ArrayList<String>();
		// Array of suit shorthands
		char[] suits = {'c', 'd', 'h', 's'};
		// Generates a 52-card deck by iterating over
		// all suits and values
		for (int i = 0; i < 4; i++) {
			for (int j = 1; j <= 13; j++) {
				// Shorthand card notation: suit + value
				// For example: 3 of hearts is h3
				// King of spades is s13
				String card = "" + suits[i] + j;
				deck.add(card);
			}
		}
	}
	
	/**
	 * Shuffles the deck of cards
	 */
	private void shuffleDeck() {
		Collections.shuffle(deck);
	}
	
	/**
	 * Draws the specified number of cards from the deck,
	 * removing them from the deck instance variable.
	 * @param num the number of cards to draw
	 * @return the cards that were drawn
	 */
	public ArrayList<String> drawCards(int num) {
		ArrayList<String> newCards = new ArrayList<String>();
		if (deck.size() < num) {
			// If there are not enough cards left in the deck,
			// we can just regenerate and shuffle the deck
			resetDeck();
			shuffleDeck();
		}
		for (int i = 0; i < num; i++) {
			// Removes cards from the top of the deck and
			// adds them to the hand
			newCards.add(deck.remove(0));
		}
		return newCards;
	}
	
	/**
	 * Discards the cards at the marked positions from the current hand
	 * @param positionsToDiscard the positions of the cards to discard
	 */
	public void discardFromHand(ArrayList<Integer> positionsToDiscard) {
		ArrayList<String> newHand = new ArrayList<String>();
		// Iterate over our current hand and check whether we should keep
		// the current card or discard it
		for (int i = 0; i < hand.size(); i++) {
			if (positionsToDiscard.indexOf(i) == -1) {
				// If the card at the current position is not marked for discarding,
				// add it to our new hand
				newHand.add(hand.get(i));
			}
		}
		// Set hand to equal the newHand
		hand = newHand;
	}
	
	/**
	 * Draw cards from the deck to replenish the hand
	 * to its original size
	 */
	public void drawToReplenish() {
		int cardsToDraw = handSize - hand.size();
		if (cardsToDraw > 0) {
			// add new cards to our current hand
			hand.addAll(drawCards(cardsToDraw));
		}
	}
	
	/**
	 * Gets the current deck
	 * @return the current deck
	 */
	public ArrayList<String> getDeck() {
		return deck;
	}
	
	/**
	 * Gets the current hand and turns each card shorthand to the full card name
	 * @return the current hand with full card names
	 */
	public ArrayList<String> getHand() {
		return hand.stream()
			// Map each card shorthand to the full card name
			.map( card -> cardToString(card) )
			// Turn the mapped cards into an ArrayList
			.collect(Collectors.toCollection(ArrayList::new));
	}
	
	/**
	 * Scores the hand
	 * @param hand the current hand
	 * @return the name of the hand
	 */
	public String scoreHand() {
		// Cache these values because they stack
		// (for example, to check if we have a straight
		// flush we can just do (straight && flush)
		boolean pairOfJacks = false;
		boolean twoPair = false;
		boolean threeOf = false;
		boolean straight = false;
		boolean flush = false;
		boolean fullHouse = false;
		boolean fourOf = false;
		boolean straightFlush = false;
		boolean royalFlush = false;
		
		// Count occurrences of each number in the current hand
		Map<Integer, Integer> occurrenceMap = countOccurrences();
		int pairs = 0;
		// Iterate over the occurrence map
		for (Entry<Integer, Integer> entry : occurrenceMap.entrySet()) {
			// If an entry (card number) == 2, then it is a pair
			if (entry.getValue() == 2) {
				pairs += 1;
				// Checks if the current card is a Jack or better, OR
				// if it's a pair of Aces
				if (entry.getKey() >= 11 || entry.getKey() == 1) {
					pairOfJacks = true;
				}
			}
			// If there are 3 instances of a card,
			// then we have a 3 of a kind
			if (entry.getValue() == 3) {
				threeOf = true;
			}
			if (entry.getValue() == 4) {
				fourOf = true;
			}
		}
		if (pairs == 2) {
			twoPair = true;
		}
		if (pairs == 1 && threeOf) {
			fullHouse = true;
		}
		
		// Create an ordered hand so we can check if we have a straight easier
		ArrayList<String> hand = orderHand(this.hand);
		// Sentinel value to watch out for so we don't have to keep iterating
		// even after it's definitely not a straight
		boolean keepGoing = true;
		// Iterate over the ordered hand to see if
		// there is a straight
		int prevCardNum = new Integer(hand.get(0).substring(1));
		for (int i = 1; i < hand.size() && keepGoing; i++) {
			String currCard = hand.get(i);
			int currCardNum = new Integer(currCard.substring(1));
			if (currCardNum == 10) {
				// If we have a 10, the previous card must either be a 9 or an Ace
				if (prevCardNum != 9 && prevCardNum != 1) {
					keepGoing = false;
				}
			} else {
				// If we don't have a 10, just make sure the previous card
				// is one less than the current card
				if (currCardNum - 1 != prevCardNum) {
					keepGoing = false;
				}
			}
			prevCardNum = currCardNum;
			// If we've made it to the end
			// and it's the last card,
			// and we are good to keep
			// going, we have a straight
			if (i == hand.size() - 1 && keepGoing) {
				straight = true;
			}
		}
		
		// Reset the keepGoing value to test for a flush
		keepGoing = true;
		String prevSuit = hand.get(0).substring(0, 1);
		for (int i = 1; i < hand.size() && keepGoing; i++) {
			String currSuit = hand.get(i).substring(0, 1);
			if (!currSuit.equals(prevSuit)) {
				keepGoing = false;
			}
			prevSuit = currSuit;
			// If we've made it to the end and currSuit has always
			// been the same as prevSuit, and we're good
			// to keep going, we have a flush
			if (i == hand.size() - 1 && keepGoing) {
				flush = true;
			}
		}
		
		if (straight && flush) {
			straightFlush = true;
			
			// Royal Flush is 1,10,11,12,13
			// So we check if card #1 is 1 and card #5 is 13
			String firstCard = hand.get(0);
			int firstCardNum = new Integer(firstCard.substring(1));
			String lastCard = hand.get(hand.size() - 1);
			int lastCardNum = new Integer(lastCard.substring(1));
			if (lastCardNum == 13 && firstCardNum == 1) {
				royalFlush = true;
			}
		}
		
		// At this point, we need to turn our booleans into a hand name
		String bestHand = handNames[0];
		if (pairOfJacks) {
			bestHand = handNames[1];
		}
		if (twoPair) {
			bestHand = handNames[2];
		}
		if (threeOf) {
			bestHand = handNames[3];
		}
		if (straight) {
			bestHand = handNames[4];
		}
		if (flush) {
			bestHand = handNames[5];
		}
		if (fullHouse) {
			bestHand = handNames[6];
		}
		if (fourOf) {
			bestHand = handNames[7];
		}
		if (straightFlush) {
			bestHand = handNames[8];
		}
		if (royalFlush) {
			bestHand = handNames[9];
		}
		return bestHand;
	}
	
	/**
	 * Counts the number of occurrences of each card number
	 * @return a map with each card number and the number of times it occurs in the hand
	 */
	private Map<Integer, Integer> countOccurrences() {
		// Make a map corresponding to each card
		Map<Integer, Integer> occurrenceMap = new HashMap<Integer, Integer>();
		for (int i = 0; i < hand.size(); i++) {
			String card = hand.get(i);
			int cardNum = new Integer(card.substring(1));
			int occurrences = 0;
			if (occurrenceMap.get(cardNum) != null) {
				occurrences = occurrenceMap.get(cardNum);
			}
			// Each time we encounter a card, 
			// increment its occurrence count
			occurrenceMap.put(cardNum, occurrences + 1);
		}
		return occurrenceMap;
	}
	
	/**
	 * Test suite for the scoring method
	 */
	public void testScoring() {
		hand = drawCards(5);
		
		for (int i = 0; i < 5; i++) {
			hand.set(i, "s" + (i + 1));
		}
		hand.set(0, "h13");
		System.out.println("\nNothing Test:");
		System.out.println(getHand());
		if (scoreHand() == handNames[0]) {
			System.out.println("This is: " + scoreHand());
		} else {
			System.out.println("Error, got " + scoreHand());
		}
		
		for (int i = 0; i < 5; i++) {
			hand.set(i, "s" + (i + 1));
		}
		hand.set(0, "h2");
		System.out.println("\nNothing Pair Test:");
		System.out.println(getHand());
		if (scoreHand() == handNames[0]) {
			System.out.println("This is: " + scoreHand());
		} else {
			System.out.println("Error, got " + scoreHand());
		}
		
		for (int i = 0; i < 5; i++) {
			hand.set(i, "s" + (i + 1));
		}
		hand.set(1, "h1");
		System.out.println("\nAce Pair Test:");
		System.out.println(getHand());
		if (scoreHand() == handNames[1]) {
			System.out.println("This is: " + scoreHand());
		} else {
			System.out.println("Error, got " + scoreHand());
		}
		
		for (int i = 0; i < 5; i++) {
			hand.set(i, "s" + (i + 9));
		}
		hand.set(0, "h12");
		System.out.println("\nJack or Better Pair Test:");
		System.out.println(getHand());
		if (scoreHand() == handNames[1]) {
			System.out.println("This is: " + scoreHand());
		} else {
			System.out.println("Error, got " + scoreHand());
		}
		
		for (int i = 0; i < 5; i++) {
			hand.set(i, "s" + (i + 1));
		}
		hand.set(1, "h1");
		hand.set(2, "h5");
		System.out.println("\nTwo Pair Test:");
		System.out.println(getHand());
		if (scoreHand() == handNames[2]) {
			System.out.println("This is: " + scoreHand());
		} else {
			System.out.println("Error, got " + scoreHand());
		}
		
		for (int i = 0; i < 5; i++) {
			hand.set(i, "s" + (i + 1));
		}
		hand.set(1, "h1");
		hand.set(2, "d1");
		System.out.println("\nThree of a Kind Test:");
		System.out.println(getHand());
		if (scoreHand() == handNames[3]) {
			System.out.println("This is: " + scoreHand());
		} else {
			System.out.println("Error, got " + scoreHand());
		}
		
		for (int i = 0; i < 5; i++) {
			hand.set(i, "s" + (i + 1));
		}
		hand.set(0, "h1");
		System.out.println("\nStraight Test:");
		System.out.println(getHand());
		if (scoreHand() == handNames[4]) {
			System.out.println("This is: " + scoreHand());
		} else {
			System.out.println("Error, got " + scoreHand());
		}
		
		for (int i = 0; i < 5; i++) {
			hand.set(i, "s" + (i + 1));
		}
		hand.set(1, "s13");
		System.out.println("\nFlush Test:");
		System.out.println(getHand());
		if (scoreHand() == handNames[5]) {
			System.out.println("This is: " + scoreHand());
		} else {
			System.out.println("Error, got " + scoreHand());
		}
		
		for (int i = 0; i < 5; i++) {
			hand.set(i, "s" + (i + 1));
		}
		hand.set(1, "d1");
		hand.set(2, "h1");
		hand.set(3, "s12");
		hand.set(4, "c12");
		System.out.println("\nFull House Test:");
		System.out.println(getHand());
		if (scoreHand() == handNames[6]) {
			System.out.println("This is: " + scoreHand());
		} else {
			System.out.println("Error, got " + scoreHand());
		}
		
		for (int i = 0; i < 5; i++) {
			hand.set(i, "h10");
		}
		hand.set(0, "c9");
		hand.set(0, "h9");
		hand.set(0, "d9");
		hand.set(0, "s9");
		System.out.println("\nFour of a Kind Test:");
		System.out.println(getHand());
		if (scoreHand() == handNames[7]) {
			System.out.println("This is: " + scoreHand());
		} else {
			System.out.println("Error, got " + scoreHand());
		}
		
		for (int i = 0; i < 5; i++) {
			hand.set(i, "s" + (i + 9));
		}
		System.out.println("\nStraight Flush Test:");
		System.out.println(getHand());
		if (scoreHand() == handNames[8]) {
			System.out.println("This is: " + scoreHand());
		} else {
			System.out.println("Error, got " + scoreHand());
		}
		
		for (int i = 0; i < 5; i++) {
			hand.set(i, "s" + (i + 9));
		}
		hand.set(0, "s1");
		System.out.println("\nRoyal Flush Test:");
		System.out.println(getHand());
		if (scoreHand() == handNames[9]) {
			System.out.println("This is: " + scoreHand());
		} else {
			System.out.println("Error, got " + scoreHand());
		}
		System.out.println();
	}
	
	/**
	 * Creates a string representation of the current poker game
	 * @return a string representation of the game
	 */
	public String toString() {
		String handName = scoreHand();
		return "Hand size: " + handSize +
			"\nHand: " + hand + 
			"\nPretty hand: " + getHand() +
			"\nHand name: " + handName +
			"\nHand payout: " + getPayout(handName);
	}
}
