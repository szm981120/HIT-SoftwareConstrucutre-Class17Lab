package circularorbit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import atomtransitionmement.Memento;
import myexception.NoObjectOnTrackException;
import physicalobject.PhysicalObject;
import track.Track;

/**
 * ConcreteCircularOrbit implements CircularOrbit.
 * 
 * @author Shen
 *
 * @param <L> class of central object in circular orbit which is one of Star, Person and Nucleus.
 * @param <E> class of physical object in circular orbit which is mostly PhysicalObject.
 */
public class ConcreteCircularOrbit<L, E> implements CircularOrbit<L, E> {

  protected L centralObject;

  protected List<Track> tracks = new ArrayList<Track>();

  protected Map<Track, List<E>> objectsInTrack = new HashMap<Track, List<E>>();

  protected Map<E, Double> relationBetweenCentralAndObject = new HashMap<E, Double>();

  protected Set<Edge<E>> relationBetweenObjects = new HashSet<Edge<E>>();

  /*
   * Abstraction function: AF(centralObject) = central object of this circular orbit AF(tracks) = a
   * list of tracks of this circular orbit AF(objectsInTrack) = a map from tracks to objects
   * indicating that on which track should each object be AF(relationBetweenCentralAndObject) = two
   * map from physical objects to intimacy indicating that adjacency from and to central object
   * AF(relationBetweenObjects) = a list of edges indicating adjacency between all physical objects.
   * 
   * Representation invariant: tracks in tracks must be in ascend order of radius. values in
   * realtionBetweenCentralAndObject is more than 0 and no more than 1.
   * 
   * Safety from rep exposure: All representation are defined private and final. All mutable rep
   * Observer has a defensive copy.
   * 
   */
  /**
   * checkRep.
   */
  private void checkRep() {
    // for (int i = 1; i < tracks.size(); i++) {
    // assert tracks.get(i - 1).getRadius() <= tracks.get(i).getRadius();
    // }
    // for (Double intimacy : this.relationBetweenCentralAndObject.values()) {
    // assert intimacy > 0 && intimacy <= 1;
    // }
  }

  /**
   * Constructor.
   */
  public ConcreteCircularOrbit() {
    // relationBetweenCentralAndObject[1] = new HashMap<E, Double>();
    checkRep();
  }

  @Override
  public void addTrack(Track track) {
    assert track != null : "track can't be null!";
    // int index = 0;
    // for (Track t : this.tracks) {
    // if (track.getRadius() > t.getRadius()) {
    // index++;
    // }
    // }
    // this.tracks.add(index, track);
    this.tracks.add(track);
    List<E> objects = new ArrayList<E>();
    objectsInTrack.put(track, objects);
    assert this.tracks.contains(track) && this.objectsInTrack.keySet().contains(track);
    checkRep();
  }

  @Override
  public void deleteTrack(Track track) {
    assert track != null : "track can't be null!";
    for (int i = 0; i < tracks.size(); i++) {
      if (track.equals(tracks.get(i))) {
        this.objectsInTrack.remove(this.tracks.get(i));
        this.tracks.remove(i);
        checkRep();
        break;
      }
    }
    assert !this.tracks.contains(track) && !this.objectsInTrack.keySet().contains(track);
    checkRep();
  }

  @Override
  public void addCentralObject(L centralObject) {
    assert centralObject != null : "centralObject can't be null!";
    this.centralObject = centralObject;
    assert this.centralObject.equals(centralObject);
    checkRep();
  }

  @Override
  public void addPhysicalObjectToTrack(E physicalObject, Track track) {
    assert physicalObject != null && track != null : "physicalObject and track can't be null!";
    // int index = 0;
    // for (PhysicalObject e : this.objectsInTrack.get(track)) {
    // if (((PhysicalObject) physicalObject).getDegree() > ((PhysicalObject) e).getDegree()) {
    // index++;
    // }
    // }
    // objectsInTrack.get(track).add(index, physicalObject);
    this.objectsInTrack.get(track).add(physicalObject);
    assert this.objectsInTrack.get(track).contains(physicalObject);
    checkRep();
  }

  @Override
  public void addRelationshipBetweenCentralAndPhysical(E physicalObject, double weight) {
    assert physicalObject != null && weight > 0
        && weight <= 1 : "physicalObject can't be null and weight must be in range (0,1].";
    this.relationBetweenCentralAndObject.put(physicalObject, weight);
    assert this.relationBetweenCentralAndObject.get(physicalObject).equals(weight);
    // if (fromCenctral) {
    // this.relationBetweenCentralAndObject[0].put(physicalObject, weight);
    // } else {
    // this.relationBetweenCentralAndObject[1].put(physicalObject, weight);
    // }
    // if (fromCenctral) {
    // assert
    // this.relationBetweenCentralAndObject[0].get(physicalObject).equals(weight);
    // } else {
    // assert
    // this.relationBetweenCentralAndObject[1].get(physicalObject).equals(weight);
    // }
    checkRep();
  }

  @Override
  public void addRelationshipBetweenPhysicalAndPhysical(E physicalObjectA, E physicalObjectB,
      double weight) {
    assert physicalObjectA != null && physicalObjectB != null && weight > 0
        && weight <= 1 : "physicalObject can't be null and weight must be in range (0,1].";
    Edge<E> edge = new Edge<E>(physicalObjectA, physicalObjectB, weight);
    // Edge<E> edge2 = new Edge<E>(physicalObjectB, physicalObjectA, weight);
    this.relationBetweenObjects.add(edge);
    // this.relationBetweenObjects.add(edge2);
    assert this.relationBetweenObjects.contains(edge);
    // && this.relationBetweenObjects.contains(edge2);
    checkRep();
  }

