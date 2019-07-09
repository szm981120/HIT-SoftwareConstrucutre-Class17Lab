package P3;

import static org.junit.Assert.*;

import org.junit.Test;

public class PieceTest {

	/**
	 * Tests that assertions are enabled
	 */
	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false;
	}
	
	@Test
	public void test() {
		Piece piece = new Piece("king", "me");
		assertEquals("expected king", "king", piece.getType());
		assertEquals("expected me", "me", piece.getColor());
	}

}
