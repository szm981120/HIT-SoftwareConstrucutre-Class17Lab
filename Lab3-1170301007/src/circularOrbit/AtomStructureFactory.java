package circularOrbit;

import centralObject.Nucleus;
import physicalObject.PhysicalObject;

/**
 * AtomStructureFactory provides a method to create a AtomStructure instance.
 * 
 * @author Shen
 *
 */
public class AtomStructureFactory extends circularOrbitFactory<Nucleus, PhysicalObject> {

	/**
	 * Constructor
	 */
	public AtomStructureFactory() {

	}

	/**
	 * Create a atom structure.
	 */
	@Override
	public CircularOrbit<Nucleus, PhysicalObject> produce() {
		return new AtomStructure();
	}

}
