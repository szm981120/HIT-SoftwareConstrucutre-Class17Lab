package physicalobjecttest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import physicalobject.ConcreteElectronFactory;
import physicalobject.ElectronFactory;
import physicalobject.PhysicalObject;

public class ElectronTest {

  private static ElectronFactory electronFactory = new ConcreteElectronFactory();
  private static PhysicalObject electron = electronFactory.produce();

  /*
   * Test strategy testGetDegree This tests getDegree. Because the degree of electron is generated
   * randomly so there's no an expectation result. The degree test actually has been ensured by
   * checkRep. This test has no necessary. testEquals This tests equals. Because all electrons have
   * no difference, equals method is override that every object is the same. To see if equals works
   * well, generate two electrons and assert they are equal. testHashCode This tests hashCode
   * Because all electrons are equal, every electron's hash code should be the same, supposed to be
   * -1. To see if hashCode works well, to see if a electron's hash code is -1. testGetDirect Test
   * that electron shouldn't have getDirect method. testRadius Test that electron shouldn't have
   * getRadius method. testName Test that electron shouldn't have getName method. testSpeed Test
   * that electron shouldn't have getSpeed method.
   */
  @Test(expected = AssertionError.class)
  public void testAssertionsEnabled() {
    assert false;
  }

  @Test
  public void testGetDegree() {
    double d = electron.getDegree();
    assertTrue(d >= 0 && d < 360);
  }

  @Test
  public void testEquals() {
    PhysicalObject electron2 = electronFactory.produce();
    assertTrue(electron2.equals(electron));
  }

  @Test
  public void testHashCode() {
    assertEquals("expected -1", -1, electron.hashCode());
  }

  @Test(expected = AssertionError.class)
  public void testGetDirect() {
    electron.getDirect();
  }

  @Test(expected = AssertionError.class)
  public void testGetRadius() {
    electron.getRaidus();
  }

  @Test(expected = AssertionError.class)
  public void testGetName() {
    electron.getName();
  }

  @Test(expected = AssertionError.class)
  public void testGetSpeed() {
    electron.getSpeed();
  }

}
