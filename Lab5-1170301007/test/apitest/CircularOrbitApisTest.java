package apitest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import api.CircularOrbitApis;
import centralobject.Nucleus;
import centralobject.Person;
import centralobject.Star;
import circularorbit.AtomStructureFactory;
import circularorbit.CircularOrbit;
import circularorbit.Edge;
import circularorbit.SocialNetworkCircleFactory;
import circularorbit.StellarSystemFactory;
import iostrategy.AtomStructureFileReader;
import iostrategy.AtomStructureIoContext;
import iostrategy.SocialNetworkCircleFileReader;
import iostrategy.SocialNetworkCircleIoContext;
import iostrategy.StellarSystemFileReader;
import iostrategy.StellarSystemIoContext;
import myexception.AtomElectronNumException;
import myexception.AtomElementException;
import myexception.AtomTrackNumException;
import myexception.DataScientificNumberException;
import myexception.DataSyntaxException;
import myexception.IllegalIntimacyInSocialTieException;
import physicalobject.ConcreteFriendFactory;
import physicalobject.ConcretePlanetFactory;
import physicalobject.FriendFactory;
import physicalobject.PhysicalObject;
import physicalobject.PlanetFactory;
import track.Track;
import track.TrackFactory;

public class CircularOrbitApisTest {

  private static CircularOrbitApis<Star, PhysicalObject> stellarSystemAPI =
      new CircularOrbitApis<Star, PhysicalObject>();
  private static CircularOrbitApis<Nucleus, PhysicalObject> atomStructureAPI =
      new CircularOrbitApis<Nucleus, PhysicalObject>();
  private static CircularOrbitApis<Person, PhysicalObject> socialNetworkCircleAPI =
      new CircularOrbitApis<Person, PhysicalObject>();
  private static StellarSystemFactory stellarSystemFactory = new StellarSystemFactory();
  private static CircularOrbit<Star, PhysicalObject> stellarSystem = stellarSystemFactory.produce();
  private static AtomStructureFactory atomStructureFactory = new AtomStructureFactory();
  private static CircularOrbit<Nucleus, PhysicalObject> atomStructure =
      atomStructureFactory.produce();
  private static SocialNetworkCircleFactory socialNetworkCircleFactory =
      new SocialNetworkCircleFactory();
  private static CircularOrbit<Person, PhysicalObject> socialNetworkCircle =
      socialNetworkCircleFactory.produce();
  private static FriendFactory friendFactory = new ConcreteFriendFactory();
  private static PlanetFactory planetFactory = new ConcretePlanetFactory();
  private static TrackFactory trackFactory = new TrackFactory();
  private static AtomStructureIoContext asContext =
      new AtomStructureIoContext(new AtomStructureFileReader());
  private static StellarSystemIoContext ssContext =
      new StellarSystemIoContext(new StellarSystemFileReader());
  private static SocialNetworkCircleIoContext sncContext =
      new SocialNetworkCircleIoContext(new SocialNetworkCircleFileReader());

  /*
   * Test strategy
   * 
   * @Before readFromFile Before any test all three circular orbits should be initialized from
   * corresponding files. testGetObjectDistributionEntropyInAtomStructure This tests
   * getObjectDistributionEntropy in atom structure. It calls getObjectDistributionEntropy and
   * compare it's result with my expectation. testGetObjectDistributionEntropyInStellarSystem This
   * tests getObjectDistributionEntropy in stellar system. It calls getObjectDistributionEntropy and
   * compare it's result with my expectation. testGetObjectDistributionEntropyInSocialNetworkCircle
   * This tests getObjectDistributionEntropy in social network circle. It calls
   * getObjectDistributionEntropy and compare it's result with my expectation.
   * testGetLogicalDistance This tests getLogicalDistance in social network circle. It calls
   * getLogicalDistance and compare results in all legal paths with expectations. testTargets This
   * tests static method targets in social network circle. It calls targets and choose FrankLee as
   * input to see if he's targets fit my expectation. testGetPhysicalDistance This tests
   * getPhysicalDistance in stellar system. It calls getPhysicalDistance and choose planets in 3rd
   * and 4th track as input to see if the physical distance fits expectation.
   * testGetPhysicalDistanceFromCentralToObject This tests getPhysicalDistanceFromCentralToObject in
   * stellar system. It calls getPhysicalDistanceFromCentralToObject and test every planet's
   * distance to central star to see if the result fits expectation. testGetDiffereceAtomStructure
   * This tests getDiffereceAtomStructure in atom structure. It calls getDifferece and test if the
   * result fits the difference between two atom structures. testGetDiffereceStellarSystem This
   * tests getDiffereceAtomStructure in stellar system. It calls getDifferece and test if the result
   * fits the difference between two stellar systems. testGetDiffereceSocialNetworkCircle This tests
   * getDiffereceAtomStructure in social network circle. It calls getDifferece and test if the
   * result fits the difference between two social network circles.
   */
  @Test(expected = AssertionError.class)
  public void testAssertionsEnabled() {
    assert false;
  }

