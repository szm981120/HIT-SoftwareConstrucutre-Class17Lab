package circularorbit;

import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import atomtransitionmement.Memento;
import centralobject.Star;
import myexception.DataNonScientificNumberException;
import myexception.DataScientificNumberException;
import physicalobject.PhysicalObject;

/**
 * StellarSystem specifies readFromFile method.
 * 
 * @author Shen
 *
 */
public class StellarSystem extends ConcreteCircularOrbit<Star, PhysicalObject> {

  private static final String NUMBER_REGEX =
      "([0-9]*|[0-9]*\\.[0-9]*|[0-9]+\\.[0-9]*e[0-9]*|\\s*[0-9]e[0-9]*\\s*)";

  private static final String LABEL_REGEX = "([a-zA-Z0-9]*)";

  private static final String COMMA_REGEX = "\\s*,\\s*";

  private static Logger stellarSystemLogger = Logger.getLogger("StellarSystem Log");

  private static int readLineCounter = 0;

  /**
   * Constructor.
   */
  public StellarSystem() {}

  // /**
  // * Override readFromFile. Before reading from file, it's representations must be reset. This can
  // * read from legal stellar system data file.
  // *
  // * @throws AtomElectronNumException shouldn't happen in stellar system.
  // * @throws AtomTrackNumException shouldn't happen in stellar system.
  // * @throws AtomElementException shouldn't happen in stellar system.
  // * @throws IllegalIntimacyInSocialTieException shouldn't happen in stellar system.
  // */
  // @Override
  // public void readFromFile(File file) throws NumberFormatException, FileNotFoundException,
  // IOException, AtomElementException, AtomTrackNumException, AtomElectronNumException,
  // DataSyntaxException, DataScientificNumberException, IllegalIntimacyInSocialTieException {
  // super.readFromFile(file);
  // long startTime = System.currentTimeMillis();
  // stellarSystemLogger.setUseParentHandlers(false);
  // Pattern pattern1;
  // Pattern pattern2;
  // Matcher matcher1;
  // Matcher matcher2;
  // FileReader reader = new FileReader(file);
  // BufferedReader br = new BufferedReader(reader);
  // String starName = null;
  // double starRadius = 0;
  // double starMass = 0;
  // String planetName = null;
  // String planetState = null;
  // String planetColor = null;
  // double planetRadius = 0;
  // double trackRadius = 0;
  // double revolutionSpeed = 0;
  // double originDegree = 0;
  // boolean revolutionDirection = true;
  // PlanetFactory planetFactory = new ConcretePlanetFactory();
  // TrackFactory trackFactory = new TrackFactory();
  // String line = "";
  // while ((line = br.readLine()) != null) {
  // if (readLineCounter % 20000 == 0) {
  // System.out.println(readLineCounter);
  // }
  // readLineCounter++;
  // if (line.isEmpty()) {
  // continue;
  // }
  // try {
  // boolean stellarMatch = false;
  // boolean planetMatch = false;
  // pattern1 = Pattern.compile("Stellar\\s*::=\\s*" + "<" + LABEL_REGEX + COMMA_REGEX
  // + NUMBER_REGEX + COMMA_REGEX + NUMBER_REGEX + ">");
  // matcher1 = pattern1.matcher(line);
  // while (matcher1.find()) {
  // stellarMatch = true;
  // starName = matcher1.group(1);
  // starRadius = parseNumber(matcher1.group(2));
  // starMass = parseNumber(matcher1.group(3));
  // this.addCentralObject(new Star(starName, starRadius, starMass));
  // }
  // pattern2 = Pattern.compile("Planet\\s*::=\\s*" + "<" + LABEL_REGEX + COMMA_REGEX
  // + LABEL_REGEX + COMMA_REGEX + LABEL_REGEX + COMMA_REGEX + NUMBER_REGEX + COMMA_REGEX
  // + NUMBER_REGEX + COMMA_REGEX + NUMBER_REGEX + COMMA_REGEX + "(CW|CCW){1}" + COMMA_REGEX
  // + NUMBER_REGEX + ">");
  // matcher2 = pattern2.matcher(line);
  // while (matcher2.find()) {
  // planetMatch = true;
  // planetName = matcher2.group(1);
  // planetState = matcher2.group(2);
  // planetColor = matcher2.group(3);
  // planetRadius = parseNumber(matcher2.group(4));
  // trackRadius = parseNumber(matcher2.group(5));
  // revolutionSpeed = parseNumber(matcher2.group(6));
  // revolutionDirection = matcher2.group(7).equals("CW") ? true : false;
  // originDegree = parseNumber(matcher2.group(8));
  // try {
  // if (originDegree < 0 || originDegree >= 360) {
  // throw new DegreeOutOfRangeException(
  // "Original degree is less than 0 or no less than 360!"
  // + " Because degree is out of range, get remainder of it as original degree.");
  // }
  // } catch (DegreeOutOfRangeException e) {
  // stellarSystemLogger.log(Level.WARNING, e.getMessage(), e);
  // originDegree = originDegree % 360;
  // }
  // // for (PhysicalObject p : this) {
  // // if (p.equals(planetFactory.produce(planetName,
  // // planetState, planetColor, planetRadius,
  // // revolutionSpeed, revolutionDirection, originDegree))) {
  // // throw new PlanetConflictException(
  // // "This planet has already existed! Exception line: " +
  // // readLineCounter + ".");
  // // }
  // // }
  // this.addTrack(trackFactory.produce(trackRadius));
  // this.addPhysicalObjectToTrack(
  // planetFactory.produce(planetName, planetState, planetColor, planetRadius,
  // revolutionSpeed, revolutionDirection, originDegree),
  // trackFactory.produce(trackRadius));
  // }
  // if (!stellarMatch && !planetMatch && line.contains("Stellar")) {
  // String[] syntaxTest = line.substring(line.indexOf("<") + 1, line.indexOf(">")).split(",");
  // if (!Pattern.compile("[a-zA-Z0-9]*").matcher(syntaxTest[0]).matches()) {
  // throw new DataSyntaxException(
  // "Star name syntax doesn't match! Exception line: " + readLineCounter + ".");
  // }
  // if (!Pattern
  // .compile("\\s*[0-9]*\\s*|\\s*[0-9]*\\.[0-9]*\\s*"
  // + "|\\s*[0-9]\\.[0-9]*e[0-9]*\\s*|\\s*[0-9]e[0-9]*\\s*")
  // .matcher(syntaxTest[1]).matches()) {
  // throw new DataSyntaxException(
  // "Star radius syntax doesn't match! Exception line: " + readLineCounter + ".");
  // }
  // if (!Pattern
  // .compile("\\s*[0-9]*\\s*|\\s*[0-9]*\\.[0-9]*\\s*"
  // + "|\\s*[0-9]\\.[0-9]*e[0-9]*\\s*|\\s*[0-9]e[0-9]*\\s*")
  // .matcher(syntaxTest[2]).matches()) {
  // throw new DataSyntaxException(
  // "Star mass syntax doesn't match! Exception line: " + readLineCounter + ".");
  // }
  // }
  // if (!stellarMatch && !planetMatch && line.contains("Planet")) {
  // String[] syntaxTest = line.substring(line.indexOf("<") + 1, line.indexOf(">")).split(",");
  // int labelSyntaxCount = 0;
  // for (int i = 0; i <= 2; i++) {
  // if (Pattern.compile("\\s*[a-zA-Z0-9]*\\s*").matcher(syntaxTest[i]).matches()) {
  // labelSyntaxCount++;
  // }
  // }
  // if (labelSyntaxCount < 3) {
  // throw new DataSyntaxException(
  // "One of planet label syntax doesn't match! It can be name, state or color."
  // + " Exception line: " + readLineCounter + ".");
  // }
  // int numberSyntaxCount = 0;
  // for (int i = 3; i <= 5; i++) {
  // if (Pattern
  // .compile("\\s*[0-9]*\\s*" + "|\\s*[0-9]*\\.[0-9]*\\s*"
  // + "|\\s*[0-9]\\.[0-9]*e[0-9]*\\s*" + "|\\s*[0-9]e[0-9]*\\s*")
  // .matcher(syntaxTest[i]).matches()) {
  // numberSyntaxCount++;
  // }
  // }
  // if (numberSyntaxCount < 3) {
  // throw new DataSyntaxException("One of planet number syntax doesn't match!"
  // + " It can be planet radius, track radius or revolution speed. Exception line: "
  // + readLineCounter + ".");
  // }
  // if (!Pattern.compile("\\s*CW\\s*|\\s*CCW\\s*").matcher(syntaxTest[6]).matches()) {
  // throw new DataSyntaxException(
  // "Planet revolution direction syntax doesn't match! Exception line: "
  // + readLineCounter + ".");
  // }
  // if (!Pattern
  // .compile("\\s*[0-9]*\\s*|\\s*[0-9]*\\.[0-9]*\\s*"
  // + "|\\s*[0-9]\\.[0-9]*e[0-9]*\\s*|\\s*[0-9]e[0-9]*\\s*")
  // .matcher(syntaxTest[7]).matches()) {
  // throw new DataSyntaxException(
  // "Planet original degree syntax doesn't match! Exception line: " + readLineCounter
  // + ".");
  // }
  // }
  // } catch (DataNonScientificNumberException e) {
  // stellarSystemLogger.log(Level.WARNING, e.getMessage()
  // + " The illegal number is treated as a legal more than 10000 number."
  // + " If modification needed, please run again after modify the file. Exception line: "
  // + readLineCounter + ".", e);
  // System.out.println(" If modification needed, please run again after modify the file."
  // + " Exception line: " + readLineCounter + ".");
  // }
  // // } catch (PlanetConflictException e) {
  // // stellarSystemLogger.log(Level.WARNING, e.getMessage()
  // // + " The latter conflicting planet is skipped. Exception line: " +
  // // readLineCounter + ".", e);
  // // System.out
  // // .println("The latter conflicting planet is skipped. Exception
  // // line: " + readLineCounter + ".");
  // // }
  // } // end while((line = br.readLine()) != null)
  // br.close();
  // long endTime = System.currentTimeMillis();
  // System.out.println(
  // "ReadFromFile stellar system runtime: " + ((double) (endTime - startTime) / 1000) + "s.");
  // }

