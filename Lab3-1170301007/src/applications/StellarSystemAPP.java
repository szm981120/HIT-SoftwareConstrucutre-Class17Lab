package applications;

import java.io.File;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import APIs.CircularOrbitAPIs;
import APIs.CircularOrbitHelper;
import centralObject.Star;
import circularOrbit.StellarSystemFactory;
import circularOrbit.CircularOrbit;
import circularOrbit.StellarSystem;
import circularOrbit.circularOrbitFactory;
import physicalObject.ConcretePlanetFactory;
import physicalObject.PhysicalObject;
import physicalObject.PlanetFactory;
import track.StellarTrack;
import track.StellarTrackFactory;
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
	private static StellarTrackFactory stellarTrackFactory = new StellarTrackFactory();
	private static Scanner in = new Scanner(System.in);
	private static Pattern pattern;
	private static Matcher matcher;
	private static final String numberRegex = "([0-9]*|[0-9]*.[0-9]*|[0-9].[0-9]*e[0-9]*)";
	private static final String labelRegex = "([a-zA-z0-9]*)";
	private static final String commaRegex = "\\s*,\\s*";

	/**
	 * Stellar system application method
	 * 
	 * @throws Exception deal with some exception when user's input disobey
	 *                   scientific notation rules.
	 */
	public static void application() throws Exception {
		CircularOrbitAPIs<Star, PhysicalObject> apis = new CircularOrbitAPIs<Star, PhysicalObject>();
		circularOrbitFactory<Star, PhysicalObject> factory = new StellarSystemFactory();
		CircularOrbit<Star, PhysicalObject> stellarSystem = factory.produce();

		while (true) {
			menu();
			int choose = in.nextInt();
			in.nextLine();
			switch (choose) {
			case 1: // Read from file to generate a atomic structure
				readMenu();
				int choose1 = in.nextInt();
				switch (choose1) {
				case 1:
					stellarSystem.readFromFile(new File("input/StellarSystem.txt"));
					break;
				case 2:
					stellarSystem.readFromFile(new File("input/StellarSystem_Medium.txt"));
					break;
				case 3:
					stellarSystem.readFromFile(new File("input/StellarSystem_Larger.txt"));
					break;
				case 4:
					stellarSystem.readFromFile(new File("input/StellarSystem312.txt"));
				default:
					System.out.println("Wrong input");
					break;
				}
				break;
			case 2: // Visualize
				CircularOrbitHelper.visualize(stellarSystem);
				break;
			case 3: // Add a track
				System.out.println("What's the long radius(decimal) of the added track?");
				double addTrackLongRadius = in.nextDouble();
				System.out.println("What's the short radius(decimal) of the added track?");
				double addTrackShortRadius = in.nextDouble();
				stellarSystem.addTrack(stellarTrackFactory.produce(addTrackLongRadius, addTrackShortRadius));
				break;
			case 4: // Add an object to a track
				System.out.println("Please input information of the added planet in order below:");
				System.out.println(
						"Planet ::= <NAME,STATE,COLOR,PLANET_RADIUS,TRACK_LONG_RADIUS,TRACK_SHORT_RADIUS,REVOLUTION_SPEED,REVOLUTION_DIRECTION,ORIGIN_DEGREE>");
				System.out.println("All data must obey the specification.");
				String addData = in.nextLine();
				pattern = Pattern.compile("Planet\\s*::=\\s*" + "<" + labelRegex + commaRegex + labelRegex + commaRegex
						+ labelRegex + commaRegex + numberRegex + commaRegex + numberRegex + commaRegex + numberRegex
						+ commaRegex + numberRegex + commaRegex + "(CW|CCW)" + commaRegex + numberRegex + ">");
				matcher = pattern.matcher(addData);
				while (matcher.find()) {
					String planetName = matcher.group(1);
					String planetState = matcher.group(2);
					String planetColor = matcher.group(3);
					double planetRadius = StellarSystem.parseNumber(matcher.group(4));
					double trackLongRadius = StellarSystem.parseNumber(matcher.group(5));
					double trackShortRadius = StellarSystem.parseNumber(matcher.group(6));
					double revolutionSpeed = StellarSystem.parseNumber(matcher.group(7));
					boolean revolutionDirection = matcher.group(8).equals("CW") ? true : false;
					double originDegree = StellarSystem.parseNumber(matcher.group(9));
					stellarSystem.addPhysicalObjectToTrack(
							planetFactory.produce(planetName, planetState, planetColor, planetRadius, revolutionSpeed,
									revolutionDirection, originDegree),
							stellarTrackFactory.produce(trackLongRadius, trackShortRadius));
				}
				break;
			case 5: // Delete a track
				System.out.println("What's the long radius(decimal) of the deleted track?");
				double deleteTrackLongRadius = in.nextDouble();
				System.out.println("What's the short radius(decimal) of the deleted track?");
				double deleteTrackShortRadius = in.nextDouble();
				stellarSystem.deleteTrack(stellarTrackFactory.produce(deleteTrackLongRadius, deleteTrackShortRadius));
				break;
			case 6: // Delete an object in a track
				System.out.println("Please input information of the deleted planet in order below:");
				System.out.println(
						"Planet ::= <NAME,STATE,COLOR,PLANET_RADIUS,TRACK_LONG_RADIUS,TRACK_SHORT_RADIUS,REVOLUTION_SPEED,REVOLUTION_DIRECTION,ORIGIN_DEGREE>");
				System.out.println("All data must obey the specification.");
				String deleteData = in.nextLine();
				pattern = Pattern.compile("Planet\\s*::=\\s*" + "<" + labelRegex + commaRegex + labelRegex + commaRegex
						+ labelRegex + commaRegex + numberRegex + commaRegex + numberRegex + commaRegex + numberRegex
						+ commaRegex + numberRegex + commaRegex + "(CW|CCW)" + commaRegex + numberRegex + ">");
				matcher = pattern.matcher(deleteData);
				while (matcher.find()) {
					String planetName = matcher.group(1);
					String planetState = matcher.group(2);
					String planetColor = matcher.group(3);
					double planetRadius = StellarSystem.parseNumber(matcher.group(4));
					double trackLongRadius = StellarSystem.parseNumber(matcher.group(5));
					double trackShortRadius = StellarSystem.parseNumber(matcher.group(6));
					double revolutionSpeed = StellarSystem.parseNumber(matcher.group(7));
					boolean revolutionDirection = matcher.group(8).equals("CW") ? true : false;
					double originDegree = StellarSystem.parseNumber(matcher.group(9));
					stellarSystem.deletePhysicalObjectFromTrack(
							planetFactory.produce(planetName, planetState, planetColor, planetRadius, revolutionSpeed,
									revolutionDirection, originDegree),
							stellarTrackFactory.produce(trackLongRadius, trackShortRadius));
				}
				break;
			case 7: // Calculate the information entropy of the system
				double entropy = apis.getObjectDistributionEntropy(stellarSystem);
				System.out.println("Information entropy: " + entropy);
				break;
			case 8: // Calculate every planet's position at time t
				System.out.println("Please input time t(days):");
				double time = in.nextDouble();
				time = time * 24 * 3600;
				for (Track track : stellarSystem.getObjectsInTrack().keySet()) {
					for (PhysicalObject planet : stellarSystem.getObjectsInTrack().get(track)) {
						double degree = planet.getDegree();
						boolean direct = planet.getDirect();
						double speed = planet.getSpeed();
						double radius = track.getLongRadius();
						double theta = time * speed / radius;
						double degree_ = direct ? degree + theta : degree - theta;
						degree_ = degree_ / Math.PI * 180 % 360;
						System.out.println(planet.getName() + ", Radius: " + radius + ", Degree: " + degree_);
					}
				}
				break;
			case 9: // Calculate the physical distance between the star and another planet
				System.out.println("Please input the name of the target planet:");
				String targetName = in.nextLine();
				for (Track track : stellarSystem.getObjectsInTrack().keySet()) {
					for (PhysicalObject planet : stellarSystem.getObjectsInTrack().get(track)) {
						if (planet.getName().equals(targetName)) {
							double distance = apis.getPhysicalDistanceFromCentralToObject(stellarSystem, planet);
							System.out.println("The distance is: " + distance);
						}
					}
				}
				break;
			case 10: // Calculate the physical distance between two planets
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
				System.out.println("The distance is: " + apis.getPhysicalDistance(stellarSystem, planet1, planet2));
				break;
			case 11: // Simulate movement in GUI
				apis.planetMovingSimulate(stellarSystem);
				break;
			case 12: // Check legality
				if (stellarSystem.getCentralObject() == null) {
					throw new Exception("ILLEGAL! The central object is null!");
				}
				for (Track track : stellarSystem.getTracks()) {
					if (stellarSystem.getObjectsInTrack().get(track).size() != 1) {
						throw new Exception("ILLEGAL! There must be one single planet in one track!");
					}
				}
				for (int i = 0; i < stellarSystem.getTracks().size() - 1; i++) {
					Track track = stellarSystem.getTracks().get(i);
					Track track_ = stellarSystem.getTracks().get(i + 1);
					double trackRadiusSum = track_.getShortRadius() - track.getShortRadius();
					double planetRadiusSum = stellarSystem.getObjectsInTrack().get(track).get(0).getRaidus()
							+ stellarSystem.getObjectsInTrack().get(track_).get(0).getRaidus();
					if (planetRadiusSum >= trackRadiusSum) {
						throw new Exception(
								"ILLEGAL! The sum of radius of two neighbouring tracks should be more than the sum of radius of the two planets in these two track!");
					}
				}
				System.out.println("LEGAL");
				break;
			default:
				in.close();
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
		System.out.println("12. Check legality.");
	}

	/**
	 * Menu in read-from-file function which indicates which file can be read from
	 */
	private static void readMenu() {
		System.out.println("1. StellarSystem.txt");
		System.out.println("2. StellarSystem_Medium.txt");
		System.out.println("3. StellarSystem_Larger.txt");
		System.out.println("4. StellarSystem312.txt");
	}
}
