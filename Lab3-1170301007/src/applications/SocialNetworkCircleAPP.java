package applications;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import APIs.CircularOrbitAPIs;
import APIs.CircularOrbitHelper;
import centralObject.Person;
import circularOrbit.CircularOrbit;
import circularOrbit.Edge;
import circularOrbit.SocialNetworkCircleFactory;
import circularOrbit.circularOrbitFactory;
import physicalObject.ConcreteFriendFactory;
import physicalObject.FriendFactory;
import physicalObject.PhysicalObject;
import track.Track;
import track.TrackFactory;

/**
 * SocialNetworkCircleAPP provides a static method for client to call in
 * Main.java. This static method is the mainly function method for social
 * network circle applications.
 * 
 * @author Shen
 *
 */
public class SocialNetworkCircleAPP {

	private static FriendFactory friendFactory = new ConcreteFriendFactory();
	private static TrackFactory trackFactory = new TrackFactory();
	private static Scanner in = new Scanner(System.in);
	private static Pattern pattern;
	private static Matcher matcher;
	private static final String labelRegex = "([a-zA-Z0-9]*)";
	private static final String commaRegex = "\\s*,\\s*";

	/**
	 * Social network circle application method
	 * 
	 * @throws Exception
	 */
	public static void application() throws Exception {
		CircularOrbitAPIs<Person, PhysicalObject> apis = new CircularOrbitAPIs<Person, PhysicalObject>();
		circularOrbitFactory<Person, PhysicalObject> factory = new SocialNetworkCircleFactory();
		CircularOrbit<Person, PhysicalObject> socialNetworkCircle = factory.produce();
		while (true) {
			menu();
			int choose = in.nextInt();
			in.nextLine();
			switch (choose) {
			case 1: // Read from file to generate a social network circle
				readMenu();
				int choose1 = in.nextInt();
				in.nextLine();
				switch (choose1) {
				case 1:
					socialNetworkCircle.readFromFile(new File("input/SocialNetworkCircle.txt"));
					break;
				case 2:
					socialNetworkCircle.readFromFile(new File("input/SocialNetworkCircle_Medium.txt"));
					break;
				case 3:
					socialNetworkCircle.readFromFile(new File("input/SocialNetworkCircle_Larger.txt"));
					break;
				default:
					System.out.println("Wrong input");
					break;
				}
				break;
			case 2: // Visualize
				CircularOrbitHelper.visualize(socialNetworkCircle);
				break;
			case 3: // Add a track
				System.out.println("What's the radius(integer) of the added track?");
				int addTrackRadius = in.nextInt();
				in.nextLine();
				socialNetworkCircle.addTrack(trackFactory.produce((double) addTrackRadius));
				break;
			case 4: // Add an object to a track
				System.out.println("Please input information of the added friend in order below:");
				System.out.println("Friend ::= <NAME,AGE,SEX>");
				System.out.println("All data must obey the specification.");
				String addData = in.nextLine();
				System.out.println("What's the radius(integer) of the target track?");
				int addRadius = in.nextInt();
				in.nextLine();
				pattern = Pattern.compile(
						"Friend\\s*::=\\s*<" + labelRegex + commaRegex + "(\\d*)" + commaRegex + "([M|F]{1})" + ">");
				matcher = pattern.matcher(addData);
				while (matcher.find()) {
					String name = matcher.group(1);
					int age = Integer.valueOf(matcher.group(2));
					char sex = matcher.group(3).charAt(0);
					socialNetworkCircle.addPhysicalObjectToTrack(friendFactory.produce(name, age, sex),
							trackFactory.produce(addRadius));
				}
				break;
			case 5: // Delete a track
				System.out.println("What's the radius(integer) of the added track?");
				int deleteTrackRadius = in.nextInt();
				in.nextLine();
				socialNetworkCircle.addTrack(trackFactory.produce((double) deleteTrackRadius));
				break;
			case 6: // Delete an object in a track
				System.out.println("Please input information of the deleted friend in order below:");
				System.out.println("Friend ::= <NAME,AGE,SEX>");
				System.out.println("All data must obey the specification.");
				String deleteData = in.nextLine();
				System.out.println("What's the radius(decimal) of the target track?");
				int deleteRadius = in.nextInt();
				in.nextLine();
				pattern = Pattern.compile(
						"Friend\\s*::=\\s*<" + labelRegex + commaRegex + "(\\d*)" + commaRegex + "([M|F]{1})" + ">");
				matcher = pattern.matcher(deleteData);
				while (matcher.find()) {
					String name = matcher.group(1);
					int age = Integer.valueOf(matcher.group(2));
					char sex = matcher.group(3).charAt(0);
					socialNetworkCircle.deletePhysicalObjectFromTrack(friendFactory.produce(name, age, sex),
							trackFactory.produce(deleteRadius));
				}
				break;
			case 7: // Calculate the information entropy of the system
				double entropy = apis.getObjectDistributionEntropy(socialNetworkCircle);
				System.out.println("Information entropy: " + entropy);
				break;
			case 8: // Calculate information diffusivity of someone in the first track
				System.out.println(
						"Information diffusivity: the number of whom can be aquainted via someone in the first track by the center person.");
				System.out.println(
						"Aquainted rule: The intimacy between intermediary and target should be no less than intimacy between center and intermediary.");
				System.out.println("Please input information of intermediary in the first track in order below:");
				System.out.println("Friend ::= <NAME,AGE,SEX>");
				System.out.println("All data must obey the specification.");
				String intermediaryData = in.nextLine();
				int diffusivity = 0;
				PhysicalObject intermediary = null;
				double intimacy = 0;
				boolean existIntermediary = false;
				pattern = Pattern.compile(
						"Friend\\s*::=\\s*<" + labelRegex + commaRegex + "(\\d*)" + commaRegex + "([M|F]{1})" + ">");
				matcher = pattern.matcher(intermediaryData);
				while (matcher.find()) {
					String name = matcher.group(1);
					int age = Integer.valueOf(matcher.group(2));
					char sex = matcher.group(3).charAt(0);
					intermediary = friendFactory.produce(name, age, sex);
				}
				for (PhysicalObject friend : socialNetworkCircle.getRelationBetweenCentralAndObject()[0].keySet()) {
					if (friend.equals(intermediary)) {
						existIntermediary = true;
						break;
					}
				}
				if (!existIntermediary) {
					System.out.println("This intermediary doesn't exist!");
					break;
				}
				intimacy = socialNetworkCircle.getRelationBetweenCentralAndObject()[0].get(intermediary);
				for (Edge<PhysicalObject> edge : socialNetworkCircle.getRelationBetweenObjects()) {
					if (edge.getSource().equals(intermediary)
							&& !socialNetworkCircle.getRelationBetweenCentralAndObject()[0].keySet()
									.contains(edge.getTarget())
							&& edge.getWeight() <= intimacy) {
						diffusivity++;
					}
				}
				System.out.println("The diffusivity via " + intermediary.getName() + " is: " + diffusivity);
				break;
			case 9: // Add or Dselete a social relationship
				System.out.println("Add(Y/y) or delete(N/n)?");
				String c = in.nextLine();
				System.out.println(c);
				if (!c.equalsIgnoreCase("Y") && !c.equalsIgnoreCase("N")) {
					System.out.println("Wrong input!");
					break;
				}
				System.out.println("The two friends in this relationship must have existed in the circle.");
				System.out.println("Please input information of the social relationship information in order below:");
				System.out.println("SocialTie ::= <NAME1, NAME2, INTIMACY>");
				System.out.println("All data must obey the specification.");
				String socialTie = in.nextLine();
				pattern = Pattern.compile(
						"SocialTie\\s*::=\\s*<" + labelRegex + commaRegex + labelRegex + commaRegex + "(0.\\d{1,3})>");
				matcher = pattern.matcher(socialTie);
				while (matcher.find()) {
					double infimacy = Double.valueOf(matcher.group(3));
					String name1 = matcher.group(1);
					String name2 = matcher.group(2);
					boolean exist1 = false, exist2 = false;
					PhysicalObject friend1 = null, friend2 = null;
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
						System.out.println("Both friends must have existed!");
					} else if (c.equalsIgnoreCase("Y")) {
						socialNetworkCircle.addRelationshipBetweenPhysicalAndPhysical(friend1, friend2, infimacy);
					} else {
						socialNetworkCircle.deleteRelationshipBetweenPhysicalAndPhysical(friend1, friend2);
					}
				}
				List<PhysicalObject> friends = new ArrayList<PhysicalObject>();
				for (PhysicalObject friend : socialNetworkCircle) {
					friends.add(friend);
				}
				List<Edge<PhysicalObject>> physicalEdges = socialNetworkCircle.getRelationBetweenObjects();
				@SuppressWarnings("unchecked")
				Map<PhysicalObject, Double>[] centralEdges = new Map[2];
				centralEdges[0] = socialNetworkCircle.getRelationBetweenCentralAndObject()[0];
				centralEdges[1] = socialNetworkCircle.getRelationBetweenCentralAndObject()[1];
				socialNetworkCircle.resetObjectsAndTrack();
				socialNetworkCircle.constructSocialNetworkCircle(friends, physicalEdges, centralEdges);
				break;
			case 10: // Calculate logical distance of two friends
				PhysicalObject friend1 = null, friend2 = null;
				System.out.println("Please input information of friend 1 in order below:");
				System.out.println("Friend ::= <NAME,AGE,SEX>");
				System.out.println("All data must obey the specification.");
				String friend1Data = in.nextLine();
				pattern = Pattern.compile(
						"Friend\\s*::=\\s*<" + labelRegex + commaRegex + "(\\d*)" + commaRegex + "([M|F]{1})" + ">");
				matcher = pattern.matcher(friend1Data);
				while (matcher.find()) {
					String name = matcher.group(1);
					int age = Integer.valueOf(matcher.group(2));
					char sex = matcher.group(3).charAt(0);
					friend1 = friendFactory.produce(name, age, sex);
				}
				System.out.println("Please input information of friend 2 in order below:");
				System.out.println("Friend ::= <NAME,AGE,SEX>");
				System.out.println("All data must obey the specification.");
				String friend2Data = in.nextLine();
				pattern = Pattern.compile(
						"Friend\\s*::=\\s*<" + labelRegex + commaRegex + "(\\d*)" + commaRegex + "([M|F]{1})" + ">");
				matcher = pattern.matcher(friend2Data);
				while (matcher.find()) {
					String name = matcher.group(1);
					int age = Integer.valueOf(matcher.group(2));
					char sex = matcher.group(3).charAt(0);
					friend2 = friendFactory.produce(name, age, sex);
				}
				System.out.println("The logical distance from friend 1 to friend 2: "
						+ apis.getLogicalDistance(socialNetworkCircle, friend1, friend2));
				break;
			case 11: // Check legality
				for (int i = 1; i <= socialNetworkCircle.getTracks().size(); i++) {
					Track track = socialNetworkCircle.getTracks().get(i - 1);
					for (PhysicalObject friend : socialNetworkCircle.getObjectsInTrack().get(track)) {
						if (apis.getLogicalDistance(socialNetworkCircle, friendFactory.produce("center", 1, 'M'),
								friend) != i) {
							System.out.println("The length of shortest path from " + friend.getName()
									+ " to center is: " + apis.getLogicalDistance(socialNetworkCircle,
											friendFactory.produce("center", 1, 'M'), friend));
							System.out.println(friend.getName() + " is on the " + i + "-th track.");
							throw new Exception(
									"If a friend in on the i-th track, then the shortest length from he/she to center must be i!");
						}
					}
				}
				break;
			default:
				in.close();
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
		System.out.println("11. Check legality.");
	}

	/**
	 * Menu in read-from-file function which indicates which file can be read from.
	 */
	private static void readMenu() {
		System.out.println("1. SocialNetworkCircle.txt");
		System.out.println("2. SocialNetworkCircle_Medium.txt");
		System.out.println("3. SocialNetworkCircle_Larger.txt");
	}
}
