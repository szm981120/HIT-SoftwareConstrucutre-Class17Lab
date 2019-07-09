package physicalObject;

/**
 * ConcreteElectronFactory provides a method to create a Electron instance.
 * 
 * @author Shen
 *
 */
public class ConcreteElectronFactory implements ElectronFactory {

	/**
	 * Constructor
	 */
	public ConcreteElectronFactory() {

	}

	/**
	 * Create an electron.
	 */
	@Override
	public PhysicalObject produce() {
		return new Electron();
	}

}
