package circularorbit;

import java.util.List;
import java.util.Map;
import java.util.Set;
import atomtransitionmement.Memento;
import myexception.NoObjectOnTrackException;
import physicalobject.PhysicalObject;
import track.Track;

/**
 * CircularOrbit extends Iterable.
 * 
 * @author Shen
 *
 * @param <L> class of central object in circular orbit which is one of Star, Person and Nucleus.
 * @param <E> class of physical object in circular orbit which is mostly PhysicalObject.
 */
public interface CircularOrbit<L, E> extends Iterable<E> {

  /**
   * Mutator. Add a track into circular orbit.
   * 
   * @param track an added track
   */
  public void addTrack(Track track);

  /**
   * Mutator. Delete a track from circular orbit.
   * 
   * @param track the deleted track
   */
  public void deleteTrack(Track track);

  /**
   * Mutator. Add a central object into circular orbit. This method should only perform in circular
   * orbit with central object.
   * 
   * @param centralObject a central object
   */
  public void addCentralObject(L centralObject);

  /**
   * Mutator. Add a physical object into a track.
   * 
   * @param physicalObject the added physical object
   * @param track the target track
   */
  public void addPhysicalObjectToTrack(E physicalObject, Track track);

  /**
   * Mutator. Add a relationship with a intimacy of weight between the central object and a physical
   * object. This method should only perform in circular orbit with relationship which is mostly
   * social network circle.
   * 
   * @param physicalObject the physical object in this relationship
   * @param weight the intimacy of this relationship, it should be a decimal from zero to one.
   */
  public void addRelationshipBetweenCentralAndPhysical(E physicalObject, double weight);

  /**
   * Mutator. Add a relationship between physicalObject A and physicalObject B with a intimacy of
   * weight. This method should only perform in circular orbit with relationship.
   * 
   * @param physicalObjectA the source physical object of this relationship
   * @param physicalObjectB the target physical object of this relationship
   * @param weight the intimacy of this relationship, it should be a decimal from zero to one
   */
  public void addRelationshipBetweenPhysicalAndPhysical(E physicalObjectA, E physicalObjectB,
      double weight);

  // /**
  // * Read data from file. All certain circular orbits are different so this method will be
  // override
  // * in each particular class. But no matter what kind of circular orbit, before it's read from
  // * file, it's representations must be reset.
  // *
  // * @param file the data file
  // * @throws FileNotFoundException file not found exception
  // * @throws AtomElectronNumException This exception is about electron number in atom structure.
  // * This includes that data doesn't indicate electron number for some tracks, number of
  // * track doesn't match the number of electron. information.
  // * @throws AtomTrackNumException This exception is about track number in atom structure. The
  // * number of track is not positive integer in data file.
  // * @throws AtomElementException This exception is about nucleus element in atom structure. This
  // * includes that element of atom contains 0 or more than 2 characters, the first character
  // * is not a upper case character, or the second character is not a lower case character.
  // * @throws IOException This exception may happen when read file with BufferedReader.
  // * @throws NumberFormatException This exception may happen like when parameter in
  // * Integer.parseOf() doesn't match format.
  // * @throws DataSyntaxException This exception is about data in file may doesn't fit syntax in
  // * specification. When this exception happens, it means some components in format data
  // * fail to match regular expression.
  // * @throws DataScientificNumberException This exception is about illegal scientifc number
  // * notation. This includes that the mantissa is not between 1 and 10 or the exponent is
  // * less than 4.
  // * @throws IllegalIntimacyInSocialTieException This exception may happens only in social network
  // * circle. When this exception happens, it means the intimacy of a social tie is no more
  // * than 0 or greater than 1.
  // */
  // public void readFromFile(File file) throws FileNotFoundException, NumberFormatException,
  // IOException, AtomElementException, AtomTrackNumException, AtomElectronNumException,
  // DataSyntaxException, DataScientificNumberException, IllegalIntimacyInSocialTieException;

  public void readFromFile();

  // public void writeToFile(File file) throws IOException;

  // /**
  // * Construct social network circle according to list of physical objects, list of adjacency
  // edges,
  // * from-center adjacency map from physical object to intimacy and to-center adjacency map from
  // * physical object to intimacy. This method should only be called when reading from file or
  // * reconstructing.
  // */
  // public void constructSocialNetworkCircle(List<PhysicalObject> friends,
  // List<Edge<PhysicalObject>> physicalEdges, Map<PhysicalObject, Double> centralEdges);
  /**
   * Construct social network circle according to list of physical objects, list of adjacency edges,
   * from-center adjacency map from physical object to intimacy and to-center adjacency map from
   * physical object to intimacy. This method should only be called when reading from file or
   * reconstructing.
   */
  public void constructSocialNetworkCircle(Map<String, PhysicalObject> friends);

  /**
   * Mutator. Delete a relationship between physicalObject A and physicalObject B.
   * 
   * @param physicalObjectA the source physical object of the deleted relationship
   * @param physicalObjectB the target physical object of the deleted relationship
   */
  public void deleteRelationshipBetweenPhysicalAndPhysical(E physicalObjectA, E physicalObjectB);

  /**
   * Mutator. Delete a physical object from the certain track.
   * 
   * @param physicalObject the deleted physical object
   * @param track the track in which the deleted physical object is
   * @throws NoObjectOnTrackException This exception happens when the
   */
  public void deletePhysicalObjectFromTrack(E physicalObject, Track track)
      throws NoObjectOnTrackException;

  /**
   * Mutator. Delete all physical objects in tracks. This method should only perform when the
   * circular orbit is restructured.
   */
  public void resetObjectsAndTrack();

  /**
   * Observer. Get central object in circular orbit. This method should only perform in circular
   * orbit with central object.
   * 
   * @return central object in this circular orbit
   */
  public L getCentralObject();

  /**
   * Observer. Get a defensive copy of the list of tracks in this circular orbit.
   * 
   * @return a defensive copy of the list of tracks in this circular orbit
   */
  public List<Track> getTracks();

  /**
   * Observer. Get a defensive copy of the map from tracks to lists of physical objects in circular
   * orbit. This method should only perform in circular orbit with relationship.
   * 
   * @return a defensive copy of the map from tracks to lists of physical objects in circular orbit
   */
  public Map<Track, List<E>> getObjectsInTrack();

  /**
   * Observer. Get a defensive copy of two maps from physical objects to intimacy in circular orbit.
   * This method should only perform in circular orbit with relationship.
   * 
   * @return a defensive copy of two maps from physical objects to intimacy in circular orbit
   */
  public Map<E, Double> getRelationBetweenCentralAndObject();

  /**
   * Observer. Get a defensive copy of the list of edges representing adjacency relationships. This
   * method should only perform in circular orbit with relationship.
   * 
   * @return a defensive copy of the list of edges representing adjacency relationships
   */
  public Set<Edge<E>> getRelationBetweenObjects();

  /**
   * Save the current state into a Memento and return this Memento. This method should only perform
   * in atom structure.
   * 
   * @return a Memento storing the current state
   */
  public Memento<L, E> save();

  /**
   * Restore a historical state from a Memento. This method should only perform in atom structure.
   * 
   * @param m a Memento storing a historical state
   */
  public void restore(Memento<L, E> m);
}
