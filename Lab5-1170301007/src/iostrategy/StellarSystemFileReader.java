package iostrategy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import centralobject.Star;
import circularorbit.CircularOrbit;
import circularorbit.StellarSystem;
import myexception.DataNonScientificNumberException;
import myexception.DataScientificNumberException;
import myexception.DataSyntaxException;
import myexception.DegreeOutOfRangeException;
import physicalobject.ConcretePlanetFactory;
import physicalobject.PhysicalObject;
import physicalobject.PlanetFactory;
import track.Track;
import track.TrackFactory;

public class StellarSystemFileReader implements StellarSystemIoStrategy {

  private static final String NUMBER_REGEX =
      "([0-9]*|[0-9]*\\.[0-9]*|[0-9]+\\.[0-9]*e[0-9]*|\\s*[0-9]e[0-9]*\\s*)";

  private static final String LABEL_REGEX = "([a-zA-Z0-9]*)";

  private static final String COMMA_REGEX = "\\s*,\\s*";

  private static Logger stellarSystemLogger = Logger.getLogger("StellarSystem Log");

  private static int readLineCounter = 0;

  public StellarSystemFileReader() {
    // TODO Auto-generated constructor stub
  }

  @Override
  public void readFromFile(CircularOrbit<Star, PhysicalObject> stellarSystem, File file)
      throws IOException, DataScientificNumberException, DataSyntaxException {
    stellarSystem.readFromFile();
    stellarSystemLogger.setUseParentHandlers(false);
    Pattern pattern1;
    Pattern pattern2;
    Matcher matcher1;
    Matcher matcher2;
    FileReader reader = new FileReader(file);
    BufferedReader br = new BufferedReader(reader);
    String planetName = null;
    String planetState = null;
    String planetColor = null;
    double planetRadius = 0;
    double trackRadius = 0;
    double revolutionSpeed = 0;
    double originDegree = 0;
    boolean revolutionDirection = true;
    String starName = null;
    double starRadius = 0;
    double starMass = 0;
    PlanetFactory planetFactory = new ConcretePlanetFactory();
    TrackFactory trackFactory = new TrackFactory();
    String line = "";
    final long startReadTime = System.currentTimeMillis();
    readLineCounter = 0;
    while ((line = br.readLine()) != null) {
      readLineCounter++;
      if (readLineCounter % 20000 == 0) {
        System.out.println(readLineCounter);
      }
      if (line.isEmpty()) {
        continue;
      }
      try {
        boolean stellarMatch = false;
        boolean planetMatch = false;
        pattern1 = Pattern.compile("Stellar\\s*::=\\s*" + "<" + LABEL_REGEX + COMMA_REGEX
            + NUMBER_REGEX + COMMA_REGEX + NUMBER_REGEX + ">");
        matcher1 = pattern1.matcher(line);
        while (matcher1.find()) {
          stellarMatch = true;
          starName = matcher1.group(1);
          starRadius = StellarSystem.parseNumber(matcher1.group(2));
          starMass = StellarSystem.parseNumber(matcher1.group(3));
          stellarSystem.addCentralObject(new Star(starName, starRadius, starMass));
        }
        pattern2 = Pattern.compile("Planet\\s*::=\\s*" + "<" + LABEL_REGEX + COMMA_REGEX
            + LABEL_REGEX + COMMA_REGEX + LABEL_REGEX + COMMA_REGEX + NUMBER_REGEX + COMMA_REGEX
            + NUMBER_REGEX + COMMA_REGEX + NUMBER_REGEX + COMMA_REGEX + "(CW|CCW){1}" + COMMA_REGEX
            + NUMBER_REGEX + ">");
        matcher2 = pattern2.matcher(line);
        while (matcher2.find()) {
          planetMatch = true;
          planetName = matcher2.group(1);
          planetState = matcher2.group(2);
          planetColor = matcher2.group(3);
          planetRadius = StellarSystem.parseNumber(matcher2.group(4));
          trackRadius = StellarSystem.parseNumber(matcher2.group(5));
          revolutionSpeed = StellarSystem.parseNumber(matcher2.group(6));
          revolutionDirection = matcher2.group(7).equals("CW") ? true : false;
          originDegree = StellarSystem.parseNumber(matcher2.group(8));
          /* DegreeOutOfRangeException */
          try {
            if (originDegree < 0 || originDegree >= 360) {
              throw new DegreeOutOfRangeException(
                  "Original degree is less than 0 or no less than 360!"
                      + " Because degree is out of range, get remainder of it as original degree.");
            }
          } catch (DegreeOutOfRangeException e) {
            stellarSystemLogger.log(Level.WARNING, e.getMessage(), e);
            originDegree = originDegree % 360;
          }
          /* END DegreeOutOfRangeException */
          /* PlanetConflictException */
          // for (PhysicalObject p : stellarSystem) {
          // if (p.equals(planetFactory.produce(planetName, planetState, planetColor, planetRadius,
          // revolutionSpeed, revolutionDirection, originDegree))) {
          // throw new PlanetConflictException(
          // "This planet has already existed! Exception line: " + readLineCounter + ".");
          // }
          // }
          /* END PlanetConflictException */
          stellarSystem.addTrack(trackFactory.produce(trackRadius));
          stellarSystem.addPhysicalObjectToTrack(
              planetFactory.produce(planetName, planetState, planetColor, planetRadius,
                  revolutionSpeed, revolutionDirection, originDegree),
              trackFactory.produce(trackRadius));

        }
        /* Stellar DataSyntaxException */
        if (!stellarMatch && !planetMatch && line.contains("Stellar")) {
          String[] syntaxTest = line.substring(line.indexOf("<") + 1, line.indexOf(">")).split(",");
          if (!Pattern.compile("[a-zA-Z0-9]*").matcher(syntaxTest[0]).matches()) {
            throw new DataSyntaxException(
                "Star name syntax doesn't match! Exception line: " + readLineCounter + ".");
          }
          if (!Pattern
              .compile("\\s*[0-9]*\\s*|\\s*[0-9]*\\.[0-9]*\\s*"
                  + "|\\s*[0-9]\\.[0-9]*e[0-9]*\\s*|\\s*[0-9]e[0-9]*\\s*")
              .matcher(syntaxTest[1]).matches()) {
            throw new DataSyntaxException(
                "Star radius syntax doesn't match! Exception line: " + readLineCounter + ".");
          }
          if (!Pattern
              .compile("\\s*[0-9]*\\s*|\\s*[0-9]*\\.[0-9]*\\s*"
                  + "|\\s*[0-9]\\.[0-9]*e[0-9]*\\s*|\\s*[0-9]e[0-9]*\\s*")
              .matcher(syntaxTest[2]).matches()) {
            throw new DataSyntaxException(
                "Star mass syntax doesn't match! Exception line: " + readLineCounter + ".");
          }
        }
        /* END Stellar DataSyntaxException */
        /* Planet DataSyntaxException */
        if (!stellarMatch && !planetMatch && line.contains("Planet")) {
          String[] syntaxTest = line.substring(line.indexOf("<") + 1, line.indexOf(">")).split(",");
          int labelSyntaxCount = 0;
          for (int i = 0; i <= 2; i++) {
            if (Pattern.compile("\\s*[a-zA-Z0-9]*\\s*").matcher(syntaxTest[i]).matches()) {
              labelSyntaxCount++;
            }
          }
          if (labelSyntaxCount < 3) {
            throw new DataSyntaxException(
                "One of planet label syntax doesn't match! It can be name, state or color."
                    + " Exception line: " + readLineCounter + ".");
          }
          int numberSyntaxCount = 0;
          for (int i = 3; i <= 5; i++) {
            if (Pattern
                .compile("\\s*[0-9]*\\s*" + "|\\s*[0-9]*\\.[0-9]*\\s*"
                    + "|\\s*[0-9]\\.[0-9]*e[0-9]*\\s*" + "|\\s*[0-9]e[0-9]*\\s*")
                .matcher(syntaxTest[i]).matches()) {
              numberSyntaxCount++;
            }
          }
          if (numberSyntaxCount < 3) {
            throw new DataSyntaxException("One of planet number syntax doesn't match!"
                + " It can be planet radius, track radius or revolution speed. Exception line: "
                + readLineCounter + ".");
          }
          if (!Pattern.compile("\\s*CW\\s*|\\s*CCW\\s*").matcher(syntaxTest[6]).matches()) {
            throw new DataSyntaxException(
                "Planet revolution direction syntax doesn't match! Exception line: "
                    + readLineCounter + ".");
          }
          if (!Pattern
              .compile("\\s*[0-9]*\\s*|\\s*[0-9]*\\.[0-9]*\\s*"
                  + "|\\s*[0-9]\\.[0-9]*e[0-9]*\\s*|\\s*[0-9]e[0-9]*\\s*")
              .matcher(syntaxTest[7]).matches()) {
            throw new DataSyntaxException(
                "Planet original degree syntax doesn't match! Exception line: " + readLineCounter
                    + ".");
          }
        }
        /* END Planet DataSyntaxException */
      } catch (DataNonScientificNumberException e) {
        stellarSystemLogger.log(Level.WARNING, e.getMessage()
            + " The illegal number is treated as a legal more than 10000 number."
            + " If modification needed, please run again after modify the file. Exception line: "
            + readLineCounter + ".", e);
        System.out.println(" If modification needed, please run again after modify the file."
            + " Exception line: " + readLineCounter + ".");
      }
      // catch (PlanetConflictException e) {
      // stellarSystemLogger.log(Level.WARNING, e.getMessage()
      // + " The latter conflicting planet is skipped. Exception line: " + readLineCounter + ".",
      // e);
      // System.out.println(
      // "The latter conflicting planet is skipped. Exception line: " + readLineCounter + ".");
      // }
    } // end while((line = br.readLine()) != null)

    final long endReadTime = System.currentTimeMillis();
    System.out.println("Read from file(FileReader) stellar system runtime: "
        + ((double) (endReadTime - startReadTime) / 1000) + "s.");
    br.close();
  }

