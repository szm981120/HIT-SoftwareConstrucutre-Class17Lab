package readfromfileexceptiontest;

import centralobject.Person;
import circularorbit.CircularOrbit;
import circularorbit.SocialNetworkCircleFactory;
import iostrategy.SocialNetworkCircleFileReader;
import iostrategy.SocialNetworkCircleIoContext;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import myexception.AtomElectronNumException;
import myexception.AtomElementException;
import myexception.AtomTrackNumException;
import myexception.DataScientificNumberException;
import myexception.DataSyntaxException;
import myexception.IllegalIntimacyInSocialTieException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import physicalobject.PhysicalObject;

public class SocialNetworkCircleTest {

  private static SocialNetworkCircleFactory socialNetworkCircleFactory =
      new SocialNetworkCircleFactory();
  private static CircularOrbit<Person, PhysicalObject> socialNetworkCircle =
      socialNetworkCircleFactory.produce();
  private static SocialNetworkCircleIoContext sncContext =
      new SocialNetworkCircleIoContext(new SocialNetworkCircleFileReader());

  /*
   * Test strategy Exceptions that may happen in a socialNetworkCircle are FileNotFoundException,
   * IllegalIntimacyInSocialTieException and DataSyntaxException. FileNotFoundException Let
   * socialNetworkCircle read file exceptionAssertionTestFile/what.txt which doesn't exist.
   * IllegalIntimacyInSocialTieException An intimacy is 0.0 in
   * SocialNetworkCircleIllegalIntimacyInSocialTieException.txt. DataSyntaxException 1. CentralUser
   * name is Tommy-Wong in SocialNetworkCircleDataSyntaxException.txt. 2. CentralUser age is 30.0 in
   * SocialNetworkCircleDataSyntaxException (2).txt. 3. CentralUser sex is m in
   * SocialNetworkCircleDataSyntaxException (3).txt. 4. A friend's name in SocialTie is Tommy-Wong
   * in SocialNetworkCircleDataSyntaxException (4).txt. 5. A friend's name in SocialTie is Lisa_Wong
   * in SocialNetworkCircleDataSyntaxException (5).txt. 6. An intimacy in SocialTie is 0.98321 in
   * SocialNetworkCircleDataSyntaxException (6).txt.
   */
  @Test(expected = AssertionError.class)
  public void testAssertionsEnabled() {
    assert false;
  }

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void testFileNotFoundException() throws NumberFormatException, FileNotFoundException,
      IOException, AtomElementException, AtomTrackNumException, AtomElectronNumException,
      DataSyntaxException, DataScientificNumberException, IllegalIntimacyInSocialTieException {
    exception.expect(FileNotFoundException.class);
    // socialNetworkCircle.readFromFile(new File("exceptionAssertionTestFile/what.txt"));
    sncContext.readFromFile(socialNetworkCircle, new File("exceptionAssertionTestFile/what.txt"));
  }

  @Test
  public void testIllegalIntimacyInSocialTieException()
      throws NumberFormatException, FileNotFoundException, IOException, AtomElementException,
      AtomTrackNumException, AtomElectronNumException, DataSyntaxException,
      DataScientificNumberException, IllegalIntimacyInSocialTieException {
    exception.expect(IllegalIntimacyInSocialTieException.class);
    exception.expectMessage("The intimacy is no greater than 0 or greater than 1!");
    // socialNetworkCircle.readFromFile(new File(
    // "exceptionAssertionTestFile/SocialNetworkCircleIllegalIntimacyInSocialTieException.txt"));
    sncContext.readFromFile(socialNetworkCircle, new File(
        "exceptionAssertionTestFile/SocialNetworkCircleIllegalIntimacyInSocialTieException.txt"));
  }

