package P3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

public class goGameTest {

	goGame game = new goGame("jack", "rose");
	Player jack = game.getPlayer1();
	Player rose = game.getPlayer2();
	
	/*
	 * Test Strategy
	 * The test strategy is test 4 functions in a go game. The situation is like below:
	 * Move
	 * 	- x-axis of target position out of board
	 * 	- y-axis of target position out of board
	 * 	- a piece is in the target place(either the owner's or the opponent's)
	 * 	About he situations below happen in places where some movings happened before, 
	 * 	first confirm the movings are valid and then confirm the moving results.
	 * Kill 
	 * 	- x-axis of target position out of board
	 * 	- y-axis of target position out of board
	 * 	- no piece is in the target position
	 * 	- the piece in the target position is not the opponent's
	 * 	About he situations below happen in places where some killings happened before, 
	 * 	first confirm the killings are valid and then confirm the killing results.
	 * Check position
	 * 	- check a moved piece position
	 * Total
	 * 	- test total before and after a kill
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
		assertFalse("Target x out of board.", game.move(jack, null, new Position(20, 1)));
		assertFalse("Target y out of board.", game.move(jack, null, new Position(3, 0)));
		game.move(jack, null, new Position(9, 9));
		assertFalse("Existed piece in the target place.", game.move(jack, null, new Position(9, 9)));
	}
	@Test
	public void testKill() {
		assertFalse("Target x out of board.", game.kill(jack, null, new Position(20, 1)));
		assertFalse("Target y out of board.", game.kill(jack, null, new Position(3, 0)));
		assertFalse("No piece in the target place.", game.kill(jack, null, new Position(5, 6)));
		game.move(jack, null, new Position(5, 5));
		assertFalse("Can't kill your piece.", game.kill(jack, null, new Position(5, 5)));
	}
	@Test
	public void testCheckPosition() {
		game.move(jack, null, new Position(10, 10));
		game.move(rose, null, new Position(3, 5));
		assertEquals("expected jack", "jack", game.getBoard().getBoard()[10][10].getColor());
		assertEquals("expected rose", "rose", game.getBoard().getBoard()[3][5].getColor());
	}
	@Test
	public void testTotal() {
		game.move(jack, null, new Position(10, 10));
		game.move(rose, null, new Position(3, 5));
		game.move(jack, null, new Position(12, 12));
		game.move(rose, null, new Position(6, 7));
		assertEquals("expected 2", 2, game.getPlayer1().getPieces().size());
		game.kill(rose, null, new Position(12, 12));
		assertEquals("expected 1", 1, game.getPlayer1().getPieces().size());
	}

}
