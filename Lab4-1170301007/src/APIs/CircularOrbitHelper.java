package APIs;

import java.awt.Color;
import java.awt.Graphics;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import circularOrbit.CircularOrbit;
import circularOrbit.SocialNetworkCircle;
import physicalObject.PhysicalObject;
import track.Track;

/**
 * CircularOrbitHelper provides only one static method for clients which is used
 * to visualize the static picture of circular orbit.
 * 
 * @author Shen
 *
 */
public class CircularOrbitHelper {

	private static final int frameSize = 800; // square frame pixel size
	private static final int centralCoordinate = frameSize / 2; // central position in frame
	private static final int objectSize = 10; // physical object dot radius pixel
	private static final int centralSize = 20; // central object dot radius pixel
	private static final int radiusScale = 35; // enlarge track radius by raidusScale times

	/**
	 * Constructor
	 */
	public CircularOrbitHelper() {

	}

	/**
	 * Visualize the circular orbit. The visualization picture contains all tracks
	 * which are showed equidistant, all physical objects in certain tracks and a
	 * central object. And if this circular orbit accepts relationship between
	 * objects, it will be showed.
	 * 
	 * @param c circular orbit
	 */
	public static <L, E> void visualize(CircularOrbit<L, PhysicalObject> c) {
		JFrame frame = new JFrame("Circular Orbit-" + c.getClass().toString());
		frame.setSize(frameSize, frameSize);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new JPanel());
		frame.setResizable(false);
		frame.setVisible(true);
		JPanel panel = new JPanel() {

			private static final long serialVersionUID = 1L;

			/**
			 * Override paint so that it can paint tracks, central object, physical objects
			 * in tracks and relationship.
			 */
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				DecimalFormat df = new DecimalFormat("######0");
				g.setColor(Color.BLACK);
				g.fillOval(centralCoordinate - centralSize / 2, centralCoordinate - centralSize / 2, centralSize,
						centralSize);
				List<Track> tracks = c.getTracks();
				for (int i = 1; i <= tracks.size(); i++) {
					int drawRadius = radiusScale * i;
					g.drawOval(centralCoordinate - drawRadius, centralCoordinate - drawRadius, 2 * drawRadius,
							2 * drawRadius);
				}
				Map<Track, List<PhysicalObject>> objectsInTrack = c.getObjectsInTrack();
				for (int i = 1; i <= tracks.size(); i++) {
					int drawRadius = radiusScale * i;

					List<PhysicalObject> objects = objectsInTrack.get(tracks.get(i - 1));
					for (int j = 0; j < objects.size(); j++) {
						double theta = objects.get(j).getDegree();
						int x = centralCoordinate
								+ Integer.parseInt(df.format(drawRadius * Math.cos(theta * Math.PI / 180)));
						int y = centralCoordinate
								- Integer.parseInt(df.format(drawRadius * Math.sin(theta * Math.PI / 180)));
						g.fillOval(x - objectSize / 2, y - objectSize / 2, objectSize, objectSize);
					}
				}
				if (c.getClass() == SocialNetworkCircle.class) {
					for (PhysicalObject p : c.getRelationBetweenCentralAndObject()[0].keySet()) {
						drawRelationBetweenCentralAndObject(p, Color.BLACK, g);
					}
					for (PhysicalObject p : c.getRelationBetweenCentralAndObject()[1].keySet()) {
						drawRelationBetweenCentralAndObject(p, Color.BLACK, g);
					}
					drawRelationBetweenObjects(c, objectsInTrack, g);
				}
			} // end paint
		};
		frame.setContentPane(panel);
	} // end visualize

	/**
	 * Draw relationship between central object and a physical object. This should
	 * only draw one single relationship between center and one certain object in
	 * first(innermost) track.
	 * 
	 * @param p     physical object with relationship with center
	 * @param color painting color
	 * @param g     Graphics object, used to draw relationship
	 */
	private static void drawRelationBetweenCentralAndObject(PhysicalObject p, Color color, Graphics g) {
		DecimalFormat df = new DecimalFormat("######0");
		int radius = 1 * radiusScale;
		double degree = p.getDegree();
		int x = centralCoordinate + Integer.parseInt(df.format(radius * Math.cos(degree * Math.PI / 180)));
		int y = centralCoordinate - Integer.parseInt(df.format(radius * Math.sin(degree * Math.PI / 180)));
		g.setColor(color);
		g.drawLine(centralCoordinate, centralCoordinate, x, y);
	}

	/**
	 * Draw all relationship between physical objects in circular orbit.
	 * 
	 * @param c              circular orbit
	 * @param objectsInTrack a mapping from tracks to a lists of physical objects
	 * @param g              Graphics object, used to draw relationship
	 */
	private static <L> void drawRelationBetweenObjects(CircularOrbit<L, PhysicalObject> c,
			Map<Track, List<PhysicalObject>> objectsInTrack, Graphics g) {
		DecimalFormat df = new DecimalFormat("######0");
		g.setColor(Color.BLACK);
		for (int i = 0; i < c.getRelationBetweenObjects().size(); i++) {
			PhysicalObject source = c.getRelationBetweenObjects().get(i).getSource();
			PhysicalObject target = c.getRelationBetweenObjects().get(i).getTarget();
			double degree1 = source.getDegree();
			double degree2 = target.getDegree();
			double radius1 = 0, radius2 = 0;
			for (Track track : objectsInTrack.keySet()) {
				if (objectsInTrack.get(track).contains(source)) {
					radius1 = radiusScale * track.getRadius();
				}
				if (objectsInTrack.get(track).contains(target)) {
					radius2 = radiusScale * track.getRadius();
				}
			}
			int x1 = centralCoordinate + Integer.parseInt(df.format(radius1 * Math.cos(degree1 * Math.PI / 180)));
			int y1 = centralCoordinate - Integer.parseInt(df.format(radius1 * Math.sin(degree1 * Math.PI / 180)));
			int x2 = centralCoordinate + Integer.parseInt(df.format(radius2 * Math.cos(degree2 * Math.PI / 180)));
			int y2 = centralCoordinate - Integer.parseInt(df.format(radius2 * Math.sin(degree2 * Math.PI / 180)));
			g.drawLine(x1, y1, x2, y2);
		}
	}
}
