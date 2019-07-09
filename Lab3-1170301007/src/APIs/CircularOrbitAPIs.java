package APIs;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import centralObject.Person;
import centralObject.Star;
import circularOrbit.CircularOrbit;
import circularOrbit.Edge;
import physicalObject.Friend;
import physicalObject.PhysicalObject;
import physicalObject.Planet;
import track.Track;

/**
 * CircularOrbitAPIs includes all the operations that clients may use in
 * concrete applications. When a client wants to use these function operations,
 * he needs to instantiate an CircularOrbitAPIs object. Not all function
 * operations in CircularOrbitAPIs can be used in all kinds of circular orbits.
 * The combination of class of central and physical objects should be determined
 * by clients and it's suggested that this combination fits specific
 * applications scenarios.
 * 
 * @author Shen
 *
 * @param <L> class of central object, should be one of Nucleus, Star and
 *        Person.
 * @param <E> class of physical object, which is supposed to be PhysicalObject
 *        mostly.
 */
public class CircularOrbitAPIs<L, E> {

	/**
	 * Constructor
	 */
	public CircularOrbitAPIs() {

	}

	/**
	 * Calculate distribution entropy of objects in circular orbit. The more
	 * dispersed the physical objects are, the larger the entropy is.
	 * 
	 * The calculation method is described as followed: if this circular orbit has n
	 * tracks and for i-th track there are a_i objects, supposing that the total of
	 * physical objects is m, then distribution entropy is calculated by Σ((a_i/m) *
	 * log(a_i/m))
	 * 
	 * @param c any circular orbit
	 * @return distribution entropy of objects in this circular orbit
	 */
	public double getObjectDistributionEntropy(CircularOrbit<L, E> c) {
		double entropy = 0;
		Map<Track, List<E>> objectsInTrack = c.getObjectsInTrack();
		int objectsNum = 0, trackNum = 0;
		int[] objectsNumEachTrack = new int[objectsInTrack.keySet().size()];
		for (List<E> l : objectsInTrack.values()) {
			objectsNum += l.size();
			objectsNumEachTrack[trackNum++] = l.size();
		}
		for (int i = 0; i < trackNum; i++) {
			double possibility = (double) objectsNumEachTrack[i] / objectsNum;
			entropy -= possibility * Math.log(possibility);
		}
		return entropy;
	}

