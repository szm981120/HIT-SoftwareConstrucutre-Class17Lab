package physicalobjecttest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import physicalobject.ConcretePlanetFactory;
import physicalobject.Planet;
import physicalobject.PlanetFactory;

public class PlanetFactoryTest {

  /*
   * Test strategy testProduce This tests produce. Call produce to create an instance to see if the
   * instance fits expectation.
   */

  @Test(expected = AssertionError.class)
  public void testAssertionsEnabled() {
    assert false;
  }

  @Test
  public void testProduce() {
    PlanetFactory planetFactory = new ConcretePlanetFactory();
    assertEquals("expected Planet class", Planet.class,
        planetFactory.produce("Earth", "Solid", "Blue", 6378.137, 29.783, true, 0).getClass());
  }

}
