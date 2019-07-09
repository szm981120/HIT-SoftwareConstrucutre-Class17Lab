package circularorbittest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import centralobject.Star;
import circularorbit.CircularOrbit;
import circularorbit.StellarSystem;
import circularorbit.StellarSystemFactory;
import iostrategy.StellarSystemFileReader;
import iostrategy.StellarSystemIoContext;
import myexception.DataScientificNumberException;
import myexception.DataSyntaxException;
import physicalobject.ConcretePlanetFactory;
import physicalobject.PhysicalObject;
import physicalobject.PlanetFactory;
import track.Track;
import track.TrackFactory;

public class StellarSystemTest {

  private static StellarSystemFactory stellarSystemFactory = new StellarSystemFactory();
  private static TrackFactory trackFactory = new TrackFactory();
  private static CircularOrbit<Star, PhysicalObject> stellarSystem = stellarSystemFactory.produce();
  private static PlanetFactory planetFactory = new ConcretePlanetFactory();

  /*
   * Test strategy All tests below are about tests in stellar system.
   * 
   * @Before readFromFile Before any test, stellarSystem should be initialized from
   * StellarSystem.txt. The test is all about stellar system from StellarSystem.txt. testAddTrack
   * This tests addTrack. Add a track to stellar system to see the change before and after adding.
   * Call getTracks to see if the test track is added. testDeleteTrack This tests deleteTrack.
   * Delete a track stellar system to see the change before and after deleting. Call getTracks to
   * see if the track in test position is missed. testAddCentralObject This tests addCentralObject.
   * Add a central object in stellar system to see the change before and after adding. Call
   * getCentralObject to see if the central object is the test one after adding.
   * testAddPhysicalObjectToTrack This tests addPhysicalObjectToTrack. Add a physical object to
   * track to see the change before and after adding. Call getObjectsInTrack to see if the test
   * object shows in the corresponding track after adding. testResetObjectsAndTrack This tests
   * resetObjectsAndTrack. Call resetObjectsAndTrack and then see if the tracks and objectInTracks
   * is empty. testGetCentralObject This tests getCentralObject. See if the central object fits the
   * data in file. testGetTracks This tests getTracks. See if the tracks fits the data in file.
   * testGetObjectsInTrack This tests getObjectInTrack. See if the objects in tracks fits the data
   * in file. testParseNumber This tests static method parseNumber in StellarSystem. 1. a number
   * string without "e", i.e. non-scientific notation. 2. a number string with "e", i.e. scientific
   * notatin. testConstructSocialNetworkCircle Test that stellar system shouldn't have
   * constructSocialNetworkCircle method. testSave Test that stellar system shouldn't have save
   * method. testRestore Test that stellar system shouldn't have restore method.
   * testAddRelationshipBetweenCentralAndPhysical Test that stellar system shouldn't have
   * addRelationshipBetweenCentralAndPhysical method.
   * testDeleteRelationshipBetweenPhysicalAndPhysical Test that stellar system shouldn't have
   * deleteRelationshipBetweenPhysicalAndPhysical method. testGetRelationBetweenCentralAndObject
   * Test that stellar system shouldn't have getRelationBetweenCentralAndObject method.
   * testGetRelationBetweenObjects Test that stellar system shouldn't have getRelationBetweenObjects
   * method.
   */

  @Test(expected = AssertionError.class)
  public void testAssertionsEnabled() {
    assert false;
  }

