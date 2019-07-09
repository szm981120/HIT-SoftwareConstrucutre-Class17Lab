package myexception;

/**
 * When calculate information diffusivity in social network circle, the intermediary that user
 * inputs may not exist.
 * 
 * @author Shen
 *
 */
public class IntermediaryNotExistException extends Exception {

  private static final long serialVersionUID = 1L;

  public IntermediaryNotExistException() {
    super();
  }

  public IntermediaryNotExistException(String message) {
    super(message);
  }

  public IntermediaryNotExistException(String message, Throwable cause) {
    super(message, cause);
  }

  public IntermediaryNotExistException(Throwable cause) {
    super(cause);
  }

}