  /**
   * readFromFile before every test.
   */
  @Before
  public void readFromFile() {
    File file1 = new File("input/AtomicStructure.txt");
    File file2 = new File("input/StellarSystem.txt");
    File file3 = new File("input/SocialNetworkCircle.txt");
    try {
      // atomStructure.readFromFile(file1);
      // stellarSystem.readFromFile(file2);
      // socialNetworkCircle.readFromFile(file3);
      asContext.readFromFile(atomStructure, file1);
      ssContext.readFromFile(stellarSystem, file2);
      sncContext.readFromFile(socialNetworkCircle, file3);
    } catch (FileNotFoundException e) {
      System.out.println("File not found. " + e.getMessage());
    } catch (IOException e) {
      e.printStackTrace();
    } catch (NumberFormatException e) {
      e.printStackTrace();
    } catch (AtomElementException | AtomTrackNumException | AtomElectronNumException
        | DataSyntaxException | DataScientificNumberException
        | IllegalIntimacyInSocialTieException e) {
      System.out.println("Please modify the file correctly. " + e.getMessage());
      e.printStackTrace();
    }

  }

  @Test
  public void testGetObjectDistributionEntropyInAtomStructure() {
    assertEquals(1.2681, atomStructureAPI.getObjectDistributionEntropy(atomStructure), 0.0001);
  }

  @Test
  public void testGetObjectDistributionEntropyInStellarSystem() {
    assertEquals(2.0794, stellarSystemAPI.getObjectDistributionEntropy(stellarSystem), 0.0001);
  }

  @Test
  public void testGetObjectDistributionEntropyInSocialNetworkCircle() {
    assertEquals(0.5623, socialNetworkCircleAPI.getObjectDistributionEntropy(socialNetworkCircle),
        0.0001);
  }

  @Test
  public void testGetLogicalDistance() {
    PhysicalObject frankLee = friendFactory.produce("FrankLee", 42, 'M');
    PhysicalObject lisaWong = friendFactory.produce("LisaWong", 25, 'F');
    PhysicalObject tomWong = friendFactory.produce("TomWong", 61, 'M');
    PhysicalObject davidChen = friendFactory.produce("DavidChen", 55, 'M');
    assertEquals("expected 2 from LisaWong to TomWong", 2,
        socialNetworkCircleAPI.getLogicalDistance(socialNetworkCircle, lisaWong, tomWong));
    assertEquals("expected 3 from LisaWong to FrankLee", 3,
        socialNetworkCircleAPI.getLogicalDistance(socialNetworkCircle, lisaWong, frankLee));
    assertEquals("expected 2 from LisaWong to DavidChen", 2,
        socialNetworkCircleAPI.getLogicalDistance(socialNetworkCircle, lisaWong, davidChen));
    assertEquals("expected 1 from FrankLee to TomWong", 1,
        socialNetworkCircleAPI.getLogicalDistance(socialNetworkCircle, frankLee, tomWong));
    assertEquals("expected 2 from DavidChen to TomWong", 2,
        socialNetworkCircleAPI.getLogicalDistance(socialNetworkCircle, davidChen, tomWong));
  }

  @Test
  public void testTargets() {
    Map<PhysicalObject, Double> targets =
        CircularOrbitApis.targets(friendFactory.produce("FrankLee", 42, 'M'),
            socialNetworkCircle.getRelationBetweenObjects());
    assertEquals("expected 2", 2, targets.keySet().size());
    assertTrue("expected TomWong",
        targets.keySet().contains(friendFactory.produce("TomWong", 61, 'M')));
    assertTrue("expected DavidChen",
        targets.keySet().contains(friendFactory.produce("DavidChen", 55, 'M')));
  }

