package applications;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import APIs.CircularOrbitAPIs;
import APIs.CircularOrbitHelper;
import MyException.AtomElectronNumException;
import MyException.AtomElementException;
import MyException.AtomTrackNumException;
import MyException.DataNonScientificNumberException;
import MyException.DataScientificNumberException;
import MyException.DataSyntaxException;
import MyException.IllegalIntimacyInSocialTieException;
import MyException.NoObjectOnTrackException;
import MyException.PlanetConflictException;
import centralObject.Star;
import circularOrbit.StellarSystemFactory;
import circularOrbit.CircularOrbit;
import circularOrbit.StellarSystem;
import circularOrbit.circularOrbitFactory;
import physicalObject.ConcretePlanetFactory;
import physicalObject.PhysicalObject;
import physicalObject.PlanetFactory;
import track.Track;
import track.TrackFactory;

/**
 * StellarSystemAPP provides a static method for client to call in Main.java.
 * This static method is the mainly function method for stellar system
 * applications.
 * 
 * @author Shen
 *
 */
public class StellarSystemAPP {

	private static PlanetFactory planetFactory = new ConcretePlanetFactory();
	private static TrackFactory trackFactory = new TrackFactory();
	private static Scanner in = new Scanner(System.in);
	private static Pattern pattern;
	private static Matcher matcher;
	private static final String numberRegex = "([0-9]*|[0-9]*.[0-9]*|[0-9].[0-9]*e[0-9]*)";
	private static final String labelRegex = "([a-zA-z0-9]*)";
	private static final String commaRegex = "\\s*,\\s*";
	private static Logger stellarSystemLogger = Logger.getLogger("StellarSystem Log");

