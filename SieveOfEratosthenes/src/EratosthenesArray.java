/**
 * Sieve of Eratosthenes Project: Array Version
 * An implementation of the Sieve of Eratosthenes using a native array
 * @author 18nleung
 * @version 8 March 2017
 */

public class EratosthenesArray {
	int max;
	int[] sieve;
	int notPrimeFlag;
	int[] cleanedSieve;
	
	/**
	 * Creates a new Sieve of Eratosthenes up to the provided maximum
	 * using a native array
	 * @param n the value at which we should stop searching for primes
	 */
	public EratosthenesArray(int n) {
		max = n;
		// The number we change the array item to
		// when we want to remove it from the sieve.
		// It's 1 more than the max, since it's impossible
		// otherwise to have more than the max.
		notPrimeFlag = max + 1;
		generateArray();
		runSieve();
		cleanSieve();
	}
	
	/**
	 * Generates the array to be used for the sieving process.
	 */
	private void generateArray() {
		// There will be all numbers 2 to max inclusive in the array
		// So, max - 2 + 1 elements.
		sieve = new int[max - 2 + 1];
		// Generates an array filled with numbers 2 - max
		for (int i = 0; i <= max - 2; i++) {
			sieve[i] = i + 2;
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
			int length = sieve.length;
			for (int j = 0; j < length; j++) {
				// Checks if the number is a multiple of the current
				// factor, and that it isn't the same number
				// (i.e. multiplied by 1).
				if (sieve[j] % i == 0 && sieve[j] != i) {
					sieve[j] = notPrimeFlag;
				}
			}
		}
	}
	
	/**
	 * Generates a new array from the sieve after the 
	 * algorithm is run, removing values from the original
	 * sieve based on the flag created in the constructor.
	 * The only remaining numbers are prime numbers.
	 */
	private void cleanSieve() {
		int length = sieve.length;
		// Track number of primes so we know how big
		// our new array should be
		int numOfPrimes = 0;
		for (int i = 0; i < length; i++) {
			// If the number _is_ a prime, increment the prime counter
			if (sieve[i] != notPrimeFlag) {
				numOfPrimes++;
			}
		}
		// Create a new array for the primes
		cleanedSieve = new int[numOfPrimes];
		// Keep track of what # prime we are on
		int primeIndex = 0;
		// Iterate over the old sieve and add primes
		// to the cleanedSieve
		for (int i = 0; i < length; i++) {
			if (sieve[i] != notPrimeFlag) {
				cleanedSieve[primeIndex] = sieve[i];
				primeIndex++;
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
		int length = cleanedSieve.length;
		// Iterate over the cleanedSieve, joining
		// the primes with commas
		for (int i = 0; i < length; i++) {
			if (i != 0) {
				result += ", ";
			}
			result += cleanedSieve[i];
		}
		return result;
	}
}
