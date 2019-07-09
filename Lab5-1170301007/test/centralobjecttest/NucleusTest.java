package centralobjecttest;

import static org.junit.Assert.assertEquals;

import centralobject.Nucleus;

import org.junit.Test;



public class NucleusTest {

  /*
   * Test strategy testToString This tests toString. toString can show the element name of nucleus.
   * Call toString to see if the result fits expectation.
   */
  @Test(expected = AssertionError.class)
  public void testAssertionsEnabled() {
    assert false;
  }

  @Test
  public void testToString() {
    char[] elementName = new char[2];
    elementName[0] = 'N';
    elementName[1] = 'a';
    Nucleus nucleus = new Nucleus(elementName);
    assertEquals("expected Na", "Na", nucleus.toString());
  }
}
