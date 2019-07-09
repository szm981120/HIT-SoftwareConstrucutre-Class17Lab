package monkeycrossriver;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogHandler {

  // Thread safety arguments:
  // - the static method is declared synchronized.
  // - in method write, there's a lock on logger so there's only one thread access to method write.
  /**
   * Make log writing a synchronized method. When this method is called, the file handler is
   * created. After log writing is done, the file handler is closed. So there shouldn't be a clk
   * file.
   * 
   * @param logger  Logger instance
   * @param message log message
   */
  public static synchronized void write(Logger logger, String message) {
    synchronized (logger) {
      FileHandler handler = null;
      try {
        handler = new FileHandler("log/crossLog.log", true);
      } catch (SecurityException | IOException e) {
        e.printStackTrace();
      }
      handler.setFormatter(new SimpleFormatter());
      logger.addHandler(handler);
      logger.log(Level.INFO, message);
      handler.close();
    }

  }
}
