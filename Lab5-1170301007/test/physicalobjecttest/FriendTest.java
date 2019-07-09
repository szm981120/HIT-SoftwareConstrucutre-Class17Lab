package physicalobjecttest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import physicalobject.ConcreteFriendFactory;
import physicalobject.FriendFactory;
import physicalobject.PhysicalObject;

public class FriendTest {

  private static FriendFactory friendFactory = new ConcreteFriendFactory();
  private static PhysicalObject friend = friendFactory.produce("Hanxiao", 20, 'M');

  /*
   * Test strategy testGetName This tests getName. Call getName to see if the result fits
   * expectation. testGetDegree This tests getDegree. Because the degree of friend is generated
   * randomly so there's no an expectation result. The degree test actually has been ensured by
   * checkRep. This test has no necessary. testEquals This tests equals. Two friends are equal only
   * when they have the same name, age and sex. Generate two friends who have the same name, age and
   * sex and see if they are equal. Generate two friends whose name, age and sex are not all the
   * same, and see if they are not equal. testHashCode This tests hashCode Two friends' hash codes
   * are equal only when they are equal. testGetDirect Test that friend shouldn't have getDirect
   * method. testRadius Test that friend shouldn't have getRadius method. testSpeed Test that friend
   * shouldn't have getSpeed method.
   */

  @Test(expected = AssertionError.class)
  public void testAssertionsEnabled() {
    assert false;
  }

  @Test
  public void testGetName() {
    assertEquals("expected Hanxiao", "Hanxiao", friend.getName());
  }

  @Test
  public void testGetDegree() {
    double d = friend.getDegree();
    assertTrue(d >= 0 && d < 360);
  }

  @Test
  public void testEquals() {
    PhysicalObject friend2 = friendFactory.produce("Hanxiao", 22, 'M');
    PhysicalObject friend3 = friendFactory.produce("hanxiao", 21, 'M');
    assertTrue(friend2.equals(friend));
    assertFalse(friend.equals(friend3));
  }

  @Test
  public void testHashCode() {
    PhysicalObject friend2 = new ConcreteFriendFactory().produce("Hanxiao", 21, 'M');
    PhysicalObject friend3 = friendFactory.produce("hanxiao", 21, 'M');
    assertEquals(friend.hashCode(), friend2.hashCode());
    assertNotEquals(friend.hashCode(), friend3.hashCode());
  }

  @Test(expected = AssertionError.class)
  public void testGetDirect() {
    friend.getDirect();
  }

  @Test(expected = AssertionError.class)
  public void testGetRadius() {
    friend.getRaidus();
  }

  @Test(expected = AssertionError.class)
  public void testGetSpeed() {
    friend.getSpeed();
  }

}
