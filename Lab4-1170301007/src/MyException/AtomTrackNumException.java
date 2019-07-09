package MyException;

/**
 * This exception is about track number in atom structure. The number of track
 * is not positive integer in data file.
 * 
 * @author Shen
 *
 */
public class AtomTrackNumException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AtomTrackNumException() {
		super();
	}

	public AtomTrackNumException(String message) {
		super(message);
	}

	public AtomTrackNumException(String message, Throwable cause) {
		super(message, cause);
	}

	public AtomTrackNumException(Throwable cause) {
		super(cause);
	}

}
