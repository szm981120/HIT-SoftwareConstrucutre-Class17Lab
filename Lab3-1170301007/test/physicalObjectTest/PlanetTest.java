package physicalObjectTest;

import static org.junit.Assert.*;

import org.junit.Test;

import physicalObject.ConcretePlanetFactory;
import physicalObject.PhysicalObject;
import physicalObject.PlanetFactory;

public class PlanetTest {

	private static PlanetFactory planetFactory = new ConcretePlanetFactory();
	PhysicalObject planet = planetFactory.produce("Earth", "Solid", "Blue", 6378.137, 29.783, true, 0);

	/*
	 * Test strategy
	 * 	testGetName
	 * 		This tests getName.
	 * 		Call getName to see if the result fits expectation.
	 * 	testGetDegree
	 * 		This tests getDegree.
	 * 		Because the degree of friend is generated designative. Test if a planet's degree is the same as the initialized degree.
	 * 	testGetDirect
	 * 		This tests getDirect.
	 * 		Because the direct of friend is generated designative. Test if a planet's direct is the same as the initialized direct.
	 * 	testGetSpeed
	 * 		This tests getSpeed.
	 * 		Because the speed of friend is generated designative. Test if a planet's speed is the same as the initialized speed.
	 * 	testGetRadius
	 * 		This tests getRadius.
	 * 		Because the radius of friend is generated designative. Test if a planet's radius is the same as the initialized radius.
	 * 	testEquals
	 * 		This tests equals.
	 * 		Two planets are equals only when they have the same name with case sensitive.
	 * 		To see if equals works well, generate two planets who have the same name and see if they are equal.
	 * 	testHashCode
	 * 		This tests hashCode
	 * 		Two planets are equal only when they have the same name. So the hash code should link with name.
	 * 		To see if hashCode works well, see if a planet's hash code is the same as name's hash code. 
	 */

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false;
	}

	@Test
	public void testGetName() {
		assertEquals("expected Earth", "Earth", planet.getName());
	}

	@Test
	public void testGetDegree() {
		assertEquals(0, planet.getDegree(), 0);
	}

	@Test
	public void testGetDirect() {
		assertTrue(planet.getDirect());
	}

	@Test
	public void testGetSpeed() {
		assertEquals(29.783, planet.getSpeed(), 0);
	}

	@Test
	public void testGetRadius() {
		assertEquals(6378.137, planet.getRaidus(), 0);
	}

	@Test
	public void testEquals() {
		PhysicalObject planet_ = planetFactory.produce("Earth", "Solid", "Blue", 6400, 30, true, 10);
		assertTrue(planet.equals(planet_));
	}

	@Test
	public void testHashCode() {
		assertEquals(planet.getName().hashCode(), planet.hashCode());
	}

}
