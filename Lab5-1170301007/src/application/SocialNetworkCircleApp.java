package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import api.CircularOrbitApis;
import api.CircularOrbitHelper;
import centralobject.Person;
import circularorbit.CircularOrbit;
import circularorbit.CircularOrbitFactory;
import circularorbit.Edge;
import circularorbit.SocialNetworkCircleFactory;
import iostrategy.SocialNetworkCircleFileReader;
import iostrategy.SocialNetworkCircleIoContext;
import iostrategy.SocialNetworkCircleIoFileStream;
import iostrategy.SocialNetworkCircleNio;
import myexception.DataSyntaxException;
import myexception.IllegalIntimacyInSocialTieException;
import myexception.IntermediaryNotExistException;
import myexception.NoObjectOnTrackException;
import physicalobject.ConcreteFriendFactory;
import physicalobject.FriendFactory;
import physicalobject.PhysicalObject;
import track.Track;
import track.TrackFactory;

/**
 * SocialNetworkCircleAPP provides a static method for client to call in Main.java. This static
 * method is the mainly function method for social network circle applications.
 * 
 * @author Shen
 *
 */
public class SocialNetworkCircleApp {

  private static FriendFactory friendFactory = new ConcreteFriendFactory();

  private static TrackFactory trackFactory = new TrackFactory();

  private static Scanner in = new Scanner(System.in);

  private static Pattern pattern;

  private static Matcher matcher;

  private static final String LABEL_REGEX = "([a-zA-Z0-9]*)";

  private static final String COMMA_REGEX = "\\s*,\\s*";

  private static Logger socialNetworkCircleLogger = Logger.getLogger("SocialNetworkCircle Log");