	/**
	 * Calculate logical distance from e1 to e2 in circular orbit. Logical distance
	 * is the length of the shortest path from e1 to e2. This method should only
	 * perform in circular orbit with relationship.
	 * 
	 * Attention: If there's need to calculate logical distance between a central
	 * object and a physical object, particularly in social network circle, a "fake"
	 * central object is allowed to use. It means if you want to calculate logical
	 * distance between a central object and a physical object in social network
	 * circle, e1 or e2 can be a "fake" central object. This "fake" central object
	 * is defined as a friend named "center", aged 1 and male. This "fake" central
	 * object has distinctive features. Mostly, no friends are equal to this "fake"
	 * one. Therefore, it can be used as central object.
	 * 
	 * @param c  circular orbit with relationship which is mostly social network
	 *           circle
	 * @param e1 source object in relationship
	 * @param e2 target object in relationship
	 * @return logical distance(length of the shortest path) from e1 to e2
	 */
	public int getLogicalDistance(CircularOrbit<L, E> c, E e1, E e2) {
		if (e1.getClass() == Friend.class && e2.getClass() == Friend.class) {
			@SuppressWarnings("unchecked")
			Map<PhysicalObject, Double>[] relationBetweenCentralAndObject = ((CircularOrbit<L, PhysicalObject>) c)
					.getRelationBetweenCentralAndObject();
			@SuppressWarnings("unchecked")
			List<Edge<PhysicalObject>> physicalEdges = ((CircularOrbit<L, PhysicalObject>) c)
					.getRelationBetweenObjects();
			int distance = 0;
			Map<PhysicalObject, Boolean> markedMap = new HashMap<PhysicalObject, Boolean>();
			List<PhysicalObject> friends = new ArrayList<PhysicalObject>();
			PhysicalObject center = new Friend("center", 1, 'M');
			Queue<PhysicalObject> queue = new LinkedList<PhysicalObject>(); // BFS iteration queue
			/*
			 * edgeTo mapping: assume A maps to B, representing A has direct relation with B. And in all directed path
			 * from source to target, A is backward B and B is forward A.
			 */
			HashMap<PhysicalObject, PhysicalObject> edgeTo = new HashMap<PhysicalObject, PhysicalObject>();
			/* Every person iteration marked initialize false */
			for (Track track : c.getTracks()) {
				for (E e : c.getObjectsInTrack().get(track)) {
					friends.add((PhysicalObject) e);
				}
			}
			for (PhysicalObject p : friends) {
				markedMap.put(p, false);
			}
			markedMap.put(center, false);
			for (PhysicalObject e : relationBetweenCentralAndObject[0].keySet()) {
				physicalEdges.add(new Edge<PhysicalObject>(center, e, relationBetweenCentralAndObject[0].get(e)));
			}
			for (PhysicalObject e : relationBetweenCentralAndObject[1].keySet()) {
				physicalEdges.add(new Edge<PhysicalObject>(e, center, relationBetweenCentralAndObject[1].get(e)));
			}
			queue.add((PhysicalObject) e1);
			markedMap.put((PhysicalObject) e1, true);
			/* BFS iteration, getting edgeTo */
			while (!queue.isEmpty()) {
				PhysicalObject tempPerson = queue.remove();
				for (PhysicalObject e : targets(tempPerson, physicalEdges).keySet()) {
					if (!markedMap.get(e)) { // Found a vertex that has not been traversed
						edgeTo.put(e, tempPerson); // record pre-post relationship
						markedMap.put(e, true); // marked true
						queue.add(e); // a new traversed vertex enqueue
					}
				}
			}
			if (!markedMap.get((PhysicalObject) e2)) {
				distance = -1;
			} else
				for (PhysicalObject e = (PhysicalObject) e2; !e.equals(e1); e = edgeTo.get(e))
					distance++;
			return distance;
		}
		return -1;
	}

	/**
	 * Get a mapping from physical objects to which are adjacent from source, to
	 * intimacy of these relationship. Actually, this method collect adjacency only
	 * related with source physical object of all adjacency. This method should only
	 * perform in a circular orbit with relationship which is mostly social network
	 * circle.
	 * 
	 * @param source        a source physical object
	 * @param physicalEdges a list of edges representing all adjacency
	 * @return a mapping from physical objects to which are adjacent from source, to
	 *         intimacy of these relationship
	 */
	public static Map<PhysicalObject, Double> targets(PhysicalObject source, List<Edge<PhysicalObject>> physicalEdges) {
		Map<PhysicalObject, Double> targetsMap = new HashMap<PhysicalObject, Double>();
		for (Edge<PhysicalObject> edge : physicalEdges) {
			if (edge.getSource().equals(source)) {
				targetsMap.put(edge.getTarget(), edge.getWeight());
			}
		}
		return targetsMap;
	}

	/**
	 * Calculate physical distance from e1 to e2 in circular orbit. This method
	 * should only perform in a circular orbit with accurate physical object
	 * position which is mostly stellar system. Particularly in stellar system, the
	 * unit of physical distance is kilometer.
	 * 
	 * @param c  circular orbit with accurate physical object position which is
	 *           mostly stellar system.
	 * @param e1 a physical object
	 * @param e2 a physical object
	 * @return physical distance from e1 to e2
	 */
	public double getPhysicalDistance(CircularOrbit<L, E> c, E e1, E e2) {
		if (e1.getClass() == Planet.class && e2.getClass() == Planet.class) {
			double degree1 = ((Planet) e1).getDegree() * Math.PI / 180;
			double degree2 = ((Planet) e2).getDegree() * Math.PI / 180;
			double longRadius1 = 0, shortRadius1 = 0, longRadius2 = 0, shortRadius2 = 0;
			for (Track track : c.getTracks()) {
				for (E e : c.getObjectsInTrack().get(track)) {
					if (e.getClass() == Planet.class && ((Planet) e).getName().equals(((Planet) e1).getName())) {
						longRadius1 = track.getLongRadius();
						shortRadius1 = track.getShortRadius();
					}
					if (e.getClass() == Planet.class && ((Planet) e).getName().equals(((Planet) e2).getName())) {
						longRadius2 = track.getLongRadius();
						shortRadius2 = track.getShortRadius();
					}
				}
			}
			double x1 = longRadius1 * Math.cos(degree1);
			double y1 = shortRadius1 * Math.sin(degree1);
			double x2 = longRadius2 * Math.cos(degree2);
			double y2 = shortRadius2 * Math.sin(degree2);
			return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
		}
		return 0;
	}

