package physicalObjectTest;

import static org.junit.Assert.*;

import org.junit.Test;

import physicalObject.ConcretePlanetFactory;
import physicalObject.PhysicalObject;
import physicalObject.PlanetFactory;
import physicalObject.PlanetWithSatellite;

public class PlanetWithSatelliteTest {

	private static PlanetFactory planetFactory = new ConcretePlanetFactory();
	private static PhysicalObject planet = planetFactory.produce("Earth", "Solid", "Blue", 6378.137, 29.783, true, 0);
	private static PhysicalObject planetWithSatellite = new PlanetWithSatellite(planet);

	/*
	 * Test strategy
	 * 	testGetName
	 * 		This tests getName.
	 * 		Call getName to see if the result fits expectation.
	 * 	testGetDegree
	 * 		This tests getDegree.
	 * 		Because the degree of planet is generated designative.
	 * 		Test if a planet's degree is the same as the initialized degree.
	 * 	testGetDirect
	 * 		This tests getDirect.
	 * 		Because the direct of planet is generated designative.
	 * 		Test if a planet's direct is the same as the initialized direct.
	 * 	testGetSpeed
	 * 		This tests getSpeed.
	 * 		Because the speed of planet is generated designative.
	 * 		Test if a planet's speed is the same as the initialized speed.
	 * 	testGetRadius
	 * 		This tests getRadius.
	 * 		Because the radius of planet is generated designative.
	 * 		Test if a planet's radius is the same as the initialized radius.
	 */

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false;
	}

	@Test
	public void testGetName() {
		assertEquals("expected Earth", "Earth", planetWithSatellite.getName());
	}

	@Test
	public void testGetDegree() {
		assertEquals(0, planetWithSatellite.getDegree(), 0);
	}

	@Test
	public void testGetDirect() {
		assertTrue(planetWithSatellite.getDirect());
	}

	@Test
	public void testGetSpeed() {
		assertEquals(29.783, planetWithSatellite.getSpeed(), 0);
	}

	@Test
	public void testGetRadius() {
		assertEquals(6378.137, planetWithSatellite.getRaidus(), 0);
	}

}
