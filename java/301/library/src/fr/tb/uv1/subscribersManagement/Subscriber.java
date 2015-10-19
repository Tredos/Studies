/*
 * Class representing a subscriber of a library
 * A subscriber knows his first and last name,
 * his born date and his number in the library
 * @author M.T. Segarra
 * @version 0.0.1
 */
package fr.tb.uv1.subscribersManagement;

import java.util.*;
import fr.tb.uv1.exceptions.*;
import fr.tb.uv1.loansManagement.Loan;
import fr.tb.uv1.main.Constraints;

public class Subscriber {

	/**
	 * @uml.property name="number"
	 */
	private long number;

	/**
	 * Getter of the property <tt>number</tt>
	 * 
	 * @return Returns the number.
	 * @uml.property name="number"
	 */
	public long getNumber() {
		return number;
	}

	/**
	 * @uml.property name="numberCreated"
	 */
	private static long numberCreated;
	
	/**
	 * @uml.property name="firstName"
	 */
	private String firstName;

	/**
	 * Getter of the property <tt>firstName</tt>
	 * 
	 * @return Returns the firstName.
	 * @uml.property name="firstName"
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Setter of the property <tt>firstName</tt>
	 * 
	 * @param firstName
	 *            The firstName to set.
	 * @uml.property name="firstName"
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @uml.property name="lastName"
	 */
	private String lastName;

	/**
	 * Getter of the property <tt>lastname</tt>
	 * 
	 * @return Returns the lastname.
	 * @uml.property name="lastName"
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Setter of the property <tt>lastname</tt>
	 * 
	 * @param lastname
	 *            The lastname to set.
	 * @uml.property name="lastName"
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @uml.property name="bornDate"
	 */
	private Calendar bornDate;

	/**
	 * Getter of the property <tt>bornDate</tt>
	 * 
	 * @return Returns the bornDate.
	 * @uml.property name="bornDate"
	 */
	public Calendar getBornDate() {
		return bornDate;
	}

	/**
	 * Setter of the property <tt>bornDate</tt>
	 * 
	 * @param bornDate
	 *            The bornDate to set.
	 * @uml.property name="bornDate"
	 */
	public void setBornDate(Calendar bornDate) {
		this.bornDate = bornDate;
	}

	/** 
	 * @uml.property name="currentLoans"
	 * @uml.associationEnd readOnly="true" multiplicity="(0 -1)" ordering="true" inverse="lender:java.util.ArrayList"
	 */
	private ArrayList<Loan> currentLoans;

	/** 
	 * Getter of the property <tt>currentLoans</tt>
	 * @return Returns the currentLoans.
	 * @uml.property name="currentLoans"
	 */
	public ArrayList<Loan> getCurrentLoans() {
		return currentLoans;
	}

	/**
	 * @param subsNumber
	 *            the number of the subscriber to create
	 */
	public Subscriber(long subsNumber) {
		this.number = subsNumber;
		this.currentLoans = new ArrayList<Loan>();
	}

	/**
	 * Constructor of a subscriber Generates the number of the subscriber
	 * 
	 * @params firstName first name of the subscriber
	 * @params lastName last name of the subscriber
	 * @params bornDate born date of the subscriber
	 * @throws BadParametersException
	 */
	public Subscriber(String firstName, String lastName, Calendar bornDate)
			throws BadParametersException {
		if ((firstName == null) || (lastName == null) || (bornDate == null))
			throw new BadParametersException();
		this.firstName = firstName;
		this.lastName = lastName;
		this.bornDate = bornDate;
		this.number = numberCreated++;
		
		this.currentLoans = new ArrayList<Loan>();
	}

	/**
	 * @return true if the subscriber has loans
	 */
	public boolean existingLoans() {
		return currentLoans.size() > 0;
	}

	/**
	 * Add a new loan to the subscriber if quota ok
	 * 
	 * @param loan
	 *            the loan to add to the subscriber
	 * @throws TooManyLoansException
	 * @throws BadParametersException
	 */
	public void lend(Loan loan) throws TooManyLoansException,
			BadParametersException {
		if (loan == null)
			throw new BadParametersException();

		if (!canLend())
			throw new TooManyLoansException();

		currentLoans.add(loan);
	}

	/**
	 * @return true if the subscriber can lend books
	 */
	public boolean canLend() {
		return currentLoans.size() < Constraints.maxLOANS;
	}

	/**
	 * Remove the loan in parameter from the list of current loans of the
	 * subscriber
	 * 
	 * @param loan
	 *            the loan to be finished
	 * @throws BadParametersException
	 * @throws LentBookException
	 *             if loan not found in the list
	 */
	public void returnBook(Loan loan) throws BadParametersException,
			LentBookException {
		if (loan == null)
			throw new BadParametersException();

		if (!currentLoans.remove(loan))
			throw new LentBookException("The lender does not contain the loan");
	}

	/*
	 * Decides if the subscriber object (parameter) is the same subscriber as
	 * this one Same first name, last name, and born date
	 * 
	 * @param subscriber object to be compared with this one
	 * 
	 * @return true if parameter object and this one are equal
	 * 
	 * @return false if parameter object is null or different from this one
	 */
	@Override
	public boolean equals(Object subscriber) {
		if (!(subscriber instanceof Subscriber))
			return false;
		Subscriber s = (Subscriber) subscriber;

		if (s.number == number)
			return true;

		if ((s.firstName == null) || (s.lastName == null)
				|| (s.bornDate == null))
			return false;

		boolean res = (s.firstName.equals(firstName))
				&& (s.lastName.equals(lastName))
				&& (s.bornDate.equals(bornDate));

		return res;
	}
	/*
	 * Returns a description of the subscriber
	 */
	public String toString() {
		return this.number + " " + this.firstName + ", " + this.lastName;
	}
}