package circularOrbit;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import atomTransitionMemento.Memento;
import physicalObject.PhysicalObject;
import track.NormalTrack;
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
public class ConcreteCircularOrbit<L, E> implements CircularOrbit<L, E> {

	protected L centralObject;
	protected List<Track> tracks = new ArrayList<Track>();
	protected Map<Track, List<E>> objectsInTrack = new HashMap<Track, List<E>>();
	@SuppressWarnings("unchecked")
	protected Map<E, Double>[] relationBetweenCentralAndObject = new Map[2];
	protected List<Edge<E>> relationBetweenObjects = new ArrayList<Edge<E>>();

	/*
	 * Abstraction function:
	 * 	AF(centralObject) = central object of this circular orbit
	 * 	AF(tracks) = a list of tracks of this circular orbit
	 * 	AF(objectsInTrack) = a map from tracks to objects indicating that on which track should each object be
	 * 	AF(relationBetweenCentralAndObject) = two map from physical objects to intimacy indicating that adjacency from and to central object
	 * 	AF(relationBetweenObjects) = a list of edges indicating adjacency between all physical objects.
	 * 
	 * Representation invariant:
	 * 	tracks in normal tracks must be in ascending order of radius.
	 * 	tracks in stellar tracks must be in ascending order of long radius.
	 * 	values in realtionBetweenCentralAndObject is more than 0 and no more than 1.
	 *  
	 * Safety from rep exposure:
	 * 	All representation are defined private and final.
	 * 	All mutable rep Observer has a defensive copy.
	 * 
	 */
	// checkRep
	private void checkRep() {
		for (int i = 1; i < tracks.size(); i++) {
			if (tracks.get(i - 1).getClass() == NormalTrack.class) {
				assert tracks.get(i - 1).getRadius() < tracks.get(i).getRadius();
			} else {
				assert tracks.get(i - 1).getLongRadius() <= tracks.get(i).getLongRadius();
			}
		}
		for (Double intimacy : this.relationBetweenCentralAndObject[0].values()) {
			assert intimacy > 0 && intimacy <= 1;
		}
		for (Double intimacy : this.relationBetweenCentralAndObject[1].values()) {
			assert intimacy > 0 && intimacy <= 1;
		}
	}

	/**
	 * Constructor
	 */
	public ConcreteCircularOrbit() {
		relationBetweenCentralAndObject[0] = new HashMap<E, Double>();
		relationBetweenCentralAndObject[1] = new HashMap<E, Double>();
		checkRep();
	}

	@Override
	public void addTrack(Track track) {
		int index = 0;
		if (track.getClass() == NormalTrack.class) {
			for (Track t : this.tracks) {
				if (track.getRadius() > t.getRadius()) {
					index++;
				}
			}
		} else {
			for (Track t : this.tracks) {
				if (track.getLongRadius() > t.getLongRadius()) {
					index++;
				}
			}
		}
		this.tracks.add(index, track);
		List<E> objects = new ArrayList<E>();
		objectsInTrack.put(track, objects);
		checkRep();
	}

	@Override
	public void deleteTrack(Track track) {
		for (int i = 0; i < tracks.size(); i++) {
			if (track.equals(tracks.get(i))) {
				this.objectsInTrack.remove(this.tracks.get(i));
				this.tracks.remove(i);
				checkRep();
				break;
			}
		}
		checkRep();
	}

	@Override
	public void addCentralObject(L centralObject) {
		this.centralObject = centralObject;
		checkRep();
	}

	@Override
	public void addPhysicalObjectToTrack(E physicalObject, Track track) {
		int index = 0;
		if (track.getClass() == NormalTrack.class) {
			for (Track t : this.tracks) {
				if (track.equals(t)) {
					for (E e : this.objectsInTrack.get(t)) {
						if (((PhysicalObject) physicalObject).getDegree() > ((PhysicalObject) e).getDegree()) {
							index++;
						}
					}
					objectsInTrack.get(t).add(index, physicalObject);
					checkRep();
					break;
				}
			}
		} else {
			for (Track t : this.tracks) {
				if (track.equals(t)) {
					for (E e : this.objectsInTrack.get(t)) {
						if (((PhysicalObject) physicalObject).getDegree() > ((PhysicalObject) e).getDegree()) {
							index++;
						}
					}
					objectsInTrack.get(t).add(index, physicalObject);
					checkRep();
					break;
				}
			}
		}

		checkRep();
	}

	@Override
	public void addRelationshipBetweenCentralAndPhysical(E physicalObject, boolean fromCenctral, double weight) {
		if (fromCenctral) {
			this.relationBetweenCentralAndObject[0].put(physicalObject, weight);
		} else {
			this.relationBetweenCentralAndObject[1].put(physicalObject, weight);
		}
		checkRep();
	}

	@Override
	public void addRelationshipBetweenPhysicalAndPhysical(E physicalObjectA, E physicalObjectB, double weight) {
		this.relationBetweenObjects.add(new Edge<E>(physicalObjectA, physicalObjectB, weight));
//		this.relationBetweenObjects.add(new Edge<E>(physicalObjectB, physicalObjectA, weight));
		checkRep();
	}

