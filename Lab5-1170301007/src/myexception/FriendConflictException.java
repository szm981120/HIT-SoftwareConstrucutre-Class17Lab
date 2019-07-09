package myexception;

/**
 * There are at least two friends with same names in data file.
 * 
 * @author Shen
 *
 */
public class FriendConflictException extends Exception {

  private static final long serialVersionUID = 1L;

  public FriendConflictException() {
    super();
  }

  public FriendConflictException(String message) {
    super(message);
  }

  public FriendConflictException(String message, Throwable cause) {
    super(message, cause);
  }

  public FriendConflictException(Throwable cause) {
    super(cause);
  }

}
