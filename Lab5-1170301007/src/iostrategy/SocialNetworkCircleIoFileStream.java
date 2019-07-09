package iostrategy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import centralobject.Person;
import circularorbit.CircularOrbit;
import circularorbit.Edge;
import myexception.DataSyntaxException;
import myexception.FriendConflictException;
import myexception.IllegalIntimacyInSocialTieException;
import myexception.NonexistentFriendInSocialTieException;
import myexception.SocialTieConflictException;
import physicalobject.ConcreteFriendFactory;
import physicalobject.FriendFactory;
import physicalobject.PhysicalObject;

public class SocialNetworkCircleIoFileStream implements SocialNetworkIoStrategy {

  private static final String LABEL_REGEX = "([a-zA-Z0-9]+)";

  private static final String COMMA_REGEX = "\\s*,\\s*";

  private static Logger socialNetworkCircleLogger = Logger.getLogger("SocialNetworkCircle Log");

  private static int readLineCounter = 0;

  public SocialNetworkCircleIoFileStream() {
    // TODO Auto-generated constructor stub
  }

  @Override
  public void readFromFile(CircularOrbit<Person, PhysicalObject> socialNetworkCircle, File file)
      throws NumberFormatException, IOException, IllegalIntimacyInSocialTieException,
      DataSyntaxException {
    socialNetworkCircle.readFromFile();
    socialNetworkCircleLogger.setUseParentHandlers(false);
    Pattern pattern1;
    Pattern pattern2;
    Pattern pattern3;
    Matcher matcher1;
    Matcher matcher2;
    Matcher matcher3;
    InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
    BufferedReader br = new BufferedReader(reader);
    Map<String, PhysicalObject> friends = new HashMap<String, PhysicalObject>();
    FriendFactory friendFactory = new ConcreteFriendFactory();
    String name = null;
    int age = 0;
    char sex;
    Person centralPerson = null;
    String line = "";
    final long startTime = System.currentTimeMillis();
    final long startReadTime = System.currentTimeMillis();
    readLineCounter = 0;
    while ((line = br.readLine()) != null) {
      readLineCounter++;
      if (line.isEmpty()) {
        continue;
      }
      try {
        boolean centralUserMatch = false;
        boolean friendMatch = false;
        boolean socialTieMatch = false;
        pattern1 = Pattern.compile("CentralUser\\s*::=\\s*<" + LABEL_REGEX + COMMA_REGEX + "(\\d*)"
            + COMMA_REGEX + "([M|F]{1})" + ">");
        matcher1 = pattern1.matcher(line);
        while (matcher1.find()) {
          centralUserMatch = true;
          name = matcher1.group(1);
          age = Integer.valueOf(matcher1.group(2));
          sex = matcher1.group(3).charAt(0);
          centralPerson = new Person(name, age, sex);
          socialNetworkCircle.addCentralObject(new Person(name, age, sex));
        }
        pattern2 = Pattern.compile("Friend\\s*::=\\s*<" + LABEL_REGEX + COMMA_REGEX + "(\\d*)"
            + COMMA_REGEX + "([M|F]{1})" + ">");
        matcher2 = pattern2.matcher(line);
        while (matcher2.find()) {
          friendMatch = true;
          name = matcher2.group(1);
          age = Integer.valueOf(matcher2.group(2));
          sex = matcher2.group(3).charAt(0);
          PhysicalObject friend = friendFactory.produce(name, age, sex);
          /* FriendConflictException */
          if (friends.containsKey(name)) {
            throw new FriendConflictException(
                "This friend has already existed! The latter conflicting friend isn't loaded."
                    + " Exception line: " + readLineCounter + ".");
          }
          /* END FriendConflictException */
          friends.put(name, friend);
        }
        pattern3 = Pattern.compile("SocialTie\\s*::=\\s*<" + LABEL_REGEX + COMMA_REGEX + LABEL_REGEX
            + COMMA_REGEX + "(0\\.\\d{1,3}\\s*|1\\.0{1,3}\\s*)>");
        matcher3 = pattern3.matcher(line);
        while (matcher3.find()) {
          socialTieMatch = true;
          String name1 = matcher3.group(1);
          String name2 = matcher3.group(2);
          Double intimacy = new Double(Double.parseDouble(matcher3.group(3)));
          if (intimacy <= 0 || intimacy > 1) {
            throw new IllegalIntimacyInSocialTieException(
                "The intimacy is no greater than 0 or greater than 1! Exception line: "
                    + readLineCounter + ".");
          }
          /* NonexistentFriendInSocialTieException */
          try {
            boolean friendExisted1 = false;
            boolean friendExisted2 = false;
            if (name1.equals(centralPerson.getName())) {
              friendExisted1 = true;
            }
            if (name2.equals(centralPerson.getName())) {
              friendExisted2 = true;
            }
            if (friends.containsKey(name1)) {
              friendExisted1 = true;
            }
            if (friends.containsKey(name2)) {
              friendExisted2 = true;
            }
            if (!friendExisted1 || !friendExisted2) {
              throw new NonexistentFriendInSocialTieException(
                  "A nonexistent friend shows in a social tie!");
            }
          } catch (NonexistentFriendInSocialTieException e) {
            socialNetworkCircleLogger.log(Level.WARNING, e.getMessage(), e);
            System.out.println(
                "This social tie is skipped. Exception social tie: " + name1 + "->" + name2);
            continue;
          }
          /* END NonexistentFriendInSocialTieException */
          /* SocialTieConflictException */
          if (name1.equals(name2)) {
            System.out.println(1);
            throw new SocialTieConflictException(
                "The two persons between this social tie are the same! The social tie is skipped."
                    + " Exception line: " + readLineCounter + ".");
          }
          // if (socialNetworkCircle.getRelationBetweenObjects().contains(new Edge<PhysicalObject>(
          // friendFactory.produce(name1, 20, 'F'), friendFactory.produce(name2, 20, 'F'), 0.5))) {
          // System.out.println(2);
          // throw new SocialTieConflictException(
          // "This social tie has already existed! The conflicting social tie is skipped."
          // + " Exception line: " + readLineCounter + ".");
          // }
          if (centralPerson.getName().equals(name1)
              && socialNetworkCircle.getRelationBetweenCentralAndObject()
                  .containsKey(friendFactory.produce(name2, 20, 'F'))) {
            System.out.println(3);
            throw new SocialTieConflictException(
                "This social tie has already existed! The conflicting social tie is skipped."
                    + " Exception line: " + readLineCounter + ".");
          }
          if (centralPerson.getName().equals(name2)
              && socialNetworkCircle.getRelationBetweenCentralAndObject()
                  .containsKey(friendFactory.produce(name1, 20, 'F'))) {
            System.out.println(4);
            throw new SocialTieConflictException(
                "This social tie has already existed! The conflicting social tie is skipped."
                    + " Exception line: " + readLineCounter + ".");
          }
          /* END SocialTieConflictException */
          if (name1.equals(centralPerson.getName())) {
            socialNetworkCircle.addRelationshipBetweenCentralAndPhysical(
                friendFactory.produce(name2, 20, 'F'), intimacy);
          } else if (name2.equals(centralPerson.getName())) {
            socialNetworkCircle.addRelationshipBetweenCentralAndPhysical(
                friendFactory.produce(name1, 20, 'F'), intimacy);
          } else {
            socialNetworkCircle.addRelationshipBetweenPhysicalAndPhysical(
                friendFactory.produce(name1, 20, 'F'), friendFactory.produce(name2, 20, 'F'),
                intimacy);
          }
        }
        if (!centralUserMatch && !friendMatch && !socialTieMatch
            && (line.contains("CentralUser") || line.contains("Friend"))) {
          String[] syntaxTest = line.substring(line.indexOf("<") + 1, line.indexOf(">")).split(",");
          if (!Pattern.compile("\\s*[a-zA-Z0-9]*\\s*").matcher(syntaxTest[0]).matches()) {
            br.close();
            throw new DataSyntaxException(
                "CentralUser name syntax doesn't match! Exception line: " + readLineCounter + ".");
          }
          if (!Pattern.compile("\\s*\\d*\\s*").matcher(syntaxTest[1]).matches()) {
            br.close();
            throw new DataSyntaxException(
                "CentralUser age syntax doesn't match! Exception line: " + readLineCounter + ".");
          }
          if (!Pattern.compile("\\s*M|F\\s*").matcher(syntaxTest[2]).matches()) {
            br.close();
            throw new DataSyntaxException(
                "CentralUser sex syntax doesn't match! Exception line: " + readLineCounter + ".");
          }
        }
        if (!centralUserMatch && !friendMatch && !socialTieMatch && line.contains("SocialTie")) {
          String[] syntaxTest = line.substring(line.indexOf("<") + 1, line.indexOf(">")).split(",");
          int labelSyntaxCount = 0;
          for (int i = 0; i <= 1; i++) {
            if (Pattern.compile("\\s*[a-zA-Z0-9]*\\s*").matcher(syntaxTest[i]).matches()) {
              labelSyntaxCount++;
            }
          }
          if (labelSyntaxCount < 2) {
            br.close();
            throw new DataSyntaxException(
                "One of friend label syntax doesn't match! Exception line: " + readLineCounter
                    + ".");
          }
          if (!Pattern.compile("\\s*0\\.\\d{1,3}\\s*|\\s*1\\.0{1,3}\\s*").matcher(syntaxTest[2])
              .matches()) {
            br.close();
            throw new DataSyntaxException(
                "SocialTie intimacy syntax doesn't match! Exception line: " + readLineCounter
                    + ".");
          }
        }
      } catch (FriendConflictException e) {
        socialNetworkCircleLogger.log(Level.WARNING, e.getMessage(), e);
        System.out.println(e.getMessage());
      } catch (SocialTieConflictException e) {
        socialNetworkCircleLogger.log(Level.WARNING, e.getMessage(), e);
        System.out.println(e.getMessage());
      }
    } // end while ((line = br.readLine()) != null)
    br.close();
    final long endReadTime = System.currentTimeMillis();
    System.out.println("Read from file(FileReader) social network circle runtime: "
        + ((double) (endReadTime - startReadTime) / 1000) + "s.");
    socialNetworkCircle.constructSocialNetworkCircle(friends);
    final long endTime = System.currentTimeMillis();
    System.out.println("Social network circle runtime in total: "
        + ((double) (endTime - startTime) / 1000) + "s.");
  }