  @Override
  public void deleteRelationshipBetweenPhysicalAndPhysical(E physicalObjectA, E physicalObjectB) {
    assert physicalObjectA != null && physicalObjectB != null;
    Iterator<Edge<E>> iterator = this.relationBetweenObjects.iterator();
    while (iterator.hasNext()) {
      Edge<E> edge = iterator.next();
      if (edge.getSource().equals(physicalObjectA) && edge.getTarget().equals(physicalObjectB)) {
        iterator.remove();
      }
      if (edge.getSource().equals(physicalObjectB) && edge.getTarget().equals(physicalObjectA)) {
        iterator.remove();
      }
    }
    assert !this.relationBetweenObjects.contains(new Edge<E>(physicalObjectA, physicalObjectB, 0.5))
        && !this.relationBetweenObjects
            .contains(new Edge<E>(physicalObjectB, physicalObjectA, 0.5));
    checkRep();
  }

  // @Override
  // public void readFromFile(File file) throws FileNotFoundException,
  // NumberFormatException,
  // IOException, AtomElementException, AtomTrackNumException,
  // AtomElectronNumException,
  // DataSyntaxException, DataScientificNumberException,
  // IllegalIntimacyInSocialTieException {
  // this.centralObject = null;
  // this.tracks = new ArrayList<Track>();
  // this.objectsInTrack = new HashMap<Track, List<E>>();
  // this.relationBetweenCentralAndObject = new HashMap<E, Double>();
  // this.relationBetweenObjects = new ArrayList<Edge<E>>();
  // checkRep();
  // }
  @Override
  public void readFromFile() {
    this.centralObject = null;
    this.tracks = new ArrayList<Track>();
    this.objectsInTrack = new HashMap<Track, List<E>>();
    this.relationBetweenCentralAndObject = new HashMap<E, Double>();
    this.relationBetweenObjects = new HashSet<Edge<E>>();
    checkRep();
  }

  // @Override
  // public void writeToFile(File file) throws IOException {
  //
  // }

  // @Override
  // public void constructSocialNetworkCircle(List<PhysicalObject> friends,
  // List<Edge<PhysicalObject>> physicalEdges, Map<PhysicalObject, Double>
  // centralEdges) {}
  @Override
  public void constructSocialNetworkCircle(Map<String, PhysicalObject> friends) {}

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
  public void deletePhysicalObjectFromTrack(E physicalObject, Track track)
      throws NoObjectOnTrackException {
    assert physicalObject != null && track != null;
    boolean exist = false;
    for (Track t : this.tracks) {
      if (t.equals(track)) {
        exist = true;
        this.objectsInTrack.get(t).remove(physicalObject);
        this.relationBetweenCentralAndObject.remove(physicalObject);
        // this.relationBetweenCentralAndObject[1].remove(physicalObject);
      }
    }
    if (!exist) {
      throw new NoObjectOnTrackException("Delete fail! This track has no such object!");
    }
    Iterator<Edge<E>> iterator = this.relationBetweenObjects.iterator();
    while (iterator.hasNext()) {
      Edge<E> edge = iterator.next();
      if (edge.getSource().equals(physicalObject) || edge.getTarget().equals(physicalObject)) {
        iterator.remove();
      }
    }
    assert !this.objectsInTrack.containsKey(physicalObject);
    checkRep();
  }

  @Override
  public void resetObjectsAndTrack() {
    this.objectsInTrack = new HashMap<Track, List<E>>();
    this.tracks = new ArrayList<Track>();
    assert this.objectsInTrack.isEmpty() && this.tracks.isEmpty();
    checkRep();
  }

  @Override
  public Map<E, Double> getRelationBetweenCentralAndObject() {
    Map<E, Double> copyRelationBetweenCentralAndObject = new HashMap<E, Double>();
    // copyRelationBetweenCentralAndObject = new HashMap<E, Double>();
    // copyRelationBetweenCentralAndObject[1] = new HashMap<E, Double>();
    for (E e : this.relationBetweenCentralAndObject.keySet()) {
      copyRelationBetweenCentralAndObject.put(e, this.relationBetweenCentralAndObject.get(e));
    }
    // for (E e : this.relationBetweenCentralAndObject[1].keySet()) {
    // copyRelationBetweenCentralAndObject[1].put(e,
    // this.relationBetweenCentralAndObject[1].get(e));
    // }
    checkRep();
    return copyRelationBetweenCentralAndObject;
  }

  @Override
  public Set<Edge<E>> getRelationBetweenObjects() {
    Set<Edge<E>> copyRelationBetweenObjects = new HashSet<Edge<E>>();
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
  public void restore(Memento<L, E> m) {}

  @Override
  public Iterator<E> iterator() {
    return new MyIterator();
  }

  /**
   * This class provides an iterator for iteration in circular orbit. This iterator can iterate
   * physical objects in circular orbit. The iteration order is ascending radius of tracks and
   * ascending degrees of objects in each track.
   * 
   * @author Shen
   *
   */
  private class MyIterator implements Iterator<E> {

    int trackIndex = 0;
    int objectIndex = 0;

    @Override
    public boolean hasNext() {
      if (trackIndex >= tracks.size()
          || objectIndex >= objectsInTrack.get(tracks.get(trackIndex)).size()) {
        return false;
      } else {
        return true;
      }
    }

    @Override
    public void remove() {
      assert false : "doesn't support remove";
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
