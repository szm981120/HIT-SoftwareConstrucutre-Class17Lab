package circularorbittest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import centralobject.Person;
import circularorbit.CircularOrbit;
import circularorbit.Edge;
import circularorbit.SocialNetworkCircleFactory;
import iostrategy.SocialNetworkCircleFileReader;
import iostrategy.SocialNetworkCircleIoContext;
import myexception.DataSyntaxException;
import myexception.IllegalIntimacyInSocialTieException;
import physicalobject.ConcreteFriendFactory;
import physicalobject.FriendFactory;
import physicalobject.PhysicalObject;
import track.Track;
import track.TrackFactory;

public class SocialNetworkCircleTest {

  private static SocialNetworkCircleFactory socialNetworkCircleFactory =
      new SocialNetworkCircleFactory();
  private static TrackFactory trackFactory = new TrackFactory();
  private static CircularOrbit<Person, PhysicalObject> socialNetworkCircle =
      socialNetworkCircleFactory.produce();
  private static FriendFactory friendFactory = new ConcreteFriendFactory();
  private static PhysicalObject frankLee = friendFactory.produce("FrankLee", 42, 'M');
  private static PhysicalObject lisaWong = friendFactory.produce("LisaWong", 25, 'F');
  private static PhysicalObject tomWong = friendFactory.produce("TomWong", 61, 'M');
  private static PhysicalObject davidChen = friendFactory.produce("DavidChen", 55, 'M');

