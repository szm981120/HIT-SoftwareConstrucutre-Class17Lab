package circularOrbit;

import centralObject.Person;
import physicalObject.PhysicalObject;

/**
 * SocialNetworkCircleFactory provides a method to create a SocialNetworkCircle
 * instance.
 * 
 * @author Shen
 *
 */
public class SocialNetworkCircleFactory extends circularOrbitFactory<Person, PhysicalObject> {

	/**
	 * Constructor
	 */
	public SocialNetworkCircleFactory() {
		
	}

	/**
	 * Create a social network circle.
	 */
	@Override
	public CircularOrbit<Person, PhysicalObject> produce() {
		return new SocialNetworkCircle();
	}

}
