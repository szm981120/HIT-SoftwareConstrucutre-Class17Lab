package circularOrbitTest;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.junit.Before;
import org.junit.Test;

import MyException.AtomElectronNumException;
import MyException.AtomElementException;
import MyException.AtomTrackNumException;
import MyException.DataScientificNumberException;
import MyException.DataSyntaxException;
import MyException.IllegalIntimacyInSocialTieException;
import MyException.NoObjectOnTrackException;
import atomTransitionMemento.Caretaker;
import centralObject.Nucleus;
import circularOrbit.AtomStructureFactory;
import circularOrbit.CircularOrbit;
import physicalObject.ConcreteElectronFactory;
import physicalObject.ElectronFactory;
import physicalObject.PhysicalObject;
import track.Track;
import track.TrackFactory;

public class AtomStructureTest {

	private static AtomStructureFactory atomStructureFactory = new AtomStructureFactory();
	private static TrackFactory trackFactory = new TrackFactory();
	private static CircularOrbit<Nucleus, PhysicalObject> atomStructure = atomStructureFactory.produce();
	private static ElectronFactory electronFactory = new ConcreteElectronFactory();

	/*
	 * Test strategy
	 * 	All tests below are about tests in atom structure. 
	 * 	@Before readFromFile
	 * 		Before any test, atomStructure should be initialized from AtomicStructure.txt.
	 * 		The test is all about atom structure from AtomicStructure.txt.
	 * 	testMementoMethod
	 * 		This tests Memento method in transition action.
	 * 		It does two transition to atom structure and saves these two states.
	 * 		And it restores these states to see if the transition can be undone.
	 * 	testAddTrack
	 * 		This tests addTrack.
	 * 		Add a track to atom structure to see the change before and after adding.
	 * 		Call getTracks to see if the test track is added.
	 * 	testDeleteTrack
	 * 		This tests deleteTrack.
	 * 		Delete a track in atom structure to see the change before and after deleting.
	 * 		Call getTracks to see if the track in test position is missed.
	 * 	testAddCentralObject
	 * 		This tests addCentralObject.
	 * 		Add a central object in atom structure to see the change before and after adding.
	 * 		Call getCentralObject to see if the central object is the test one after adding.
	 * 	testAddPhysicalObjectToTrack
	 * 		This tests addPhysicalObjectToTrack.
	 * 		Add a physical object to track to see the change before and after adding.
	 * 		Call getObjectsInTrack to see if the number of objects in corresponding track changes after adding.
	 * 	testDeletePhysicalObjectFromTrack
	 * 		This tests deletePhysicalObjectFromTrack.
	 * 		Delete a physical object from track to see the change before and after deleting.
	 * 		Call getObjectsInTrack to see if the number of objects in corresponding track changes after deleting.
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
	 * 	testConstructSocialNetworkCircle
	 * 		Test that atom structure shouldn't have constructSocialNetworkCircle method.
	 * 	testAddRelationshipBetweenCentralAndPhysical
	 * 		Test that atom structure shouldn't have addRelationshipBetweenCentralAndPhysical method.
	 * 	testAddRelationshipBetweenPhysicalAndPhysical
	 * 		Test that atom structure shouldn't have addRelationshipBetweenPhysicalAndPhysical method.
	 * 	testDeleteRelationshipBetweenPhysicalAndPhysical
	 * 		Test that atom structure shouldn't have deleteRelationshipBetweenPhysicalAndPhysical method.
	 * 	testGetRelationBetweenCentralAndObject
	 * 		Test that atom structure shouldn't have getRelationBetweenCentralAndObject method.
	 * 	testGetRelationBetweenObjects
	 * 		Test that atom structure shouldn't have getRelationBetweenObjects method.
	 */
	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false;
	}

	@Before
	public void readFromFile() {
		File file = new File("input/AtomicStructure.txt");
		try {
			atomStructure.readFromFile(file);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			System.out.println("File not found. File: " + file.getPath() + ". " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (AtomElementException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (AtomTrackNumException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (AtomElectronNumException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (DataSyntaxException | DataScientificNumberException | IllegalIntimacyInSocialTieException e) {
			assert false : "shouldn't have this exception.";
			e.printStackTrace();
		}
	}

	@Test
	public void testMementoMethod() throws Exception {
		Caretaker<Nucleus, PhysicalObject> caretaker = new Caretaker<Nucleus, PhysicalObject>();
		caretaker.addMemento(atomStructure.save());
		for (int i = 1; i <= 2; i++) {
			atomStructure.deletePhysicalObjectFromTrack(electronFactory.produce(), trackFactory.produce(2));
			atomStructure.addPhysicalObjectToTrack(electronFactory.produce(), trackFactory.produce(1));
			caretaker.addMemento(atomStructure.save());
		}
		assertEquals("expected 4", 4, atomStructure.getObjectsInTrack().get(atomStructure.getTracks().get(0)).size());
		atomStructure.restore(caretaker.getMemento(1));
		assertEquals("expected 3", 3, atomStructure.getObjectsInTrack().get(atomStructure.getTracks().get(0)).size());
		atomStructure.restore(caretaker.getMemento(2));
		assertEquals("expected 2", 2, atomStructure.getObjectsInTrack().get(atomStructure.getTracks().get(0)).size());
	}

	@Test
	public void testAddTrack() {
		assertEquals("expected 5", 5, atomStructure.getTracks().size());
		atomStructure.addTrack(trackFactory.produce(10));
		assertEquals("expected 6", 6, atomStructure.getTracks().size());
		assertEquals(10.0, atomStructure.getTracks().get(5).getRadius(), 0.001);
	}

	@Test
	public void testDeleteTrack() {
		assertEquals("expected 5", 5, atomStructure.getTracks().size());
		atomStructure.deleteTrack(trackFactory.produce(5.0));
		assertEquals("expected 4", 4, atomStructure.getTracks().size());
		assertEquals(4, atomStructure.getTracks().get(3).getRadius(), 0.001);
	}

	@Test
	public void testAddCentralObject() {
		char[] elementName = new char[2];
		elementName[0] = 'N';
		elementName[1] = 'a';
		assertEquals("expected Rb", "Rb", atomStructure.getCentralObject().toString());
		atomStructure.addCentralObject(new Nucleus(elementName));
		assertEquals("expecte Na", "Na", atomStructure.getCentralObject().toString());
	}

	@Test
	public void testAddPhysicalObjectToTrack() {
		assertEquals("expected 8", 8, atomStructure.getObjectsInTrack().get(atomStructure.getTracks().get(1)).size());
		atomStructure.addPhysicalObjectToTrack(electronFactory.produce(), atomStructure.getTracks().get(1));
		assertEquals("expected 9", 9, atomStructure.getObjectsInTrack().get(atomStructure.getTracks().get(1)).size());
	}

	@Test
	public void testDeletePhysicalObjectFromTrack() throws NoObjectOnTrackException {
		assertEquals("expected 8", 8, atomStructure.getObjectsInTrack().get(atomStructure.getTracks().get(1)).size());
		atomStructure.deletePhysicalObjectFromTrack(electronFactory.produce(), atomStructure.getTracks().get(1));
		assertEquals("expected 7", 7, atomStructure.getObjectsInTrack().get(atomStructure.getTracks().get(1)).size());
	}

	@Test
	public void testResetObjectsAndTrack() {
		assertEquals("expected 8", 8, atomStructure.getObjectsInTrack().get(atomStructure.getTracks().get(1)).size());
		assertEquals("expected 5", 5, atomStructure.getTracks().size());
		atomStructure.resetObjectsAndTrack();
		assertTrue(atomStructure.getTracks().isEmpty());
		assertTrue(atomStructure.getObjectsInTrack().isEmpty());
	}

	@Test
	public void testGetCentralObject() {
		assertEquals("expected Rb", "Rb", atomStructure.getCentralObject().toString());
	}

	@Test
	public void testGetTracks() {
		List<Track> tracks = atomStructure.getTracks();
		assertEquals("expected 5", 5, tracks.size());
		assertEquals(1, tracks.get(0).getRadius(), 0);
		assertEquals(2, tracks.get(1).getRadius(), 0);
		assertEquals(3, tracks.get(2).getRadius(), 0);
		assertEquals(4, tracks.get(3).getRadius(), 0);
		assertEquals(5, tracks.get(4).getRadius(), 0);
	}

	@Test
	public void testGetObjectsInTrack() {
		assertEquals("expected 5 tracks", 5, atomStructure.getTracks().size());
		Map<Track, List<PhysicalObject>> objectsInTrack = atomStructure.getObjectsInTrack();
		assertEquals("expected 2 electrons", 2, objectsInTrack.get(trackFactory.produce(1)).size());
		assertEquals("expected 2 electrons", 8, objectsInTrack.get(trackFactory.produce(2)).size());
		assertEquals("expected 2 electrons", 18, objectsInTrack.get(trackFactory.produce(3)).size());
		assertEquals("expected 2 electrons", 8, objectsInTrack.get(trackFactory.produce(4)).size());
		assertEquals("expected 2 electrons", 1, objectsInTrack.get(trackFactory.produce(5)).size());
	}

	@Test(expected = AssertionError.class)
	public void testConstructSocialNetworkCircle() {
		atomStructure.constructSocialNetworkCircle(null, null, null);
	}
	@Test(expected = AssertionError.class)
	public void testAddRelationshipBetweenCentralAndPhysical() {
		atomStructure.addRelationshipBetweenCentralAndPhysical(electronFactory.produce(), true, 0.5);
	}

	@Test(expected = AssertionError.class)
	public void testAddRelationshipBetweenPhysicalAndPhysical() {
		atomStructure.addRelationshipBetweenPhysicalAndPhysical(electronFactory.produce(), electronFactory.produce(),
				0.5);
	}

	@Test(expected = AssertionError.class)
	public void testDeleteRelationshipBetweenPhysicalAndPhysical() {
		atomStructure.deleteRelationshipBetweenPhysicalAndPhysical(electronFactory.produce(),
				electronFactory.produce());
	}

	@Test(expected = AssertionError.class)
	public void testGetRelationBetweenCentralAndObject() {
		atomStructure.getRelationBetweenCentralAndObject();
	}

	@Test(expected = AssertionError.class)
	public void testGetRelationBetweenObjects() {
		atomStructure.getRelationBetweenObjects();
	}
}
