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

public class AtomStructureIoContext {

  private AtomStructureIoStrategy strategy;

  public AtomStructureIoContext(AtomStructureIoStrategy strategy) {
    this.strategy = strategy;
  }

  public void readFromFile(CircularOrbit<Nucleus, PhysicalObject> atomStructure, File file)
      throws NumberFormatException, FileNotFoundException, AtomElectronNumException,
      AtomElementException, IOException, AtomTrackNumException {
    this.strategy.readFromFile(atomStructure, file);
  }

  public void writeToFile(CircularOrbit<Nucleus, PhysicalObject> atomStructure, File file)
      throws IOException {
    this.strategy.writeToFile(atomStructure, file);
  }
}