  @Override
  public void writeToFile(CircularOrbit<Person, PhysicalObject> socialNetworkCircle, File file)
      throws IOException {
    if (!file.exists()) {
      file.createNewFile();
    }
    final long startTime = System.currentTimeMillis();
    FileOutputStream fop = new FileOutputStream(file);
    Person centralPerson = socialNetworkCircle.getCentralObject();
    fop.write(("CentralUser ::= <" + centralPerson.getName() + "," + centralPerson.getAge() + ","
        + centralPerson.getSex() + ">\r\n").getBytes());
    for (PhysicalObject friend : socialNetworkCircle) {
      fop.write(("Friend ::= <" + friend.getName() + "," + friend.getAge() + "," + friend.getSex()
          + ">\r\n").getBytes());
    }
    Map<PhysicalObject, Double> relationBetweenCentralAndPhysical =
        socialNetworkCircle.getRelationBetweenCentralAndObject();
    for (PhysicalObject friend : relationBetweenCentralAndPhysical.keySet()) {
      fop.write(("SocialTie ::= <" + centralPerson.getName() + "," + friend.getName() + ","
          + relationBetweenCentralAndPhysical.get(friend) + ">\r\n").getBytes());
    }
    Set<Edge<PhysicalObject>> relationBetweenObjects =
        socialNetworkCircle.getRelationBetweenObjects();
    for (Edge<PhysicalObject> edge : relationBetweenObjects) {
      fop.write(("SocialTie ::= <" + edge.getSource().getName() + "," + edge.getTarget().getName()
          + "," + edge.getWeight() + ">\r\n").getBytes());
    }
    fop.close();
    final long endTime = System.currentTimeMillis();
    System.out.println("Write to file(FileOutputStream) social network circle runtime: "
        + ((double) (endTime - startTime) / 1000) + "s.");
  }
}
