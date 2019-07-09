package MyException;

public class PlanetConflictException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PlanetConflictException() {
		super();
	}

	public PlanetConflictException(String message) {
		super(message);
	}

	public PlanetConflictException(String message, Throwable cause) {
		super(message, cause);
	}

	public PlanetConflictException(Throwable cause) {
		super(cause);
	}

}