  /*
   * Test strategy All tests below are about tests in social network circle.
   * 
   * @Before readFromFile Before any test, atomStructure should be initialized from
   * SocialNetworkCircle.txt. The test is all about atom structure from SocialNetworkCircle.txt.
   * testAddTrack This tests addTrack. Add a track to atom structure to see the change before and
   * after adding. Call getTracks to see if the test track is added. testDeleteTrack This tests
   * deleteTrack. Delete a track in atom structure to see the change before and after deleting. Call
   * getTracks to see if the track in test position is missed. testAddCentralObject This tests
   * addCentralObject. Add a central object to see the change before and after adding. Call
   * getCentralObject to see if the central object is the test one after adding.
   * testAddPhysicalObjectToTrack This tests addPhysicalObjectToTrack. Add a physical object to
   * track to see the change before and after adding. Call getObjectsInTrack to see if the number of
   * objects in corresponding track changes after adding.
   * testAddRelationshipBetweenCentralAndPhysical This tests
   * addRelationshipBetweenCentralAndPhysical. Add a relationship between the central person and a
   * friend. Use FrankLee as the test friend. This relationship shouldn't exist before adding. See
   * if the relationship shows after adding. testAddRelationshipBetweenPhysicalAndPhysical This
   * tests addRelationshipBetweenPhysicalAndPhysical. Add a relationship between two friends. Use
   * LisaWong and TomWong as the test friends. This relationship shouldn't exist before adding. See
   * if the relationship shows after adding. testDeleteRelationshipFromPhysicalToPhysical This tests
   * deleteRelationshipFromPhysicalToPhysical. Delete a relationship between two friends. Use
   * TomWong and FrankLee as the test friends. This relationship should exist before adding. See if
   * the relationship disappears after adding. testResetObjectsAndTrack This tests
   * resetObjectsAndTrack. Call resetObjectsAndTrack and then see if the tracks and objectInTracks
   * is empty. testGetCentralObject This tests getCentralObject. See if the central object fits the
   * data in file. testGetTracks This tests getTracks. See if the tracks fit the data in file.
   * testGetObjectsInTrack This tests getObjectInTrack. See if the objects in tracks fit the data in
   * file. testGetRelationBetweenCentralAndObject This tests getRelationBetweenCentralAndObject. See
   * if all the relationship between the central person and friends fit the data in file.
   * testGetRelationBetweenObjects This tests getRelationBetweenObjects. See if all the relationship
   * between friends fit the data in file. testSave Test that social network circle shouldn't have
   * save method. testRestore Test that social network circle shouldn't have restore method.
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
    File file = new File("input/SocialNetworkCircle.txt");
    SocialNetworkCircleIoContext sncContext =
        new SocialNetworkCircleIoContext(new SocialNetworkCircleFileReader());
    try {
      // socialNetworkCircle.readFromFile(file);
      sncContext.readFromFile(socialNetworkCircle, file);
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
    } catch (IllegalIntimacyInSocialTieException e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
  }

  @Test
  public void testAddTrack() {
    assertEquals("expected 2", 2, socialNetworkCircle.getTracks().size());
    socialNetworkCircle.addTrack(trackFactory.produce(10));
    assertEquals("expected 3", 3, socialNetworkCircle.getTracks().size());
    assertEquals(10.0, socialNetworkCircle.getTracks().get(2).getRadius(), 0.001);
  }

  @Test
  public void testDeleteTrack() {
    assertEquals("expected 2", 2, socialNetworkCircle.getTracks().size());
    socialNetworkCircle.deleteTrack(trackFactory.produce(2.0));
    assertEquals("expected 1", 1, socialNetworkCircle.getTracks().size());
    assertEquals(1.0, socialNetworkCircle.getTracks().get(0).getRadius(), 0.001);
  }

  @Test
  public void testAddCentralObject() {
    Person person = new Person("Wanghang", 20, 'M');
    assertEquals("expected TommyWong", "TommyWong",
        socialNetworkCircle.getCentralObject().getName());
    socialNetworkCircle.addCentralObject(person);
    assertEquals("expected Wanghang", "Wanghang", socialNetworkCircle.getCentralObject().getName());
  }

  @Test
  public void testAddPhysicalObjectToTrack() {
    assertEquals("expected 3", 3,
        socialNetworkCircle.getObjectsInTrack().get(socialNetworkCircle.getTracks().get(0)).size());
    socialNetworkCircle.addPhysicalObjectToTrack(friendFactory.produce("Hanxiao", 20, 'M'),
        socialNetworkCircle.getTracks().get(0));
    assertEquals("expected 4", 4,
        socialNetworkCircle.getObjectsInTrack().get(socialNetworkCircle.getTracks().get(0)).size());
    assertTrue(socialNetworkCircle.getObjectsInTrack().get(socialNetworkCircle.getTracks().get(0))
        .contains(friendFactory.produce("Hanxiao", 20, 'M')));
  }

  @Test
  public void testAddRelationshipBetweenCentralAndPhysical() {
    assertFalse(
        socialNetworkCircle.getRelationBetweenCentralAndObject().keySet().contains(frankLee));
    // assertFalse(
    // socialNetworkCircle.getRelationBetweenCentralAndObject()[1].keySet().contains(frankLee));
    socialNetworkCircle.addRelationshipBetweenCentralAndPhysical(frankLee, 0.5);
    // socialNetworkCircle.addRelationshipBetweenCentralAndPhysical(frankLee, false, 0.5);
    assertTrue(
        socialNetworkCircle.getRelationBetweenCentralAndObject().keySet().contains(frankLee));
    // assertTrue(
    // socialNetworkCircle.getRelationBetweenCentralAndObject()[1].keySet().contains(frankLee));
  }

  @Test
  public void testAddRelationshipBetweenPhysicalAndPhysical() {
    assertFalse(socialNetworkCircle.getRelationBetweenObjects()
        .contains(new Edge<PhysicalObject>(lisaWong, tomWong, 0.5)));
    socialNetworkCircle.addRelationshipBetweenPhysicalAndPhysical(lisaWong, tomWong, 0.5);
    assertTrue(socialNetworkCircle.getRelationBetweenObjects()
        .contains(new Edge<PhysicalObject>(lisaWong, tomWong, 0.5)));
  }

  @Test
  public void testDeleteRelationshipFromPhysicalToPhysical() {
    assertTrue(socialNetworkCircle.getRelationBetweenObjects()
        .contains(new Edge<PhysicalObject>(tomWong, frankLee, 0.71)));
    socialNetworkCircle.deleteRelationshipBetweenPhysicalAndPhysical(tomWong, frankLee);
    assertFalse(socialNetworkCircle.getRelationBetweenObjects()
        .contains(new Edge<PhysicalObject>(tomWong, frankLee, 0.71)));
  }

  @Test
  public void testResetObjectsAndTrack() {
    assertEquals("expected 3", 3,
        socialNetworkCircle.getObjectsInTrack().get(socialNetworkCircle.getTracks().get(0)).size());
    assertEquals("expected 2", 2, socialNetworkCircle.getTracks().size());
    socialNetworkCircle.resetObjectsAndTrack();
    assertTrue(socialNetworkCircle.getTracks().isEmpty());
    assertTrue(socialNetworkCircle.getObjectsInTrack().isEmpty());
  }

  @Test
  public void testGetCentralObject() {
    assertEquals("expected TommyWong", new Person("TommyWong", 30, 'M'),
        socialNetworkCircle.getCentralObject());
  }

  @Test
  public void testGetTracks() {
    List<Track> tracks = socialNetworkCircle.getTracks();
    assertEquals("expected 2", 2, tracks.size());
    assertEquals(1, tracks.get(0).getRadius(), 0);
    assertEquals(2, tracks.get(1).getRadius(), 0);
  }

  @Test
  public void testGetObjectsInTrack() {
    Map<Track, List<PhysicalObject>> objectsInTrack = socialNetworkCircle.getObjectsInTrack();
    assertTrue(objectsInTrack.get(socialNetworkCircle.getTracks().get(0)).contains(lisaWong));
    assertTrue(objectsInTrack.get(socialNetworkCircle.getTracks().get(0)).contains(tomWong));
    assertTrue(objectsInTrack.get(socialNetworkCircle.getTracks().get(0)).contains(davidChen));
    assertTrue(objectsInTrack.get(socialNetworkCircle.getTracks().get(1)).contains(frankLee));
  }

  @Test
  public void testGetRelationBetweenCentralAndObject() {
    Map<PhysicalObject, Double> relationBetweenCentralAndObject =
        socialNetworkCircle.getRelationBetweenCentralAndObject();
    assertEquals("expected 3", 3, relationBetweenCentralAndObject.size());
    // assertEquals("expected 3", 3, relationBetweenCentralAndObject[1].size());
    assertEquals(0.98, relationBetweenCentralAndObject.get(lisaWong), 0.001);
    assertEquals(0.2, relationBetweenCentralAndObject.get(tomWong), 0.01);
    assertEquals(0.342, relationBetweenCentralAndObject.get(davidChen), 0.0001);
    // assertEquals(0.98, relationBetweenCentralAndObject[1].get(lisaWong), 0.001);
    // assertEquals(0.2, relationBetweenCentralAndObject[1].get(tomWong), 0.01);
    // assertEquals(0.342, relationBetweenCentralAndObject[1].get(davidChen), 0.0001);
  }

  @Test
  public void testGetRelationBetweenObjects() {
    Set<Edge<PhysicalObject>> relationBetweenObjects =
        socialNetworkCircle.getRelationBetweenObjects();
    assertTrue(relationBetweenObjects.contains(new Edge<PhysicalObject>(tomWong, frankLee, 0.71)));
    assertTrue(
        relationBetweenObjects.contains(new Edge<PhysicalObject>(frankLee, davidChen, 0.02)));
    assertTrue(relationBetweenObjects.contains(new Edge<PhysicalObject>(frankLee, tomWong, 0.71)));
    assertTrue(
        relationBetweenObjects.contains(new Edge<PhysicalObject>(davidChen, frankLee, 0.02)));
  }

  @Test(expected = AssertionError.class)
  public void testSave() {
    socialNetworkCircle.save();
  }

  @Test(expected = AssertionError.class)
  public void testRestore() {
    socialNetworkCircle.restore(null);
  }

}
