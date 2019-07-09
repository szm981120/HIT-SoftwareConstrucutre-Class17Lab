package myexception;

public class NonexistentFriendInSocialTieException extends Exception {

  private static final long serialVersionUID = 1L;

  public NonexistentFriendInSocialTieException() {
    super();
  }

  public NonexistentFriendInSocialTieException(String message) {
    super(message);
  }

  public NonexistentFriendInSocialTieException(String message, Throwable cause) {
    super(message, cause);
  }

  public NonexistentFriendInSocialTieException(Throwable cause) {
    super(cause);
  }

}