	@Override
	public void deleteRelationshipBetweenPhysicalAndPhysical(E physicalObjectA, E physicalObjectB) {
		Iterator<Edge<E>> iterator = this.relationBetweenObjects.iterator();
		while (iterator.hasNext()) {
			Edge<E> edge = iterator.next();
			if (edge.getSource().equals(physicalObjectA) && edge.getTarget().equals(physicalObjectB)) {
				iterator.remove();
			}
//			if (edge.getSource().equals(physicalObjectB) && edge.getTarget().equals(physicalObjectA)) {
//				iterator.remove();
//			}
		}
		checkRep();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void readFromFile(File file) {
		this.centralObject = null;
		this.tracks = new ArrayList<Track>();
		this.objectsInTrack = new HashMap<Track, List<E>>();
		this.relationBetweenCentralAndObject = new Map[2];
		this.relationBetweenCentralAndObject[0] = new HashMap<E, Double>();
		this.relationBetweenCentralAndObject[1] = new HashMap<E, Double>();
		this.relationBetweenObjects = new ArrayList<Edge<E>>();
		checkRep();
	}

	@Override
	public void constructSocialNetworkCircle(List<PhysicalObject> friends, List<Edge<PhysicalObject>> physicalEdges,
			Map<PhysicalObject, Double>[] centralEdges) {

	}

	@Override
	public L getCentralObject() {
		return this.centralObject;
	}

	@Override
	public List<Track> getTracks() {
		List<Track> copyTracks = new ArrayList<Track>();
		for (Track track : this.tracks) {
			copyTracks.add(track);
		}
		checkRep();
		return this.tracks;
	}

	@Override
	public Map<Track, List<E>> getObjectsInTrack() {
		Map<Track, List<E>> copyObjectsInTrack = new HashMap<Track, List<E>>();
		for (Track track : this.objectsInTrack.keySet()) {
			List<E> copyObjectsList = new ArrayList<E>();
			for (E e : objectsInTrack.get(track)) {
				copyObjectsList.add(e);
			}
			copyObjectsInTrack.put(track, copyObjectsList);
		}
		checkRep();
		return copyObjectsInTrack;
	}

	@Override
	public void deletePhysicalObjectFromTrack(E physicalObject, Track track) throws Exception {
		for (Track t : this.tracks) {
			if (t.equals(track)) {
				if (this.objectsInTrack.get(t).size() <= 0) {
					throw new Exception("This track has no object!");
				}
				this.objectsInTrack.get(t).remove(physicalObject);
				this.relationBetweenCentralAndObject[0].remove(physicalObject);
				this.relationBetweenCentralAndObject[1].remove(physicalObject);
			}
		}
		Iterator<Edge<E>> iterator = this.relationBetweenObjects.iterator();
		while (iterator.hasNext()) {
			Edge<E> edge = iterator.next();
			if (edge.getSource().equals(physicalObject) || edge.getTarget().equals(physicalObject)) {
				iterator.remove();
			}
		}
		checkRep();
	}

	@Override
	public void resetObjectsAndTrack() {
		this.objectsInTrack = new HashMap<Track, List<E>>();
		this.tracks = new ArrayList<Track>();
		checkRep();
	}

	@Override
	public Map<E, Double>[] getRelationBetweenCentralAndObject() {
		@SuppressWarnings("unchecked")
		Map<E, Double>[] copyRelationBetweenCentralAndObject = new Map[2];
		copyRelationBetweenCentralAndObject[0] = new HashMap<E, Double>();
		copyRelationBetweenCentralAndObject[1] = new HashMap<E, Double>();
		for (E e : this.relationBetweenCentralAndObject[0].keySet()) {
			copyRelationBetweenCentralAndObject[0].put(e, this.relationBetweenCentralAndObject[0].get(e));
		}
		for (E e : this.relationBetweenCentralAndObject[1].keySet()) {
			copyRelationBetweenCentralAndObject[1].put(e, this.relationBetweenCentralAndObject[1].get(e));
		}
		checkRep();
		return copyRelationBetweenCentralAndObject;
	}

	@Override
	public List<Edge<E>> getRelationBetweenObjects() {
		List<Edge<E>> copyRelationBetweenObjects = new ArrayList<Edge<E>>();
		for (Edge<E> edge : this.relationBetweenObjects) {
			copyRelationBetweenObjects.add(edge);
		}
		checkRep();
		return copyRelationBetweenObjects;
	}

	@Override
	public Memento<L, E> save() {
		return null;
	}

	@Override
	public void restore(Memento<L, E> m) {

	}

	@Override
	public Iterator<E> iterator() {
		return new MyIterator();
	}

	/**
	 * This class provides an iterator for iteration in circular orbit. This
	 * iterator can iterate physical objects in circular orbit. The iteration order
	 * is ascending radius of tracks and ascending degrees of objects in each track.
	 * 
	 * @author Shen
	 *
	 */
	private class MyIterator implements Iterator<E> {

		int trackIndex = 0, objectIndex = 0;

		@Override
		public boolean hasNext() {
			if (trackIndex >= tracks.size() || objectIndex >= objectsInTrack.get(tracks.get(trackIndex)).size()) {
				return false;
			} else {
				return true;
			}
		}

		@Override
		public E next() {
			List<E> objects = objectsInTrack.get(tracks.get(trackIndex));
			E e = objects.get(objectIndex);
			objectIndex++;
			if (trackIndex < tracks.size() - 1 && objectIndex >= objects.size()) {
				trackIndex++;
				objectIndex = 0;
			}
			return e;
		}
	}
}
