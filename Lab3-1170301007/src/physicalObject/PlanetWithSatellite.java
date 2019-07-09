package physicalObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Planet with some satellites.
 * 
 * @author Shen
 *
 */
public class PlanetWithSatellite extends SatelliteDecorator {

	private final List<Satellite> satellites = new ArrayList<Satellite>();

	/*
	 * Abstraction function:
	 * 	AF(satellites) = a list of satellites which the mother planet has
	 * 
	 * Representation invariant:
	 * 	None.
	 * 
	 * Safety from rep exposure:
	 * 	All representations are defined private and final.
	 * 	All representations in Observer are immutable.
	 */

	// No checkRep
	
	/**
	 * Constructor. Extends it's super-class.
	 * 
	 * @param planet
	 */
	public PlanetWithSatellite(PhysicalObject planet) {
		super(planet);
		addSatellite();
	}

	@Override
	public double getDegree() {
		return this.planet.getDegree();
	}

	@Override
	public String getName() {
		return this.planet.getName();
	}

	@Override
	public boolean getDirect() {
		return this.planet.getDirect();
	}

	@Override
	public double getSpeed() {
		return this.planet.getSpeed();
	}

	@Override
	public double getRaidus() {
		return this.planet.getRaidus();
	}

	/**
	 * When a plant is decorated by this class, it means this planet is added a
	 * satellite, so this method should be called in constructor when a planet is
	 * decorated by this class.
	 */
	private void addSatellite() {
		this.satellites.add(new Satellite());
	}

}