  /**
   * readFromFile before all tests.
   */
  @Before
  public void readFromFile() {
    File file = new File("input/StellarSystem.txt");
    StellarSystemIoContext ssContext = new StellarSystemIoContext(new StellarSystemFileReader());
    try {
      // stellarSystem.readFromFile(file);
      ssContext.readFromFile(stellarSystem, file);
    } catch (NumberFormatException e) {
      e.printStackTrace();
    } catch (FileNotFoundException e) {
      System.out.println("File not found. File: " + file.getPath() + ". " + e.getMessage());
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (DataSyntaxException e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    } catch (DataScientificNumberException e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }

  }

  @Test
  public void testAddTrack() {
    assertEquals("expected 8", 8, stellarSystem.getTracks().size());
    stellarSystem.addTrack(trackFactory.produce(1.1e5));
    assertEquals("expected 9", 9, stellarSystem.getTracks().size());
    assertEquals(1.49e8, stellarSystem.getTracks().get(0).getRadius(), 1000);
  }

  @Test
  public void testDeleteTrack() {
    assertEquals("expected 8", 8, stellarSystem.getTracks().size());
    stellarSystem.deleteTrack(trackFactory.produce(1.49e5));
    assertEquals("expected 7", 7, stellarSystem.getTracks().size());
    assertEquals(1.49e8, stellarSystem.getTracks().get(0).getRadius(), 1000);
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
    assertEquals("expected 1", 1,
        stellarSystem.getObjectsInTrack().get(stellarSystem.getTracks().get(0)).size());
    stellarSystem.addPhysicalObjectToTrack(earth2, trackFactory.produce(1.49e8));
    assertEquals("expected 2", 2,
        stellarSystem.getObjectsInTrack().get(stellarSystem.getTracks().get(0)).size());
    assertTrue(
        stellarSystem.getObjectsInTrack().get(stellarSystem.getTracks().get(0)).contains(earth2));
  }

  @Test
  public void testResetObjectsAndTrack() {
    assertEquals("expected 1", 1,
        stellarSystem.getObjectsInTrack().get(stellarSystem.getTracks().get(0)).size());
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
  }

  @Test
  public void testGetObjectsInTrack() {
    Map<Track, List<PhysicalObject>> objectsInTrack = stellarSystem.getObjectsInTrack();
    assertTrue(objectsInTrack.get(stellarSystem.getTracks().get(0))
        .contains(planetFactory.produce("Earth", "Solid", "Blue", 6378.137, 29.783, true, 0)));
    assertTrue(objectsInTrack.get(stellarSystem.getTracks().get(1))
        .contains(planetFactory.produce("Mercury", "Solid", "Dark", 1378.137, 69, true, 20.085)));
    assertTrue(objectsInTrack.get(stellarSystem.getTracks().get(2))
        .contains(planetFactory.produce("Saturn", "Liquid", "Red", 2178, 2.33e5, false, 39.21)));
    assertTrue(objectsInTrack.get(stellarSystem.getTracks().get(3))
        .contains(planetFactory.produce("Jupiter", "Gas", "Blue", 1637.007, 30, true, 70)));
    assertTrue(objectsInTrack.get(stellarSystem.getTracks().get(4))
        .contains(planetFactory.produce("Mars", "Silid", "Red", 637.137, 1000.93, false, 110)));
    assertTrue(objectsInTrack.get(stellarSystem.getTracks().get(5)).contains(
        planetFactory.produce("Neptune", "Liquid", "Blue", 6627.137, 9293.05, false, 359)));
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

  @Test(expected = AssertionError.class)
  public void testConstructSocialNetworkCircle() {
    stellarSystem.constructSocialNetworkCircle(null);
  }

  @Test(expected = AssertionError.class)
  public void testSave() {
    stellarSystem.save();
  }

  @Test(expected = AssertionError.class)
  public void testRestore() {
    stellarSystem.restore(null);
  }

  @Test(expected = AssertionError.class)
  public void testAddRelationshipBetweenCentralAndPhysical() {
    stellarSystem.addRelationshipBetweenCentralAndPhysical(null, 0);
  }

  @Test(expected = AssertionError.class)
  public void testDeleteRelationshipBetweenPhysicalAndPhysical() {
    stellarSystem.deleteRelationshipBetweenPhysicalAndPhysical(null, null);
  }

  @Test(expected = AssertionError.class)
  public void testGetRelationBetweenCentralAndObject() {
    stellarSystem.getRelationBetweenCentralAndObject();
  }

  @Test(expected = AssertionError.class)
  public void testGetRelationBetweenObjects() {
    stellarSystem.getRelationBetweenObjects();
  }

}
