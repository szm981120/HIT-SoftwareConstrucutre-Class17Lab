package circularOrbit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import MyException.AtomElectronNumException;
import MyException.AtomElementException;
import MyException.AtomTrackNumException;
import MyException.DataNonScientificNumberException;
import MyException.DataScientificNumberException;
import MyException.DataSyntaxException;
import MyException.IllegalIntimacyInSocialTieException;
import MyException.PlanetConflictException;
import atomTransitionMemento.Memento;
import centralObject.Nucleus;
import physicalObject.ConcreteElectronFactory;
import physicalObject.ElectronFactory;
import physicalObject.PhysicalObject;
import track.TrackFactory;

/**
 * AtomStructure specifies readFromFile method.
 * 
 * @author Shen
 *
 */
public class AtomStructure extends ConcreteCircularOrbit<Nucleus, PhysicalObject> {

	private static Logger atomStructureLogger = Logger.getLogger("AtomStructure Log");
	private static int readLineCounter = 0;

	/**
	 * Constructor
	 */
	public AtomStructure() {

	}

	/**
	 * Override readFromFile. Before reading from file, it's representations must be
	 * reset. This can read from legal atom structure data file.
	 * 
	 * @throws DataScientificNumberException       shouldn't happen in atom
	 *                                             structure.
	 * @throws DataSyntaxException                 shouldn't happen in atom
	 *                                             structure.
	 * @throws IllegalIntimacyInSocialTieException shouldn't happen in atom
	 *                                             structure.s
	 */
	@Override
	public void readFromFile(File file) throws NumberFormatException, IOException, AtomElementException,
			AtomTrackNumException, AtomElectronNumException, DataSyntaxException, DataScientificNumberException,
			IllegalIntimacyInSocialTieException {
		super.readFromFile(file);
		atomStructureLogger.setUseParentHandlers(false);
		Pattern pattern1, pattern2, pattern3;
		Matcher matcher1, matcher2, matcher3;
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
					throw new AtomElementException("The first character is not a upper case character! Exception line: "
							+ readLineCounter + ".");
				}
				if (matcher1.group(1).length() >= 2 && !Character.isLowerCase(matcher1.group(1).charAt(1))) {
					throw new AtomElementException(
							"The second character is not a lower case character! Exception line: " + readLineCounter
									+ ".");
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
				this.addCentralObject(new Nucleus(elementName));
			}
			pattern2 = Pattern.compile("NumberOfTracks\\s*::=\\s*(\\d*)");
			matcher2 = pattern2.matcher(line);
			while (matcher2.find()) {
				if (Integer.parseInt(matcher2.group(1)) <= 0) {
					throw new AtomTrackNumException(
							"Number of track is not a positive integer! Exception line: " + readLineCounter + ".");
				}
				numberOfTrack = Integer.valueOf(matcher2.group(1));
				for (int i = 1; i <= numberOfTrack; i++) {
					this.addTrack(trackFactory.produce(i));
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
							"The number of electron information doesn't match the number of track! Exception line: "
									+ readLineCounter + ".");
				}
				for (int i = 0; i < numberOfElectronString.length; i += 2) {
					numberOfElectron.put(Integer.parseInt(numberOfElectronString[i]),
							Integer.parseInt(numberOfElectronString[i + 1]));
				}
				for (int i = 1; i <= numberOfTrack; i++) {
					for (int j = 1; j <= numberOfElectron.get(i); j++) {
						this.addPhysicalObjectToTrack(electronFactory.produce(), trackFactory.produce(i));
					}
				}
			}
		} // end while((line = br.readLine()) != null)
	}

	/**
	 * This method is non-existent in atom structure.
	 */
	@Override
	public void constructSocialNetworkCircle(List<PhysicalObject> friends, List<Edge<PhysicalObject>> physicalEdges,
			Map<PhysicalObject, Double>[] centralEdges) {
		assert false : "shouldn't call this method";
	}

	@Override
	public Memento<Nucleus, PhysicalObject> save() {
		return new Memento<Nucleus, PhysicalObject>(this.centralObject, this.tracks, this.objectsInTrack,
				this.relationBetweenCentralAndObject, this.relationBetweenObjects);
	}

	@Override
	public void restore(Memento<Nucleus, PhysicalObject> m) {
		this.centralObject = m.getCentralObject();
		this.tracks = m.getTracks();
		this.objectsInTrack = m.getObjectsInTrack();
		this.relationBetweenCentralAndObject = m.getRelationBetweenCentralAndObject();
		this.relationBetweenObjects = m.getRelationBetweenObjects();
	}

	/**
	 * This method is non-existent in atom structure.
	 */
	@Override
	public void addRelationshipBetweenCentralAndPhysical(PhysicalObject physicalObject, boolean fromCentral,
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
	public Map<PhysicalObject, Double>[] getRelationBetweenCentralAndObject() {
		assert false : "shouldn't call this method";
		return null;
	}

	/**
	 * This method is non-existent in atom structure.
	 */
	@Override
	public List<Edge<PhysicalObject>> getRelationBetweenObjects() {
		assert false : "shouldn't call this method";
		return null;
	}
}
