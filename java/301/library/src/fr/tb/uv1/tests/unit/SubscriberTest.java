/*
 * @author M.T. Segarra
 * @version 0.0.1
 */
package fr.tb.uv1.tests.unit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;
import fr.tb.uv1.booksManagement.Book;
import fr.tb.uv1.exceptions.BadParametersException;
import fr.tb.uv1.exceptions.LentBookException;
import fr.tb.uv1.exceptions.TooManyLoansException;
import fr.tb.uv1.loansManagement.Loan;
import fr.tb.uv1.main.Constraints;
import fr.tb.uv1.subscribersManagement.Subscriber;

public class SubscriberTest {

	private Subscriber s;

	@Before
	public void beforeTests() throws BadParametersException {
		this.s = new Subscriber("Mayte", "Segarra", new GregorianCalendar());
	}

	@Test
	public void testCanLend() {
		assertTrue(this.s.canLend());
	}

	@Test
	public void testEqualsObject() throws BadParametersException {

		// Compare subscribers with data
		Subscriber s2 = new Subscriber("Mayte", "Segarra",
				new GregorianCalendar());
		assertTrue(this.s.equals(s2));

		s2 = new Subscriber("Mayte", "Seg", new GregorianCalendar());
		assertFalse(this.s.equals(s2));

		s2 = new Subscriber("MTS", "Segarra", new GregorianCalendar());
		assertFalse(this.s.equals(s2));

		s2 = new Subscriber("Mayte", "Segarra", new GregorianCalendar(2000, 12,
				13));
		assertFalse(this.s.equals(s2));

		// Compare subscribers with their number
		this.s = new Subscriber(3);
		s2 = new Subscriber(3);
		assertTrue(this.s.equals(s2));

		this.s = new Subscriber(4);
		assertFalse(this.s.equals(s2));
	}

	@Test
	public void testExistingLoans() throws BadParametersException,
			TooManyLoansException, LentBookException {

		assertFalse(this.s.existingLoans());

		ArrayList<String> authors = new ArrayList<String>();
		Book b = new Book("La noche", authors, new GregorianCalendar(),
				"23456566");
		new Loan(b, this.s);

		assertTrue(this.s.existingLoans());
	}

	@Test
	public void testLend() throws BadParametersException,
			TooManyLoansException, LentBookException {

		ArrayList<String> authors = new ArrayList<String>();
		Book b = new Book("La noche", authors, new GregorianCalendar(),
				"23456566");

		new Loan(b, this.s);
		assertTrue(this.s.getCurrentLoans().size() == 1);
	}

	@Test(expected = BadParametersException.class)
	public void testNullSubscriber() throws BadParametersException {
		new Subscriber("Mayte", "Segarra", null);
		/*
		 * new Subscriber("Mayte", null, new GregorianCalendar()); new
		 * Subscriber(null, "Segarra", new GregorianCalendar());
		 */
	}

	@Test
	public void testOKSubscriber() throws BadParametersException {
		Subscriber s = new Subscriber("Mayte", "Segarra",
				new GregorianCalendar());
		assertTrue(s.getCurrentLoans().size() == 0);
	}

	@Test
	public void testSubscriberLong() {
		this.s = new Subscriber(3);
		assertTrue(this.s.getCurrentLoans().size() == 0);
	}

	@Test
	public void testReturnBook() throws BadParametersException,
			TooManyLoansException, LentBookException {
		ArrayList<String> authors = new ArrayList<String>();
		Book b = new Book("La noche", authors, new GregorianCalendar(),
				"23456566");
		Loan l = new Loan(b, this.s);

		this.s.returnBook(l);
		assertFalse(this.s.existingLoans());
	}

	@Test(expected = TooManyLoansException.class)
	public void testTooManyLoans() throws BadParametersException,
			TooManyLoansException, LentBookException {

		Book b = null;
		Loan l = null;
		ArrayList<String> authors = new ArrayList<String>();
		for (int i = 1; i <= Constraints.maxLOANS; i++) {
			b = new Book("La noche "+i, authors, new GregorianCalendar(),
					"23456566");
			l = new Loan(b, this.s);
			this.s.lend(l);
		}
		assertTrue(this.s.getCurrentLoans().size() == Constraints.maxLOANS);
		
		b = new Book("Luna", authors, new GregorianCalendar(), "23457766");
		l = new Loan(b, this.s);
		this.s.lend(l);
	}
}
