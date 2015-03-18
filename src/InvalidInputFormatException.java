/*
 * File: InvalidInputFormatException.java
 * Description: This class extends Exception and allows us to
 * 				handle formatting errors related to user-inputed
 * 				excel documents more specifically.
 */

public class InvalidInputFormatException extends Exception {
	private static final long serialVersionUID = 1L;

	InvalidInputFormatException() {
		super();
	}
	
	InvalidInputFormatException (String s) {
		super(s);
	}	  
}
