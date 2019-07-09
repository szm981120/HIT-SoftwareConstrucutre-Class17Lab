package physicalobject;

/**
 * Satellite decorator. Delegate from PhysicalObject.
 * 
 * @author Shen
 *
 */
public abstract class SatelliteDecorator implements PhysicalObject {

  protected final PhysicalObject planet;
  /*
   * Abstraction function: AF(planet) = the mother planet of satellite and it is also used for
   * delegation
   * 
   * Representation invariant: None.
   * 
   * Safety from rep exposure: All representations are defined private and final. All
   * representations in Observer are immutable.
   */

  // No checkRep
  /**
   * Constructor.
   * 
   * @param planet delegation from PhysicalObject
   */
  public SatelliteDecorator(PhysicalObject planet) {
    this.planet = planet;
  }
}
