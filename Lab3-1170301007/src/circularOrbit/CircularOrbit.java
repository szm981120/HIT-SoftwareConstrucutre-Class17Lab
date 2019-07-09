package circularOrbit;

import java.io.File;
import java.util.List;
import java.util.Map;

import atomTransitionMemento.Memento;
import physicalObject.PhysicalObject;
import track.Track;

/**
 * 
 * @author Shen
 *
 * @param <L> class of central object in circular orbit which is one of Star,
 *        Person and Nucleus.
 * @param <E> class of physical object in circular orbit which is mostly
 *        PhysicalObject.
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
	 * Mutator. Add a central object into circular orbit. This method should only
	 * perform in circular orbit with central object.
	 * 
	 * @param centralObject a central object
	 */
	public void addCentralObject(L centralObject);

	/**
	 * Mutator. Add a physical object into a track.
	 * 
	 * @param physicalObject the added physical object
	 * @param track          the target track
	 */
	public void addPhysicalObjectToTrack(E physicalObject, Track track);

	/**
	 * Mutator. Add a relationship with a intimacy of weight from the central object
	 * to a physical object or the other way round. This method should only perform
	 * in circular orbit with relationship which is mostly social network circle.
	 * 
	 * @param physicalObject the physical object in this relationship
	 * @param fromCentral    true if the relationship is from central object to
	 *                       physical object, false if the relationship is from
	 *                       physical object to central object
	 * @param weight         the intimacy of this relationship, it should be a
	 *                       decimal from zero to one.
	 */
	public void addRelationshipBetweenCentralAndPhysical(E physicalObject, boolean fromCentral, double weight);

	/**
	 * Mutator. Add a relationship from physicalObject A to physicalObject B with a
	 * intimacy of weight. This method should only perform in circular orbit with
	 * relationship.
	 * 
	 * @param physicalObjectA the source physical object of this relationship
	 * @param physicalObjectB the target physical object of this relationship
	 * @param weight          the intimacy of this relationship, it should be a
	 *                        decimal from zero to one
	 */
	public void addRelationshipBetweenPhysicalAndPhysical(E physicalObjectA, E physicalObjectB, double weight);

	/**
	 * Read data from file. All certain circular orbits are different so this method
	 * will be override in each particular class. But no matter what kind of
	 * circular orbit, before it's read from file, it's representations must be
	 * reset.
	 * 
	 * @param file the data file
	 */
	public void readFromFile(File file);

	/**
	 * Construct social network circle according to list of physical objects, list
	 * of adjacency edges, from-center adjacency map from physical object to
	 * intimacy and to-center adjacency map from physical object to intimacy. This
	 * method should only be called when reading from file or reconstructing.
	 */
	public void constructSocialNetworkCircle(List<PhysicalObject> friends, List<Edge<PhysicalObject>> physicalEdges,
			Map<PhysicalObject, Double>[] centralEdges);

	/**
	 * Mutator. Delete a relationship from physicalObject A to physicalObject B.
	 * 
	 * @param physicalObjectA the source physical object of the deleted relationship
	 * @param physicalObjectB the target physical object of the deleted relationship
	 */
	public void deleteRelationshipBetweenPhysicalAndPhysical(E physicalObjectA, E physicalObjectB);

	/**
	 * Mutator. Delete a physical object from the certain track.
	 * 
	 * @param physicalObject the deleted physical object
	 * @param track          the track in which the deleted physical object is
	 * @throws Exception no object on this track
	 */
	public void deletePhysicalObjectFromTrack(E physicalObject, Track track) throws Exception;

	/**
	 * Mutator. Delete all physical objects in tracks. This method should only
	 * perform when the circular orbit is restructured.
	 */
	public void resetObjectsAndTrack();

	/**
	 * Observer. Get central object in circular orbit. This method should only
	 * perform in circular orbit with central object.
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
	 * Observer. Get a defensive copy of the map from tracks to lists of physical
	 * objects in circular orbit. This method should only perform in circular orbit
	 * with relationship.
	 * 
	 * @return a defensive copy of the map from tracks to lists of physical objects
	 *         in circular orbit
	 */
	public Map<Track, List<E>> getObjectsInTrack();

	/**
	 * Observer. Get a defensive copy of two maps from physical objects to intimacy
	 * in circular orbit. This method should only perform in circular orbit with
	 * relationship.
	 * 
	 * @return a defensive copy of two maps from physical objects to intimacy in
	 *         circular orbit
	 */
	public Map<E, Double>[] getRelationBetweenCentralAndObject();

	/**
	 * Observer. Get a defensive copy of the list of edges representing adjacency
	 * relationships. This method should only perform in circular orbit with
	 * relationship.
	 * 
	 * @return a defensive copy of the list of edges representing adjacency
	 *         relationships
	 */
	public List<Edge<E>> getRelationBetweenObjects();

	/**
	 * Save the current state into a Memento and return this Memento. This method
	 * should only perform in atom structure.
	 * 
	 * @return a Memento storing the current state
	 */
	public Memento<L, E> save();

	/**
	 * Restore a historical state from a Memento. This method should only perform in
	 * atom structure.
	 * 
	 * @param m a Memento storing a historical state
	 */
	public void restore(Memento<L, E> m);

}
