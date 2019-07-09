package circularorbit;

import centralobject.Star;
import physicalobject.PhysicalObject;

/**
 * StellarSystemFactory provides a method to create a StellarSystem instance.
 * 
 * @author Shen
 *
 */
public class StellarSystemFactory extends CircularOrbitFactory<Star, PhysicalObject> {

  /**
   * Constructor.
   */
  public StellarSystemFactory() {

  }

  /**
   * Create a stellar system.
   */
  @Override
  public CircularOrbit<Star, PhysicalObject> produce() {
    return new StellarSystem();
  }

}
