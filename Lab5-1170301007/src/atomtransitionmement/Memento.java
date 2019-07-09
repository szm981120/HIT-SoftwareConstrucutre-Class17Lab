package atomtransitionmement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import circularorbit.Edge;
import track.Track;

/**
 * Memento stores a historical state of atom structure.
 * 
 * @author Shen
 *
 * @param <L> class of central object which is supposed to be Nucleus
 * @param <E> class of physical object which is supposed to be PhysicalObject
 */
public class Memento<L, E> {

  private L centralObject;

  private List<Track> tracks = new ArrayList<Track>();

  private Map<Track, List<E>> objectsInTrack = new HashMap<Track, List<E>>();

  @SuppressWarnings("unchecked")
  private Map<E, Double> relationBetweenCentralAndObject = new HashMap<E, Double>();

  private Set<Edge<E>> relationBetweenObjects = new HashSet<Edge<E>>();

  /**
   * Constructor.
   * 
   * @param centralObject central object in this historical state.
   * @param tracks tracks in this historical state.
   * @param objectsInTrack objects in tracks in this historical state.
   * @param relationBetweenCentralAndObject relationship between central and physical objects in
   *        this historical state.
   * @param relationBetweenObjects relationship between physical objects in this historical state.
   */
  public Memento(L centralObject, List<Track> tracks, Map<Track, List<E>> objectsInTrack,
      Map<E, Double> relationBetweenCentralAndObject, Set<Edge<E>> relationBetweenObjects) {
    this.centralObject = centralObject;
    List<Track> copyTracks = new ArrayList<Track>();
    for (Track track : tracks) {
      copyTracks.add(track);
    }
    this.tracks = copyTracks;
    Map<Track, List<E>> copyObjectsInTrack = new HashMap<Track, List<E>>();
    for (Track track : objectsInTrack.keySet()) {
      List<E> copyObjects = new ArrayList<E>();
      for (E e : objectsInTrack.get(track)) {
        copyObjects.add(e);
      }
      copyObjectsInTrack.put(track, copyObjects);
    }
    this.objectsInTrack = copyObjectsInTrack;
    this.relationBetweenCentralAndObject = relationBetweenCentralAndObject;
    Set<Edge<E>> copyRelationBetweenObjects = new HashSet<Edge<E>>();
    for (Edge<E> edge : relationBetweenObjects) {
      copyRelationBetweenObjects.add(edge);
    }
    this.relationBetweenObjects = copyRelationBetweenObjects;
  }

  /**
   * Observer. Get central object.
   * 
   * @return central object of this atom structure state.
   */
  public L getCentralObject() {
    return this.centralObject;
  }

  /**
   * Observer. Get a defensive copy of tracks.
   * 
   * @return a defensive copy of a list of tracks in this atom structure state.
   */
  public List<Track> getTracks() {
    List<Track> copyTracks = new ArrayList<Track>();
    for (Track track : this.tracks) {
      copyTracks.add(track);
    }
    return copyTracks;
  }

  /**
   * Observer. Get a defensive copy of objects in tracks.
   * 
   * @return a defensive copy of a map from tracks to lists of objects in this atom structure state.
   */
  public Map<Track, List<E>> getObjectsInTrack() {
    Map<Track, List<E>> copyObjectsInTrack = new HashMap<Track, List<E>>();
    for (Track track : this.objectsInTrack.keySet()) {
      List<E> copyObjects = new ArrayList<E>();
      for (E e : this.objectsInTrack.get(track)) {
        copyObjects.add(e);
      }
      copyObjectsInTrack.put(track, copyObjects);
    }
    return copyObjectsInTrack;
  }

  /**
   * Observer. Get a map from physical objects to intimacy in this atom structure state. Physical
   * objects in keySet all have relation with central object.
   * 
   * @return a map from physical objects to intimacy in this atom structure state.
   */
  public Map<E, Double> getRelationBetweenCentralAndObject() {
    return this.relationBetweenCentralAndObject;
  }

  /**
   * Observer. Get a defensive copy of list of adjacency edges in this atom structure state.
   * 
   * @return a defensive copy of list of adjacency edges in this atom structure state.
   */
  public Set<Edge<E>> getRelationBetweenObjects() {
    Set<Edge<E>> copyRelationBetweenObjects = new HashSet<Edge<E>>();
    for (Edge<E> edge : this.relationBetweenObjects) {
      copyRelationBetweenObjects.add(edge);
    }
    return copyRelationBetweenObjects;
  }
}
