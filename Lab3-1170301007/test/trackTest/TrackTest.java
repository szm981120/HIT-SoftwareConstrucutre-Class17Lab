package trackTest;

import static org.junit.Assert.*;

import org.junit.Test;

import track.NormalTrack;
import track.StellarTrackFactory;
import track.Track;
import track.TrackFactory;

public class TrackTest {

	private static TrackFactory trackFactory = new TrackFactory();
	private static StellarTrackFactory stellarTrackFactory = new StellarTrackFactory();
	private static Track track = trackFactory.produce(12.3);
	private static Track stellarTrack = stellarTrackFactory.produce(18, 9);

	/*
	 * Test strategy
	 * 	testGetRadius
	 * 		This tests getRadius.
	 * 		The radius of normal track is generated designative.
	 * 		Test if a normal track's radius is the same as the initialized radius.
	 * 	testGetLongRadius
	 * 		This tests getLongRadius.
	 * 		The long radius of stellar track is generated designative.
	 * 		Test if a stellar track's radius is the same as the initialized long radius.
	 * testGetRadius
	 * 		This tests getRadius.
	 * 		The radius of stellar track is generated designative.
	 * 		Test if a stellar track's radius is the same as the initialized short radius.
	 * 	testEquals
	 * 		Any two tracks who have the same radius should be concerned the same.
	 * 		To see if equals works well, generate two tracks who have the same radius and assert they are equal.
	 */

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false;
	}

	@Test
	public void testGetRadius() {
		assertEquals(12.3, track.getRadius(), 0);
	}

	@Test
	public void testGetLongRadius() {
		assertEquals(18, stellarTrack.getLongRadius(), 0);
	}

	@Test
	public void testGetShortRadius() {
		assertEquals(9, stellarTrack.getShortRadius(), 0);
	}

	@Test
	public void testEquals() {
		Track track_ = trackFactory.produce(12.3);
		assertTrue(track.equals(track_));
	}

}