	/**
	 * Stellar system application method
	 * 
	 */
	public static void application() {
		CircularOrbitAPIs<Star, PhysicalObject> apis = new CircularOrbitAPIs<Star, PhysicalObject>();
		circularOrbitFactory<Star, PhysicalObject> factory = new StellarSystemFactory();
		CircularOrbit<Star, PhysicalObject> stellarSystem = factory.produce();
		stellarSystemLogger.setUseParentHandlers(false);
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
			case 1: // Read from file to generate a atomic structure
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
						stellarSystemLogger.log(Level.INFO, "Read from input/StellarSystem.txt.");
						file = new File("input/StellarSystem.txt");
						stellarSystem.readFromFile(file);
						stellarSystemLogger.log(Level.INFO, "Success: Read from input/StellarSystem.txt.");
						break;
					case 2:
						stellarSystemLogger.log(Level.INFO, "Read from input/StellarSystem_Medium.txt.");
						file = new File("input/StellarSystem_Medium.txt");
						stellarSystem.readFromFile(file);
						stellarSystemLogger.log(Level.INFO, "Success: Read from input/StellarSystem_Medium.txt.");
						break;
					case 3:
						stellarSystemLogger.log(Level.INFO, "Read from input/StellarSystem_Large.txt.");
						file = new File("input/StellarSystem_Larger.txt");
						stellarSystem.readFromFile(file);
						stellarSystemLogger.log(Level.INFO, "Success: Read from input/StellarSystem_Large.txt.");
						break;
					case 4:
						System.out.println("Please input absolute file path.");
						String absolutePath = in.nextLine();
						file = new File(absolutePath);
						stellarSystem.readFromFile(file);
						stellarSystemLogger.info("Success: Read from " + absolutePath + ". Restore present state.");
						break;
					default:
						System.err.println("Wrong input");
						break;
					}
				} catch (FileNotFoundException e) {
					stellarSystemLogger.log(Level.SEVERE,
							"File not found. File: " + file.getPath() + ". " + e.getMessage(), e);
					System.err.println("File not found. File: " + file.getPath() + ". " + e.getMessage());
				} catch (DataSyntaxException e) {
					stellarSystemLogger.log(Level.SEVERE, e.getMessage(), e);
					System.err.println(e.getMessage());
				} catch (DataScientificNumberException e) {
					stellarSystemLogger.log(Level.SEVERE, e.getMessage(), e);
					System.err.println(e.getMessage());
				} catch (IllegalIntimacyInSocialTieException | AtomElementException | AtomTrackNumException
						| AtomElectronNumException e) {
					assert false : "shouldn't have this exception.";
				} catch (IOException e) {
					e.printStackTrace();
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
				break;
			case 2: // Visualize
				stellarSystemLogger.log(Level.INFO, "Visualize.");
				CircularOrbitHelper.visualize(stellarSystem);
				stellarSystemLogger.info("Success: Visualize.");
				break;
			case 3: // Add a track
				stellarSystemLogger.info("Add a track.");
				System.out.println("What's the radius(decimal) of the added track?");
				double addTrackRadius = 0;
				try {
					addTrackRadius = in.nextDouble();
					in.nextLine();
				} catch (InputMismatchException e) {
					System.err.println(e.getClass() + "Please input again.");
					in.nextLine();
					break;
				}
				stellarSystem.addTrack(trackFactory.produce(addTrackRadius));
				stellarSystemLogger.info("Success: Add a track. Radius is " + addTrackRadius + ".");
				break;
			case 4: // Add an object to a track
				stellarSystemLogger.info("Add an object to a track.");
				System.out.println("Please input information of the added planet in order below:");
				System.out.println(
						"Planet ::= <NAME,STATE,COLOR,PLANET_RADIUS,TRACK_RADIUS,REVOLUTION_SPEED,REVOLUTION_DIRECTION,ORIGIN_DEGREE>");
				System.out.println("All data must obey the specification.");
				String addData = in.nextLine();
				pattern = Pattern.compile("Planet\\s*::=\\s*" + "<" + labelRegex + commaRegex + labelRegex + commaRegex
						+ labelRegex + commaRegex + numberRegex + commaRegex + numberRegex + commaRegex + numberRegex
						+ commaRegex + "(CW|CCW)" + commaRegex + numberRegex + ">");
				matcher = pattern.matcher(addData);
				boolean find = matcher.find();
				if (find) {
					String planetName = matcher.group(1);
					String planetState = matcher.group(2);
					String planetColor = matcher.group(3);
					double planetRadius = 0, trackRadius = 0, revolutionSpeed = 0, originDegree = 0;
					try {
						planetRadius = StellarSystem.parseNumber(matcher.group(4));
					} catch (DataNonScientificNumberException e) {
						System.err.println(
								"There is at least one number greater than 10000 which doesn't use scientific notation. But it is treated as legal.");
						stellarSystemLogger.log(Level.WARNING, e.getMessage()
								+ " There is at least one number greater than 10000 which doesn't use scientific notation. But it is treated as legal.",
								e);
					} catch (DataScientificNumberException e) {
						System.err.println("Please run again with correct input.");
						stellarSystemLogger.log(Level.SEVERE, e.getMessage(), e);
						break;
					}
					try {
						trackRadius = StellarSystem.parseNumber(matcher.group(5));
					} catch (DataNonScientificNumberException e) {
						System.err.println(
								"There is at least one number greater than 10000 which doesn't use scientific notation. But it is treated as legal.");
						stellarSystemLogger.log(Level.WARNING, e.getMessage()
								+ " There is at least one number greater than 10000 which doesn't use scientific notation. But it is treated as legal.",
								e);
					} catch (DataScientificNumberException e) {
						System.err.println("Please run again with correct input.");
						stellarSystemLogger.log(Level.SEVERE, e.getMessage(), e);
						break;
					}
					try {
						revolutionSpeed = StellarSystem.parseNumber(matcher.group(6));
					} catch (DataNonScientificNumberException e) {
						System.err.println(
								"There is at least one number greater than 10000 which doesn't use scientific notation. But it is treated as legal.");
						stellarSystemLogger.log(Level.WARNING, e.getMessage()
								+ " There is at least one number greater than 10000 which doesn't use scientific notation. But it is treated as legal.",
								e);
					} catch (DataScientificNumberException e) {
						System.err.println("Please run again with correct input.");
						stellarSystemLogger.log(Level.SEVERE, e.getMessage(), e);
						break;
					}
					boolean revolutionDirection = matcher.group(7).equals("CW") ? true : false;
					try {
						originDegree = StellarSystem.parseNumber(matcher.group(8));
					} catch (DataNonScientificNumberException e) {
						System.err.println(
								"There is at least one number greater than 10000 which doesn't use scientific notation. But it is treated as legal.");
						stellarSystemLogger.log(Level.WARNING, e.getMessage()
								+ " There is at least one number greater than 10000 which doesn't use scientific notation. But it is treated as legal.",
								e);
					} catch (DataScientificNumberException e) {
						System.err.println("Please run again with correct input.");
						stellarSystemLogger.log(Level.SEVERE, e.getMessage(), e);
						break;
					}
					stellarSystem
							.addPhysicalObjectToTrack(
									planetFactory.produce(planetName, planetState, planetColor, planetRadius,
											revolutionSpeed, revolutionDirection, originDegree),
									trackFactory.produce(trackRadius));
					stellarSystemLogger
							.info("Success: Add a planet " + planetName + " on the " + trackRadius + " track.");
				} else {
					stellarSystemLogger.log(Level.SEVERE, "Planet syntax doesn't match!");
					System.err.println("Planet syntax doesn't match!");
					break;
				}
				break;
			case 5: // Delete a track
				stellarSystemLogger.info("Delete a track.");
				System.out.println("What's the radius(decimal) of the deleted track?");
				double deleteTrackRadius = 0;
				try {
					deleteTrackRadius = in.nextDouble();
					in.nextLine();
				} catch (InputMismatchException e) {
					System.err.println(e.getClass() + "Please input again.");
					in.nextLine();
					break;
				}
				stellarSystem.deleteTrack(trackFactory.produce(deleteTrackRadius));
				stellarSystemLogger.info("Success: Delete a " + deleteTrackRadius + " track.");
				break;
			case 6: // Delete an object in a track
				stellarSystemLogger.info("Delete an object in a track.");
				System.out.println("Please input information of the deleted planet in order below:");
				System.out.println(
						"Planet ::= <NAME,STATE,COLOR,PLANET_RADIUS,TRACK_RADIUS,REVOLUTION_SPEED,REVOLUTION_DIRECTION,ORIGIN_DEGREE>");
				System.out.println("All data must obey the specification.");
				String deleteData = in.nextLine();
				pattern = Pattern.compile("Planet\\s*::=\\s*" + "<" + labelRegex + commaRegex + labelRegex + commaRegex
						+ labelRegex + commaRegex + numberRegex + commaRegex + numberRegex + commaRegex + numberRegex
						+ commaRegex + "(CW|CCW)" + commaRegex + numberRegex + ">");
				matcher = pattern.matcher(deleteData);
				boolean find1 = matcher.find();
				if (find1) {
					String planetName = matcher.group(1);
					String planetState = matcher.group(2);
					String planetColor = matcher.group(3);
					double planetRadius = 0, trackRadius = 0, revolutionSpeed = 0, originDegree = 0;
					try {
						planetRadius = StellarSystem.parseNumber(matcher.group(4));
					} catch (DataNonScientificNumberException e) {
						System.err.println(
								"There is at least one number greater than 10000 which doesn't use scientific notation. But it is treated as legal.");
						stellarSystemLogger.log(Level.WARNING, e.getMessage()
								+ " There is at least one number greater than 10000 which doesn't use scientific notation. But it is treated as legal.",
								e);
					} catch (DataScientificNumberException e) {
						System.err.println("Please run again with correct input.");
						stellarSystemLogger.log(Level.SEVERE, e.getMessage(), e);
						break;
					}
					try {
						trackRadius = StellarSystem.parseNumber(matcher.group(5));
					} catch (DataNonScientificNumberException e) {
						System.err.println(
								"There is at least one number greater than 10000 which doesn't use scientific notation. But it is treated as legal.");
						stellarSystemLogger.log(Level.WARNING, e.getMessage()
								+ " There is at least one number greater than 10000 which doesn't use scientific notation. But it is treated as legal.",
								e);
					} catch (DataScientificNumberException e) {
						System.err.println("Please run again with correct input.");
						stellarSystemLogger.log(Level.SEVERE, e.getMessage(), e);
						break;
					}
					try {
						revolutionSpeed = StellarSystem.parseNumber(matcher.group(6));
					} catch (DataNonScientificNumberException e) {
						System.err.println(
								"There is at least one number greater than 10000 which doesn't use scientific notation. But it is treated as legal.");
						stellarSystemLogger.log(Level.WARNING, e.getMessage()
								+ " There is at least one number greater than 10000 which doesn't use scientific notation. But it is treated as legal.",
								e);
					} catch (DataScientificNumberException e) {
						System.err.println("Please run again with correct input.");
						stellarSystemLogger.log(Level.SEVERE, e.getMessage(), e);
						break;
					}
					boolean revolutionDirection = matcher.group(7).equals("CW") ? true : false;
					try {
						originDegree = StellarSystem.parseNumber(matcher.group(8));
					} catch (DataNonScientificNumberException e) {
						System.err.println(
								"There is at least one number greater than 10000 which doesn't use scientific notation. But it is treated as legal.");
						stellarSystemLogger.log(Level.WARNING, e.getMessage()
								+ " There is at least one number greater than 10000 which doesn't use scientific notation. But it is treated as legal.",
								e);
					} catch (DataScientificNumberException e) {
						System.err.println("Please run again with correct input.");
						stellarSystemLogger.log(Level.SEVERE, e.getMessage(), e);
						break;
					}
					try {
						stellarSystem.deletePhysicalObjectFromTrack(
								planetFactory.produce(planetName, planetState, planetColor, planetRadius,
										revolutionSpeed, revolutionDirection, originDegree),
								trackFactory.produce(trackRadius));
					} catch (NoObjectOnTrackException e) {
						System.err.println("Fail track: " + trackRadius + ".");
						stellarSystemLogger.log(Level.SEVERE, e.getMessage() + " Fail track: " + trackRadius + ".", e);
						break;
					}
					stellarSystemLogger
							.info("Success: Delete a planet " + planetName + " from " + trackRadius + " track.");
				} else {
					stellarSystemLogger.log(Level.SEVERE, "Planet syntax doesn't match!");
					System.err.println("Planet syntax doesn't match!");
					break;
				}
				break;
			case 7: // Calculate the information entropy of the system
				stellarSystemLogger.info("Calculate the information entropy of the system.");
				double entropy = apis.getObjectDistributionEntropy(stellarSystem);
				System.out.println("Information entropy: " + entropy);
				stellarSystemLogger.info("Success: Calculate the information entropy: " + entropy + ".");
				break;
			case 8: // Calculate every planet's position at time t
				stellarSystemLogger.info("Calculate every planet's position at time t.");
				System.out.println("Please input time t(days):");
				double time = 0;
				try {
					time = in.nextDouble();
					in.nextLine();
				} catch (InputMismatchException e) {
					System.err.println(e.getClass() + "Please input again.");
					in.nextLine();
					break;
				}
				time = time * 24 * 3600;
				for (Track track : stellarSystem.getObjectsInTrack().keySet()) {
					for (PhysicalObject planet : stellarSystem.getObjectsInTrack().get(track)) {
						double degree = planet.getDegree();
						boolean direct = planet.getDirect();
						double speed = planet.getSpeed();
						double radius = track.getRadius();
						double theta = time * speed / radius;
						double degree_ = direct ? degree + theta : degree - theta;
						degree_ = degree_ / Math.PI * 180 % 360;
						System.out.println(planet.getName() + ", Radius: " + radius + ", Degree: " + degree_);
					}
				}
				stellarSystemLogger.info("Success: Calculate every planet's position at time t.");
				break;
			case 9: // Calculate the physical distance between the star and another planet
				stellarSystemLogger.info("Calculate the physical distance between the star and another planet");
				System.out.println("Please input the name of the target planet:");
				String targetName = in.nextLine();
				for (Track track : stellarSystem.getObjectsInTrack().keySet()) {
					for (PhysicalObject planet : stellarSystem.getObjectsInTrack().get(track)) {
						if (planet.getName().equals(targetName)) {
							System.out.println("The distance is: " + track.getRadius());
							stellarSystemLogger.info("Success: Calculate physical distance from star to planet "
									+ targetName + ": " + track.getRadius());
						}
					}
				}
				break;
			case 10: // Calculate the physical distance between two planets
				stellarSystemLogger.info("Calculate the physical distance between two planets");
				System.out.println("Please input the name of planet 1: ");
				String planetName1 = in.nextLine();
				System.out.println("Please input the name of planet 2: ");
				String planetName2 = in.nextLine();
				PhysicalObject planet1 = null, planet2 = null;
				for (PhysicalObject planet : stellarSystem) {
					if (planet.getName().equals(planetName1)) {
						planet1 = planet;
					}
					if (planet.getName().equals(planetName2)) {
						planet2 = planet;
					}
				}
				double distance = apis.getPhysicalDistance(stellarSystem, planet1, planet2);
				System.out.println("The distance is: " + distance);
				stellarSystemLogger.info("Success: Calculate physical distance from planet " + planetName1
						+ " to planet " + planetName2 + ": " + distance);
				break;
			case 11: // Simulate movement in GUI
				stellarSystemLogger.info("Simulate movements in GUI.");
				apis.planetMovingSimulate(stellarSystem);
				stellarSystemLogger.info("Success: Simulate movements in GUI.");
				break;
			case 12:
				apis.logSearch(new File("log/stellarSystemLog.log"));
				break;
			case 13: // Check legality
				stellarSystemLogger.info("Check legality.");
				if (stellarSystem.getCentralObject() == null) {
					stellarSystemLogger.log(Level.SEVERE, "The central object is null!");
					assert false : "The central object is null!";
				}
				for (Track track : stellarSystem.getTracks()) {
					if (stellarSystem.getObjectsInTrack().get(track).size() != 1) {
						stellarSystemLogger.log(Level.SEVERE, "There must be one single planet in one track!");
						assert false : "There must be one single planet in one track!";
					}
				}
				for (int i = 0; i < stellarSystem.getTracks().size() - 1; i++) {
					Track track = stellarSystem.getTracks().get(i);
					Track track_ = stellarSystem.getTracks().get(i + 1);
					double trackRadiusSum = track.getRadius() + track_.getRadius();
					double planetRadiusSum = stellarSystem.getObjectsInTrack().get(track).get(0).getRaidus()
							+ stellarSystem.getObjectsInTrack().get(track_).get(0).getRaidus();
					if (planetRadiusSum >= trackRadiusSum) {
						stellarSystemLogger.log(Level.SEVERE,
								"The sum of radius of two neighbouring tracks should be more than the sum of radius of the two planets in these two track! "
										+ "Exception track location: " + i + " and " + (i + 1) + ".");
						assert false : "The sum of radius of two neighbouring tracks should be more than the sum of radius of the two planets in these two track!";

					}
				}
				stellarSystemLogger.info("Legality checked!");
				break;
			default:
				in.close();
				stellarSystemLogger.info("StellarSystem application deactivate.");
				System.exit(0);
				break;
			}
		}
	}

	/**
	 * Menu in stellar system application which indicates users' choices
	 */
	private static void menu() {
		System.out.println("1. Read from file to generate a atomic structure.");
		System.out.println("2. Visualize.");
		System.out.println("3. Add a track.");
		System.out.println("4. Add an object to a track.");
		System.out.println("5. Delete a track.");
		System.out.println("6. Delete an object in a track.");
		System.out.println("7. Calculate the information entropy of the system.");
		System.out.println("8. Calculate every planet's position at time t.");
		System.out.println("9. Calculate the physical distance between the star and another planet.");
		System.out.println("10. Calculate the physical distance between two planets.");
		System.out.println("11. Simulate movement in GUI.");
		System.out.println("12. Log search.");
		System.out.println("13. Check legality.");
	}

	/**
	 * Menu in read-from-file function which indicates which file can be read from
	 */
	private static void readMenu() {
		System.out.println("1. StellarSystem.txt");
		System.out.println("2. StellarSystem_Medium.txt");
		System.out.println("3. StellarSystem_Larger.txt");
		System.out.println("4. Other file(Absolute path).");
	}

}
