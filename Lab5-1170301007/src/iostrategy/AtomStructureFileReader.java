package iostrategy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import centralobject.Nucleus;
import circularorbit.CircularOrbit;
import myexception.AtomElectronNumException;
import myexception.AtomElementException;
import myexception.AtomTrackNumException;
import physicalobject.ConcreteElectronFactory;
import physicalobject.ElectronFactory;
import physicalobject.PhysicalObject;
import track.Track;
import track.TrackFactory;

public class AtomStructureFileReader implements AtomStructureIoStrategy {

  private static Logger atomStructureLogger = Logger.getLogger("AtomStructure Log");

  private static int readLineCounter = 0;

  public AtomStructureFileReader() {
    // TODO Auto-generated constructor stub
  }

  @Override
  public void readFromFile(CircularOrbit<Nucleus, PhysicalObject> atomStructure, File file)
      throws AtomElectronNumException, AtomElementException, NumberFormatException, IOException,
      AtomTrackNumException {
    atomStructure.readFromFile();
    atomStructureLogger.setUseParentHandlers(false);
    Pattern pattern1;
    Pattern pattern2;
    Pattern pattern3;
    Matcher matcher1;
    Matcher matcher2;
    Matcher matcher3;
    FileReader reader = new FileReader(file);
    BufferedReader br = new BufferedReader(reader);
    char[] elementName = new char[2];
    int numberOfTrack = 0;
    Map<Integer, Integer> numberOfElectron = new HashMap<Integer, Integer>();
    TrackFactory trackFactory = new TrackFactory();
    ElectronFactory electronFactory = new ConcreteElectronFactory();
    String line = "";
    while ((line = br.readLine()) != null) {
      readLineCounter++;
      if (line.isEmpty()) {
        continue;
      }
      pattern1 = Pattern.compile("ElementName\\s*::=\\s*([a-zA-Z]*)");
      matcher1 = pattern1.matcher(line);
      while (matcher1.find()) {
        if (matcher1.group(1).length() >= 3 || matcher1.group(1).length() <= 0) {
          throw new AtomElementException(
              "The element of this atom contains 0 or more than 2 characters. Exception line: "
                  + readLineCounter + ".");
        } else if (!Character.isUpperCase(matcher1.group(1).charAt(0))) {
          throw new AtomElementException(
              "The first character is not a upper case character! Exception line: "
                  + readLineCounter + ".");
        }
        if (matcher1.group(1).length() >= 2
            && !Character.isLowerCase(matcher1.group(1).charAt(1))) {
          throw new AtomElementException(
              "The second character is not a lower case character! Exception line: "
                  + readLineCounter + ".");
        }
        if (matcher1.group(1).length() >= 1) {
          elementName[0] = matcher1.group(1).charAt(0);
        } else {
          elementName[0] = '\0';
        }
        if (matcher1.group(1).length() >= 2) {
          elementName[1] = matcher1.group(1).charAt(1);
        } else {
          elementName[1] = '\0';
        }
        atomStructure.addCentralObject(new Nucleus(elementName));
      }
      pattern2 = Pattern.compile("NumberOfTracks\\s*::=\\s*(\\d*)");
      matcher2 = pattern2.matcher(line);
      while (matcher2.find()) {
        if (Integer.parseInt(matcher2.group(1)) <= 0) {
          throw new AtomTrackNumException(
              "Number of track is not a positive integer! Exception line: " + readLineCounter
                  + ".");
        }
        numberOfTrack = Integer.valueOf(matcher2.group(1));
        for (int i = 1; i <= numberOfTrack; i++) {
          atomStructure.addTrack(trackFactory.produce(i));
        }
      }
      pattern3 = Pattern.compile("NumberOfElectron\\s*::=\\s*([0-9/;]*)");
      matcher3 = pattern3.matcher(line);
      String[] numberOfElectronString = null;
      while (matcher3.find()) {
        numberOfElectronString = matcher3.group(1).split("/|;");
        if (numberOfElectronString.length % 2 == 1) {
          throw new AtomElectronNumException(
              "Each track order number should match a number of electron! Exception line: "
                  + readLineCounter + ".");
        }
        if (numberOfElectronString.length != (numberOfTrack * 2)) {
          throw new AtomElectronNumException(
              "The number of electron information doesn't match the number of track!"
                  + " Exception line: " + readLineCounter + ".");
        }
        for (int i = 0; i < numberOfElectronString.length; i += 2) {
          numberOfElectron.put(Integer.parseInt(numberOfElectronString[i]),
              Integer.parseInt(numberOfElectronString[i + 1]));
        }
        for (int i = 1; i <= numberOfTrack; i++) {
          for (int j = 1; j <= numberOfElectron.get(i); j++) {
            atomStructure.addPhysicalObjectToTrack(electronFactory.produce(),
                trackFactory.produce(i));
          }
        }
      }
    } // end while((line = br.readLine()) != null)
    br.close();
  }

  @Override
  public void writeToFile(CircularOrbit<Nucleus, PhysicalObject> atomStructure, File file)
      throws IOException {
    if (!file.exists()) {
      file.createNewFile();
    }
    FileWriter writer = new FileWriter(file);
    BufferedWriter bw = new BufferedWriter(writer);
    bw.write("ElementName ::= " + atomStructure.getCentralObject().toString() + "\r\n");
    bw.write("NumberOfTracks ::= " + atomStructure.getTracks().size() + "\r\n");
    bw.write("NumberOfElectron ::= ");
    Map<Track, List<PhysicalObject>> objectInTrack = atomStructure.getObjectsInTrack();
    List<Track> tracks = atomStructure.getTracks();
    for (int i = 0; i < objectInTrack.size(); i++) {
      bw.write((int) tracks.get(i).getRadius() + "/" + objectInTrack.get(tracks.get(i)).size());
      if (i != objectInTrack.size() - 1) {
        bw.write(";");
      }
    }
    bw.flush();
    bw.close();
  }

}
