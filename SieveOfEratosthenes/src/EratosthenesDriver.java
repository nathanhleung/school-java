/**
 * Sieve of Eratosthenes Project: Driver
 * Driver for Sieve of Eratosthenes Implementations
 * @author 18nleung
 * @version 9 March 2017
 */

public class EratosthenesDriver {

	/**
	 * This method tests the different sieve implementations
	 * and prints the results of the tests
	 * @param args args passed to the program
	 */
	public static void main(String[] args) {
		System.out.println("Running tests...");
		System.out.println();
		int trials = 1000;
		int max = 1000;
		// Run tests
		long arrayTime = arrayTest(trials, max);
		long arrayListTime = arrayListTest(trials, max);
		
		System.out.println(trials + " trials, sieving prime numbers up to " + max + ":");
		System.out.println("Array time: " + arrayTime + "ms");
		System.out.println("ArrayList time: " + arrayListTime + "ms");
		System.out.println();
		
		// Print result of tests
		if (arrayTime < arrayListTime) { 
			System.out.println("The native array implementation is more efficient.");
		} else if (arrayListTime < arrayTime) {
			System.out.println("The ArrayList implementation is more efficient.");
		} else {
			System.out.println("It's a tie!");
		}
		
		System.out.println();
		
		int max2 = 1000;
		// Print the actual prime numbers
		System.out.println("First " + max2 + " prime numbers:");
		EratosthenesArray ea = new EratosthenesArray(max2);
		System.out.println("Array implementation: ");
		System.out.println(ea);
		
		EratosthenesArrayList eal = new EratosthenesArrayList(max2);
		System.out.println("ArrayList implementation: ");
		System.out.println(eal);
	}
	
	/**
	 * This method tests the native array implementation of the sieve
	 * @param trials the number of times to run the sieve
	 * @param max the maximum value to sieve to
	 * @return the total time it took to run the native array sieves
	 */
	private static long arrayTest(int trials, int max) {
		long totalTime = 0;
		for (int i = 0; i < trials; i++) {
			long startTime = System.currentTimeMillis();
			EratosthenesArray ea = new EratosthenesArray(max);
			long endTime = System.currentTimeMillis();
			totalTime += (endTime - startTime);
		}
		return totalTime;
	}
	
	/**
	 * This method tests the ArrayList implementation of the sieve
	 * @param trials the number of times to run the sieve
	 * @param max the maximum value to sieve to
	 * @return the total time it took to run the ArrayList sieves
	 */
	private static long arrayListTest(int trials, int max) {
		long totalTime = 0;
		for (int i = 0; i < trials; i++) {
			long startTime = System.currentTimeMillis();
			EratosthenesArrayList eal = new EratosthenesArrayList(max);
			long endTime = System.currentTimeMillis();
			totalTime += (endTime - startTime);
		}
		return totalTime;
	}

}
