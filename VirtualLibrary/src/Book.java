/**
 * Virtual Library: Book Class
 * A class which represents a book in our virtual library system
 * @author 18nleung
 * @version 13 March 2016
 */

import java.util.ArrayList;

public class Book {
	private String title;
	private String author;
	private String ISBN;
	private ArrayList<String> pages;
	private String subject;
	
	/**
	 * Constructs a new instance of the Book class, if pages argument is omitted
	 * @param title the title of the book
	 * @param author the author of the book
	 * @param ISBN the ISBN of the book
	 * @param subject the subject of the book
	 */
	public Book(
		String title,
		String author,
		String ISBN,
		String subject
	) {
		this(title, author, ISBN, new ArrayList<String>(), subject);
	}
	
	/**
	 * Constructs a new instance of the Book class
	 * @param title the title of the book
	 * @param author the author of the book
	 * @param ISBN the ISBN of the book
	 * @param pages pages of the book
	 * @param subject subject of the book
	 */
	public Book(
		String title,
		String author,
		String ISBN,
		ArrayList<String> pages,
		String subject
	) {
		this.title = title;
		this.author = author;
		this.ISBN = ISBN;
		this.pages = pages;
		this.subject = subject;
	}
	
	/**
	 * Gets the title of the book
	 * @return the title of the book
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Sets the title of the book
	 * @param title the title of the book
	 * @return the new title of the book
	 */
	public String setTitle(String title) {
		this.title = title;
		return this.title;
	}
	
	/**
	 * Gets the author of the book
	 * @return the author of the book
	 */
	public String getAuthor() {
		return author;
	}
	
	/**
	 * Sets the author of the book
	 * @param author the author of the book
	 * @return the new author of the book
	 */
	public String setAuthor(String author) {
		this.author = author;
		return this.author;
	}
	
	/**
	 * Gets the ISBN of the book
	 * @return the ISBN of the book
	 */
	public String getISBN() {
		return ISBN;
	}
	
	/**
	 * Sets the ISBN of the book
	 * @param ISBN the ISBN of the book
	 * @return the new ISBN of the book
	 */
	public String setISBN(String ISBN) {
		this.ISBN = ISBN;
		return this.ISBN;
	}
	
	/**
	 * Gets the pages of the book
	 * @return the pages of the book
	 */
	public ArrayList<String> getPages() {
		return pages;
	}
	
	/**
	 * Gets the page of the book at the specified position
	 * @param position the position of the page to get
	 * @return the page at the specified position
	 */
	public String getPage(int position) {
		return pages.get(position);
	}
	
	/**
	 * Sets a page of the book
	 * @param position the position of the page to set
	 * @param page the contents of the page
	 * @return the page that was set
	 */
	public String setPage(int position, String page) {
		pages.set(position, page);
		return pages.get(position);
	}
	
	/**
	 * Adds a page to the end of the book
	 * @param page the contents of the page to add
	 * @return the page that was added
	 */
	public String addPage(String page) {
		pages.add(page);
		return pages.get(pages.size() - 1);
	}
	
	/**
	 * Adds a page to the book at the specified position
	 * @param position the position at which to add the page
	 * @param page the contents of the page to add
	 * @return the page that was added
	 */
	public String addPage(int position, String page) {
		pages.add(position, page);
		return pages.get(position);
	}
	
	/**
	 * Deletes a page of the book at the specified position
	 * @param position the position of the page to delete
	 * @return the page that was deleted
	 */
	public String deletePage(int position) {
		return pages.remove(position);
	}
	
	/**
	 * Gets the subject of the book
	 * @return the subject of the book
	 */
	public String getSubject() {
		return subject;
	}
	
	/**
	 * Sets the subject of the book
	 * @param subject the subject of the book
	 * @return the new subject of the book
	 */
	public String setSubject(String subject) {
		this.subject = subject;
		return this.subject;
	}
	
	/**
	 * Prints the pages of the book
	 */
	public void printPages() {
		for (int i = 0; i < pages.size(); i++) {
			System.out.println("Page " + (i + 1) + ":");
			System.out.println(pages.get(i));
			System.out.println();
		}
	}
	
	/**
	 * Creates a string representation of the current Book instance
	 * @return a string that represents the book
	 */
	public String toString() {
		return "Title: " + title + "\n" +
			"Author: " + author + "\n" + 
			"ISBN: " + ISBN + "\n" + 
			"Pages: " + pages.size() + "\n" + 
			"Subject: " + subject;
	}
}