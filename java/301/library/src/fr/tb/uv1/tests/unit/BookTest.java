/*
 * @author M.T. Segarra
 * @version 0.0.1
 */
package fr.tb.uv1.tests.unit;

import static org.junit.Assert.*;
import org.junit.*;
import java.util.*;

import fr.tb.uv1.booksManagement.Book;
import fr.tb.uv1.exceptions.*;
import fr.tb.uv1.loansManagement.Loan;
import fr.tb.uv1.subscribersManagement.Subscriber;

public class BookTest {

	private Subscriber s;
	private Book b;
	private ArrayList<String> authors;
	
	@Before
	public void beforeTests() throws BadParametersException {
		s = new Subscriber("Mayte", "Segarra", new GregorianCalendar());
		
		authors = new ArrayList<String>();
		authors.add("Corral");
		b = new Book("Noche", authors, new GregorianCalendar(), "026765415");
	}
	@Test
	public void testEqualsObject() throws BadParametersException {
		Book b2 = new Book("Noche", authors, new GregorianCalendar(), "026765415");
		assertTrue(b.equals(b2));
		
		b2 = new Book("Noche", authors, new GregorianCalendar(), "020178517");
		assertFalse(b.equals(b2));
		
		b2 = new Book("El dia", authors, new GregorianCalendar(), "026765415");
		assertTrue(b.equals(b2));
		
		
	}

	@Test
	public void testOKBook() throws BadParametersException {
		new Book("Noche", authors, new GregorianCalendar(), "020178515");
	}
	
	@Test(expected = BadParametersException.class)
	public void testNullBook() throws BadParametersException{
		new Book("Noche", authors, new GregorianCalendar(), null);
		/*authors = null;
		new Book(null, authors, new GregorianCalendar(), "020178515");
		new Book("Noche", authors, null, "020178515");
		new Book("Noche", authors, new GregorianCalendar(), null);*/
	}
	
	@Test
	public void testLendBook() throws BadParametersException, 
	TooManyLoansException, LentBookException{
		new Loan(b,s);
		assertTrue(b.getLoan() == null);
	}
	
	@Test(expected = BadParametersException.class)
	public void testBadLendBook() throws BadParametersException, 
	TooManyLoansException, LentBookException{
		b.lend(null);
	}
	
	@Test(expected = LentBookException.class)
	public void testLendLentBook() throws BadParametersException, 
	TooManyLoansException, LentBookException{
		new Loan(b,s);
		s = new Subscriber("Mayte", "Seg", new GregorianCalendar());
		
		new Loan(b,s);
	}
	
	@Test
	public void testReturnBook() throws BadParametersException, 
	TooManyLoansException, LentBookException{
		new Loan(b,s);
		
		b.returnBook();
		assertTrue(b.getLoan() == null);
	}
	
	@Test
	public void testIslent() throws BadParametersException, 
	TooManyLoansException, LentBookException{
		new Loan(b,s);

		assertTrue(b.isLent());
		
		b.returnBook();
		assertFalse(b.isLent());
	}
}
