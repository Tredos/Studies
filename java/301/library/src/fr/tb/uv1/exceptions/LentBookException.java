/*
 * Exception raised when trying to lent a
 * book already lent
 * @author M.T. Segarra
 * @version 0.0.1
 */
package fr.tb.uv1.exceptions;

public class LentBookException extends Exception {

	private static final long serialVersionUID = 1L;

	public LentBookException(String msg) {
		super(msg);
	}

}