  @Test
  public void testGetPhysicalDistance() {
    PhysicalObject e1 =
        stellarSystem.getObjectsInTrack().get(stellarSystem.getTracks().get(2)).get(0);
    PhysicalObject e2 =
        stellarSystem.getObjectsInTrack().get(stellarSystem.getTracks().get(3)).get(0);
    assertEquals(1.987e8, stellarSystemAPI.getPhysicalDistance(stellarSystem, e1, e2), 0.02e8);
  }

  @Test
  public void testGetPhysicalDistanceFromCentralToObject() {
    for (Track track : stellarSystem.getTracks()) {
      for (PhysicalObject planet : stellarSystem.getObjectsInTrack().get(track)) {
        assertTrue(track.getRadius() == stellarSystemAPI
            .getPhysicalDistanceFromCentralToObject(stellarSystem, planet));
      }
    }
  }

  @Test
  public void testGetDiffereceAtomStructure() throws Exception {
    CircularOrbit<Nucleus, PhysicalObject> c = atomStructureFactory.produce();
    // c.readFromFile(new File("input/AtomicStructure_Medium.txt"));
    asContext.readFromFile(c, new File("input/AtomicStructure_Medium.txt"));
    String difference;
    difference = "轨道数差异：-1\n" + "轨道1的物体数量差异：0\n" + "轨道2的物体数量差异：0\n" + "轨道3的物体数量差异：0\n"
        + "轨道4的物体数量差异：-22\n" + "轨道5的物体数量差异：-7\n" + "轨道6的物体数量差异：-2\n";
    assertEquals(difference, atomStructureAPI.getDifference(atomStructure, c).toString());
  }

  @Test
  public void testGetDiffereceStellarSystem() throws Exception {
    CircularOrbit<Star, PhysicalObject> c = stellarSystemFactory.produce();
    // c.readFromFile(new File("input/StellarSystem.txt"));
    ssContext.readFromFile(c, new File("input/StellarSystem.txt"));
    c.addTrack(trackFactory.produce(1.49e10));
    c.addPhysicalObjectToTrack(planetFactory.produce("Earth2", "Solid", "Blue", 6400, 30, true, 20),
        trackFactory.produce(1.49e10));
    String difference;
    difference =
        "轨道数差异：-1\n" + "轨道1的物体数量差异：0；物体差异：无\n" + "轨道2的物体数量差异：0；物体差异：无\n" + "轨道3的物体数量差异：0；物体差异：无\n"
            + "轨道4的物体数量差异：0；物体差异：无\n" + "轨道5的物体数量差异：0；物体差异：无\n" + "轨道6的物体数量差异：0；物体差异：无\n"
            + "轨道7的物体数量差异：0；物体差异：无\n" + "轨道8的物体数量差异：0；物体差异：无\n" + "轨道9的物体数量差异：-1；物体差异：无-Earth2\n";
    assertEquals(difference, stellarSystemAPI.getDifference(stellarSystem, c).toString());
  }

  @Test
  public void testGetDiffereceSocialNetworkCircle() throws Exception {
    CircularOrbit<Person, PhysicalObject> c = socialNetworkCircleFactory.produce();
    // c.readFromFile(new File("input/SocialNetWorkCircle.txt"));
    sncContext.readFromFile(c, new File("input/SocialNetWorkCircle.txt"));
    c.deletePhysicalObjectFromTrack(friendFactory.produce("FrankLee", 42, 'M'),
        trackFactory.produce(2.0));
    Map<String, PhysicalObject> friends = new HashMap<String, PhysicalObject>();
    for (PhysicalObject friend : c) {
      friends.put(friend.getName(), friend);
    }
    @SuppressWarnings("unchecked")
    Map<PhysicalObject, Double> centralEdges = new HashMap<PhysicalObject, Double>();
    centralEdges = c.getRelationBetweenCentralAndObject();
    // centralEdges[1] = c.getRelationBetweenCentralAndObject()[1];
    Set<Edge<PhysicalObject>> physicalEdges = c.getRelationBetweenObjects();
    c.resetObjectsAndTrack();
    c.constructSocialNetworkCircle(friends);
    String difference;
    difference = "轨道数差异：1\n" + "轨道1的物体数量差异：0；物体差异：无\n" + "轨道2的物体数量差异：1；物体差异：FrankLee-无\n";
    assertEquals(difference,
        socialNetworkCircleAPI.getDifference(socialNetworkCircle, c).toString());
  }
}
