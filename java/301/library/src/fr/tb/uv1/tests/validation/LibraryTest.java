/*
 * Test class for the Library class
 * @author M.T. Segarra
 * @version 0.0.1
 */
package fr.tb.uv1.tests.validation;

import static org.junit.Assert.*;

import java.util.*;
import org.junit.*;

import fr.tb.uv1.booksManagement.Book;
import fr.tb.uv1.exceptions.*;
import fr.tb.uv1.loansManagement.Loan;
import fr.tb.uv1.main.Library;
import fr.tb.uv1.subscribersManagement.Subscriber;

public class LibraryTest {

	private Library lib;
	private Book b;
	private Subscriber s;
	private ArrayList<String> authors;

	@Before
	public void beforeTests() throws BadParametersException {
		lib = new Library();
		s = new Subscriber("Yann", "Bellier", new GregorianCalendar(2000, 12,
				13));

		authors = new ArrayList<String>();
		authors.add("Corral");
		b = new Book("La noche", authors, new GregorianCalendar(), "0201708515");
	}

	@Test
	public void testAddNewSubscriber() throws BadParametersException,
			SubscriberExistsException {
		System.out.println("*****Validating: Adding a new subscriber*****");
		long subsNumber = lib.addSubscriber(s);
		assert (lib.getSubscribers().size() == 1);

		System.out.println("Subscriber identifier: " + subsNumber);

		s = new Subscriber("Yann", "Dupont",
				new GregorianCalendar(2000, 12, 13));
		subsNumber = lib.addSubscriber(s);
		assert (lib.getSubscribers().size() == 2);

		System.out.println("*****End validating: Adding a new subscriber*****");
	}

	@Test
	public void testAddExistingSubscriber() throws BadParametersException,
			SubscriberExistsException {
		System.out
				.println("*****Validating: Adding an existing subscriber*****");
		lib.addSubscriber(s);
		System.out.println("Added one subscriber: "
				+ lib.getSubscribers().size());
		s = new Subscriber("Yann", "Bellier", new GregorianCalendar(2000, 12,
				13));
		System.out.println("Adding the same subscriber: " + s);
		try {
			lib.addSubscriber(s);
		} catch (SubscriberExistsException ex) {
			System.out
					.println("The subscriber already exists. Number of subscribers: "
							+ lib.getSubscribers().size());
			System.out
					.println("*****End validating: Adding a new subscriber*****");
		}
	}

	@Test
	public void testAddMissingInfosSubs() throws SubscriberExistsException {
		System.out
				.println("*****Validating: Adding a subscriber. Missing infos*****");
		try {
			lib.addSubscriber(new Subscriber("Yann", "Bellier", null));
		} catch (BadParametersException ex) {
			System.out.println("Missing some infos of the subscriber: "
					+ lib.getSubscribers().size());
			System.out
					.println("*****End validating: Adding a subscriber. Missing infos*****");
		}
	}

	@Test
	public void testDelSubsWithoutLoans() throws BadParametersException,
			SubscriberExistsException, SubscriberWithLoansException {
		System.out
				.println("*****Validating: Deleting an existing subscriber without loans*****");
		long number = lib.addSubscriber(s);
		lib.deleteSubscriber(number);
		System.out.println("Deleted subscriber: " + s);
		assertTrue(lib.getSubscribers().size() == 0);
		System.out
				.println("*****End validating: Deleting an existing subscriber without loans*****");
	}

	@Test
	public void testDelSubsWithLoans() throws BadParametersException,
			SubscriberExistsException, BookExistsException, LentBookException,
			TooManyLoansException {
		System.out
				.println("*****Validating: Deleting an existing subscriber with loans*****");
		long number = lib.addSubscriber(s);
		lib.addBook(b);

		// Create a loan
		lib.lend(b, s);

		try {
			lib.deleteSubscriber(number);
		} catch (SubscriberWithLoansException ex) {
			System.out.println("Subscriber with loans. Delete not possible.");
			System.out
					.println("*****************************************************");
		}
		System.out
				.println("*****End validating: Deleting an existing subscriber with loans*****");
	}

	@Test
	public void testDelNotExistingSubs() throws BadParametersException,
			SubscriberWithLoansException {
		System.out
				.println("*****Validating: Deleting an not existing subscriber******");
		System.out.println("Number of subscribers: "
				+ lib.getSubscribers().size());
		try {
			lib.deleteSubscriber(s.getNumber());
		} catch (SubscriberExistsException ex) {
			System.out
					.println("Subscriber does not exist. Delete not possible.");
			System.out
					.println("*****************************************************");
		}
		System.out
				.println("*****End validating: Deleting an not existing subscriber*****");
	}

	@Test
	public void testAddMissingInfosBook() throws BadParametersException,
			BookExistsException {
		System.out
				.println("*****Validating: Adding a book. Missing infos*****");
		try {
			lib.addBook(new Book("La noche", null, new GregorianCalendar(),
					"0201708515"));
		} catch (BadParametersException ex) {
			System.out.println("Missing some infos of the book: "
					+ lib.getBooks().size());
			System.out
					.println("*****************************************************");
		}
		System.out
				.println("*****End validating: Adding a book. Missing infos*****");
	}

