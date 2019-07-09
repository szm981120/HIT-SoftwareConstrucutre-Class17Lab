package P3;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class PlayerTest {

	/**
	 * Tests that assertions are enabled
	 */
	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false;
	}

	@Test
	public void test() {
		Player player = new Player("me");
		Piece piece = new Piece("queen", "me");
		Piece piece_ = new Piece("king", "you");
		String testHistory = "a test message";
		Set<Piece> p = new HashSet<Piece>();
		List<String> history = new ArrayList<String>();
		assertEquals("expected me", "me", player.getName());
		assertTrue("expected null", player.getPieces().isEmpty());
		assertTrue("expected null", player.getHistory().isEmpty());

		/* test addPiece */
		player.addPiece(piece);
		assertTrue("expected true", player.getPieces().contains(piece));

		/* Below testing getPieces defensive copy */
		p = player.getPieces();
		p.add(piece_);
		assertTrue("expected true", p.contains(piece_));
		assertFalse("expected false", player.getPieces().contains(piece_));

		/* test removePiece */
		player.removePiece(piece);
		assertFalse("expected", player.getPieces().contains(piece));

		/* test addHistory */
		player.addHistory(testHistory);
		assertTrue("expected true", player.getHistory().contains(testHistory));

		/* Below testing getHistory defensive copy */
		history = player.getHistory();
		history.remove(testHistory);
		assertFalse("expected false", history.contains(testHistory));
		assertTrue("expected true", player.getHistory().contains(testHistory));

	}

}