  /**
   * Social network circle application method.
   */
  public static void application() {
    CircularOrbitApis<Person, PhysicalObject> apis =
        new CircularOrbitApis<Person, PhysicalObject>();
    CircularOrbitFactory<Person, PhysicalObject> factory = new SocialNetworkCircleFactory();
    CircularOrbit<Person, PhysicalObject> socialNetworkCircle = factory.produce();
    socialNetworkCircleLogger.setUseParentHandlers(false);
    SocialNetworkCircleIoContext fileReaderContext =
        new SocialNetworkCircleIoContext(new SocialNetworkCircleFileReader());
    SocialNetworkCircleIoContext ioStreamReaderContext =
        new SocialNetworkCircleIoContext(new SocialNetworkCircleIoFileStream());
    SocialNetworkCircleIoContext nioContext =
        new SocialNetworkCircleIoContext(new SocialNetworkCircleNio());
    while (true) {
      menu();
      int choose = 0;
      try {
        choose = in.nextInt();
        in.nextLine();
      } catch (InputMismatchException e) {
        System.err.println(e.getClass() + " Please input again.");
        in.nextLine();
        continue;
      }
      switch (choose) {
        case 1: // Read from file to generate a social network circle
          ioStrategy();
          int ioStrategy = 0;
          try {
            ioStrategy = in.nextInt();
            in.nextLine();
          } catch (InputMismatchException e) {
            System.err.println(e.getClass() + " Please input again.");
            in.nextLine();
            continue;
          }
          readMenu();
          File file = null;
          int choose1 = 0;
          try {
            choose1 = in.nextInt();
            in.nextLine();
          } catch (InputMismatchException e) {
            System.err.println(e.getMessage() + "Please run again.");
            in.nextLine();
            break;
          }
          try {
            switch (choose1) {
              case 1:
                socialNetworkCircleLogger.info("Read from input/SocialNetworkCircle.txt");
                file = new File("input/SocialNetworkCircle.txt");
                // socialNetworkCircle.readFromFile(file);
                fileReaderContext.readFromFile(socialNetworkCircle, file);
                socialNetworkCircleLogger.info("Success: Read from input/SocialNetworkCircle.txt");
                break;
              case 2:
                socialNetworkCircleLogger.info("Read from input/SocialNetworkCircle_Medium.txt");
                file = new File("input/SocialNetworkCircle_Medium.txt");
                // socialNetworkCircle.readFromFile(file);
                fileReaderContext.readFromFile(socialNetworkCircle, file);
                socialNetworkCircleLogger
                    .info("Success: Read from input/SocialNetworkCircle_Medium.txt");
                break;
              case 3:
                socialNetworkCircleLogger.info("Read from input/SocialNetworkCircle_Larger.txt");
                file = new File("input/SocialNetworkCircle_Larger.txt");
                // socialNetworkCircle.readFromFile(file);
                fileReaderContext.readFromFile(socialNetworkCircle, file);
                socialNetworkCircleLogger
                    .info("Success: Read from input/SocialNetworkCircle_Larger.txt");
                break;
              case 4:
                System.out.println("Please input absolute file path.");
                String absolutePath = in.nextLine();
                file = new File(absolutePath);
                // socialNetworkCircle.readFromFile(file);
                fileReaderContext.readFromFile(socialNetworkCircle, file);
                socialNetworkCircleLogger
                    .info("Success: Read from " + absolutePath + ". Restore present state.");
                break;
              case 5:
                socialNetworkCircleLogger.log(Level.INFO,
                    "Read from Lab5_input/SocialNetworkCircle.txt.");
                file = new File("Lab5_input/SocialNetworkCircle.txt");
                // stellarSystem.readFromFile(file);
                switch (ioStrategy) {
                  case 1:
                    fileReaderContext.readFromFile(socialNetworkCircle, file);
                    break;
                  case 2:
                    ioStreamReaderContext.readFromFile(socialNetworkCircle, file);
                    break;
                  case 3:
                    nioContext.readFromFile(socialNetworkCircle, file);
                    break;
                  default:
                    break;
                }
                socialNetworkCircleLogger.log(Level.INFO,
                    "Success: Read from Lab5_input/SocialNetworkCircle.txt.");
                break;
              default:
                System.err.println("Wrong input");
                break;
            }
          } catch (FileNotFoundException e) {
            socialNetworkCircleLogger.log(Level.SEVERE,
                "File not found. File: " + file.getPath() + ". " + e.getMessage(), e);
            System.err.println("File not found. File: " + file.getPath() + ". " + e.getMessage());
          } catch (DataSyntaxException e) {
            socialNetworkCircleLogger.log(Level.SEVERE, e.getMessage(), e);
            System.err.println(e.getMessage());
          } catch (IllegalIntimacyInSocialTieException e) {
            socialNetworkCircleLogger.log(Level.SEVERE, e.getMessage(), e);
            System.err.println(e.getMessage());
          } catch (IOException e) {
            e.printStackTrace();
          } catch (NumberFormatException e) {
            e.printStackTrace();
          }
          break;
        case 2: // Visualize
          socialNetworkCircleLogger.info("Visualize.");
          CircularOrbitHelper.visualize(socialNetworkCircle);
          socialNetworkCircleLogger.info("Success: Visualize.");
          break;
        case 3: // Add a track
          socialNetworkCircleLogger.info("Add a track.");
          System.out.println("What's the radius(integer) of the added track?");
          int addTrackRadius = 0;
          try {
            addTrackRadius = in.nextInt();
            in.nextLine();
          } catch (InputMismatchException e) {
            System.err.println(e.getMessage() + "Please run again.");
            in.nextLine();
            break;
          }
          socialNetworkCircle.addTrack(trackFactory.produce((double) addTrackRadius));
          socialNetworkCircleLogger.info("Success: Add a " + addTrackRadius + " track.");
          break;
        case 4: // Add an object to a track
          socialNetworkCircleLogger.info("Add an object to a track.");
          System.out.println("Please input information of the added friend in order below:");
          System.out.println("Friend ::= <NAME,AGE,SEX>");
          System.out.println("All data must obey the specification.");
          final String addData = in.nextLine();
          System.out.println("What's the radius(decimal) of the target track?");
          int addRadius = 0;
          try {
            addRadius = in.nextInt();
            in.nextLine();
          } catch (InputMismatchException e) {
            System.err.println(e.getMessage() + "Please run again.");
            in.nextLine();
            break;
          }
          pattern = Pattern.compile("Friend\\s*::=\\s*<" + LABEL_REGEX + COMMA_REGEX + "(\\d*)"
              + COMMA_REGEX + "([M|F]{1})" + ">");
          matcher = pattern.matcher(addData);
          boolean find = matcher.find();
          if (find) {
            String name = matcher.group(1);
            int age = Integer.valueOf(matcher.group(2));
            char sex = matcher.group(3).charAt(0);
            socialNetworkCircle.addPhysicalObjectToTrack(friendFactory.produce(name, age, sex),
                trackFactory.produce((double) addRadius));
            socialNetworkCircleLogger
                .info("Success: Add a friend " + name + " to " + addRadius + " track.");
          } else {
            socialNetworkCircleLogger.log(Level.SEVERE, "Friend syntax doesn't match!");
            System.err.println("Friend syntax doesn't match!");
            break;
          }
          break;
        case 5: // Delete a track
          socialNetworkCircleLogger.info("Delete a track.");
          System.out.println("What's the radius(integer) of the deleted track?");
          int deleteTrackRadius = 0;
          try {
            deleteTrackRadius = in.nextInt();
            in.nextLine();
          } catch (InputMismatchException e) {
            System.err.println(e.getMessage() + "Please run again.");
            in.nextLine();
            break;
          }
          socialNetworkCircle.deleteTrack(trackFactory.produce((double) deleteTrackRadius));
          socialNetworkCircleLogger.info("Success: Delete a " + deleteTrackRadius + " track.");
          break;
        case 6: // Delete an object in a track
          socialNetworkCircleLogger.info("Delete an object in a track.");
          System.out.println("Please input information of the deleted friend in order below:");
          System.out.println("Friend ::= <NAME,AGE,SEX>");
          System.out.println("All data must obey the specification.");
          final String deleteData = in.nextLine();
          System.out.println("What's the radius(integer) of the target track?");
          int deleteRadius = 0;
          try {
            deleteRadius = in.nextInt();
            in.nextLine();
          } catch (InputMismatchException e) {
            System.err.println(e.getMessage() + "Please run again.");
            in.nextLine();
            break;
          }
          pattern = Pattern.compile("Friend\\s*::=\\s*<" + LABEL_REGEX + COMMA_REGEX + "(\\d*)"
              + COMMA_REGEX + "([M|F]{1})" + ">");
          matcher = pattern.matcher(deleteData);
          boolean find1 = matcher.find();
          if (find1) {
            String name = matcher.group(1);
            int age = Integer.valueOf(matcher.group(2));
            char sex = matcher.group(3).charAt(0);
            try {
              socialNetworkCircleLogger
                  .info("Success: Delete friend " + name + " from " + deleteRadius + " track.");
              socialNetworkCircle.deletePhysicalObjectFromTrack(
                  friendFactory.produce(name, age, sex),
                  trackFactory.produce((double) deleteRadius));
            } catch (NoObjectOnTrackException e) {
              socialNetworkCircleLogger.log(Level.SEVERE, e.getMessage(), e);
              System.err.println(e.getMessage());
              break;
            }
          } else {
            socialNetworkCircleLogger.log(Level.SEVERE, "Friend syntax doesn't match!");
            System.err.println("Friend syntax doesn't match!");
            break;
          }
          break;
        case 7: // Calculate the information entropy of the system
          socialNetworkCircleLogger.info("Calculate the information entropy of the system.");
          double entropy = apis.getObjectDistributionEntropy(socialNetworkCircle);
          System.out.println("Information entropy: " + entropy);
          socialNetworkCircleLogger
              .info("Success: Calculate the information entropy: " + entropy + ".");
          break;
        case 8: // Calculate information diffusivity of someone in the first track
          socialNetworkCircleLogger
              .info("Calculate information diffusivity of someone in the first track.");
          System.out.println("Information diffusivity: the number of whom can be aquainted "
              + "via someone in the first track by the center person.");
          System.out
              .println("Aquainted rule: The intimacy between intermediary and target should be"
                  + " no less than intimacy between center and intermediary.");
          System.out.println(
              "Please input information of intermediary in the first track in order below:");
          System.out.println("Friend ::= <NAME,AGE,SEX>");
          System.out.println("All data must obey the specification.");
          String intermediaryData = in.nextLine();
          int diffusivity = 0;
          PhysicalObject intermediary = null;
          boolean existIntermediary = false;
          pattern = Pattern.compile("Friend\\s*::=\\s*<" + LABEL_REGEX + COMMA_REGEX + "(\\d*)"
              + COMMA_REGEX + "([M|F]{1})" + ">");
          matcher = pattern.matcher(intermediaryData);
          boolean find2 = matcher.find();
          if (find2) {
            String name = matcher.group(1);
            int age = Integer.valueOf(matcher.group(2));
            char sex = matcher.group(3).charAt(0);
            intermediary = friendFactory.produce(name, age, sex);
          } else {
            socialNetworkCircleLogger.log(Level.SEVERE, "Intermediary syntax doesn't match!");
            System.err.println("Intermediary syntax doesn't match!");
            break;
          }
          for (PhysicalObject friend : socialNetworkCircle.getRelationBetweenCentralAndObject()
              .keySet()) {
            if (friend.equals(intermediary)) {
              existIntermediary = true;
              break;
            }
          }
          try {
            if (!existIntermediary) {
              throw new IntermediaryNotExistException("This intermediary doesn't exist!");
            }
          } catch (IntermediaryNotExistException e) {
            socialNetworkCircleLogger.log(Level.SEVERE, e.getMessage(), e);
            System.err.println(e.getMessage());
            break;
          }
          double intimacy = 0;
          intimacy = socialNetworkCircle.getRelationBetweenCentralAndObject().get(intermediary);
          for (Edge<PhysicalObject> edge : socialNetworkCircle.getRelationBetweenObjects()) {
            if (edge.getSource().equals(intermediary) && !socialNetworkCircle
                .getRelationBetweenCentralAndObject().keySet().contains(edge.getTarget())
                && edge.getWeight() <= intimacy) {
              diffusivity++;
            }
          }
          System.out
              .println("The diffusivity via " + intermediary.getName() + " is: " + diffusivity);
          socialNetworkCircleLogger.info("Success: Calculate diffusivity via "
              + intermediary.getName() + " is: " + diffusivity);
          break;
        case 9: // Add/Delete a social relationship
          socialNetworkCircleLogger.info("Add/Delete a social relationship.");
          System.out.println("Add(Y/y) or delete(N/n)?");
          String c = in.nextLine();
          if (!c.equalsIgnoreCase("Y") && !c.equalsIgnoreCase("N")) {
            System.err.println("Wrong input!");
            socialNetworkCircleLogger.log(Level.SEVERE, "Wrong input(Y/y N/n)");
            break;
          }
          System.out
              .println("The two friends in this relationship must have existed in the circle.");
          System.out.println(
              "Please input information of the social relationship information in order below:");
          System.out.println("SocialTie ::= <NAME1, NAME2, INTIMACY>");
          System.out.println("All data must obey the specification.");
          String socialTie = in.nextLine();
          pattern = Pattern.compile("SocialTie\\s*::=\\s*<" + LABEL_REGEX + COMMA_REGEX
              + LABEL_REGEX + COMMA_REGEX + "(0.\\d{1,3})>");
          matcher = pattern.matcher(socialTie);
          boolean find3 = matcher.find();
          if (find3) {
            double infimacy = Double.valueOf(matcher.group(3));
            String name1 = matcher.group(1);
            String name2 = matcher.group(2);
            boolean exist1 = false;
            boolean exist2 = false;
            PhysicalObject friend1 = null;
            PhysicalObject friend2 = null;
            for (Track track : socialNetworkCircle.getObjectsInTrack().keySet()) {
              for (PhysicalObject friend : socialNetworkCircle.getObjectsInTrack().get(track)) {
                if (friend.getName().equals(name1)) {
                  friend1 = friend;
                  exist1 = true;
                }
                if (friend.getName().equals(name2)) {
                  friend2 = friend;
                  exist2 = true;
                }
              }
            }
            if (!exist1 || !exist2) {
              System.err.println("Both friends must have existed!");
              socialNetworkCircleLogger.log(Level.SEVERE,
                  "Both friend in socialtie must have existed!");
              break;
            } else if (c.equalsIgnoreCase("Y")) {
              socialNetworkCircle.addRelationshipBetweenPhysicalAndPhysical(friend1, friend2,
                  infimacy);
            } else {
              socialNetworkCircle.deleteRelationshipBetweenPhysicalAndPhysical(friend1, friend2);
            }
          } else {
            socialNetworkCircleLogger.log(Level.SEVERE, "SocialTie syntax doesn't match!");
            System.err.println("SocialTie syntax doesn't match!");
            break;
          }
          Map<String, PhysicalObject> friends = new HashMap<String, PhysicalObject>();
          for (PhysicalObject friend : socialNetworkCircle) {
            friends.put(friend.getName(), friend);
          }
          socialNetworkCircle.resetObjectsAndTrack();
          socialNetworkCircle.constructSocialNetworkCircle(friends);
          socialNetworkCircleLogger.info("Success: Add/Delete a social relationship.");
          break;
        case 10: // Calculate logical distance of two friends
          socialNetworkCircleLogger.info("Calculate logical distance of two friends.");
          PhysicalObject friend1 = null;
          PhysicalObject friend2 = null;
          System.out.println("Please input information of friend 1 in order below:");
          System.out.println("Friend ::= <NAME,AGE,SEX>");
          System.out.println("All data must obey the specification.");
          String friend1Data = in.nextLine();
          pattern = Pattern.compile("Friend\\s*::=\\s*<" + LABEL_REGEX + COMMA_REGEX + "(\\d*)"
              + COMMA_REGEX + "([M|F]{1})" + ">");
          matcher = pattern.matcher(friend1Data);
          boolean find4 = matcher.find();
          if (find4) {
            String name = matcher.group(1);
            int age = Integer.valueOf(matcher.group(2));
            char sex = matcher.group(3).charAt(0);
            friend1 = friendFactory.produce(name, age, sex);
          } else {
            socialNetworkCircleLogger.log(Level.SEVERE, "Friend syntax doens't match!");
            System.err.println("Friend syntax doens't match!");
            break;
          }
          System.out.println("Please input information of friend 2 in order below:");
          System.out.println("Friend ::= <NAME,AGE,SEX>");
          System.out.println("All data must obey the specification.");
          String friend2Data = in.nextLine();
          pattern = Pattern.compile("Friend\\s*::=\\s*<" + LABEL_REGEX + COMMA_REGEX + "(\\d*)"
              + COMMA_REGEX + "([M|F]{1})" + ">");
          matcher = pattern.matcher(friend2Data);
          boolean find5 = matcher.find();
          if (find5) {
            String name = matcher.group(1);
            int age = Integer.valueOf(matcher.group(2));
            char sex = matcher.group(3).charAt(0);
            friend2 = friendFactory.produce(name, age, sex);
          } else {
            socialNetworkCircleLogger.log(Level.SEVERE, "Friend syntax doesn't match!");
            System.err.println("Friend syntax doens't match!");
            break;
          }
          System.out.println("The logical distance from friend 1 to friend 2: "
              + apis.getLogicalDistance(socialNetworkCircle, friend1, friend2));
          socialNetworkCircleLogger.info("Success: The logical distance from friend 1 to friend 2: "
              + apis.getLogicalDistance(socialNetworkCircle, friend1, friend2));
          break;
        case 11:
          apis.logSearch(new File("log/socialNetworkCircleLog.log"));
          break;
        case 12: // Check legality
          socialNetworkCircleLogger.info("Check legality.");
          for (int i = 1; i <= socialNetworkCircle.getTracks().size(); i++) {
            Track track = socialNetworkCircle.getTracks().get(i - 1);
            for (PhysicalObject friend : socialNetworkCircle.getObjectsInTrack().get(track)) {
              if (apis.getLogicalDistance(socialNetworkCircle,
                  friendFactory.produce("center", 1, 'M'), friend) != i) {
                System.err.println("The length of shortest path from " + friend.getName()
                    + " to center is: " + apis.getLogicalDistance(socialNetworkCircle,
                        friendFactory.produce("center", 1, 'M'), friend));
                System.err.println(friend.getName() + " is on the " + i + "-th track.");
                socialNetworkCircleLogger.log(Level.SEVERE,
                    "The length of shortest path from " + friend.getName() + " to center is: "
                        + apis.getLogicalDistance(socialNetworkCircle,
                            friendFactory.produce("center", 1, 'M'), friend)
                        + ". But " + friend.getName() + " is on the " + i + "-th track. "
                        + "If a friend in on the i-th track"
                        + ", then the shortest length from he/she to center must be i!");
                assert false : "If a friend in on the i-th track"
                    + ", then the shortest length from he/she to center must be i!";
              }
            }
          }
          socialNetworkCircleLogger.info("Legality checked!");
          break;
        case 13:
          socialNetworkCircleLogger.info("Write the current social network circle to file.");
          ioStrategy();
          try {
            ioStrategy = in.nextInt();
            in.nextLine();
          } catch (InputMismatchException e) {
            System.err.println(e.getClass() + " Please input again.");
            in.nextLine();
            continue;
          }
          System.out.println("Please input the path of the written file.");
          String writePath = in.nextLine();
          File writeFile = new File(writePath);
          switch (ioStrategy) {
            case 1:
              try {
                fileReaderContext.writeToFile(socialNetworkCircle, writeFile);
              } catch (IOException e) {
                e.printStackTrace();
              }
              break;
            case 2:
              try {
                ioStreamReaderContext.writeToFile(socialNetworkCircle, writeFile);
              } catch (IOException e) {
                e.printStackTrace();
              }
              break;
            case 3:
              try {
                nioContext.writeToFile(socialNetworkCircle, writeFile);
              } catch (IOException e) {
                e.printStackTrace();
              }
              break;
            default:
              break;
          }
          socialNetworkCircleLogger
              .info("Success: Write the current social network circle to " + writePath + ".");
          break;
        case 14:
          System.out.println(socialNetworkCircle.getTracks().size());
          System.out.println(socialNetworkCircle.getRelationBetweenCentralAndObject().size());
          System.out.println(socialNetworkCircle.getRelationBetweenObjects().size());
          break;
        default:
          in.close();
          socialNetworkCircleLogger.info("SocialNetworkCircle application deactivate.");
          System.exit(0);
          break;
      }
    }
  }

