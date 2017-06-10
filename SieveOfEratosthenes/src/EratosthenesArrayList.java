/**
 * Sieve of Eratosthenes Project: ArrayList Version
 * An implementation of the Sieve of Eratosthenes using an ArrayList
 * @author 18nleung
 * @version 8 March 2017
 */

import java.util.ArrayList;

public class EratosthenesArrayList {
	int max;
	ArrayList<Integer> sieve;
	int notPrimeFlag;
	
	/**
	 * Creates a new Sieve of Eratosthenes up to the provided maximum
	 * using an ArrayList
	 * @param n the value at which we should stop searching for primes
	 */
	public EratosthenesArrayList(int n) {
		max = n;
		// The number we change the array item to
		// when we want to remove it from the sieve.
		// It's 1 more than the max, since it's impossible
		// otherwise to have more than the max.
		notPrimeFlag = max + 1;
		generateArrayList();
		runSieve();
		cleanSieve();
	}
	
	/**
	 * Generates the ArrayList to be used for the sieving process.
	 */
	private void generateArrayList() {
		sieve = new ArrayList<Integer>();
		// Generates an ArrayList filled with numbers 2 - max
		for (int i = 2; i <= max; i++) {
			sieve.add(i);
		}
	}
	
	/**
	 * Runs the sieving algorithm in order to find prime numbers.
	 */
	private void runSieve() {
		// Stops searching at the square root of the max.
		// This is an assumption of the algorithm
		double stopSearching = (int) Math.ceil(Math.sqrt(max));
		// Check the sieve for multiples of every number from
		// 2 to the square root of max.
		// (since every number is a multiple of 1)
		for (int i = 2; i <= stopSearching; i++) {
			int length = sieve.size();
			for (int j = 0; j < length; j++) {
				// Checks if the number is a multiple of the current
				// factor, and that it isn't the same number
				// (i.e. multiplied by 1).
				if (sieve.get(j) % i == 0 && sieve.get(j) != i) {
					sieve.set(j, notPrimeFlag);
				}
			}
		}
	}
	
	/**
	 * Cleans the sieve in place after the algorithm is run,
	 * removing values from the original
	 * sieve based on the flag created in the constructor.
	 * The only remaining numbers are prime numbers.
	 */
	private void cleanSieve() {
		int length = sieve.size();
		for (int i = 0; i < length; i++) {
			// If the current value is not prime,
			// remove it from the sieve
			if (sieve.get(i) == notPrimeFlag) {
				sieve.remove(i);
				// Since we've removed the item,
				// we need to check the index again
				// with the new item
				i -= 1;
				// Also, the new length of the ArrayList
				// is 1 less
				length -= 1;
			}
		}
	}
	
	/**
	 * Method which generates a string representation of the sieve.
	 * (i.e. displays all prime numbers up to the provided max).
	 * @return a string representation of the sieve
	 */
	public String toString() {
		String result = "";
		int length = sieve.size();
		// Iterate over the clean sieve and join
		// primes with a comma
		for (int i = 0; i < length; i++) {
			if (i != 0) {
				result += ", ";
			}
			result += sieve.get(i);
		}
		return result;
	}
}