package readFromFileExceptionTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import MyException.AtomElectronNumException;
import MyException.AtomElementException;
import MyException.AtomTrackNumException;
import MyException.DataScientificNumberException;
import MyException.DataSyntaxException;
import MyException.IllegalIntimacyInSocialTieException;
import centralObject.Nucleus;
import circularOrbit.AtomStructureFactory;
import circularOrbit.CircularOrbit;
import physicalObject.PhysicalObject;

public class AtomStructureTest {

	private static AtomStructureFactory atomStructureFactory = new AtomStructureFactory();
	private static CircularOrbit<Nucleus, PhysicalObject> atomStructure = atomStructureFactory.produce();

	/*
	 * Test strategy
	 * 	Exceptions that may happen in a atomstructure are 
	 * 		FileNotFoundException, AtomElementException, AtomTrackNumException and AtomElectronNumException.
	 * 	FileNotFoundException
	 * 		Let atomStructure read file exceptionAssertionTestFile/what.txt which doesn't exist.
	 * 	AtomElementException
	 * 		1. Element of atom is Rbc in AtomicStructureAtomElementException.txt.
	 * 		2. Element of atom is rb in AtomicStructureAtomElementException (2).txt.
	 * 		3. Element of atom is RB in AtomicStructureAtomElementException (3).txt.
	 * 	AtomTrackNumException
	 * 		Number of track is 0 in AtomicStructureAtomTrackNumException.txt.
	 * 	AtomElectronNumException
	 * 		1. Some tracks have no matched electron number information in AtomicStructureAtomElectronNumException.txt.
	 * 		2. The number of electron information doesn't match the number of track in AtomicStructureAtomElectronNumException (2).txt.
	 */
	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false;
	}

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testFileNotFoundException() throws NumberFormatException, FileNotFoundException, IOException,
			AtomElementException, AtomTrackNumException, AtomElectronNumException, DataSyntaxException,
			DataScientificNumberException, IllegalIntimacyInSocialTieException {
		exception.expect(FileNotFoundException.class);
		atomStructure.readFromFile(new File("exceptionAssertionTestFile/what.txt"));
	}

	@Test
	public void testAtomElementException() throws NumberFormatException, FileNotFoundException, IOException,
			AtomElementException, AtomTrackNumException, AtomElectronNumException, DataSyntaxException,
			DataScientificNumberException, IllegalIntimacyInSocialTieException {
		exception.expect(AtomElementException.class);
		exception.expectMessage("The element of this atom contains 0 or more than 2 characters.");
		atomStructure.readFromFile(new File("exceptionAssertionTestFile/AtomicStructureAtomElementException.txt"));
	}

	@Test
	public void testAtomElementException2() throws NumberFormatException, FileNotFoundException, IOException,
			AtomElementException, AtomTrackNumException, AtomElectronNumException, DataSyntaxException,
			DataScientificNumberException, IllegalIntimacyInSocialTieException {
		exception.expect(AtomElementException.class);
		exception.expectMessage("The first character is not a upper case character!");
		atomStructure.readFromFile(new File("exceptionAssertionTestFile/AtomicStructureAtomElementException (2).txt"));
	}

	@Test
	public void testAtomElementException3() throws NumberFormatException, FileNotFoundException, IOException,
			AtomElementException, AtomTrackNumException, AtomElectronNumException, DataSyntaxException,
			DataScientificNumberException, IllegalIntimacyInSocialTieException {
		exception.expect(AtomElementException.class);
		exception.expectMessage("The second character is not a lower case character!");
		atomStructure.readFromFile(new File("exceptionAssertionTestFile/AtomicStructureAtomElementException (3).txt"));
	}

	@Test
	public void testAtomTrackNumException() throws NumberFormatException, FileNotFoundException, IOException,
			AtomElementException, AtomTrackNumException, AtomElectronNumException, DataSyntaxException,
			DataScientificNumberException, IllegalIntimacyInSocialTieException {
		exception.expect(AtomTrackNumException.class);
		exception.expectMessage("Number of track is not a positive integer!");
		atomStructure.readFromFile(new File("exceptionAssertionTestFile/AtomicStructureAtomTrackNumException.txt"));
	}

	@Test
	public void testAtomElectronNumException() throws NumberFormatException, FileNotFoundException, IOException,
			AtomElementException, AtomTrackNumException, AtomElectronNumException, DataSyntaxException,
			DataScientificNumberException, IllegalIntimacyInSocialTieException {
		exception.expect(AtomElectronNumException.class);
		exception.expectMessage("Each track order number should match a number of electron!");
		atomStructure.readFromFile(new File("exceptionAssertionTestFile/AtomicStructureAtomElectronNumException.txt"));
	}

	@Test
	public void testAtomElectronNumException2() throws NumberFormatException, FileNotFoundException, IOException,
			AtomElementException, AtomTrackNumException, AtomElectronNumException, DataSyntaxException,
			DataScientificNumberException, IllegalIntimacyInSocialTieException {
		exception.expect(AtomElectronNumException.class);
		exception.expectMessage("The number of electron information doesn't match the number of track!");
		atomStructure
				.readFromFile(new File("exceptionAssertionTestFile/AtomicStructureAtomElectronNumException (2).txt"));
	}

}
