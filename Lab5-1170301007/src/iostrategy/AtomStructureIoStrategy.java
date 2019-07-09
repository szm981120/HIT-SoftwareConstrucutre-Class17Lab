package iostrategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import centralobject.Nucleus;
import circularorbit.CircularOrbit;
import myexception.AtomElectronNumException;
import myexception.AtomElementException;
import myexception.AtomTrackNumException;
import physicalobject.PhysicalObject;

public interface AtomStructureIoStrategy {

  public void readFromFile(CircularOrbit<Nucleus, PhysicalObject> atomStructure, File file)
      throws AtomElectronNumException, FileNotFoundException, AtomElementException,
      NumberFormatException, IOException, AtomTrackNumException;

  public void writeToFile(CircularOrbit<Nucleus, PhysicalObject> atomStructure, File file)
      throws IOException;
}
