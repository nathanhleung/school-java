/**
 * Virtual Library: Driver Class
 * A driver class to test our virtual library system
 * @author 18nleung
 * @version 13 March 2016
 */

import java.util.ArrayList;
import java.util.Arrays;

public class VirtualLibraryDriver {
	
	/**
	 * The method which tests and runs our Library and Book code
	 * @param args arguments passed to the program
	 */
	public static void main(String[] args) {
		System.out.println("Testing Book class:");
		testBook();
		System.out.println("Testing Library class:");
		testLibrary();
	}
	
	/**
	 * This method tests the Book class
	 */
	private static void testBook() {
		ArrayList<String> dickensPages = new ArrayList<String>(
			Arrays.asList(
				"A Tale of Two Cities, by Charles Dickens",
				"It was the best of times, it was the worst of times."
			)
		);
		Book twoCities = new Book(
			"A Tale of Two Cities",
			"Charles Dickens",
			"9781534968998",
			dickensPages,
			"Fiction"
		);

		// toString
		System.out.println("Book#toString()");
		System.out.println(twoCities);
		// printPages
		System.out.println("\nBook#printPages()");
		twoCities.printPages();	
		// getTitle
		System.out.println("\nBook#getTitle()");
		System.out.println(twoCities.getTitle());
		// setTitle
		System.out.println("\nBook#setTitle()");
		System.out.println(twoCities.setTitle("Le Petit Prince"));
		// getAuthor
		System.out.println("\nBook#getAuthor()");
		System.out.println(twoCities.getAuthor());
		// setAuthor
		System.out.println("\nBook#setAuthor()");
		System.out.println(twoCities.setAuthor("Antoine de Saint-Exupery"));
		// getISBN
		System.out.println("\nBook#getISBN()");
		System.out.println(twoCities.getISBN());
		// setISBN
		System.out.println("\nBook#setISBN()");
		System.out.println(twoCities.setISBN("9788998469863"));
		// getPages
		System.out.println("\nBook#getPages()");
		System.out.println(twoCities.getPages());
		// getPage
		System.out.println("\nBook#getPage()");
		System.out.println(twoCities.getPage(0));
		// setPage
		System.out.println("\nBook#setPage(0)");
		System.out.println(twoCities.setPage(0, "Le Petit Prince, par Antoine de Saint-Exupery"));
		// addPage 1
		System.out.println("\nBook#addPage() (1 arg)");
		System.out.println(twoCities.addPage("Bienvenue."));
		// addPage 2
		System.out.println("\nBook#addPage(1) (2 args)");
		System.out.println(twoCities.addPage(1, "Cover Page."));
		// deletePage
		System.out.println("\nBook#deletePage(2)");
		System.out.println(twoCities.deletePage(2));	
		// getSubject
		System.out.println("\nBook#getSubject()");
		System.out.println(twoCities.getSubject());
		// setSubject
		System.out.println("\nBook#setSubject()");
		System.out.println(twoCities.setSubject("Children's Literature"));
		// toString
		System.out.println("\nBook#toString()");
		System.out.println(twoCities);
		// printPages
		System.out.println("\nBook#printPages()");
		twoCities.printPages();	
	}
	
	/**
	 * This method tests the library class
	 */
	private static void testLibrary() {
		ArrayList<Book> books = new ArrayList<Book>(
			Arrays.asList(
				new Book(
					"Great Expectations",
					"Charles Dickens",
					"9781282756557",
					"Fiction"
				),
				new Book(
					"Les Miserables",
					"Victor Hugo",
					"9788408081890",
					"Fiction"
				)
			)
		);
		Library babel = new Library("Jorge Luis Borges", "Babel", books);
		Book frankenstein = new Book(
			"Frankenstein, or the Modern Prometheus",
			"Mary Shelley (with Percy Shelley)",
			"9781608438037",
			"Fiction"
		);
		Book pageant = new Book(
			"The American Pageant, 13th edition",
			"Kennedy, Cohen and Bailey",
			"9780618479405",
			"Reference"
		);
		Book giancoli = new Book(
			"Physics: Principles and Applications",
			"Douglas C. Giancoli",
			"9780133447682",
			"Reference"
		);
		
		// toString
		System.out.println("Library#toString()");
		System.out.println(babel);
		// getBooks
		System.out.println("\nLibrary#getBooks()");
		System.out.println(babel.getBooks());
		// getBook
		System.out.println("\nLibrary#getBook(0)");
		System.out.println(babel.getBook(0));
		// setBook
		System.out.println("\nLibrary#setBook(0)");
		System.out.println(babel.setBook(0, frankenstein));
		// addBook (1 arg)
		System.out.println("\nLibrary#addBook() (1 arg)");
		System.out.println(babel.addBook(pageant));
		// addBook (2 args)
		System.out.println("\nLibrary#addBook(1) (2 args)");
		System.out.println(babel.addBook(1, giancoli));
		// deleteBook
		System.out.println("\nLibrary#deleteBook(2)");
		System.out.println(babel.deleteBook(2));
		// getLibrarian
		System.out.println("\nLibrary#getLibrarian()");
		System.out.println(babel.getLibrarian());
		// setLibrarian
		System.out.println("\nLibrary#setLibrarian()");
		System.out.println(babel.setLibrarian("Morgan le Fay"));
		// getLocation
		System.out.println("\nLibrary#getLocation()");
		System.out.println(babel.getLocation());
		// setLocation
		System.out.println("\nLibrary#setLocation()");
		System.out.println(babel.setLocation("Magic Tree House"));
		// printBooks
		System.out.println("\nLibrary#printBooks()");
		babel.printBooks();
		// sortBooksByISBN
		System.out.println("\nLibrary#sortBooksByISBN()");
		babel.sortBooksByISBN();
		babel.printBooks();
		// findBookByTitle
		System.out.println("\nLibrary#findBookByTitle(\"Frankenstein, or the Modern Prometheus\")");
		System.out.println(babel.findBookByTitle("Frankenstein, or the Modern Prometheus"));
		// printAuthors
		System.out.println("\nLibrary#printAuthors()");
		babel.printAuthors();
		// displayAllBooksFromAuthor
		System.out.println("\nLibrary#displayAllBooksFromAuthor(\"Douglas C. Giancoli\")");
		babel.displayAllBooksFromAuthor("Douglas C. Giancoli");
		// toString
		System.out.println("\nLibrary#toString()");
		System.out.println(babel);
	}
}
