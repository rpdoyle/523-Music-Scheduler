/**
 * Thrown when input Excel files have formatting errors
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