  // @Override
  // public void writeToFile(File file) throws IOException {
  // super.writeToFile(file);
  // FileWriter writer = new FileWriter(file);
  // BufferedWriter bw = new BufferedWriter(writer);
  // bw.write("Stellar ::= <" + this.centralObject.getName() + "," + this.centralObject.getRadius()
  // + "," + this.centralObject.getMass() + ">\r\n");
  // bw.flush();
  // int bufferFlushSize = 0;
  // for (Track track : this.tracks) {
  // for (PhysicalObject planet : this.objectsInTrack.get(track)) {
  // String direction = planet.getDirect() ? "CW" : "CCW";
  // bw.write("Planet ::= <" + planet.getName() + "," + planet.getState() + ","
  // + planet.getRaidus() + "," + track.getRadius() + "," + planet.getSpeed() + ","
  // + direction + "," + planet.getDegree() + ">\r\n");
  // bufferFlushSize++;
  // if (bufferFlushSize % 100 == 0) {
  // bw.flush();
  // bufferFlushSize = 0;
  // }
  // }
  // }
  // bw.close();
  // }

  /**
   * Turn a notation number string into a real number. If this notation doesn't contain "e", then
   * it's not a scientific notation. If it's not a scientific notation number, turn it into real
   * number directly, else call parseScientific method to parse scientific notation.
   * 
   * @param s a number notation string
   * @return real number of s
   * @throws DataNonScientificNumberException deal with some exception when a more than 10000 number
   *         doesn't use scientific notation.
   * @throws DataScientificNumberException deal with some exception when disobeys scientifi notation
   *         rules.
   */
  public static double parseNumber(String s)
      throws DataNonScientificNumberException, DataScientificNumberException {
    if (s.contains("e")) {
      return parseScientific(s);
    } else {
      double d = Double.parseDouble(s);
      if (d >= 10000) {
        throw new DataNonScientificNumberException(
            "A number greater than 10000 doesn't use scientific notation!"
                + " The illegal number is treated as a legal more than 10000 number."
                + " Exception line: " + readLineCounter + ".");
      }
      return d;
    }
  }

