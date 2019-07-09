package MyException;

/**
 * Deal with some exception when a more than 10000 number doesn't use scientific
 * notation.
 * 
 * @author Shen
 *
 */
public class DataNonScientificNumberException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DataNonScientificNumberException() {
		super();
	}

	public DataNonScientificNumberException(String message) {
		super(message);
	}

	public DataNonScientificNumberException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataNonScientificNumberException(Throwable cause) {
		super(cause);
	}

}
