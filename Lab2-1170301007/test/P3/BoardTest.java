package P3;

import static org.junit.Assert.*;

import org.junit.Test;

public class BoardTest {

	/**
	 * Tests that assertions are enabled
	 */
	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false;
	}
	
	@Test
	public void test() {
		Board board = new Board(8);
		Piece[][] b = new Piece[9][9];
		Piece piece1 = new Piece("pawn1", "black");
		board.setBoard(new Position(3, 4), piece1);
		b = board.getBoard();
		assertEquals("expected piece1", piece1, board.getBoard()[3][4]);
		assertEquals("expected piece1", piece1, b[3][4]);
		board.setBoard(new Position(3, 4), null);
		b = board.getBoard();
		assertEquals("expected null", null, board.getBoard()[3][4]);
		assertEquals("expected null", null, b[3][4]);
		/* Below testing defensive copy */
		b[5][5] = piece1;
		assertEquals("expected piece1", piece1, b[5][5]);
		assertEquals("expected null", null, board.getBoard()[5][5]);
	}

}
