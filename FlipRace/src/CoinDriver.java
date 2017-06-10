/**
 * Flip Race Project
 * <p>
 * A class to see which coin gets 10 of the same outcome in a row first.
 * @author 18nleung
 * @version 28 February 2017
 */

public class CoinDriver {
	
	int turns = 0;
	
	Coin player1 = new Coin();
	Coin player2 = new Coin();
	
	int hStreak1 = 0;
	int tStreak1 = 0;
	int hStreak2 = 0;
	int tStreak2 = 0;

	/**
	 * Method which runs the main program
	 * @param args arguments passed to the program
	 */
	public static void main(String[] args) {
		CoinDriver game = new CoinDriver();
		while (!game.isWinner()) {
			game.turn();
		}
		System.out.println("The game was won in " + game.turns + " turns.");
		int winner = game.getWinner();
		if (winner == 0) {
			System.out.println("Looks like there was a tie!");
			if (game.hStreak1 == 10) {
				System.out.println("Player 1 had 10 heads.");
			} else {
				System.out.println("Player 1 had 10 tails.");
			}
			if (game.hStreak2 == 10){ 
				System.out.println("Player 2 had 10 heads.");
			} else {
				System.out.println("Player 2 had 10 tails.");
			}
		} else {
			System.out.println("The game was won by player " + winner + ".");
			int winningSide = game.getWinningSide();
			if (winningSide == 0) {
				System.out.println("Player " + winner + " had 10 heads.");
			} else {
				System.out.println("Player " + winner + " had 10 tails.");
			}
		}
	}
	
	/**
	 * Method which flips each player's coins
	 */
	public void turn() {
		turns++;
		player1.flip();
		player2.flip();
		if (player1.isHeads()) {
			hStreak1++;
			tStreak1 = 0;
		} else {
			tStreak1++;
			hStreak1 = 0;
		}
		if (player2.isHeads()) {
			hStreak2++;
			tStreak2 = 0;
		} else {
			tStreak2++;
			hStreak2 = 0;
		}
	}
	
	/**
	 * Method which checks if there has been a winner
	 * @return whether someone has won
	 */
	public boolean isWinner() {
		if (hStreak1 == 10 || tStreak1 == 10 || hStreak2 == 10 || tStreak2 == 10) {
			return true;
		}
		return false;
	}
	
	/**
	 * Method which gets the number of the winning player, where a tie is 0
	 * @return number of the winning player
	 */
	public int getWinner() {
		if ((hStreak1 == 10 || tStreak1 == 10) && (hStreak2 == 10 || tStreak2 == 10)) {
			return 0;
		} else if (hStreak1 == 10 || tStreak1 == 10) {
			return 1;
		} else if (hStreak2 == 10 || tStreak2 == 10) {
			return 2;
		}
		return -1;
	}
	
	/**
	 * Method which gets the side that the winning player won on
	 * @return the side the winning player won on, heads is 0 and tails is 1
	 */
	public int getWinningSide() {
		if (hStreak1 == 10 || hStreak2 == 10) {
			return 0;
		} else if (tStreak1 == 10 || tStreak2 == 10) {
			return 1;
		}
		return -1;
	}
}
