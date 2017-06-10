/**
 * Poker Driver
 * A driver class for our poker game
 * @author 18nleung
 * @version 17 March 2017
 */

import java.util.ArrayList;
import java.util.Scanner;

public class PokerDriver {
	public static void main(String[] args) {
		// Set to true if you want to run the test suite
		boolean test = false;
		if (test) {
			Poker game = new Poker();
			game.testScoring();
			System.out.println(game);
			System.out.println();
		}
		boolean playAgain = false;
		do {
			Poker game = new Poker();
			ArrayList<String> hand = game.getHand();
			System.out.println("You have: " + hand);
			
			Scanner sc = new Scanner(System.in);
			ArrayList<Integer> positionsToDiscard = new ArrayList<Integer>();
			for (int i = 0; i < hand.size(); i++) {
				System.out.println("Do you want to keep your " + hand.get(i) + "? (y/n)");
				char c;
				do {
					System.out.print("? ");
					c = sc.next().charAt(0);
				} while (c != 'y' && c != 'n');
				if (c == 'n') {
					positionsToDiscard.add(i);
				}
			}
			game.discardFromHand(positionsToDiscard);
			game.drawToReplenish();
			System.out.println("\nYou now have: " + game.getHand());
			String handName = game.scoreHand();
			System.out.println("This hand is called: \"" + handName + "\"");
			System.out.println("You win: " + Poker.getPayout(handName) + " credits");
			System.out.println("Play again? (y/n)");
			char c;
			do {
				System.out.print("? ");
				c = sc.next().charAt(0);
			} while (c != 'y' && c != 'n');
			if (c == 'y') {
				playAgain = true;
			} else {
				playAgain = false;
			}
			System.out.println();
		} while (playAgain);
	}
}
