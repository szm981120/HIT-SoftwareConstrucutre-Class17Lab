package MyException;

public class NoObjectOnTrackException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoObjectOnTrackException() {
		super();
	}

	public NoObjectOnTrackException(String message) {
		super(message);
	}

	public NoObjectOnTrackException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoObjectOnTrackException(Throwable cause) {
		super(cause);
	}
}
