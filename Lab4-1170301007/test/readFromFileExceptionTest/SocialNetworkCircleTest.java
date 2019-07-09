package readFromFileExceptionTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
import centralObject.Person;
import circularOrbit.AtomStructureFactory;
import circularOrbit.CircularOrbit;
import circularOrbit.SocialNetworkCircleFactory;
import physicalObject.PhysicalObject;

public class SocialNetworkCircleTest {

	private static SocialNetworkCircleFactory socialNetworkCircleFactory = new SocialNetworkCircleFactory();
	private static CircularOrbit<Person, PhysicalObject> socialNetworkCircle = socialNetworkCircleFactory.produce();

	/*
	 * Test strategy
	 * 	Exceptions that may happen in a socialNetworkCircle are 
	 * 		FileNotFoundException, IllegalIntimacyInSocialTieException and DataSyntaxException.
	 * 	FileNotFoundException
	 * 		Let socialNetworkCircle read file exceptionAssertionTestFile/what.txt which doesn't exist.
	 * 	IllegalIntimacyInSocialTieException
	 * 		An intimacy is 0.0 in SocialNetworkCircleIllegalIntimacyInSocialTieException.txt.
	 * 	DataSyntaxException
	 * 		1. CentralUser name is Tommy-Wong in SocialNetworkCircleDataSyntaxException.txt.
	 * 		2. CentralUser age is 30.0 in SocialNetworkCircleDataSyntaxException (2).txt.
	 * 		3. CentralUser sex is m in SocialNetworkCircleDataSyntaxException (3).txt.
	 * 		4. A friend's name in SocialTie is Tommy-Wong in SocialNetworkCircleDataSyntaxException (4).txt.
	 * 		5. A friend's name in SocialTie is Lisa_Wong in SocialNetworkCircleDataSyntaxException (5).txt.
	 * 		6. An intimacy in SocialTie is 0.98321 in SocialNetworkCircleDataSyntaxException (6).txt.
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
		socialNetworkCircle.readFromFile(new File("exceptionAssertionTestFile/what.txt"));
	}

	@Test
	public void testIllegalIntimacyInSocialTieException() throws NumberFormatException, FileNotFoundException,
			IOException, AtomElementException, AtomTrackNumException, AtomElectronNumException, DataSyntaxException,
			DataScientificNumberException, IllegalIntimacyInSocialTieException {
		exception.expect(IllegalIntimacyInSocialTieException.class);
		exception.expectMessage("The intimacy is no greater than 0 or greater than 1!");
		socialNetworkCircle.readFromFile(
				new File("exceptionAssertionTestFile/SocialNetworkCircleIllegalIntimacyInSocialTieException.txt"));
	}

	@Test
	public void testDataSyntaxException() throws NumberFormatException, FileNotFoundException, IOException,
			AtomElementException, AtomTrackNumException, AtomElectronNumException, DataSyntaxException,
			DataScientificNumberException, IllegalIntimacyInSocialTieException {
		exception.expect(DataSyntaxException.class);
		exception.expectMessage("CentralUser name syntax doesn't match!");
		socialNetworkCircle
				.readFromFile(new File("exceptionAssertionTestFile/SocialNetworkCircleDataSyntaxException.txt"));
	}

	@Test
	public void testDataSyntaxException2() throws NumberFormatException, FileNotFoundException, IOException,
			AtomElementException, AtomTrackNumException, AtomElectronNumException, DataSyntaxException,
			DataScientificNumberException, IllegalIntimacyInSocialTieException {
		exception.expect(DataSyntaxException.class);
		exception.expectMessage("CentralUser age syntax doesn't match!");
		socialNetworkCircle
				.readFromFile(new File("exceptionAssertionTestFile/SocialNetworkCircleDataSyntaxException (2).txt"));
	}

	@Test
	public void testDataSyntaxException3() throws NumberFormatException, FileNotFoundException, IOException,
			AtomElementException, AtomTrackNumException, AtomElectronNumException, DataSyntaxException,
			DataScientificNumberException, IllegalIntimacyInSocialTieException {
		exception.expect(DataSyntaxException.class);
		exception.expectMessage("CentralUser sex syntax doesn't match!");
		socialNetworkCircle
				.readFromFile(new File("exceptionAssertionTestFile/SocialNetworkCircleDataSyntaxException (3).txt"));
	}

	@Test
	public void testDataSyntaxException4() throws NumberFormatException, FileNotFoundException, IOException,
			AtomElementException, AtomTrackNumException, AtomElectronNumException, DataSyntaxException,
			DataScientificNumberException, IllegalIntimacyInSocialTieException {
		exception.expect(DataSyntaxException.class);
		exception.expectMessage("One of friend label syntax doesn't match!");
		socialNetworkCircle
				.readFromFile(new File("exceptionAssertionTestFile/SocialNetworkCircleDataSyntaxException (4).txt"));
	}

	@Test
	public void testDataSyntaxException5() throws NumberFormatException, FileNotFoundException, IOException,
			AtomElementException, AtomTrackNumException, AtomElectronNumException, DataSyntaxException,
			DataScientificNumberException, IllegalIntimacyInSocialTieException {
		exception.expect(DataSyntaxException.class);
		exception.expectMessage("One of friend label syntax doesn't match!");
		socialNetworkCircle
				.readFromFile(new File("exceptionAssertionTestFile/SocialNetworkCircleDataSyntaxException (5).txt"));
	}

	@Test
	public void testDataSyntaxException6() throws NumberFormatException, FileNotFoundException, IOException,
			AtomElementException, AtomTrackNumException, AtomElectronNumException, DataSyntaxException,
			DataScientificNumberException, IllegalIntimacyInSocialTieException {
		exception.expect(DataSyntaxException.class);
		exception.expectMessage("SocialTie intimacy syntax doesn't match!");
		socialNetworkCircle
				.readFromFile(new File("exceptionAssertionTestFile/SocialNetworkCircleDataSyntaxException (6).txt"));
	}
}
