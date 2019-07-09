package myexception;

/**
 * This exception is about data in file may doesn't fit syntax in specification. When this exception
 * happens, it means some components in format data fail to match regular expression.
 * 
 * @author Shen
 *
 */
public class DataSyntaxException extends Exception {

  private static final long serialVersionUID = 1L;

  public DataSyntaxException() {
    super();
  }

  public DataSyntaxException(String message) {
    super(message);
  }

  public DataSyntaxException(String message, Throwable cause) {
    super(message, cause);
  }

  public DataSyntaxException(Throwable cause) {
    super(cause);
  }

}
