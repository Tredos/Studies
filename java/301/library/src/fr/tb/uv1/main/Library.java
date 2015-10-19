package fr.tb.uv1.main;

/*
 * Class offering the fr.tb.uv1.main functions of the library
 * @author M.T. Segarra
 * @version 0.0.1
 */

import java.util.*;

import fr.tb.uv1.booksManagement.Book;
import fr.tb.uv1.exceptions.*;
import fr.tb.uv1.loansManagement.Loan;
import fr.tb.uv1.subscribersManagement.Subscriber;

public class Library {

	/**
	 * @uml.property name="subscribers"
	 * @uml.associationEnd multiplicity="(0 -1)" ordering="true"
	 *                     inverse="library:fr.tb.uv1.subscribersManagement.Subscriber"
	 */
	private final ArrayList<Subscriber> subscribers = new ArrayList<Subscriber>();

	/**
	 * @uml.property name="books"
	 * @uml.associationEnd multiplicity="(0 -1)" ordering="true"
	 *                     inverse="library:fr.tb.uv1.booksManagement.Book"
	 */
	private final ArrayList<Book> books = new ArrayList<Book>();

	/**
	 * @uml.property name="loans"
	 * @uml.associationEnd readOnly="true" multiplicity="(0 -1)" ordering="true"
	 *                     inverse="library:fr.tb.uv1.loansManagement.Loan"
	 */
	private final ArrayList<Loan> loans = new ArrayList<Loan>();

	/**
	 * Adds a new book to the library
	 * 
	 * @param book
	 *            the book to be added
	 * @throws BadParametersException
	 * @throws BookExistsException
	 */
	public void addBook(Book book) throws BadParametersException,
			BookExistsException {
		if (book == null)
			throw new BadParametersException();

		// Look if the book is already in the library
		if (isABook(book))
			throw new BookExistsException();

		this.books.add(book);
	}

	/**
	 * This method add data about a new subscriber
	 * 
	 * @param s
	 *            the subscriber to be added
	 * @return subscriber number
	 * @throws BadParametersException
	 * @throws SubscriberExistsException
	 */
	public long addSubscriber(Subscriber s) throws BadParametersException,
			SubscriberExistsException {
		if (s == null)
			throw new BadParametersException();

		// Do not subscribe more than once a subscriber
		if (isASubscriber(s))
			throw new SubscriberExistsException();

		this.subscribers.add(s);
		return s.getNumber();
	}

	/**
	 * This method deletes all data of a book from the library
	 * 
	 * @param book
	 *            the book to delete
	 * @throws BadParametersException
	 * @throws BookExistsException
	 * @throws LentBookException
	 */
	public void deleteBook(Book book) throws BadParametersException,
			BookExistsException, LentBookException {
		if (book == null)
			throw new BadParametersException();

		// If the book does not exist, exception
		if (!isABook(book))
			throw new BookExistsException();

		// Get the book from the list
		int index = this.books.indexOf(book);
		book = this.books.get(index);

		// If the book is lent do not delete
		if (book.isLent())
			throw new LentBookException("Book lent. Cannot delete.");

		this.books.remove(book);
	}

	/**
	 * This method deletes data of a book from the library
	 * 
	 * @param isbn
	 *            ISBN of the book
	 * @throws BookExistsException
	 * @throws BadParametersException
	 * @throws LentBookException
	 * @throws SubscriberExistsException
	 */
	public void deleteBook(String isbn) throws BadParametersException,
			BookExistsException, LentBookException {

		Book b = new Book(isbn);

		// If the isbn is not a book
		// raise exception
		if (!isABook(b))
			throw new BookExistsException();

		// Get the book from the list
		int index = this.books.indexOf(b);
		b = this.books.get(index);

		// If loans, raise exception
		if (b.isLent())
			throw new LentBookException("Book lent. Cannot delete.");

		// Remove the book
		this.books.remove(index);
	}