  @Test
  public void testDataSyntaxException() throws NumberFormatException, FileNotFoundException,
      IOException, AtomElementException, AtomTrackNumException, AtomElectronNumException,
      DataSyntaxException, DataScientificNumberException, IllegalIntimacyInSocialTieException {
    exception.expect(DataSyntaxException.class);
    exception.expectMessage("CentralUser name syntax doesn't match!");
    // socialNetworkCircle.readFromFile(
    // new File("exceptionAssertionTestFile/SocialNetworkCircleDataSyntaxException.txt"));
    sncContext.readFromFile(socialNetworkCircle,
        new File("exceptionAssertionTestFile/SocialNetworkCircleDataSyntaxException.txt"));
  }

  @Test
  public void testDataSyntaxException2() throws NumberFormatException, FileNotFoundException,
      IOException, AtomElementException, AtomTrackNumException, AtomElectronNumException,
      DataSyntaxException, DataScientificNumberException, IllegalIntimacyInSocialTieException {
    exception.expect(DataSyntaxException.class);
    exception.expectMessage("CentralUser age syntax doesn't match!");
    // socialNetworkCircle.readFromFile(
    // new File("exceptionAssertionTestFile/SocialNetworkCircleDataSyntaxException (2).txt"));
    sncContext.readFromFile(socialNetworkCircle,
        new File("exceptionAssertionTestFile/SocialNetworkCircleDataSyntaxException (2).txt"));
  }

  @Test
  public void testDataSyntaxException3() throws NumberFormatException, FileNotFoundException,
      IOException, AtomElementException, AtomTrackNumException, AtomElectronNumException,
      DataSyntaxException, DataScientificNumberException, IllegalIntimacyInSocialTieException {
    exception.expect(DataSyntaxException.class);
    exception.expectMessage("CentralUser sex syntax doesn't match!");
    // socialNetworkCircle.readFromFile(
    // new File("exceptionAssertionTestFile/SocialNetworkCircleDataSyntaxException (3).txt"));
    sncContext.readFromFile(socialNetworkCircle,
        new File("exceptionAssertionTestFile/SocialNetworkCircleDataSyntaxException (3).txt"));
  }

  @Test
  public void testDataSyntaxException4() throws NumberFormatException, FileNotFoundException,
      IOException, AtomElementException, AtomTrackNumException, AtomElectronNumException,
      DataSyntaxException, DataScientificNumberException, IllegalIntimacyInSocialTieException {
    exception.expect(DataSyntaxException.class);
    exception.expectMessage("One of friend label syntax doesn't match!");
    // socialNetworkCircle.readFromFile(
    // new File("exceptionAssertionTestFile/SocialNetworkCircleDataSyntaxException (4).txt"));
    sncContext.readFromFile(socialNetworkCircle,
        new File("exceptionAssertionTestFile/SocialNetworkCircleDataSyntaxException (4).txt"));
  }

  @Test
  public void testDataSyntaxException5() throws NumberFormatException, FileNotFoundException,
      IOException, AtomElementException, AtomTrackNumException, AtomElectronNumException,
      DataSyntaxException, DataScientificNumberException, IllegalIntimacyInSocialTieException {
    exception.expect(DataSyntaxException.class);
    exception.expectMessage("One of friend label syntax doesn't match!");
    // socialNetworkCircle.readFromFile(
    // new File("exceptionAssertionTestFile/SocialNetworkCircleDataSyntaxException (5).txt"));
    sncContext.readFromFile(socialNetworkCircle,
        new File("exceptionAssertionTestFile/SocialNetworkCircleDataSyntaxException (5).txt"));
  }

  @Test
  public void testDataSyntaxException6() throws NumberFormatException, FileNotFoundException,
      IOException, AtomElementException, AtomTrackNumException, AtomElectronNumException,
      DataSyntaxException, DataScientificNumberException, IllegalIntimacyInSocialTieException {
    exception.expect(DataSyntaxException.class);
    exception.expectMessage("SocialTie intimacy syntax doesn't match!");
    // socialNetworkCircle.readFromFile(
    // new File("exceptionAssertionTestFile/SocialNetworkCircleDataSyntaxException (6).txt"));
    sncContext.readFromFile(socialNetworkCircle,
        new File("exceptionAssertionTestFile/SocialNetworkCircleDataSyntaxException (6).txt"));
  }
}
