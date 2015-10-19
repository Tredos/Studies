/*
 * Unit test for the Loan class
 * @author M.T. Segarra
 * @version 0.0.1
 */
package fr.tb.uv1.tests.unit;

import static org.junit.Assert.assertTrue;
import org.junit.*;
import java.util.*;

import fr.tb.uv1.booksManagement.Book;
import fr.tb.uv1.exceptions.*;
import fr.tb.uv1.loansManagement.Loan;
import fr.tb.uv1.subscribersManagement.Subscriber;

public class LoanTest {

	private Loan l;
	private Book b;
	private Subscriber s;

	@Before
	public void beforeTests() throws BadParametersException {
		this.s = new Subscriber("Mayte", "Segarra", new GregorianCalendar());
		ArrayList<String> authors = new ArrayList<String>();
		this.b = new Book("Noche", authors, new GregorianCalendar(), "026765415");
	}

	@Test
	public void testEquals() throws BadParametersException, TooManyLoansException, LentBookException {
		
		ArrayList<String> authors = new ArrayList<String>();
		Book b2 = new Book("Noche", authors, new GregorianCalendar(), "026765417");
		
		// The same subscriber loans two books
		l = new Loan(b, s);	
		Loan l2 = new Loan(b2, s);
		assertTrue(!l.equals(l2));
		
		// Different subscribers loan different books
		authors = new ArrayList<String>();
		Book b3 = new Book("Noche", authors, new GregorianCalendar(), "026765418");
		this.s = new Subscriber("M", "Segarra", new GregorianCalendar());
		Loan l3 = new Loan(b3, s);
		assertTrue(!l.equals(l3));
		
		// Book unit test fr.tb.uv1.tests if loaning twice the same book
	}
	
	@Test
	public void testLoan() throws BadParametersException, TooManyLoansException, LentBookException {
		l = new Loan(b, s);
		assertTrue(l.getBook().equals(b) && l.getLender().equals(s));
	}

	@Test(expected = BadParametersException.class)
	public void testNullLoan() throws BadParametersException, TooManyLoansException,
			LentBookException {
		new Loan(null, null);
		/* new Loan(null, new Subscriber("Mayte", "Segarra", new GregorianCalendar()));
		 * ArrayList<String> authors = new ArrayList<String>();
		 * authors.add("Corral");
		 * new Loan(new Book("La nuit", authors, new GregorianCalendar(), "026765415"), null);
		 */
	}

	@Test
	public void testReturnBook() throws BadParametersException, TooManyLoansException,
			LentBookException {
		l = new Loan(b, s);
		l.returnBook();
		assertTrue(l.getBook() == null);
		assertTrue(l.getLender() == null);
		assertTrue(l.getReturnDate() == null);
	}

}