  /**
   * Turn a scientific notation number string into a real number.
   * 
   * @param s a scientific notation number string
   * @return real number of s
   * @throws DataScientificNumberException deal with some exception when s disobeys scientific
   *         notation rules.
   */
  private static double parseScientific(String s) throws DataScientificNumberException {
    double mantissa;
    int exponent;
    String[] ss = s.split("e");
    mantissa = Double.parseDouble(ss[0]);
    exponent = Integer.parseInt(ss[1]);
    if (!(mantissa >= 1 && mantissa < 10)) {
      throw new DataScientificNumberException(
          "The mantissa is not between 1 and 10. Exception line: " + readLineCounter + ".");
    }
    if (exponent < 4) {
      throw new DataScientificNumberException(
          "The exponent is less than 4. Exception line: " + readLineCounter + ".");
    }
    double result = mantissa * Math.pow(10, exponent);
    return result;
  }

  // /**
  // * This method is non-existent in atom structure.
  // */
  // @Override
  // public void constructSocialNetworkCircle(List<PhysicalObject> friends,
  // List<Edge<PhysicalObject>> physicalEdges, Map<PhysicalObject, Double> centralEdges) {
  // assert false : "shouldn't call this method";
  // }
  /**
   * This method is non-existent in atom structure.
   */
  @Override
  public void constructSocialNetworkCircle(Map<String, PhysicalObject> friends) {
    assert false : "shouldn't call this method";
  }

