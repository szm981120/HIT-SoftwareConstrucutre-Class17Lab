package MyException;

/**
 * When roll back transition version, the number of versions rolled back may be
 * greater than the total of versions.
 * 
 * @author Shen
 *
 */
public class MementoRollBackTooManyStepsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MementoRollBackTooManyStepsException() {
		super();
	}

	public MementoRollBackTooManyStepsException(String message) {
		super(message);
	}

	public MementoRollBackTooManyStepsException(String message, Throwable cause) {
		super(message, cause);
	}

	public MementoRollBackTooManyStepsException(Throwable cause) {
		super(cause);
	}

}
