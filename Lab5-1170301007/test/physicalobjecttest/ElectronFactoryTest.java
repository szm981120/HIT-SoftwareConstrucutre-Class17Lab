package physicalobjecttest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import physicalobject.ConcreteElectronFactory;
import physicalobject.Electron;
import physicalobject.ElectronFactory;

public class ElectronFactoryTest {

  /*
   * Test strategy testProduce This tests produce. Call produce to create an instance to see if the
   * instance fits expectation.
   */

  @Test(expected = AssertionError.class)
  public void testAssertionsEnabled() {
    assert false;
  }

  @Test
  public void testProduce() {
    ElectronFactory electronFactory = new ConcreteElectronFactory();
    assertEquals("expected Electron class", Electron.class, electronFactory.produce().getClass());
  }

}
