package circularOrbitTest;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import centralObject.Star;
import circularOrbit.CircularOrbit;
import circularOrbit.StellarSystem;
import circularOrbit.StellarSystemFactory;
import physicalObject.ConcretePlanetFactory;
import physicalObject.PhysicalObject;
import physicalObject.PlanetFactory;
import track.StellarTrackFactory;
import track.Track;

public class StellarSystemTest {

	private static StellarSystemFactory stellarSystemFactory = new StellarSystemFactory();
	private static StellarTrackFactory stellarTrackFactory = new StellarTrackFactory();
	private static CircularOrbit<Star, PhysicalObject> stellarSystem = stellarSystemFactory.produce();
	private static PlanetFactory planetFactory = new ConcretePlanetFactory();

	/*
	 * Test strategy
	 * 	All tests below are about tests in stellar system. 
	 * 	@Before readFromFile
	 * 		Before any test, stellarSystem should be initialized from StellarSystem.txt.
	 * 		The test is all about stellar system from StellarSystem.txt.
	 * 	testAddTrack
	 * 		This tests addTrack.
	 * 		Add a track to stellar system to see the change before and after adding.
	 * 		Call getTracks to see if the test track is added.
	 * 	testDeleteTrack
	 * 		This tests deleteTrack.
	 * 		Delete a track stellar system to see the change before and after deleting.
	 * 		Call getTracks to see if the track in test position is missed.
	 * 	testAddCentralObject
	 * 		This tests addCentralObject.
	 * 		Add a central object in stellar system to see the change before and after adding.
	 * 		Call getCentralObject to see if the central object is the test one after adding.
	 * 	testAddPhysicalObjectToTrack
	 * 		This tests addPhysicalObjectToTrack.
	 * 		Add a physical object to track to see the change before and after adding.
	 * 		Call getObjectsInTrack to see if the test object shows in the corresponding track after adding.
	 * 	testResetObjectsAndTrack
	 * 		This tests resetObjectsAndTrack.
	 * 		Call resetObjectsAndTrack and then see if the tracks and objectInTracks is empty.
	 * 	testGetCentralObject
	 * 		This tests getCentralObject.
	 * 		See if the central object fits the data in file.
	 * 	testGetTracks
	 * 		This tests getTracks.
	 * 		See if the tracks fits the data in file.
	 * 	testGetObjectsInTrack
	 * 		This tests getObjectInTrack.
	 * 		See if the objects in tracks fits the data in file.
	 * 	testParseNumber
	 * 		This tests static method parseNumber in StellarSystem.
	 * 		1. a number string without "e", i.e. non-scientific notation.
	 * 		2. a number string with "e", i.e. scientific notatin.
	 */

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false;
	}

	@Before
	public void readFromFile() {
		stellarSystem.readFromFile(new File("input/StellarSystem312.txt"));
	}

	@Test
	public void testAddTrack() {
		assertEquals("expected 8", 8, stellarSystem.getTracks().size());
		stellarSystem.addTrack(stellarTrackFactory.produce(1.3e5, 1.1e5));
		assertEquals("expected 9", 9, stellarSystem.getTracks().size());
		assertEquals(1.3e5, stellarSystem.getTracks().get(0).getLongRadius(), 0);
		assertEquals(1.1e5, stellarSystem.getTracks().get(0).getShortRadius(), 0);
	}

	@Test
	public void testDeleteTrack() {
		assertEquals("expected 8", 8, stellarSystem.getTracks().size());
		stellarSystem.deleteTrack(stellarTrackFactory.produce(1.99e5, 1.49e5));
		assertEquals("expected 7", 7, stellarSystem.getTracks().size());
		assertEquals(1.99e6, stellarSystem.getTracks().get(0).getLongRadius(), 0);
		assertEquals(1.49e6, stellarSystem.getTracks().get(0).getShortRadius(), 0);
	}

	@Test
	public void testAddCentralObject() {
		Star star = new Star("Sun2", 7e5, 2e30);
		assertEquals("expected Sun", "Sun", stellarSystem.getCentralObject().getName());
		stellarSystem.addCentralObject(star);
		assertEquals("expected Sun2", "Sun2", stellarSystem.getCentralObject().getName());
	}

	@Test
	public void testAddPhysicalObjectToTrack() {
		PhysicalObject earth2 = planetFactory.produce("Earth2", "Solid", "Blue", 6400, 30, true, 10);
		assertEquals("expected 1", 1, stellarSystem.getObjectsInTrack().get(stellarSystem.getTracks().get(3)).size());
		stellarSystem.addPhysicalObjectToTrack(earth2, stellarTrackFactory.produce(1.99e8, 1.49e8));
		assertEquals("expected 2", 2, stellarSystem.getObjectsInTrack().get(stellarSystem.getTracks().get(3)).size());
		assertTrue(stellarSystem.getObjectsInTrack().get(stellarSystem.getTracks().get(3)).contains(earth2));
	}

	@Test
	public void testResetObjectsAndTrack() {
		assertEquals("expected 1", 1, stellarSystem.getObjectsInTrack().get(stellarSystem.getTracks().get(0)).size());
		assertEquals("expected 8", 8, stellarSystem.getTracks().size());
		stellarSystem.resetObjectsAndTrack();
		assertTrue(stellarSystem.getTracks().isEmpty());
		assertTrue(stellarSystem.getObjectsInTrack().isEmpty());
	}

	@Test
	public void testGetCentralObject() {
		assertEquals(new Star("Sun", 6.96392e5, 1.9885e30), stellarSystem.getCentralObject());
	}

	@Test
	public void testGetTracks() {
		List<Track> tracks = stellarSystem.getTracks();
		assertEquals("expected 8", 8, tracks.size());
		assertEquals(1.99e5, tracks.get(0).getLongRadius(), 0.001 * tracks.get(0).getLongRadius());
		assertEquals(1.99e6, tracks.get(1).getLongRadius(), 0.001 * tracks.get(1).getLongRadius());
		assertEquals(1.99e7, tracks.get(2).getLongRadius(), 0.001 * tracks.get(2).getLongRadius());
		assertEquals(1.99e8, tracks.get(3).getLongRadius(), 0.001 * tracks.get(3).getLongRadius());
		assertEquals(2.8e8, tracks.get(4).getLongRadius(), 0.001 * tracks.get(4).getLongRadius());
		assertEquals(1.19e11, tracks.get(5).getLongRadius(), 0.001 * tracks.get(5).getLongRadius());
		assertEquals(1.99e11, tracks.get(6).getLongRadius(), 0.001 * tracks.get(6).getLongRadius());
		assertEquals(1.99e20, tracks.get(7).getLongRadius(), 0.001 * tracks.get(7).getLongRadius());

		assertEquals(1.49e5, tracks.get(0).getShortRadius(), 0.001 * tracks.get(0).getShortRadius());
		assertEquals(1.49e6, tracks.get(1).getShortRadius(), 0.001 * tracks.get(1).getShortRadius());
		assertEquals(1.49e7, tracks.get(2).getShortRadius(), 0.001 * tracks.get(2).getShortRadius());
		assertEquals(1.49e8, tracks.get(3).getShortRadius(), 0.001 * tracks.get(3).getShortRadius());
		assertEquals(2e8, tracks.get(4).getShortRadius(), 0.001 * tracks.get(4).getShortRadius());
		assertEquals(9.99e10, tracks.get(5).getShortRadius(), 0.001 * tracks.get(5).getShortRadius());
		assertEquals(1.49e11, tracks.get(6).getShortRadius(), 0.001 * tracks.get(6).getShortRadius());
		assertEquals(1.49e20, tracks.get(7).getShortRadius(), 0.001 * tracks.get(7).getShortRadius());

	}

	@Test
	public void testGetObjectsInTrack() {
		Map<Track, List<PhysicalObject>> objectsInTrack = stellarSystem.getObjectsInTrack();
		assertTrue(objectsInTrack.get(stellarSystem.getTracks().get(3))
				.contains(planetFactory.produce("Earth", "Solid", "Blue", 6378.137, 29.783, true, 0)));
		assertTrue(objectsInTrack.get(stellarSystem.getTracks().get(2))
				.contains(planetFactory.produce("Mercury", "Solid", "Dark", 1378.137, 69, true, 20.085)));
		assertTrue(objectsInTrack.get(stellarSystem.getTracks().get(1))
				.contains(planetFactory.produce("Saturn", "Liquid", "Red", 2178, 2.33e5, false, 39.21)));
		assertTrue(objectsInTrack.get(stellarSystem.getTracks().get(4))
				.contains(planetFactory.produce("Jupiter", "Gas", "Blue", 1637.007, 30, true, 70)));
		assertTrue(objectsInTrack.get(stellarSystem.getTracks().get(5))
				.contains(planetFactory.produce("Mars", "Silid", "Red", 637.137, 1000.93, false, 110)));
		assertTrue(objectsInTrack.get(stellarSystem.getTracks().get(0))
				.contains(planetFactory.produce("Neptune", "Liquid", "Blue", 6627.137, 9293.05, false, 359)));
		assertTrue(objectsInTrack.get(stellarSystem.getTracks().get(6))
				.contains(planetFactory.produce("Uranus", "Gas", "Blue", 637.137, 1e5, true, 359)));
		assertTrue(objectsInTrack.get(stellarSystem.getTracks().get(7))
				.contains(planetFactory.produce("Venus", "Solid", "Red", 6378.137, 203.24, false, 181.23)));
	}

	@Test
	public void testParseNumber() throws Exception {
		assertEquals(4.567, StellarSystem.parseNumber("4.567"), 0.0001);
		assertEquals(4.567e10, StellarSystem.parseNumber("4.567e10"), 1e6);
	}

}
