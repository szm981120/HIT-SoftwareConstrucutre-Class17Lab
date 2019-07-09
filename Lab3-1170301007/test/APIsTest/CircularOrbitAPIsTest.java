package APIsTest;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import APIs.CircularOrbitAPIs;
import centralObject.Nucleus;
import centralObject.Person;
import centralObject.Star;
import circularOrbit.AtomStructureFactory;
import circularOrbit.CircularOrbit;
import circularOrbit.Edge;
import circularOrbit.SocialNetworkCircleFactory;
import circularOrbit.StellarSystemFactory;
import physicalObject.ConcreteFriendFactory;
import physicalObject.ConcretePlanetFactory;
import physicalObject.FriendFactory;
import physicalObject.PhysicalObject;
import physicalObject.PlanetFactory;
import track.StellarTrackFactory;
import track.Track;
import track.TrackFactory;

public class CircularOrbitAPIsTest {

	private static CircularOrbitAPIs<Star, PhysicalObject> stellarSystemAPI = new CircularOrbitAPIs<Star, PhysicalObject>();
	private static CircularOrbitAPIs<Nucleus, PhysicalObject> atomStructureAPI = new CircularOrbitAPIs<Nucleus, PhysicalObject>();
	private static CircularOrbitAPIs<Person, PhysicalObject> socialNetworkCircleAPI = new CircularOrbitAPIs<Person, PhysicalObject>();
	private static StellarSystemFactory stellarSystemFactory = new StellarSystemFactory();
	private static CircularOrbit<Star, PhysicalObject> stellarSystem = stellarSystemFactory.produce();
	private static AtomStructureFactory atomStructureFactory = new AtomStructureFactory();
	private static CircularOrbit<Nucleus, PhysicalObject> atomStructure = atomStructureFactory.produce();
	private static SocialNetworkCircleFactory socialNetworkCircleFactory = new SocialNetworkCircleFactory();
	private static CircularOrbit<Person, PhysicalObject> socialNetworkCircle = socialNetworkCircleFactory.produce();
	private static FriendFactory friendFactory = new ConcreteFriendFactory();
	private static PlanetFactory planetFactory = new ConcretePlanetFactory();
	private static TrackFactory trackFactory = new TrackFactory();
	private static StellarTrackFactory stellarTrackFactory = new StellarTrackFactory();

