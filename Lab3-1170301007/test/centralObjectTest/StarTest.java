package centralObjectTest;

import static org.junit.Assert.*;

import org.junit.Test;

import centralObject.Star;

public class StarTest {

	private static Star star = new Star("Planet", 1000, 2000);

	/*
	 * Test strategy
	 * 	testGetName
	 * 		This tests getName.
	 * 		Call getName to see if the result fits expectation.
	 * 	testGetRadius
	 * 		This tests getRadius.
	 * 		Call getRadius to see if the result fits expectation.
	 */
	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false;
	}

	@Test
	public void testGetName() {
		assertEquals("expected Planet", "Planet", star.getName());
	}

	@Test
	public void testGetRadius() {
		assertEquals(1000, star.getRadius(), 0);
	}

}