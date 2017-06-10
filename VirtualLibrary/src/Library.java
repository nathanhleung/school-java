/**
 * Virtual Library: Library Class
 * A class which represents a library branch in our virtual library system
 * @author 18nleung
 * @version 13 March 2016
 */

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Library {
	private String librarian;
	private String location;
	private ArrayList<Book> books;
	
	/**
	 * Constructs a new instance of the Library class, when the books parameter is omitted
	 * @param librarian the name of the librarian
	 * @param location the location of the library
	 */
	public Library(String librarian, String location) {
		this(librarian, location, new ArrayList<Book>());
	}
	
	/**
	 * Constructs a new instance of the Library class
	 * @param librarian the name of the librarian
	 * @param location the location of the library
	 * @param books a list of books that should be in the library initially
	 */
	public Library(String librarian, String location, ArrayList<Book> books) {
		this.librarian = librarian;
		this.location = location;
		this.books = books;
	}
	
	
	/**
	 * Gets a list of all the books currently in the library
	 * @return an ArrayList of books in the library
	 */
	public ArrayList<Book> getBooks() {
		return books;
	}
	
	/**
	 * Gets the book at the specified position in the library
	 * @param position the position of the book to get
	 * @return the book at the specified position
	 */
	public Book getBook(int position) {
		return books.get(position);
	}
	
	/**
	 * Sets the provided book in the specified position in the library
	 * @param position the position at which to set the book
	 * @param book the book to set
	 * @return the book that was set
	 */
	public Book setBook(int position, Book book) {
		books.set(position, book);
		return books.get(position);
	}
	
	/**
	 * Adds the provided book at the end of the shelf in the library
	 * @param book the book to add to the library
	 * @return the book that was added
	 */
	public Book addBook(Book book) {
		books.add(book);
		return books.get(books.size() - 1);
	}
	
	/**
	 * Adds the provided book at the specified position in the library
	 * @param position the position at which to add the book
	 * @param book the book to add to the library
	 * @return the book that was added
	 */
	public Book addBook(int position, Book book) {
		books.add(position, book);
		return books.get(position);
	}
	
	/**
	 * Deletes the book at the specified position in the library
	 * @param position the position of the book to delete
	 * @return the book that was deleted
	 */
	public Book deleteBook(int position) {
		return books.remove(position);
	}
	
	/**
	 * Gets the name of the librarian at the library
	 * @return the name of the librarian
	 */
	public String getLibrarian() {
		return librarian;
	}
	
	
	/**
	 * Sets the name of the librarian at the library
	 * @param librarian
	 * @return the new name of the librarian
	 */
	public String setLibrarian(String librarian) {
		this.librarian = librarian;
		return this.librarian;
	}
	
	/**
	 * Gets the location of the library
	 * @return the location of the library
	 */
	public String getLocation() {
		return location;
	}
	
	/**
	 * Sets the location of the library
	 * @param location
	 * @return the new location of the library
	 */
	public String setLocation(String location) {
		this.location = location;
		return this.location;
	}
	
	/**
	 * Sorts the books in the library by ISBN
	 * @return the newly sorted books
	 */
	public ArrayList<Book> sortBooksByISBN() {
		books.sort((book1, book2) ->
			book1.getISBN().compareTo(book2.getISBN())
		);
		return books;
	}
	
	/**
	 * Finds the position of a book in the library based on its title
	 * @param title the title of the book to search for
	 * @return the book with the specified title
	 */
	public int findBookByTitle(String title) {
		for (int i = 0; i < books.size(); i++) {
			if (books.get(i).getTitle().equals(title)) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Prints the list of books in the library to the console
	 */
	public void printBooks() {
		for (int i = 0; i < books.size(); i++) { 
			System.out.println("Book " + (i + 1) + ":");
			System.out.println(books.get(i));
			System.out.println();
		}
	}
	
	/**
	 * Prints the list of authors represented in the library
	 */
	public void printAuthors() {
		Map<String, Integer> authors = new HashMap<String, Integer>();
		for (int i = 0; i < books.size(); i++) {
			String authorName = books.get(i).getAuthor();
			if (authors.containsKey(authorName)) {
				int currentBooks = authors.get(authorName);
				authors.put(authorName, currentBooks + 1);
			} else {
				authors.put(authorName, 1);
			}
		}
		for (Map.Entry<String, Integer> author : authors.entrySet()) {
			System.out.println(
				author.getKey() + ": " + author.getValue() + " book(s)"
			);
		}
	}
	
	/**
	 * Prints a list of books from the specified author to the console
	 * @param author the author to find books from
	 */
	public void displayAllBooksFromAuthor(String author) {
		for (int i = 0; i < books.size(); i++) {
			Book book = books.get(i);
			if (book.getAuthor().equals(author)) {
				System.out.println(book);
			}
		}
	}
	
	/**
	 * Creates a string representation of the Library instance
	 * @return a string representation of the Library
	 */
	public String toString() {
		return "Librarian: " + librarian + "\n" +
			"Location: " + location + "\n" +
			"Books: " + books.size();
	}
}
