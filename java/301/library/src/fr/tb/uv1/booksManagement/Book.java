/*
 * Class representing a book of a library
 * @author M.T. Segarra
 * @version 0.0.1
 */
package fr.tb.uv1.booksManagement;

import java.util.*;

import fr.tb.uv1.exceptions.*;
import fr.tb.uv1.loansManagement.Loan;

public class Book {

	/**
	 * @uml.property name="title"
	 */
	private String title;

	/**
	 * Getter of the property <tt>title</tt>
	 * 
	 * @return Returns the title.
	 * @uml.property name="title"
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Setter of the property <tt>title</tt>
	 * 
	 * @param title
	 *            The title to set.
	 * @uml.property name="title"
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @uml.property name="authors"
	 */
	private ArrayList<String> authors;

	/**
	 * Getter of the property <tt>authors</tt>
	 * 
	 * @return Returns the authors.
	 * @uml.property name="authors"
	 */
	public ArrayList<String> getAuthors() {
		return authors;
	}

	/**
	 * Setter of the property <tt>authors</tt>
	 * 
	 * @param authors
	 *            The authors to set.
	 * @uml.property name="authors"
	 */
	public void setAuthors(ArrayList<String> authors) {
		this.authors = authors;
	}

	/**
	 * @uml.property name="editionDate"
	 */
	private Calendar editionDate;

	/**
	 * Getter of the property <tt>editionDate</tt>
	 * 
	 * @return Returns the editionDate.
	 * @uml.property name="editionDate"
	 */
	public Calendar getEditionDate() {
		return editionDate;
	}

	/**
	 * Setter of the property <tt>editionDate</tt>
	 * 
	 * @param editionDate
	 *            The editionDate to set.
	 * @uml.property name="editionDate"
	 */
	public void setEditionDate(Calendar editionDate) {
		this.editionDate = editionDate;
	}

	/**
	 * @uml.property name="isbn" readOnly="true"
	 */
	private String isbn;

	/**
	 * Getter of the property <tt>isbn</tt>
	 * 
	 * @return Returns the isbn.
	 * @uml.property name="isbn"
	 */
	public String getIsbn() {
		return isbn;
	}

	/**
	 * @uml.property name="loan"
	 * @uml.associationEnd inverse="book:fr.tb.uv1.loansManagement.Loan"
	 */
	private Loan loan;

	/**
	 * Getter of the property <tt>loan</tt>
	 * 
	 * @return Returns the loan.
	 * @uml.property name="loan"
	 */
	public Loan getLoan() {
		return loan;
	}

	/**
	 * Setter of the property <tt>loan</tt>
	 * 
	 * @param loan
	 *            The loan to set.
	 * @uml.property name="loan"
	 */
	public void setLoan(Loan loan) {
		this.loan = loan;
	}

	/**
	 * Creates a book All parameters should be given
	 * 
	 * @param title
	 *            the title of the book
	 * @param authors
	 *            the list of the authors (names)
	 * @param editionDate
	 *            date of the edition of the book
	 * @param isbn
	 *            ISBN of the book
	 * @throws BadParametersException
	 */
	public Book(String title, ArrayList<String> authors, Calendar editionDate,
			String isbn) throws BadParametersException {
		if ((title == null) || (authors == null) || (editionDate == null)
				|| (isbn == null))
			throw new BadParametersException();

		this.title = title;
		this.authors = authors;
		this.editionDate = editionDate;
		this.isbn = isbn;
	}

	/**
	 * Creates a book Just the isbn is given
	 * 
	 * @param isbn
	 *            ISBN of the book
	 * @throws BadParametersException
	 */
	public Book(String isbn) throws BadParametersException {
		if (isbn == null)
			throw new BadParametersException();
		this.isbn = isbn;
	}

	/**
	 * @return true if this book is lent
	 */
	public boolean isLent() {
		return (loan != null);
	}

	/**
	 * Makes actions to return a book No test if the return date is lower than
	 * today
	 * 
	 * @throws LentBookException
	 * @throws BadParametersException
	 */
	public void returnBook() throws LentBookException, BadParametersException {

		if (!isLent())
			throw new LentBookException("The book is not lent");

		loan.returnBook();

		this.loan = null;
	}

	/**
	 * Makes actions to lend a book
	 * 
	 * @throws LentBookException
	 * @throws BadParametersException
	 */
	public void lend(Loan loan) throws LentBookException,
			BadParametersException {
		if (loan == null)
			throw new BadParametersException();

		// Check if the book is available
		if (this.isLent())
			throw new LentBookException("Book already lent.");

		this.loan = loan;
	}

	/*
	 * Two books are equal if their ISBN is the same
	 */
	@Override
	public boolean equals(Object book) {
		if (!(book instanceof Book))
			return false;

		Book b = (Book) book;

		return b.isbn == isbn;
	}

	/**
	 * Returns a description of the book
	 */
	@Override
	public String toString() {
		return this.title + ", " + this.isbn;
	}
}