package circularOrbit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import APIs.CircularOrbitAPIs;
import atomTransitionMemento.Memento;
import centralObject.Person;
import physicalObject.ConcreteFriendFactory;
import physicalObject.Friend;
import physicalObject.FriendFactory;
import physicalObject.PhysicalObject;
import track.TrackFactory;

/**
 * SocialNetworkCircle specifies readFromFile method.
 * 
 * @author Shen
 *
 */
public class SocialNetworkCircle extends ConcreteCircularOrbit<Person, PhysicalObject> {

	private static final String labelRegex = "([a-zA-Z0-9]*)";
	private static final String commaRegex = "\\s*,\\s*";

	/**
	 * Constructor
	 */
	public SocialNetworkCircle() {

	}

	/**
	 * Override readFromFile. Before reading from file, it's representations must be
	 * reset. This can read from legal social network circle data file.
	 */
	@Override
	public void readFromFile(File file) {
		super.readFromFile(file);
		Pattern pattern1, pattern2, pattern3;
		Matcher matcher1, matcher2, matcher3;
		try {
			FileReader reader = new FileReader(file);
			BufferedReader br = new BufferedReader(reader);
			List<PhysicalObject> friends = new ArrayList<PhysicalObject>();
			FriendFactory friendFactory = new ConcreteFriendFactory();
			String name = null;
			int age = 0;
			char sex;
			List<Edge<PhysicalObject>> physicalEdges = new ArrayList<Edge<PhysicalObject>>();
			@SuppressWarnings("unchecked")
			Map<PhysicalObject, Double>[] centralEdges = new Map[2];
			centralEdges[0] = new HashMap<PhysicalObject, Double>();
			centralEdges[1] = new HashMap<PhysicalObject, Double>();
			Map<String[], Double> adjacency = new HashMap<String[], Double>();
			String line = "";
			try {
				while ((line = br.readLine()) != null) {
					pattern1 = Pattern.compile("CentralUser\\s*::=\\s*<" + labelRegex + commaRegex + "(\\d*)"
							+ commaRegex + "([M|F]{1})" + ">");
					matcher1 = pattern1.matcher(line);
					while (matcher1.find()) {
						name = matcher1.group(1);
						age = Integer.valueOf(matcher1.group(2));
						sex = matcher1.group(3).charAt(0);
						this.addCentralObject(new Person(name, age, sex));
					}
					pattern2 = Pattern.compile("Friend\\s*::=\\s*<" + labelRegex + commaRegex + "(\\d*)" + commaRegex
							+ "([M|F]{1})" + ">");
					matcher2 = pattern2.matcher(line);
					while (matcher2.find()) {
						name = matcher2.group(1);
						age = Integer.valueOf(matcher2.group(2));
						sex = matcher2.group(3).charAt(0);
						friends.add(friendFactory.produce(name, age, sex));
					}

					pattern3 = Pattern.compile("SocialTie\\s*::=\\s*<" + labelRegex + commaRegex + labelRegex
							+ commaRegex + "(0.\\d{1,3})>");
					matcher3 = pattern3.matcher(line);
					while (matcher3.find()) {
						Double intimacy = new Double(Double.parseDouble(matcher3.group(3)));
						String names[] = new String[2];
						names[0] = matcher3.group(1);
						names[1] = matcher3.group(2);
						adjacency.put(names, intimacy);
					}
				} // end while
				for (String[] names : adjacency.keySet()) {
					double infimacy = adjacency.get(names);
					if (names[0].equals(this.centralObject.getName())) {
						for (PhysicalObject friend : friends) {
							if (friend.getName().equals(names[1])) {
								centralEdges[0].put(friend, infimacy);
								this.addRelationshipBetweenCentralAndPhysical(friend, true, infimacy);
//								this.addRelationshipBetweenCentralAndPhysical(friend, false, infimacy);
							}
						}
					} else if (names[1].equals(this.centralObject.getName())) {
						for (PhysicalObject friend : friends) {
							if (friend.getName().equals(names[0])) {
								centralEdges[1].put(friend, infimacy);
//								this.addRelationshipBetweenCentralAndPhysical(friend, true, infimacy);
								this.addRelationshipBetweenCentralAndPhysical(friend, false, infimacy);
							}
						}
					} else {
						PhysicalObject friendA = null, friendB = null;
						for (PhysicalObject friend : friends) {
							if (friend.getName().equals(names[0])) {
								friendA = friend;
							}
							if (friend.getName().equals(names[1])) {
								friendB = friend;
							}
						}
						physicalEdges.add(new Edge<PhysicalObject>(friendA, friendB, infimacy));
						this.addRelationshipBetweenPhysicalAndPhysical(friendA, friendB, infimacy);
//						this.addRelationshipBetweenPhysicalAndPhysical(friendB, friendA, infimacy);
					}
				}
				constructSocialNetworkCircle(friends, physicalEdges, centralEdges);
				br.close();
			} catch (Exception e) {

			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
			System.exit(-1);
		}
	}

	@Override
	public void constructSocialNetworkCircle(List<PhysicalObject> friends, List<Edge<PhysicalObject>> physicalEdges,
			Map<PhysicalObject, Double>[] centralEdges) {
		Map<PhysicalObject, Integer> trackRadiusMap = getSocialTrackRadius(friends, physicalEdges, centralEdges);
		int maxRaidus = 0;
		TrackFactory trackFactory = new TrackFactory();
		for (PhysicalObject p : trackRadiusMap.keySet()) {
			if (trackRadiusMap.get(p) > maxRaidus) {
				maxRaidus = trackRadiusMap.get(p);
			}
		}
		for (int i = 1; i <= maxRaidus; i++) {
			this.addTrack(trackFactory.produce(i));
		}

		for (PhysicalObject p : trackRadiusMap.keySet()) {
			if (trackRadiusMap.get(p) > 0) {
				this.addPhysicalObjectToTrack(p, trackFactory.produce(trackRadiusMap.get(p)));
			}
		}
	}

	/**
	 * Get a mapping from physical objects to radius(integer) of the track in which
	 * the physical object is. This mapping indicates in which track(what radius) a
	 * physical object should be.
	 * 
	 * @param friends       a list of all physical objects
	 * @param physicalEdges a list of edges which represents adjacency about
	 *                      physical objects
	 * @param centralEdges  consists of two mappings from physical objects to
	 *                      intimacy, map[0] represents adjacency from central
	 *                      object to physical objects, map[1] represents adjacency
	 *                      from physical objects to central object
	 * @return a mapping from physical objects to radius(integer) of the track in
	 *         which the physical object is
	 */
	public static Map<PhysicalObject, Integer> getSocialTrackRadius(List<PhysicalObject> friends,
			List<Edge<PhysicalObject>> physicalEdges, Map<PhysicalObject, Double>[] centralEdges) {
		Map<PhysicalObject, Integer> distance = new HashMap<PhysicalObject, Integer>();
		Map<PhysicalObject, Boolean> markedMap = new HashMap<PhysicalObject, Boolean>();
		PhysicalObject center = new Friend("center", 1, 'M');
		Queue<PhysicalObject> queue = new LinkedList<PhysicalObject>(); // BFS iteration queue
		/*
		 * edgeTo mapping: assume A maps to B, representing A has direct relation with B. And in all directed path
		 * from source to target, A is backward B and B is forward A.
		 */
		HashMap<PhysicalObject, PhysicalObject> edgeTo = new HashMap<PhysicalObject, PhysicalObject>();
		/* Every person iteration marked initialize false */
		for (PhysicalObject p : friends)
			markedMap.put(p, false);
		/* BFS iteration, getting edgeTo */
		for (PhysicalObject p : centralEdges[0].keySet()) {
			if (!markedMap.get(p)) {
				edgeTo.put(p, center);
				markedMap.put(p, true);
				queue.add(p);
				distance.put(p, 1);
			}
		}
		while (!queue.isEmpty()) {
			PhysicalObject tempPerson = queue.remove();
			int tempDistance = distance.get(tempPerson);
			/* Traversing all adjacent points of the elements of the dequeue */

			for (PhysicalObject p : CircularOrbitAPIs.targets(tempPerson, physicalEdges).keySet()) {
				if (!markedMap.get(p)) { // Found a vertex that has not been traversed
					edgeTo.put(p, tempPerson); // record pre-post relationship
					markedMap.put(p, true); // marked true
					queue.add(p); // a new traversed vertex enqueue
					distance.put(p, tempDistance + 1);
				}
			}
		}
		for (PhysicalObject p : markedMap.keySet()) {
			if (!markedMap.get(p)) {
				distance.put(p, -1);
			}
		}
		/* 
		 * At this point, the BFS traversal is completed, and the edgeTo is used to reverse 
		 * the path from the source point to the target point, meanwhile the path length is calculated. 
		 */
		return distance;
	}

	/**
	 * This method is non-existent in atom structure.
	 */
	@Override
	public Memento<Person, PhysicalObject> save() {
		assert false : "shouldn't have this method";
		return null;
	}

	/**
	 * This method is non-existent in atom structure.
	 */
	@Override
	public void restore(Memento<Person, PhysicalObject> m) {
		assert false : "shouldn't have this method";
	}
}