  /**
   * This method is non-existent in atom structure.
   */
  @Override
  public Memento<Star, PhysicalObject> save() {
    assert false : "shouldn't have this method";
    return null;
  }

  /**
   * This method is non-existent in atom structure.
   */
  @Override
  public void restore(Memento<Star, PhysicalObject> m) {
    assert false : "shouldn't have this method";
  }

  /**
   * This method is non-existent in atom structure.
   */
  @Override
  public void addRelationshipBetweenCentralAndPhysical(PhysicalObject physicalObject,
      double weight) {
    assert false : "shouldn't call this method";
  }

  /**
   * This method is non-existent in atom structure.
   */
  @Override
  public void addRelationshipBetweenPhysicalAndPhysical(PhysicalObject physicalObjectA,
      PhysicalObject physicalObjectB, double weight) {
    assert false : "shouldn't call this method";
  }

  /**
   * This method is non-existent in atom structure.
   */
  @Override
  public void deleteRelationshipBetweenPhysicalAndPhysical(PhysicalObject physicalObjectA,
      PhysicalObject physicalObjectB) {
    assert false : "shouldn't call this method";
  }

  /**
   * This method is non-existent in atom structure.
   */
  @Override
  public Map<PhysicalObject, Double> getRelationBetweenCentralAndObject() {
    assert false : "shouldn't call this method";
    return null;
  }

  /**
   * This method is non-existent in atom structure.
   */
  @Override
  public Set<Edge<PhysicalObject>> getRelationBetweenObjects() {
    assert false : "shouldn't call this method";
    return null;
  }
}
