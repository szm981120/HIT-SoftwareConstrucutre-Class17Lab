package P3;

import static org.junit.Assert.*;

import org.junit.Test;

public class PositionTest {

	/**
	 * Tests that assertions are enabled
	 */
	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false;
	}
	
	@Test
	public void test() {
		Position position = new Position(1, 2);
		assertEquals("expected 1", 1, position.getX());
		assertEquals("expected 2", 2, position.getY());
	}
}
