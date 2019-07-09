package P3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class chessGameTest {

	chessGame game = new chessGame("jack", "rose");
	Player jack = game.getPlayer1();
	Player rose = game.getPlayer2();

	/*
	 * Test Strategy
	 * The test strategy is test 4 functions in a chess game. The situation is like below:
	 * Move
	 * 	The situations below happen in places where no moving happened before
	 * 	- x-axis of source position out of board
	 * 	- y-axis of source position out of board
	 * 	- x-axis of target position out of board
	 * 	- y-axis of target position out of board
	 * 	- a piece is in the target place
	 * 	- no piece is in the source place
	 * 	- two position are the same
	 * 	- the piece in source position is the opponent's
	 * 	About he situations below happen in places where some movings happened before, 
	 * 	first confirm the movings are valid and then confirm the moving results.
	 * Kill 
	 * 	The situations below happen in places where no killing happened before
	 * 	- x-axis of source position out of board
	 * 	- y-axis of source position out of board
	 * 	- x-axis of target position out of board
	 * 	- y-axis of target position out of board
	 * 	- no piece is in the source position
	 * 	- no piece is in the target position
	 * 	- the two positions are the same
	 * 	- the piece in the source position is the opponent's
	 * 	- the piece in the target position is not the opponent's
	 * 	About he situations below happen in places where some killings happened before, 
	 * 	first confirm the killings are valid and then confirm the killing results.
	 * Check position
	 * 	- check the original position
	 * 	- check a moved piece position
	 * Total
	 * 	- test original total
	 * 	- test total after a kill
	 */
	/**
	 * Tests that assertions are enabled
	 */
	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false;
	}

	@Test
	public void testMove() {
		assertFalse("Source x out of board.", game.move(jack, new Position(0, 1), new Position(1, 1)));
		assertFalse("Source y out of board.", game.move(jack, new Position(1, 9), new Position(1, 1)));
		assertFalse("Target x out of board.", game.move(jack, new Position(1, 1), new Position(10, 1)));
		assertFalse("Target y out of board.", game.move(jack, new Position(1, 2), new Position(3, 0)));
		assertFalse("Existed piece in the target place.", game.move(jack, new Position(2, 2), new Position(5, 1)));
		assertFalse("Existed piece in the target place.", game.move(jack, new Position(2, 2), new Position(8, 8)));
		assertFalse("No piece in the source place.", game.move(jack, new Position(5, 5), new Position(8, 8)));
		assertFalse("Two same position.", game.move(jack, new Position(2, 2), new Position(2, 2)));
		assertFalse("Not your piece.", game.move(rose, new Position(2, 1), new Position(3, 4)));
		assertTrue(game.move(jack, new Position(1, 1), new Position(1, 3)));
		assertTrue(game.move(rose, new Position(3, 8), new Position(4, 4)));
		assertFalse("Existed piece in the target place.", game.move(jack, new Position(1, 3), new Position(4, 4)));
		assertFalse("Existed piece in the target place.", game.move(rose, new Position(4, 4), new Position(1, 3)));
		assertFalse("Not your piece.", game.move(jack, new Position(4, 4), new Position(3, 3)));
		assertFalse("Not your piece.", game.move(rose, new Position(1, 3), new Position(5, 5)));
	}

	@Test
	public void testKill() {
		assertFalse("Source x out of board.", game.kill(jack, new Position(0, 1), new Position(1, 1)));
		assertFalse("Source y out of board.", game.kill(jack, new Position(1, 9), new Position(1, 1)));
		assertFalse("Target x out of board.", game.kill(jack, new Position(1, 1), new Position(10, 1)));
		assertFalse("Target y out of board.", game.kill(jack, new Position(1, 2), new Position(3, 0)));
		assertFalse("No piece in the source place.", game.kill(jack, new Position(5, 5), new Position(8, 8)));
		assertFalse("No piece in the target place.", game.kill(jack, new Position(5, 1), new Position(5, 6)));
		assertFalse("Two same position.", game.kill(rose, new Position(7, 7), new Position(7, 7)));
		assertFalse("Not your piece.", game.kill(rose, new Position(2, 1), new Position(3, 4)));
		assertFalse("Can't kill your piece.", game.kill(rose, new Position(2, 1), new Position(7, 8)));
		assertTrue(game.kill(jack, new Position(1, 1), new Position(7, 8)));
		assertEquals("expected rose", "jack", game.getBoard().getBoard()[7][8].getColor());
		assertTrue(game.kill(rose, new Position(3, 8), new Position(2, 2)));
	}

	@Test
	public void testCheckPosition() {
		assertEquals("expected pawn1", "pawn1", game.getBoard().getBoard()[1][2].getType());
		assertEquals("expected queen", "queen", game.getBoard().getBoard()[5][8].getType());
		game.move(jack, new Position(3, 1), new Position(4, 4));
		assertEquals("expected bishop1", "bishop1", game.getBoard().getBoard()[4][4].getType());
	}

	@Test
	public void testTotal() {
		assertEquals("expected 16", 16, game.getPlayer1().getPieces().size());
		game.kill(rose, new Position(5, 8), new Position(2, 2));
		assertEquals("expected 15", 15, game.getPlayer1().getPieces().size());
	}

}
