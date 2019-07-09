package myexception;

/**
 * This exception is about electron number in atom structure. This includes that data doesn't
 * indicate electron number for some tracks, number of track doesn't match the number of electron.
 * 
 * @author Shen
 *
 */
public class AtomElectronNumException extends Exception {

  private static final long serialVersionUID = 1L;

  public AtomElectronNumException() {
    super();
  }

  public AtomElectronNumException(String message) {
    super(message);
  }

  public AtomElectronNumException(String message, Throwable cause) {
    super(message, cause);
  }

  public AtomElectronNumException(Throwable cause) {
    super(cause);
  }

}
