package iostrategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import centralobject.Star;
import circularorbit.CircularOrbit;
import myexception.DataScientificNumberException;
import myexception.DataSyntaxException;
import physicalobject.PhysicalObject;


public interface StellarSystemIoStrategy {

  public void readFromFile(CircularOrbit<Star, PhysicalObject> stellarSystem, File file)
      throws IOException, DataSyntaxException, DataScientificNumberException;

  public void writeToFile(CircularOrbit<Star, PhysicalObject> stellarSystem, File file)
      throws IOException;
}