	/**
	 * Calculate physical distance from central object to a physical object in
	 * circular orbit. This method should only perform in a circular orbit with
	 * accurate physical object position which is mostly stellar system.
	 * Particularly in stellar system, the unit of physical distance is kilometer.
	 * 
	 * @param c circular orbit with accurate physical object position which is
	 *          mostly stellar system.
	 * @param e a physical object
	 * @return physical distance from central object to e
	 */
	public double getPhysicalDistanceFromCentralToObject(CircularOrbit<L, E> c, E e) {
		if (e.getClass() == Planet.class) {
			for (Track track : c.getTracks()) {
				for (E e1 : c.getObjectsInTrack().get(track)) {
					if (e1.equals(e)) {
						double a = track.getLongRadius();
						double b = track.getShortRadius();
						double degree = ((Planet) e1).getDegree() * Math.PI / 180;
						double distance = Math
								.sqrt(Math.pow((a * Math.cos(degree)), 2) + Math.pow((b * Math.sin(degree)), 2));
						return distance;
					}
				}
			}
		}
		return 0;
	}

	/**
	 * Get differences between two circular orbits. These two circular orbits must
	 * be the same type.
	 * 
	 * @param c1 a circular orbit
	 * @param c2 a circular orbit
	 * @return differences between these two circular orbits
	 * @throws Exception two circular orbits are not the same type
	 */
	public Difference getDifference(CircularOrbit<L, E> c1, CircularOrbit<L, E> c2) throws Exception {
		if (c1.getCentralObject().getClass() != c2.getCentralObject().getClass()) {
			throw new Exception("Two circular orbits are not the same type!");
		}
		Difference difference = new Difference();
		int trackDiff = c1.getTracks().size() - c2.getTracks().size();
		difference.setTrackDiff(trackDiff);
		int trackNum_ = trackDiff > 0 ? c1.getTracks().size() : c2.getTracks().size();
		int trackNum = trackDiff > 0 ? c2.getTracks().size() : c1.getTracks().size();
		for (int i = 0; i < trackNum; i++) {
			int objectsNumDiff = c1.getObjectsInTrack().get(c1.getTracks().get(i)).size()
					- c2.getObjectsInTrack().get(c2.getTracks().get(i)).size();
			difference.setObjectsNumDiff(i, objectsNumDiff);
		}
		for (int i = trackNum; i < trackNum_; i++) {
			int objectsNumDiff = trackDiff > 0 ? c1.getObjectsInTrack().get(c1.getTracks().get(i)).size()
					: -c2.getObjectsInTrack().get(c2.getTracks().get(i)).size();
			difference.setObjectsNumDiff(i, objectsNumDiff);
		}
		if (c1.getCentralObject().getClass() == Person.class || c1.getCentralObject().getClass() == Star.class) {
			int length = 0;
			for (int i = 0; i < trackNum; i++) {
				String a1 = "", a2 = "";
				length = 0;
				for (E friend : c1.getObjectsInTrack().get(c1.getTracks().get(i))) {
					if (!c2.getObjectsInTrack().get(c2.getTracks().get(i)).contains(friend)) {
						a1 += ((PhysicalObject) friend).getName() + ",";
						length++;
					}
				}
				if (length > 0) {
					a1 = a1.substring(0, a1.length() - 1);
					if (length > 1) {
						a1 = "{" + a1 + "}";
					}
				} else {
					a1 += "无";
				}
				length = 0;
				for (E friend : c2.getObjectsInTrack().get(c2.getTracks().get(i))) {
					if (!c1.getObjectsInTrack().get(c1.getTracks().get(i)).contains(friend)) {
						a2 += ((PhysicalObject) friend).getName() + ",";
						length++;
					}
				}
				if (length > 0) {
					a2 = a2.substring(0, a2.length() - 1);
					if (length > 1) {
						a2 = "{" + a2 + "}";
					}
				} else {
					a2 += "无";
				}
				if (a1.equals(a2)) {
					difference.setObjectsDiff(i, "无");
				} else {
					difference.setObjectsDiff(i, a1 + "-" + a2);
				}
			}
			for (int i = trackNum; i < trackNum_; i++) {
				String a = "";
				length = 0;
				if (trackDiff > 0) {
					for (E friend : c1.getObjectsInTrack().get(c1.getTracks().get(i))) {
						a += ((PhysicalObject) friend).getName() + ",";
						length++;
					}
				} else {
					for (E friend : c2.getObjectsInTrack().get(c2.getTracks().get(i))) {
						a += ((PhysicalObject) friend).getName() + ",";
						length++;
					}
				}
				if (length == 0) {
					a = "无";
				} else if (length == 1) {
					a = a.substring(0, a.length() - 1);
				} else if (length > 1) {
					a = a.substring(0, a.length() - 1);
					a = "{" + a + "}";
				}
				String objectDiff = trackDiff > 0 ? a + "-无" : "无-" + a;
				difference.setObjectsDiff(i, objectDiff);
			}
		}
		return difference;
	}

