package readFromFileExceptionTest;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import centralObject.Star;
import circularOrbit.AtomStructureFactory;
import circularOrbit.CircularOrbit;
import circularOrbit.StellarSystemFactory;
import physicalObject.PhysicalObject;

public class StellarSystemTest {

	private static StellarSystemFactory stellarSystemFactory = new StellarSystemFactory();
	private static CircularOrbit<Star, PhysicalObject> stellarSystem = stellarSystemFactory.produce();

	/*
	 * Test strategy
	 * 	Exceptions that may happen in a stellarSystem are 
	 * 		FileNotFoundException, DataSyntaxException and DataScientificNumberException.
	 * 	FileNotFoundException
	 * 		Let stellarSystem read file exceptionAssertionTestFile/what.txt which doesn't exist.
	 * 	DataSyntaxException
	 * 		1. Star name is Sun^^ in StellarSystemDataSyntaxException.txt.
	 * 		2. Star radius is 6.96392Ae5 in StellarSystemDataSyntaxException (2).txt.
	 * 		3. Star mass is 1.9885Ae30 in StellarSystemDataSyntaxException (3).txt.
	 * 		4. A planet's name is Earth= in StellarSystemDataSyntaxException (4).txt.
	 * 		5. A planet's state is Soli-d in StellarSystemDataSyntaxException (5).txt.
	 * 		6. A planet's color is Bl**ue in StellarSystemDataSyntaxException (6).txt.
	 * 		7. A planet's radius is 6378._137 in StellarSystemDataSyntaxException (7).txt.
	 * 		8. A track's radius is 1·.49e8 in StellarSystemDataSyntaxException (8).txt.
	 * 		9. A revolution speed is 29./783 in StellarSystemDataSyntaxException (9).txt.
	 * 		10. A revolution direction is CWB in StellarSystemDataSyntaxException (10).txt.
	 * 		11. A original degree is 0+ in StellarSystemDataSyntaxException (11).txt.
	 * 	DataScientificNumberException
	 * 		1. A track's radius is 11.49e8 in StellarSystemDataScientificNumberException.txt.
	 * 		2. A track's radius is 1.49e2 in StellarSystemDataScientificNumberException (2).txt.
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
		stellarSystem.readFromFile(new File("exceptionAssertionTestFile/what.txt"));
	}

	@Test
	public void testDataSyntaxException() throws NumberFormatException, FileNotFoundException, IOException,
			AtomElementException, AtomTrackNumException, AtomElectronNumException, DataSyntaxException,
			DataScientificNumberException, IllegalIntimacyInSocialTieException {
		exception.expect(DataSyntaxException.class);
		exception.expectMessage("Star name syntax doesn't match!");
		stellarSystem.readFromFile(new File("exceptionAssertionTestFile/StellarSystemDataSyntaxException.txt"));
	}

	@Test
	public void testDataSyntaxException2() throws NumberFormatException, FileNotFoundException, IOException,
			AtomElementException, AtomTrackNumException, AtomElectronNumException, DataSyntaxException,
			DataScientificNumberException, IllegalIntimacyInSocialTieException {
		exception.expect(DataSyntaxException.class);
		exception.expectMessage("Star radius syntax doesn't match!");
		stellarSystem.readFromFile(new File("exceptionAssertionTestFile/StellarSystemDataSyntaxException (2).txt"));
	}

	@Test
	public void testDataSyntaxException3() throws NumberFormatException, FileNotFoundException, IOException,
			AtomElementException, AtomTrackNumException, AtomElectronNumException, DataSyntaxException,
			DataScientificNumberException, IllegalIntimacyInSocialTieException {
		exception.expect(DataSyntaxException.class);
		exception.expectMessage("Star mass syntax doesn't match!");
		stellarSystem.readFromFile(new File("exceptionAssertionTestFile/StellarSystemDataSyntaxException (3).txt"));
	}

	@Test
	public void testDataSyntaxException4() throws NumberFormatException, FileNotFoundException, IOException,
			AtomElementException, AtomTrackNumException, AtomElectronNumException, DataSyntaxException,
			DataScientificNumberException, IllegalIntimacyInSocialTieException {
		exception.expect(DataSyntaxException.class);
		exception.expectMessage("One of planet label syntax doesn't match! It can be name, state or color.");
		stellarSystem.readFromFile(new File("exceptionAssertionTestFile/StellarSystemDataSyntaxException (4).txt"));
	}

	@Test
	public void testDataSyntaxException5() throws NumberFormatException, FileNotFoundException, IOException,
			AtomElementException, AtomTrackNumException, AtomElectronNumException, DataSyntaxException,
			DataScientificNumberException, IllegalIntimacyInSocialTieException {
		exception.expect(DataSyntaxException.class);
		exception.expectMessage("One of planet label syntax doesn't match! It can be name, state or color.");
		stellarSystem.readFromFile(new File("exceptionAssertionTestFile/StellarSystemDataSyntaxException (5).txt"));
	}

	@Test
	public void testDataSyntaxException6() throws NumberFormatException, FileNotFoundException, IOException,
			AtomElementException, AtomTrackNumException, AtomElectronNumException, DataSyntaxException,
			DataScientificNumberException, IllegalIntimacyInSocialTieException {
		exception.expect(DataSyntaxException.class);
		exception.expectMessage("One of planet label syntax doesn't match! It can be name, state or color.");
		stellarSystem.readFromFile(new File("exceptionAssertionTestFile/StellarSystemDataSyntaxException (6).txt"));
	}

	@Test
	public void testDataSyntaxException7() throws NumberFormatException, FileNotFoundException, IOException,
			AtomElementException, AtomTrackNumException, AtomElectronNumException, DataSyntaxException,
			DataScientificNumberException, IllegalIntimacyInSocialTieException {
		exception.expect(DataSyntaxException.class);
		exception.expectMessage(
				"One of planet number syntax doesn't match! It can be planet radius, track radius or revolution speed.");
		stellarSystem.readFromFile(new File("exceptionAssertionTestFile/StellarSystemDataSyntaxException (7).txt"));
	}

	@Test
	public void testDataSyntaxException8() throws NumberFormatException, FileNotFoundException, IOException,
			AtomElementException, AtomTrackNumException, AtomElectronNumException, DataSyntaxException,
			DataScientificNumberException, IllegalIntimacyInSocialTieException {
		exception.expect(DataSyntaxException.class);
		exception.expectMessage(
				"One of planet number syntax doesn't match! It can be planet radius, track radius or revolution speed.");
		stellarSystem.readFromFile(new File("exceptionAssertionTestFile/StellarSystemDataSyntaxException (8).txt"));
	}

	@Test
	public void testDataSyntaxException9() throws NumberFormatException, FileNotFoundException, IOException,
			AtomElementException, AtomTrackNumException, AtomElectronNumException, DataSyntaxException,
			DataScientificNumberException, IllegalIntimacyInSocialTieException {
		exception.expect(DataSyntaxException.class);
		exception.expectMessage(
				"One of planet number syntax doesn't match! It can be planet radius, track radius or revolution speed.");
		stellarSystem.readFromFile(new File("exceptionAssertionTestFile/StellarSystemDataSyntaxException (9).txt"));
	}

	@Test
	public void testDataSyntaxException10() throws NumberFormatException, FileNotFoundException, IOException,
			AtomElementException, AtomTrackNumException, AtomElectronNumException, DataSyntaxException,
			DataScientificNumberException, IllegalIntimacyInSocialTieException {
		exception.expect(DataSyntaxException.class);
		exception.expectMessage("Planet revolution direction syntax doesn't match!");
		stellarSystem.readFromFile(new File("exceptionAssertionTestFile/StellarSystemDataSyntaxException (10).txt"));
	}

	@Test
	public void testDataSyntaxException11() throws NumberFormatException, FileNotFoundException, IOException,
			AtomElementException, AtomTrackNumException, AtomElectronNumException, DataSyntaxException,
			DataScientificNumberException, IllegalIntimacyInSocialTieException {
		exception.expect(DataSyntaxException.class);
		exception.expectMessage("Planet original degree syntax doesn't match!");
		stellarSystem.readFromFile(new File("exceptionAssertionTestFile/StellarSystemDataSyntaxException (11).txt"));
	}

	@Test
	public void testDataScientificNumberException() throws NumberFormatException, FileNotFoundException, IOException,
			AtomElementException, AtomTrackNumException, AtomElectronNumException, DataSyntaxException,
			DataScientificNumberException, IllegalIntimacyInSocialTieException {
		exception.expect(DataScientificNumberException.class);
		exception.expectMessage("The mantissa is not between 1 and 10.");
		stellarSystem
				.readFromFile(new File("exceptionAssertionTestFile/StellarSystemDataScientificNumberException.txt"));
	}

	@Test
	public void testDataScientificNumberException2() throws NumberFormatException, FileNotFoundException, IOException,
			AtomElementException, AtomTrackNumException, AtomElectronNumException, DataSyntaxException,
			DataScientificNumberException, IllegalIntimacyInSocialTieException {
		exception.expect(DataScientificNumberException.class);
		exception.expectMessage("The exponent is less than 4.");
		stellarSystem.readFromFile(
				new File("exceptionAssertionTestFile/StellarSystemDataScientificNumberException (2).txt"));
	}

}
