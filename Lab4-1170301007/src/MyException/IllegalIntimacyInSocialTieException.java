package MyException;

/**
 * This exception may happens only in social network circle. When this exception
 * happens, it means the intimacy of a social tie is no more than 0 or greater
 * than 1.
 * 
 * @author Shen
 *
 */
public class IllegalIntimacyInSocialTieException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IllegalIntimacyInSocialTieException() {
		super();
	}

	public IllegalIntimacyInSocialTieException(String message) {
		super(message);
	}

	public IllegalIntimacyInSocialTieException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalIntimacyInSocialTieException(Throwable cause) {
		super(cause);
	}

}
