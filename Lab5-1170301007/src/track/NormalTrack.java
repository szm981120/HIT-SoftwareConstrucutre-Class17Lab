package track;

/**
 * A normal circular track.
 * 
 * @author Shen
 *
 */
public class NormalTrack implements Track {
  // IMMUTABLE
  // factory method

  private final double radius;
  /*
   * Abstraction function: AF(radius) = radius of this track
   * 
   * Representation invariant: radius must be positive
   * 
   * Safety from rep exposure: All representations are defined private and final. All
   * representations in Observer are immutable.
   */

  // checkRep
  private void checkRep() {
    assert this.radius > 0;
  }

  /**
   * Constructor.
   * 
   * @param radius radius of track, must be positive.
   */
  public NormalTrack(double radius) {
    this.radius = radius;
    checkRep();
  }

  @Override
  public double getRadius() {
    double radius = this.radius;
    checkRep();
    return radius;
  }

  /**
   * Two tracks are equals only when they have the same radius.
   */
  @Override
  public boolean equals(Object track) {
    return track != null && track.getClass() == NormalTrack.class && Double
        .doubleToLongBits(this.radius) == Double.doubleToLongBits(((NormalTrack) track).radius);
  }

  @Override
  public int hashCode() {
    long longbits = Double.doubleToLongBits(this.radius);
    int hashcode = new Long(longbits).intValue();
    checkRep();
    return hashcode;
  }
}
