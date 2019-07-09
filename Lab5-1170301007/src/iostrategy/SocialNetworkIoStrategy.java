package iostrategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import centralobject.Person;
import circularorbit.CircularOrbit;
import myexception.DataSyntaxException;
import myexception.IllegalIntimacyInSocialTieException;
import physicalobject.PhysicalObject;

public interface SocialNetworkIoStrategy {

  public void readFromFile(CircularOrbit<Person, PhysicalObject> socialNetworkCircle, File file)
      throws FileNotFoundException, NumberFormatException, IOException,
      IllegalIntimacyInSocialTieException, DataSyntaxException;

  public void writeToFile(CircularOrbit<Person, PhysicalObject> socialNetworkCircle, File file)
      throws IOException;
}
