package iostrategy;

import java.io.File;
import java.io.IOException;

import centralobject.Star;
import circularorbit.CircularOrbit;
import myexception.DataScientificNumberException;
import myexception.DataSyntaxException;
import physicalobject.PhysicalObject;

public class StellarSystemIoContext {

  private StellarSystemIoStrategy strategy;

  public StellarSystemIoContext(StellarSystemIoStrategy strategy) {
    this.strategy = strategy;
  }

  public void readFromFile(CircularOrbit<Star, PhysicalObject> stellarSystem, File file)
      throws IOException, DataSyntaxException, DataScientificNumberException {
    this.strategy.readFromFile(stellarSystem, file);
  }

  public void writeToFile(CircularOrbit<Star, PhysicalObject> stellarSystem, File file)
      throws IOException {
    this.strategy.writeToFile(stellarSystem, file);
  }

}
