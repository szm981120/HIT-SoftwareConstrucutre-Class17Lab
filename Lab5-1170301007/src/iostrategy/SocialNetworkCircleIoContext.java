package iostrategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import centralobject.Person;
import circularorbit.CircularOrbit;
import myexception.DataSyntaxException;
import myexception.IllegalIntimacyInSocialTieException;
import physicalobject.PhysicalObject;

public class SocialNetworkCircleIoContext {

  private SocialNetworkIoStrategy strategy;

  public SocialNetworkCircleIoContext(SocialNetworkIoStrategy strategy) {
    this.strategy = strategy;
  }

  public void readFromFile(CircularOrbit<Person, PhysicalObject> socialNetworkCircle, File file)
      throws NumberFormatException, FileNotFoundException, IOException,
      IllegalIntimacyInSocialTieException, DataSyntaxException {
    this.strategy.readFromFile(socialNetworkCircle, file);
  }

  public void writeToFile(CircularOrbit<Person, PhysicalObject> socialNetworkCircle, File file)
      throws IOException {
    this.strategy.writeToFile(socialNetworkCircle, file);
  }

}
