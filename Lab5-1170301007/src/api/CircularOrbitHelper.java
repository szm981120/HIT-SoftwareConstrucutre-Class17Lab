package api;

import java.awt.Color;
import java.awt.Graphics;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.JPanel;
import circularorbit.CircularOrbit;
import circularorbit.Edge;
import circularorbit.SocialNetworkCircle;
import physicalobject.PhysicalObject;
import track.Track;

/**
 * CircularOrbitHelper provides only one static method for clients which is used to visualize the
 * static picture of circular orbit.
 * 
 * @author Shen
 *
 */
public class CircularOrbitHelper {

  private static final int FLAT_ANGLE = 180;

  private static final int FRAME_SIZE = 800; // square frame pixel size

  private static final int CENTRAL_COORDINATE = FRAME_SIZE / 2; // central position in frame

  private static final int OBJECT_SIZE = 10; // physical object dot radius pixel

  private static final int CENTRAL_SIZE = 20; // central object dot radius pixel

  private static final int RADIUS_SCALE = 35; // enlarge track radius by raidusScale times

  /**
   * Constructor.
   */
  public CircularOrbitHelper() {}

  /**
   * Visualize the circular orbit. The visualization picture contains all tracks which are showed
   * equidistant, all physical objects in certain tracks and a central object. And if this circular
   * orbit accepts relationship between objects, it will be showed.
   * 
   * @param c circular orbit
   */
  public static <L, E> void visualize(CircularOrbit<L, PhysicalObject> c) {
    JFrame frame = new JFrame("Circular Orbit-" + c.getClass().toString());
    frame.setSize(FRAME_SIZE, FRAME_SIZE);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().add(new JPanel());
    frame.setResizable(false);
    frame.setVisible(true);
    JPanel panel = new JPanel() {

      private static final long serialVersionUID = 1L;

      /**
       * Override paint so that it can paint tracks, central object, physical objects in tracks and
       * relationship.
       */
      @Override
      public void paint(Graphics g) {
        super.paint(g);
        DecimalFormat df = new DecimalFormat("######0");
        g.setColor(Color.BLACK);
        g.fillOval(CENTRAL_COORDINATE - CENTRAL_SIZE / 2, CENTRAL_COORDINATE - CENTRAL_SIZE / 2,
            CENTRAL_SIZE, CENTRAL_SIZE);
        List<Track> tracks = c.getTracks();
        for (int i = 1; i <= tracks.size(); i++) {
          int drawRadius = RADIUS_SCALE * i;
          g.drawOval(CENTRAL_COORDINATE - drawRadius, CENTRAL_COORDINATE - drawRadius,
              2 * drawRadius, 2 * drawRadius);
        }
        Map<Track, List<PhysicalObject>> objectsInTrack = c.getObjectsInTrack();
        for (int i = 1; i <= tracks.size(); i++) {
          int drawRadius = RADIUS_SCALE * i;
          List<PhysicalObject> objects = objectsInTrack.get(tracks.get(i - 1));
          for (int j = 0; j < objects.size(); j++) {
            double theta = objects.get(j).getDegree();
            int x = CENTRAL_COORDINATE
                + Integer.parseInt(df.format(drawRadius * Math.cos(theta * Math.PI / FLAT_ANGLE)));
            int y = CENTRAL_COORDINATE
                - Integer.parseInt(df.format(drawRadius * Math.sin(theta * Math.PI / FLAT_ANGLE)));
            g.fillOval(x - OBJECT_SIZE / 2, y - OBJECT_SIZE / 2, OBJECT_SIZE, OBJECT_SIZE);
          }
        }
        if (c.getClass() == SocialNetworkCircle.class) {
          for (PhysicalObject p : c.getRelationBetweenCentralAndObject().keySet()) {
            drawRelationBetweenCentralAndObject(p, Color.BLACK, g);
          }
          drawRelationBetweenObjects(c, objectsInTrack, g);
        }
      } // end paint
    };
    frame.setContentPane(panel);
  } // end visualize

  /**
   * Draw relationship between central object and a physical object. This should only draw one
   * single relationship between center and one certain object in first(innermost) track.
   * 
   * @param p physical object with relationship with center
   * @param color painting color
   * @param g Graphics object, used to draw relationship
   */
  private static void drawRelationBetweenCentralAndObject(PhysicalObject p, Color color,
      Graphics g) {
    DecimalFormat df = new DecimalFormat("######0");
    int radius = 1 * RADIUS_SCALE;
    double degree = p.getDegree();
    int x = CENTRAL_COORDINATE
        + Integer.parseInt(df.format(radius * Math.cos(degree * Math.PI / FLAT_ANGLE)));
    int y = CENTRAL_COORDINATE
        - Integer.parseInt(df.format(radius * Math.sin(degree * Math.PI / FLAT_ANGLE)));
    g.setColor(color);
    g.drawLine(CENTRAL_COORDINATE, CENTRAL_COORDINATE, x, y);
  }

  /**
   * Draw all relationship between physical objects in circular orbit.
   * 
   * @param <L> class of central object
   * @param c circular orbit
   * @param objectsInTrack a mapping from tracks to a lists of physical objects
   * @param g Graphics object, used to draw relationship
   */
  private static <L> void drawRelationBetweenObjects(CircularOrbit<L, PhysicalObject> c,
      Map<Track, List<PhysicalObject>> objectsInTrack, Graphics g) {
    DecimalFormat df = new DecimalFormat("######0");
    g.setColor(Color.BLACK);
    Set<Edge<PhysicalObject>> relationBetweenObjects = c.getRelationBetweenObjects();
    for (Edge<PhysicalObject> edge : relationBetweenObjects) {
      PhysicalObject source = edge.getSource();
      PhysicalObject target = edge.getTarget();
      double degree1 = source.getDegree();
      double degree2 = target.getDegree();
      double radius1 = 0;
      double radius2 = 0;
      for (Track track : objectsInTrack.keySet()) {
        if (objectsInTrack.get(track).contains(source)) {
          radius1 = RADIUS_SCALE * track.getRadius();
        }
        if (objectsInTrack.get(track).contains(target)) {
          radius2 = RADIUS_SCALE * track.getRadius();
        }
      }
      int x1 = CENTRAL_COORDINATE
          + Integer.parseInt(df.format(radius1 * Math.cos(degree1 * Math.PI / FLAT_ANGLE)));
      int y1 = CENTRAL_COORDINATE
          - Integer.parseInt(df.format(radius1 * Math.sin(degree1 * Math.PI / FLAT_ANGLE)));
      int x2 = CENTRAL_COORDINATE
          + Integer.parseInt(df.format(radius2 * Math.cos(degree2 * Math.PI / FLAT_ANGLE)));
      int y2 = CENTRAL_COORDINATE
          - Integer.parseInt(df.format(radius2 * Math.sin(degree2 * Math.PI / FLAT_ANGLE)));
      g.drawLine(x1, y1, x2, y2);
    }
  }
}