  @Override
  public void writeToFile(CircularOrbit<Star, PhysicalObject> stellarSystem, File file)
      throws IOException {
    if (!file.exists()) {
      file.createNewFile();
    }
    final long startTime = System.currentTimeMillis();
    FileWriter writer = new FileWriter(file);
    BufferedWriter bw = new BufferedWriter(writer);
    writer.write("Stellar ::= <" + stellarSystem.getCentralObject().getName() + ","
        + stellarSystem.getCentralObject().getRadius() + ","
        + stellarSystem.getCentralObject().getMass() + ">\r\n");
    bw.flush();
    int bufferFlushSize = 0;
    List<Track> tracks = stellarSystem.getTracks();
    Map<Track, List<PhysicalObject>> objectsInTrack = stellarSystem.getObjectsInTrack();
    for (Track track : tracks) {
      for (PhysicalObject planet : objectsInTrack.get(track)) {
        String direction = planet.getDirect() ? "CW" : "CCW";
        writer.write("Planet ::= <" + planet.getName() + "," + planet.getState() + ","
            + planet.getRaidus() + "," + track.getRadius() + "," + planet.getSpeed() + ","
            + direction + "," + planet.getDegree() + ">\r\n");
        bufferFlushSize++;
        if (bufferFlushSize % 20000 == 0) {
          bw.flush();
          bufferFlushSize = 0;
        }
      }
    }
    bw.close();
    final long endTime = System.currentTimeMillis();
    System.out.println("Write to file(FileWriter) stellar system runtime: "
        + ((double) (endTime - startTime) / 1000) + "s.");
  }

}