	/**
	 * This method deletes data of a subscriber from the library
	 * 
	 * @param subsNumber
	 *            number of the subscriber
	 * @throws BadParametersException
	 * @throws SubscriberExistsException
	 */
	public void deleteSubscriber(long subsNumber)
			throws BadParametersException, SubscriberWithLoansException,
			SubscriberExistsException {

		Subscriber s = new Subscriber(subsNumber);

		// If the number is not a subscriber number
		// raise exception
		if (!isASubscriber(s))
			throw new SubscriberExistsException();

		// Get the subscriber from the list
		int index = this.subscribers.indexOf(s);
		s = this.subscribers.get(index);

		// If loans, raise exception
		if (s.existingLoans())
			throw new SubscriberWithLoansException();

		// Remove the subscriber
		this.subscribers.remove(index);
	}

	/**
	 * Getter of the property <tt>books</tt>
	 * 
	 * @return Returns the books.
	 * @uml.property name="books"
	 */
	public ArrayList<Book> getBooks() {
		return this.books;
	}

	/**
	 * Getter of the property <tt>loans</tt>
	 * 
	 * @return Returns the loans.
	 * @uml.property name="loans"
	 */
	public ArrayList<Loan> getLoans() {
		return loans;
	}

	/**
	 * Getter of the property <tt>subscribers</tt>
	 * 
	 * @return Returns the subscribers.
	 * @uml.property name="subscribers"
	 */
	public ArrayList<Subscriber> getSubscribers() {
		return this.subscribers;
	}

	/**
	 * Method to lend a book for a subscriber
	 * 
	 * @param book
	 *            the book to be lent
	 * @param lender
	 *            subscriber that whant to lend
	 * @return return date for the lent book
	 * @throws BadParametersException
	 * @throws BookExistsException
	 * @throws LentBookException
	 * @throws SubscriberExistsException
	 * @throws SubscriberWithLoansException
	 * @throws TooManyLoansException
	 */
	public Calendar lend(Book book, Subscriber lender)
			throws BadParametersException, BookExistsException,
			LentBookException, SubscriberExistsException, TooManyLoansException {

		if (book == null || lender == null)
			throw new BadParametersException();

		// Check if the book exists
		if (!isABook(book))
			throw new BookExistsException();

		// Get the book instance from the list
		int index = this.books.indexOf(book);
		book = this.books.get(index);

		// Check if the subscriber exists
		if (!isASubscriber(lender))
			throw new SubscriberExistsException();

		// Get the subscriber instance from the list
		index = this.subscribers.indexOf(lender);
		lender = this.subscribers.get(index);

		// Make the loan if possible
		Loan l = new Loan(book, lender);
		this.loans.add(l);
		return l.getReturnDate();
	}

	/**
	 * @return all books of the library
	 */
	public ArrayList<Book> listBooks() {
		return this.books;
	}

	/**
	 * Return the list of currrent loans
	 */
	public ArrayList<Loan> listLoans() {
		return this.loans;
	}

	/**
	 * @return all subscribers of the library
	 */
	public ArrayList<Subscriber> listSubscribers() {
		return this.subscribers;
	}

	/**
	 * This method returns the book book
	 * 
	 * @param book
	 *            the book to be returned
	 * @throws BookExistsException
	 * @throws LentBookException
	 */
	public void returnBook(Book book) throws BadParametersException,
			BookExistsException, LentBookException {
		if (book == null)
			throw new BadParametersException();

		// Check if the book exists
		if (!isABook(book))
			throw new BookExistsException();

		// Get the book instance from the list
		int index = books.indexOf(book);
		book = books.get(index);

		// Get the loan from the list
		index = loans.indexOf(book.getLoan());

		// Ask the book to make return
		book.returnBook();

		// Delete the loan from my list
		loans.remove(index);
	}

	/**
	 * A utility method to know if a book is already in the library
	 * 
	 * @param book
	 *            to
	 * @return false if the book not known by the library
	 * @return true if the book is known by the library
	 */
	private boolean isABook(Book book) throws BadParametersException {
		if (book == null)
			throw new BadParametersException();

		// If the object is in the list,
		// it is a known book
		return books.contains(book);
	}

	/**
	 * A utility method to know if a subscriber is known of the library
	 * 
	 * @param subscriber
	 *            to
	 * @return false if the subscriber of the parameter is not known of the
	 *         library
	 * @return true if the subscriber of the parameter is known of the library
	 */
	private boolean isASubscriber(Subscriber subscriber)
			throws BadParametersException {
		if (subscriber == null)
			throw new BadParametersException();

		// If the object is in the list,
		// it is a known subscriber
		return this.subscribers.contains(subscriber);
	}
}