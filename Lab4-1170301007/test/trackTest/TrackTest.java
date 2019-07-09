package trackTest;

import static org.junit.Assert.*;

import org.junit.Test;

import track.Track;
import track.TrackFactory;

public class TrackTest {

	private static TrackFactory trackFactory = new TrackFactory();
	private static Track track = trackFactory.produce(12.3);

	/*
	 * Test strategy
	 * 	testGetRadius
	 * 		This tests getRadius.
	 * 		Because the radius of track is generated designative. Test if a track's radius is the same as the initialized radius.
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
	public void testEquals() {
		Track track_ = trackFactory.produce(12.3);
		assertTrue(track.equals(track_));
	}

}
