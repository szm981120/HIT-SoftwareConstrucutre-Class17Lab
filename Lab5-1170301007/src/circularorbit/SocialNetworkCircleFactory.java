package circularorbit;

import centralobject.Person;
import physicalobject.PhysicalObject;

/**
 * SocialNetworkCircleFactory provides a method to create a SocialNetworkCircle instance.
 * 
 * @author Shen
 *
 */
public class SocialNetworkCircleFactory extends CircularOrbitFactory<Person, PhysicalObject> {

  /**
   * Constructor.
   */
  public SocialNetworkCircleFactory() {}

  /**
   * Create a social network circle.
   */
  @Override
  public CircularOrbit<Person, PhysicalObject> produce() {
    return new SocialNetworkCircle();
  }
}
