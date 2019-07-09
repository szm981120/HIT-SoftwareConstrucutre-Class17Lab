package circularOrbit;

/**
 * 
 * @author Shen
 *
 * @param <L> class of central object in circular orbit which is one of Star,
 *        Person and Nucleus.
 * @param <E> class of physical object in circular orbit which is mostly
 *        PhysicalObject.
 */
public abstract class circularOrbitFactory<L, E> {

	/**
	 * Constructor
	 */
	public circularOrbitFactory() {
		
	}

	/**
	 * Produce a circular orbit in certain class. This should be override in
	 * particular classes.
	 * 
	 * @return a circular orbit
	 */
	public abstract CircularOrbit<L, E> produce();

}
