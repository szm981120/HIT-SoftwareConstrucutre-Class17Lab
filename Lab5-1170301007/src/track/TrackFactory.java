package track;

/**
 * TrackFactory provides a method to create a Track instance.
 * 
 * @author Shen
 *
 */
public class TrackFactory {

  /**
   * Constructor.
   */
  public TrackFactory() {}

  /**
   * Produce a track.
   * 
   * @param radius radius of track
   * @return a Track instance
   */
  public Track produce(double radius) {
    return new NormalTrack(radius);
  }
}