	/**
	 * This class defines differences between two circular orbits with same type.
	 * Difference includes differences of number of tracks, number of objects in the
	 * same track position and differences between two set of objects in the same
	 * track position, if this kind of circular orbit accepts difference of objects.
	 * This result will show as a list of message.
	 * 
	 * @author Shen
	 *
	 */
	public class Difference {
		private int trackDiff;
		private List<Integer> objectsNumDiff = new ArrayList<Integer>();
		private List<String> objectsDiff = new ArrayList<String>();

		/**
		 * Constructor
		 */
		public Difference() {

		}

		/**
		 * Mutator. Set difference of number of tracks.
		 * 
		 * @param a difference of number of tracks which is the number of tracks in the
		 *          first circular orbit minus the number of the second
		 */
		public void setTrackDiff(int a) {
			this.trackDiff = a;
		}

		/**
		 * Mutator. Set difference of number of objects in the same track position.
		 * 
		 * @param index track position
		 * @param a     difference which is the number of objects in the first circular
		 *              orbit minus the number of the second in the same track position.
		 */
		public void setObjectsNumDiff(int index, Integer a) {
			this.objectsNumDiff.add(index, a);
		}

		/**
		 * Mutator. Set difference of objects in the same track position. This method
		 * should only perform when circular orbits accept difference of objects which
		 * are mostly stellar system and social network circle.
		 * 
		 * @param index track position, i.e. index-th track from inside to outside.
		 * @param a     a string message like {A}-{B}. A shows a set of objects in the
		 *              first circular orbit but not in the second, while B shows a set
		 *              of objects in the second but not in the first. If there's only
		 *              one object in A or B, then there'll be no curly braces around
		 *              it.
		 */
		public void setObjectsDiff(int index, String a) {
			this.objectsDiff.add(index, a);
		}

		/**
		 * Override toString. Because we need Difference to show as a string message,
		 * the content basically includes difference of number of tracks, difference of
		 * number of objects in same track position and difference of objects in same
		 * track position if objects can tell difference.
		 */
		@Override
		public String toString() {
			String difference = "";
			difference += "轨道数差异：" + this.trackDiff + "\n";
			for (int i = 1; i <= this.objectsNumDiff.size(); i++) {
				difference += "轨道" + i + "的物体数量差异：" + this.objectsNumDiff.get(i - 1);
				if (!this.objectsDiff.isEmpty()) {
					difference += "；物体差异：" + this.objectsDiff.get(i - 1) + "\n";
				} else {
					difference += "\n";
				}
			}
			return difference;
		}
	}

