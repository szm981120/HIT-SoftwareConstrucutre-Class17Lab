package myexception;

/**
 * Planet's degree is out of range [0,360).
 * 
 * @author Shen
 *
 */
public class DegreeOutOfRangeException extends Exception {

  private static final long serialVersionUID = 1L;

  public DegreeOutOfRangeException() {
    super();
  }

  public DegreeOutOfRangeException(String message) {
    super(message);
  }

  public DegreeOutOfRangeException(String message, Throwable cause) {
    super(message, cause);
  }

  public DegreeOutOfRangeException(Throwable cause) {
    super(cause);
  }

}