  /**
   * Menu in social network circle application which indicates users' choices.
   */
  private static void menu() {
    System.out.println("1. Read from file to generate a social network circle.");
    System.out.println("2. Visualize.");
    System.out.println("3. Add a track.");
    System.out.println("4. Add an object to a track.");
    System.out.println("5. Delete a track.");
    System.out.println("6. Delete an object in a track.");
    System.out.println("7. Calculate the information entropy of the system.");
    System.out.println("8. Calculate information diffusivity of someone in the first track.");
    System.out.println("9. Add/Delete a social relationship.");
    System.out.println("10. Calculate logical distance of two friends.");
    System.out.println("11. Log search.");
    System.out.println("12. Check legality.");
    System.out.println("13. Write the current social network circle to file.");
  }

  /**
   * Menu in read-from-file function which indicates which file can be read from.
   */
  private static void readMenu() {
    System.out.println("1. input/SocialNetworkCircle.txt");
    System.out.println("2. input/SocialNetworkCircle_Medium.txt");
    System.out.println("3. input/SocialNetworkCircle_Larger.txt");
    System.out.println("4. Other file(Absolute path).");
    System.out.println("5. Lab5_input/SocialNetworkCircle.txt");
  }

  private static void ioStrategy() {
    System.out.println("1. FileReader.");
    System.out.println("2. InputStreamReader.");
    System.out.println("3. NIO.");
  }
}
