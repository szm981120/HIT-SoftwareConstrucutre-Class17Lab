package application;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Main.
 * 
 * @author Shen
 *
 */
public class Main {

  private static Scanner in = new Scanner(System.in);

  private static Logger atomStructureLogger = Logger.getLogger("AtomStructure Log");

  private static Logger stellarSystemLogger = Logger.getLogger("StellarSystem Log");

  private static Logger socialNetworkCircleLogger = Logger.getLogger("SocialNetworkCircle Log");

  /**
   * main.
   * 
   * @param args no meanings
   */
  public static void main(String[] args) {
    Locale.setDefault(new Locale("en", "EN"));
    menu();
    atomStructureLogger.setLevel(Level.INFO);
    stellarSystemLogger.setLevel(Level.INFO);
    socialNetworkCircleLogger.setLevel(Level.INFO);
    FileHandler atomStructureHandler = null;
    FileHandler stellarSystemHandler = null;
    FileHandler socialNetworkCircleHandler = null;
    try {
      atomStructureHandler = new FileHandler("log/atomStructureLog.log", true);
      stellarSystemHandler = new FileHandler("log/stellarSystemLog.log", true);
      socialNetworkCircleHandler = new FileHandler("log/socialNetworkCircleLog.log", true);
    } catch (SecurityException e) {
      e.printStackTrace();
      return;
    } catch (IOException e) {
      e.printStackTrace();
      return;
    }
    atomStructureHandler.setFormatter(new SimpleFormatter());
    stellarSystemHandler.setFormatter(new SimpleFormatter());
    socialNetworkCircleHandler.setFormatter(new SimpleFormatter());
    atomStructureLogger.addHandler(atomStructureHandler);
    stellarSystemLogger.addHandler(stellarSystemHandler);
    socialNetworkCircleLogger.addHandler(socialNetworkCircleHandler);
    int choose = 0;
    try {
      choose = in.nextInt();
      in.nextLine();
    } catch (InputMismatchException e) {
      System.out.println("Input mismatch. Please run again.");
      return;
    }
    switch (choose) {
      case 1:
        atomStructureLogger.info("AtomStructure application activate.");
        AtomStructureApp.application();
        break;
      case 2:
        stellarSystemLogger.info("StellarSystem application activate.");
        StellarSystemApp.application();
        break;
      case 3:
        socialNetworkCircleLogger.info("SocialNetworkCircle application activate.");
        SocialNetworkCircleApp.application();
        break;
      default:
        break;
    }
    atomStructureHandler.close();
    stellarSystemHandler.close();
    socialNetworkCircleHandler.close();
  }

  /**
   * Menu which indicates user's choices on what kind of circular orbit.
   */
  private static void menu() {
    System.out.println("1. Atom Structure.");
    System.out.println("2. Stellar System.");
    System.out.println("3. Social Network Circle.");
  }
}
