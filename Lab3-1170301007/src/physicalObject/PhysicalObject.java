package physicalObject;

public interface PhysicalObject {

	/**
	 * Observer. Get degree of the physical object.
	 * 
	 * @return degree of the physical object
	 */
	public double getDegree();

	/**
	 * Observer. Get name of the physical object. This method should only perform in
	 * social network circle and stellar system.
	 * 
	 * @return name of the physical object
	 */
	public String getName();

	/**
	 * Observer. Get direct of the physical object. This method should only perform
	 * in stellar system.
	 * 
	 * @return direct of the physical object, true if the direct is clockwise, false
	 *         if the direct is counter-clockwise.
	 */
	public boolean getDirect();

	/**
	 * Observer. Get speed of the physical object. This method should only perform
	 * in stellar system.
	 * 
	 * @return speed of the physical object. The unit is kilogram per second.
	 */
	public double getSpeed();

	/**
	 * Observer. Get radius of the physical object. This method should only perform
	 * in stellar system.
	 * 
	 * @return radius of the physical object. The unit is kilometer.
	 */
	public double getRaidus();
}
