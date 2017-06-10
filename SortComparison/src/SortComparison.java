/**
 * Sort Comparison Class
 * Compares the running time of bubble, selection, and insertion sorts
 * @author 18nleung
 * @version 24 April 2017
 */

/**
 * Sort comparison questions:
 * 
 * PART 1
 * 1.	The insertion sort consistently ran the fastest. I expected it to be faster
 * 		than the others since it didn't have to move all over the place on the array
 * 		(it mostly went one place over to the left to sort)
 * 2.	The timings were pretty constant across sort types, (i.e. bubble sort was always slowest)
 * 		although there was some variance between trials - probably due to the different background
 * 		tasks going on while the sorts were all running.
 * 
 * PART 2
 * 1.	Insertion sort was still the fastest after the addition of "jawn". I still expected it
 * 		to be the fastest since it involved the least amount of jumping around on the array
 * 2.	Using system time might be inconsistent in that there might be other jobs on a thread
 * 		that prevent the system time from being returned immediately after the code finishes running,
 * 		thus making the time seem longer than it actually was.
 * 3. 	A better way to determine the efficiency of sorting algorithms could be by counting the number
 * 		of a certain type of operation, such as comparisons or array switches. In the case of counting
 * 		comparisons, we could initialize a counter in the instance data of a class and then increment
 * 		the counter each time a sort uses a comparison operator (<, > ==). The sort with the least number
 * 		of comparisons needed would then be the one considered the most efficient.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;

public class SortComparison {
	/**
	 * Method that runs our tests
	 * @param args args for the program
	 */
	public static void main(String[] args) {
		// Read from file
		// Replace em dashes with spaces
		// Remove non-alphabetic
		// characters besides newlines and spaces
		// Make everything lowercase
		// Then split based on spaces
		String[] contents =
			readFromFile("gadsby.txt")
				.replace("--", " ")
				.replaceAll("[^A-Za-z\n ]", "")
				.toLowerCase()
				.split("\\s+");
		
		System.out.println("Initial sorting:");
		runSorts(contents);
		
		System.out.println();
	    System.out.println("With addition of 'jawn':");

		String[] contents2 = addElementToHead(contents, "jawn");
		runSorts(contents2);
		
	    
	}
	
	/**
	 * Reads data from a file
	 * @param filename the file to read from (relative to class location)
	 * @return the data that was read
	 */
	public static String readFromFile(String filename) {
		URL path = SortComparison.class.getResource(filename);
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
	 * Runs an insertion sort
	 * @param words the array to sort
	 */
	public static void insertionSort(String[] words) {
		String key;
		int pos;
		
		for (int i = 1; i < words.length; i += 1) {
			key = words[i];
			pos = i;
			
			// .compareTo returns 1 if key should be before word[pos - 1]
			while (pos > 0 && words[pos - 1].compareTo(key) > 0) {
				words[pos] = words[pos - 1];
				pos -= 1;
			}
			
			words[pos] = key;
		}
	}
	
	/**
	 * Runs a selection sort
	 * @param words the array to sort
	 */
	public static void selectionSort(String[] words) {
		int minPos;
		
		for (int i = 0; i < words.length; i += 1) {
			minPos = i;
			
			for (int j = i; j < words.length; j += 1) {
				// If current word is greater than min word
				if (words[minPos].compareTo(words[j]) > 0) {
					minPos = j;
				}
			}
			
			if (!words[i].equals(words[minPos])) {
				String temp = words[i];
				words[i] = words[minPos];
				words[minPos] = temp;
			}
		}
	}
	
	/**
	 * Runs a bubble sort
	 * @param words the words to sort
	 */
	public static void bubbleSort(String[] words) {
		for (int i = 0; i < words.length; i += 1) {
			for (int j = 0; j < words.length - i - 1; j += 1) {
				// If words j is greater than words j + 1
				if (words[j].compareTo(words[j + 1]) > 0) {
					String temp = words[j];
					words[j] = words[j + 1];
					words[j + 1] = temp;
				}
			}
		}
	}
	
	/**
	 * Checks if the three provided arrays are equal
	 * @param arr1 the first array to check equality with
	 * @param arr2 the second array to check equality with
	 * @param arr3 the third array to check equality with
	 * @return whether the three arrays are equal
	 */
	public static boolean checkEquality(String[] arr1, String[] arr2, String[] arr3) {
		// check equality, not working
		boolean lengthsEqual = (arr1.length == arr2.length) && (arr2.length == arr3.length);
		if (!lengthsEqual) {
			System.out.println("Lengths are unequal");
			return false;
		}
		for (int i = 0; i < arr1.length; i += 1) {
			String item1 = arr1[i];
			String item2 = arr2[i];
			String item3 = arr3[i];
			boolean allEqual = (item1.equals(item2) && item2.equals(item3));
			if (!allEqual) {
				System.out.println("The items are not equal at position " + i);
				System.out.println("arr1: " + item1);
				System.out.println("arr2: " + item2);
				System.out.println("arr3: " + item3);
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Adds an element to the beginning of an array by creating a new array
	 * @param arr the original array
	 * @param el the element to add to the beginning
	 * @return the new array with the provided element at the head
	 */
	public static String[] addElementToHead(String[] arr, String el) {
		int length = arr.length;
		String[] newArr = new String[length + 1];
		System.arraycopy(arr, 0, newArr, 1, length);
		newArr[0] = el;
		return newArr;
	}
	
	/**
	 * Runs the sorting algorithms and prints results
	 * @param initialArr the array to sort
	 */
	public static void runSorts(String[] initialArr) {
		String[] bubbleSortArray = initialArr.clone();
		String[] selectionSortArray = initialArr.clone();
		String[] insertionSortArray = initialArr.clone();
		
		long bubbleStart = System.nanoTime();
		bubbleSort(bubbleSortArray);
		long bubbleEnd = System.nanoTime();
		long bubbleTime = bubbleEnd - bubbleStart;
		
		long selectionStart = System.nanoTime();
		selectionSort(selectionSortArray);
		long selectionEnd = System.nanoTime();
		long selectionTime = selectionEnd - selectionStart;
		
		long insertionStart = System.nanoTime();
		insertionSort(insertionSortArray);
		
		long insertionEnd = System.nanoTime();
		long insertionTime = insertionEnd - insertionStart;
		
		// Checking for equality among the arrays is a good proxy for making
		// sure the arrays were actually sorted
	    System.out.println("Did sorting occur? " + checkEquality(bubbleSortArray, selectionSortArray, insertionSortArray));

		System.out.println("Bubble sort took: " + convertNsToMinString(bubbleTime));
		System.out.println("Selection sort took: " + convertNsToMinString(selectionTime));
		System.out.println("Insertion sort took: " + convertNsToMinString(insertionTime));
	}
	
	/**
	 * Prints an array
	 * @param arr the arr to print
	 */
	public static void printArray(String[] arr) {
		System.out.println("Array contents:");
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + ", ");
			if (i % 10 == 0 && i != 0) {
				System.out.println();
			}
		}
		System.out.println();
	}
	
	/**
	 * Converts the ns value to a string with minutes and seconds
	 * @param ns the number of nanoseconds
	 * @return a string with minutes and seconds
	 */
	public static String convertNsToMinString(long ns) {
		int min = (int) (ns / (1e9 * 60));
		double sec = (ns - min * (1e9 * 60)) / 1e9;
		// Round to nearest thousandth
		sec = Math.round(sec * 1000) / 1000;
		return String.format("%dm:%fs", min, sec);
	}
}

