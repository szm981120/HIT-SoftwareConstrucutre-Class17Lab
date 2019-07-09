package myexception;

/**
 * This exception is about illegal scientifc number notation. This includes that the mantissa is not
 * between 1 and 10 or the exponent is less than 4.
 * 
 * @author Shen
 *
 */
public class DataScientificNumberException extends Exception {

  private static final long serialVersionUID = 1L;

  public DataScientificNumberException() {
    super();
  }

  public DataScientificNumberException(String message) {
    super(message);
  }

  public DataScientificNumberException(String message, Throwable cause) {
    super(message, cause);
  }

  public DataScientificNumberException(Throwable cause) {
    super(cause);
  }

}