	/*
	 * Test strategy
	 * 	@Before readFromFile
	 * 		Before any test all three circular orbits should be initialized from corresponding files.
	 * 	testGetObjectDistributionEntropyInAtomStructure
	 * 		This tests getObjectDistributionEntropy in atom structure.
	 * 		It calls getObjectDistributionEntropy and compare it's result with my expectation.
	 * 	testGetObjectDistributionEntropyInStellarSystem
	 * 		This tests getObjectDistributionEntropy in stellar system.
	 * 		It calls getObjectDistributionEntropy and compare it's result with my expectation.
	 * 	testGetObjectDistributionEntropyInSocialNetworkCircle
	 * 		This tests getObjectDistributionEntropy in social network circle.
	 * 		It calls getObjectDistributionEntropy and compare it's result with my expectation.
	 * 	testGetLogicalDistance
	 * 		This tests getLogicalDistance in social network circle.
	 * 		It calls getLogicalDistance and compare results in all legal paths with expectations.
	 * 	testTargets
	 * 		This tests static method targets in social network circle.
	 * 		It calls targets and choose FrankLee as input to see if he's targets fit my expectation.
	 * 	testGetPhysicalDistance
	 * 		This tests getPhysicalDistance in stellar system.
	 * 		It calls getPhysicalDistance and choose planets in 3rd and 4th track as input to see if the physical distance fits expectation.
	 * 	testGetPhysicalDistanceFromCentralToObject
	 * 		This tests getPhysicalDistanceFromCentralToObject in stellar system.
	 * 		It calls getPhysicalDistanceFromCentralToObject and test every planet's distance to central star to see if the result fits expectation.
	 * 	testGetDiffereceAtomStructure
	 * 		This tests getDiffereceAtomStructure in atom structure.
	 * 		It calls getDifferece and test if the result fits the difference between two atom structures.
	 */
	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false;
	}

	@Before
	public void readFromFile() {
		atomStructure.readFromFile(new File("input/AtomicStructure.txt"));
		stellarSystem.readFromFile(new File("input/StellarSystem312.txt"));
		socialNetworkCircle.readFromFile(new File("input/SocialNetworkCircle.txt"));
	}

	@Test
	public void testGetObjectDistributionEntropyInAtomStructure() {
		assertTrue("expected 1.2681...",
				Math.abs(atomStructureAPI.getObjectDistributionEntropy(atomStructure) - 1.2681) < 0.0001);
	}

	@Test
	public void testGetObjectDistributionEntropyInStellarSystem() {
		assertTrue("expected 2.0794...",
				Math.abs(stellarSystemAPI.getObjectDistributionEntropy(stellarSystem) - 2.0794) < 0.0001);
	}

	@Test
	public void testGetObjectDistributionEntropyInSocialNetworkCircle() {
		assertTrue("expected 0.5623...",
				Math.abs(socialNetworkCircleAPI.getObjectDistributionEntropy(socialNetworkCircle) - 0.5623) < 0.0001);
	}

	@Test
	public void testGetLogicalDistance() {
		PhysicalObject frankLee = friendFactory.produce("FrankLee", 42, 'M');
		PhysicalObject lisaWong = friendFactory.produce("LisaWong", 25, 'F');
		PhysicalObject tomWong = friendFactory.produce("TomWong", 61, 'M');
		PhysicalObject davidChen = friendFactory.produce("DavidChen", 55, 'M');
		assertEquals("expected -1 from LisaWong to TomWong", -1,
				socialNetworkCircleAPI.getLogicalDistance(socialNetworkCircle, lisaWong, tomWong));
//		assertEquals("expected 3 from LisaWong to FrankLee", 3,
//				socialNetworkCircleAPI.getLogicalDistance(socialNetworkCircle, lisaWong, frankLee));
//		assertEquals("expected 2 from LisaWong to DavidChen", 2,
//				socialNetworkCircleAPI.getLogicalDistance(socialNetworkCircle, lisaWong, davidChen));
		assertEquals("expected 1 from TomWong to FrankLee", 1,
				socialNetworkCircleAPI.getLogicalDistance(socialNetworkCircle, tomWong, frankLee));
		assertEquals("expected 2 from TomWong to DavidChen", 2,
				socialNetworkCircleAPI.getLogicalDistance(socialNetworkCircle, tomWong, davidChen));
	}

	@Test
	public void testTargets() {
		Map<PhysicalObject, Double> targets = CircularOrbitAPIs.targets(friendFactory.produce("FrankLee", 42, 'M'),
				socialNetworkCircle.getRelationBetweenObjects());
		assertEquals("expected 1", 1, targets.keySet().size());
//		assertTrue("expected TomWong", targets.keySet().contains(friendFactory.produce("TomWong", 61, 'M')));
		assertTrue("expected DavidChen", targets.keySet().contains(friendFactory.produce("DavidChen", 55, 'M')));
	}

	@Test
	public void testGetPhysicalDistance() {
		PhysicalObject e1 = stellarSystem.getObjectsInTrack().get(stellarSystem.getTracks().get(2)).get(0);
		PhysicalObject e2 = stellarSystem.getObjectsInTrack().get(stellarSystem.getTracks().get(3)).get(0);
		assertEquals(1.791e8, stellarSystemAPI.getPhysicalDistance(stellarSystem, e1, e2), 0.1e8);
	}

	@Test
	public void testGetPhysicalDistanceFromCentralToObject() {
		for (Track track : stellarSystem.getTracks()) {
			for (PhysicalObject planet : stellarSystem.getObjectsInTrack().get(track)) {
				assertEquals(
						Math.sqrt(Math.pow(track.getLongRadius() * Math.cos(planet.getDegree() * Math.PI / 180), 2)
								+ Math.pow(track.getShortRadius() * Math.sin(planet.getDegree() * Math.PI / 180), 2)),
						stellarSystemAPI.getPhysicalDistanceFromCentralToObject(stellarSystem, planet), 0);
			}
		}
	}

	@Test
	public void testGetDiffereceAtomStructure() throws Exception {
		CircularOrbit<Nucleus, PhysicalObject> c = atomStructureFactory.produce();
		c.readFromFile(new File("input/AtomicStructure_Medium.txt"));
		String difference;
		difference = "轨道数差异：-1\n" + "轨道1的物体数量差异：0\n" + "轨道2的物体数量差异：0\n" + "轨道3的物体数量差异：0\n" + "轨道4的物体数量差异：-22\n"
				+ "轨道5的物体数量差异：-7\n" + "轨道6的物体数量差异：-2\n";
		assertEquals(difference, atomStructureAPI.getDifference(atomStructure, c).toString());
	}

	@Test
	public void testGetDiffereceStellarSystem() throws Exception {
		CircularOrbit<Star, PhysicalObject> c = stellarSystemFactory.produce();
		c.readFromFile(new File("input/StellarSystem312.txt"));
		c.addTrack(stellarTrackFactory.produce(1.99e10, 1.49e10));
		c.addPhysicalObjectToTrack(planetFactory.produce("Earth2", "Solid", "Blue", 6400, 30, true, 20),
				stellarTrackFactory.produce(1.99e10, 1.49e10));
		String difference;
		difference = "轨道数差异：-1\n" + "轨道1的物体数量差异：0；物体差异：无\n" + "轨道2的物体数量差异：0；物体差异：无\n" + "轨道3的物体数量差异：0；物体差异：无\n"
				+ "轨道4的物体数量差异：0；物体差异：无\n" + "轨道5的物体数量差异：0；物体差异：无\n" + "轨道6的物体数量差异：0；物体差异：Mars-Earth2\n"
				+ "轨道7的物体数量差异：0；物体差异：Uranus-Mars\n" + "轨道8的物体数量差异：0；物体差异：Venus-Uranus\n"
				+ "轨道9的物体数量差异：-1；物体差异：无-Venus\n";
		assertEquals(difference, stellarSystemAPI.getDifference(stellarSystem, c).toString());
	}

	@Test
	public void testGetDiffereceSocialNetworkCircle() throws Exception {
		CircularOrbit<Person, PhysicalObject> c = socialNetworkCircleFactory.produce();
		c.readFromFile(new File("input/SocialNetWorkCircle.txt"));
		c.deletePhysicalObjectFromTrack(friendFactory.produce("FrankLee", 42, 'M'), trackFactory.produce(2.0));
		List<PhysicalObject> friends = new ArrayList<PhysicalObject>();
		for (PhysicalObject friend : c) {
			friends.add(friend);
		}
		List<Edge<PhysicalObject>> physicalEdges = c.getRelationBetweenObjects();
		@SuppressWarnings("unchecked")
		Map<PhysicalObject, Double>[] centralEdges = new Map[2];
		centralEdges[0] = c.getRelationBetweenCentralAndObject()[0];
		centralEdges[1] = c.getRelationBetweenCentralAndObject()[1];
		c.resetObjectsAndTrack();
		c.constructSocialNetworkCircle(friends, physicalEdges, centralEdges);
		String difference;
		difference = "轨道数差异：1\n" + "轨道1的物体数量差异：0；物体差异：无\n" + "轨道2的物体数量差异：1；物体差异：FrankLee-无\n";
		assertEquals(difference, socialNetworkCircleAPI.getDifference(socialNetworkCircle, c).toString());
	}
}
