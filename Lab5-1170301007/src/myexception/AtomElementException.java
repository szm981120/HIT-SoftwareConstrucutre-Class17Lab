package myexception;

/**
 * This exception is about nucleus element in atom structure. This includes that element of atom
 * contains 0 or more than 2 characters, the first character is not a upper case character, or the
 * second character is not a lower case character.
 * 
 * @author Shen
 *
 */
public class AtomElementException extends Exception {

  private static final long serialVersionUID = 1L;

  public AtomElementException() {
    super();
  }

  public AtomElementException(String message) {
    super(message);
  }

  public AtomElementException(String message, Throwable cause) {
    super(message, cause);
  }

  public AtomElementException(Throwable cause) {
    super(cause);
  }

}