	@Test
	public void testAddBook() throws BadParametersException,
			BookExistsException {
		System.out.println("******Validating: Adding a new book******");
		lib.addBook(b);
		assertTrue(lib.getBooks().size() == 1);

		b = new Book("El dia", authors, new GregorianCalendar(), "0201708516");
		lib.addBook(b);
		assertTrue(lib.getBooks().size() == 2);
		System.out.println("******End validating: Adding a new book******");
	}

	@Test
	public void testAddExistingBook() throws BadParametersException {
		System.out.println("*****Validating: Adding an existing book*****");
		try {
			lib.addBook(b);
		} catch (BookExistsException ex) {
			System.out.println("The book already exists. Number of books: "
					+ lib.getBooks().size());
			System.out
					.println("*****************************************************");
		}

		ArrayList<String> authors = new ArrayList<String>();
		authors.add("Corral");
		b = new Book("La noche", authors, new GregorianCalendar(), "0201708515");
		try {
			lib.addBook(b);
		} catch (BookExistsException ex) {
			System.out.println("The book already exists. Number of books: "
					+ lib.getBooks().size());
			System.out
					.println("*****************************************************");
		}
		System.out.println("*****End validating: Adding an existing book*****");
	}

	@Test
	public void testDelBook() throws BadParametersException,
			BookExistsException, LentBookException {
		System.out
				.println("*****Validating: Deleting an existing book without loans******");
		lib.addBook(b);
		lib.deleteBook(b.getIsbn());
		assertTrue(lib.getBooks().size() == 0);
		System.out
				.println("*****End validating: Deleting an existing book without loans*****");
	}

	@Test
	public void testDelLentBook() throws TooManyLoansException,
			LentBookException, BadParametersException, BookExistsException {
		System.out
				.println("*****Validating: Deleting an existing book with loans*****");
		lib.addBook(b);
		new Loan(b, s);
		try {
			lib.deleteBook(b.getIsbn());
		} catch (LentBookException ex) {
			System.out.println("Book with loan (not deleted): " + b);
			System.out.println("Number of books: " + lib.getBooks().size());
			System.out
					.println("*****************************************************");
		}
		System.out
				.println("*****End Validating: Deleting an existing book with loans*****");
	}

	@Test
	public void testDelNotExistingBook() throws BadParametersException,
			LentBookException {
		System.out
				.println("*****Validating: Deleting an not existing book*****");
		System.out.println("Number of books: " + lib.getBooks().size());
		try {
			lib.deleteBook(b.getIsbn());
		} catch (BookExistsException ex) {
			System.out
					.println("Subscriber does not exist. Delete not possible.");
			System.out.println("Number of books: " + lib.getBooks().size());
			System.out
					.println("*****************************************************");
		}
		System.out
				.println("*****End validating: Deleting an not existing book*****");
	}

	@Test
	public void testLend() throws BadParametersException, BookExistsException,
			SubscriberExistsException, TooManyLoansException, LentBookException {
		System.out
				.println("*****Validating: Lending an existing book by a subscriber*****");
		lib.addBook(b);
		lib.addSubscriber(s);

		lib.lend(b, s);

		assertTrue(lib.getLoans().size() == 1);
		System.out
				.println("*****End validating: Lending an existing book by a subscriber*****");
	}

	@Test
	public void testNoBookLend() throws BadParametersException,
			SubscriberExistsException, TooManyLoansException, LentBookException {
		System.out.println("*****Validating: Lending a not existing book*****");
		lib.addSubscriber(s);

		try {
			lib.lend(b, s);
		} catch (BookExistsException ex) {
			System.out.println("Book does not exist. Loan not possible.");
			System.out.println("Number of loans: " + lib.getLoans().size());
			System.out
					.println("*****************************************************");
		}
		System.out
				.println("*****End validating: Lending a not existing book*****");
	}

	@Test
	public void testNoSubscriberLend() throws BadParametersException,
			BookExistsException, SubscriberExistsException,
			TooManyLoansException, LentBookException {
		System.out
				.println("*****Validating: Loan by a not existing subscriber*******");
		lib.addBook(b);

		try {
			lib.lend(b, s);
		} catch (SubscriberExistsException ex) {
			System.out.println("Subscriber does not exist. Loan not possible.");
			System.out.println("Number of loans: " + lib.getLoans().size());
			System.out
					.println("*****************************************************");
		}
		System.out
				.println("*****End validating : Loan by a not existing subscriber*****");
	}

	@Test
	public void testReturn() throws TooManyLoansException, LentBookException,
			BadParametersException, BookExistsException,
			SubscriberExistsException {
		System.out.println("*****Validating: Returning a lent book*****");
		lib.addBook(b);
		lib.addSubscriber(s);

		lib.lend(b, s);

		lib.returnBook(b);
		assertTrue(lib.getLoans().size() == 0);
		System.out.println("*****End validating: Returning a lent book*****");
	}
}