	/**
	 * Simulate planets movement in stellar system. This method should only perform
	 * in stellar system.
	 * 
	 * @param c a stellar system
	 */
	public void planetMovingSimulate(CircularOrbit<L, PhysicalObject> c) {
		JFrame frame = new JFrame("Simulate Stellar System Movement");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new VisualPanel(c));
		frame.setSize(800, 800);
		frame.setVisible(true);
	}

	/**
	 * VisualPanel creates a JPanel where a animation simulating planets movement
	 * shows.
	 * 
	 * @author Shen
	 *
	 */
	private class VisualPanel extends JPanel {
		private static final int frameSize = 800; // square frame pixel size
		private static final int centralCoordinate = frameSize / 2; // central position in frame
		private static final int objectSize = 10; // physical object dot radius pixel
		private static final int centralSize = 20; // central object dot radius pixel
		private static final int radiusScale = 35; // enlarge track radius by raidusScale times
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private CircularOrbit<L, PhysicalObject> c;
		private int delay = 33;
		private long startTime;
		javax.swing.Timer timer;

		/**
		 * Constructor. In this constructor, a timer is set up and startTime is
		 * initialize which can be used to calculate how long it has passed in this
		 * simulation. Besides, a panel is created for showing the animation. And start
		 * time is recorded for calculating running time.
		 * 
		 * @param c a stellar system
		 */
		public VisualPanel(CircularOrbit<L, PhysicalObject> c) {
			this.c = c;
			timer = new javax.swing.Timer(delay, new VisualListener());
			JPanel panel = new JPanel();
			add(panel);
			timer.start();
			startTime = System.currentTimeMillis();
		}

		/**
		 * Every time repaint() method is called, so is this method. It is used to draw
		 * all planets' position and tracks in the current time.
		 */
		public void paintComponent(Graphics canvas) {
			super.paintComponent(canvas);
			DecimalFormat df = new DecimalFormat("######0");
			canvas.setColor(Color.BLACK);
			canvas.fillOval(centralCoordinate - centralSize / 2, centralCoordinate - centralSize / 2, centralSize,
					centralSize);
			List<Track> tracks = this.c.getTracks();
			for (int i = 0; i < this.c.getTracks().size(); i++) {
				double scale = tracks.get(i).getLongRadius() / tracks.get(i).getShortRadius();
				int drawLongRadius = Integer.parseInt(df.format(radiusScale * (i + 1) * scale));
				int drawShortRadius = radiusScale * (i + 1);
				canvas.drawOval(centralCoordinate - drawLongRadius, centralCoordinate - drawShortRadius,
						2 * drawLongRadius, 2 * drawShortRadius);
				for (PhysicalObject planet : this.c.getObjectsInTrack().get(this.c.getTracks().get(i))) {
					long time = (System.currentTimeMillis() - startTime) * 1000;
					double degree = planet.getDegree();
					boolean direct = planet.getDirect();
					double speed = planet.getSpeed();
					double radius = this.c.getTracks().get(i).getLongRadius();
					double theta = time * speed / radius;
					double degree_ = direct ? degree + theta : degree - theta;
					degree_ = degree_ / Math.PI * 180 % 360;

					int x = centralCoordinate
							+ Integer.parseInt(df.format(drawLongRadius * Math.cos(degree_ * Math.PI / 180)));
					int y = centralCoordinate
							- Integer.parseInt(df.format(drawShortRadius * Math.sin(degree_ * Math.PI / 180)));
					canvas.fillOval(x - objectSize / 2, y - objectSize / 2, objectSize, objectSize);
				}
			}
		}

		/**
		 * 
		 * @author Shen
		 *
		 */
		private class VisualListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
			}
		}
	}
}
