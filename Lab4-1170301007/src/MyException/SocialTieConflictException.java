package MyException;

public class SocialTieConflictException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SocialTieConflictException() {
		super();
	}

	public SocialTieConflictException(String message) {
		super(message);
	}

	public SocialTieConflictException(String message, Throwable cause) {
		super(message, cause);
	}

	public SocialTieConflictException(Throwable cause) {
		super(cause);
	}

}
