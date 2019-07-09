package applications;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import APIs.CircularOrbitAPIs;
import APIs.CircularOrbitHelper;
import MyException.AtomElectronNumException;
import MyException.AtomElementException;
import MyException.AtomTrackNumException;
import MyException.DataScientificNumberException;
import MyException.DataSyntaxException;
import MyException.IllegalIntimacyInSocialTieException;
import MyException.NoObjectOnTrackException;
import atomTransitionMemento.Caretaker;
import centralObject.Nucleus;
import circularOrbit.AtomStructureFactory;
import circularOrbit.CircularOrbit;
import circularOrbit.circularOrbitFactory;
import physicalObject.ConcreteElectronFactory;
import physicalObject.ElectronFactory;
import physicalObject.PhysicalObject;
import track.TrackFactory;

/**
 * AtomStructureAPP provides a static method for client to call in Main.java.
 * This static method is the mainly function method for atom structure
 * applications.
 * 
 * @author Shen
 *
 */
public class AtomStructureAPP {

	private static Scanner in = new Scanner(System.in);
	private static Logger atomStructureLogger = Logger.getLogger("AtomStructure Log");

	/**
	 * atom structure application method
	 */
	public static void application() {
		CircularOrbitAPIs<Nucleus, PhysicalObject> apis = new CircularOrbitAPIs<Nucleus, PhysicalObject>();
		circularOrbitFactory<Nucleus, PhysicalObject> factory = new AtomStructureFactory();
		CircularOrbit<Nucleus, PhysicalObject> atomStructure = factory.produce();
		ElectronFactory electronFactory = new ConcreteElectronFactory();
		TrackFactory trackFactory = new TrackFactory();
		Caretaker<Nucleus, PhysicalObject> caretaker = new Caretaker<Nucleus, PhysicalObject>();
		atomStructureLogger.setUseParentHandlers(false);
		while (true) {
			menu();
			int choose = 0;
			try {
				choose = in.nextInt();
				in.nextLine();
			} catch (InputMismatchException e) {
				System.err.println(e.getMessage() + "Please input again.");
				in.nextLine();
				continue;
			}
			switch (choose) {
			case 1: // Read from file to generate a atomic structure
				readMenu();
				File file = null;
				int choose1 = 0;
				try {
					choose1 = in.nextInt();
					in.nextLine();
				} catch (InputMismatchException e) {
					System.out.println(e.getMessage() + "Please run again.");
					in.nextLine();
					break;
				}
				try {
					switch (choose1) {
					case 1:
						atomStructureLogger.info("Read from input/AtomicStructure.txt. Restore present state.");
						file = new File("input/AtomicStructure.txt");
						atomStructure.readFromFile(file);
						caretaker.addMemento(atomStructure.save());
						atomStructureLogger
								.info("Success: Read from input/AtomicStructure.txt. Restore present state.");
						break;
					case 2:
						atomStructureLogger.info("Read from input/AtomicStructure_Medium.txt. Restore present state.");
						file = new File("input/AtomicStructure_Medium.txt");
						atomStructure.readFromFile(file);
						caretaker.addMemento(atomStructure.save());
						atomStructureLogger
								.info("Success: Read from input/AtomicStructure_Medium.txt. Restore present state.");
						break;
					case 3:
						System.out.println("Please input absolute file path.");
						String absolutePath = in.nextLine();
						file = new File(absolutePath);
						atomStructure.readFromFile(file);
						caretaker.addMemento(atomStructure.save());
						atomStructureLogger.info("Success: Read from " + absolutePath + ". Restore present state.");
						break;
					default:
						System.out.println("Wrong input");
						break;
					}
				} catch (FileNotFoundException e) {
					atomStructureLogger.log(Level.SEVERE,
							"File not found. File: " + file.getPath() + ". " + e.getMessage(), e);
					System.err.println("File not found. File: " + file.getPath() + ". " + e.getMessage());
				} catch (AtomElementException e) {
					atomStructureLogger.log(Level.SEVERE, e.getMessage(), e);
					System.err.println(e.getMessage());
				} catch (AtomTrackNumException e) {
					atomStructureLogger.log(Level.SEVERE, e.getMessage(), e);
					System.err.println(e.getMessage());
				} catch (AtomElectronNumException e) {
					atomStructureLogger.log(Level.SEVERE, e.getMessage(), e);
					System.err.println(e.getMessage());
				} catch (DataSyntaxException | DataScientificNumberException | IllegalIntimacyInSocialTieException e) {
					assert false : "shouldn't have this exception.";
				} catch (IOException e) {
					e.printStackTrace();
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
				break;
			case 2: // Visualize
				atomStructureLogger.info("Visualize.");
				CircularOrbitHelper.visualize(atomStructure);
				atomStructureLogger.info("Success: Visualize.");
				break;
			case 3: // Add a track
				atomStructureLogger.info("Add a track.");
				System.out.println("What's the radius(integer) of the added track?");
				int addTrackRadius = 0;
				try {
					addTrackRadius = in.nextInt();
					in.nextLine();
				} catch (InputMismatchException e) {
					System.err.println(e.getMessage() + "Please input again.");
					in.nextLine();
					break;
				}
				atomStructure.addTrack(trackFactory.produce((double) addTrackRadius));
				atomStructureLogger.info("Success: Add a track. Radius is " + addTrackRadius + ".");
				break;
			case 4:// Add an object to a track
				atomStructureLogger.info("Add an object to a track.");
				System.out.println("What's the radius(integer) of the track of the added electron?");
				int addElectronRadius = 0;
				try {
					addElectronRadius = in.nextInt();
					in.nextLine();
				} catch (InputMismatchException e) {
					System.err.println(e.getMessage() + "Please input again.");
					in.nextLine();
					break;
				}
				atomStructure.addPhysicalObjectToTrack(electronFactory.produce(),
						trackFactory.produce((double) addElectronRadius));
				atomStructureLogger.info("Success: Add an electron to track with " + addElectronRadius + " radius.");
				break;
			case 5: // Delete a track
				atomStructureLogger.info("Delete a track.");
				System.out.println("What's the radius(integer) of the deleted track?");
				int deleteTrackRadius = 0;
				try {
					deleteTrackRadius = in.nextInt();
					in.nextLine();
				} catch (InputMismatchException e) {
					System.err.println(e.getMessage() + "Please input again.");
					in.nextLine();
					break;
				}
				atomStructure.deleteTrack(trackFactory.produce((double) deleteTrackRadius));
				atomStructureLogger.info("Delete a track with " + deleteTrackRadius + " radius.");
				break;
			case 6: // Delete an object from a track
				atomStructureLogger.info("Delete an object from a track.");
				System.out.println("What's the radius(integer) of the track of the deleted electron?");
				int deleteElectronRadius = 0;
				try {
					deleteElectronRadius = in.nextInt();
					in.nextLine();
				} catch (InputMismatchException e) {
					System.err.println(e.getMessage() + "Please input again.");
					in.nextLine();
					break;
				}
				try {
					atomStructure.deletePhysicalObjectFromTrack(electronFactory.produce(),
							trackFactory.produce((double) deleteElectronRadius));
				} catch (NoObjectOnTrackException e) {
					System.err.println("Fail track: " + deleteElectronRadius + ".");
					atomStructureLogger.log(Level.SEVERE, e.getMessage() + " Fail track: " + deleteElectronRadius + ".",
							e);
					break;
				}
				atomStructureLogger.log(Level.INFO,
						"Success: Delete an electron from track with " + deleteElectronRadius + " radius.");
				break;
			case 7: // Calculate the information entropy of the system
				atomStructureLogger.info("Calculate the information entropy of the system");
				double entropy = apis.getObjectDistributionEntropy(atomStructure);
				System.out.println("Information entropy: " + entropy);
				atomStructureLogger.log(Level.INFO, "Success: Calculate the information entropy: " + entropy + ".");
				break;
			case 8: // Transition
				atomStructureLogger.info("Transition.");
				int sourceRadius = 0;
				int targetRadius = 0;
				try {
					System.out.println("What's the radius(integer) of the source track?");
					sourceRadius = in.nextInt();
					in.nextLine();
					System.out.println("What's the radius(integer) of the target track?");
					targetRadius = in.nextInt();
					in.nextLine();
				} catch (InputMismatchException e) {
					System.err.println(e.getMessage() + "Please input again.");
					in.nextLine();
					break;
				}
				try {
					atomStructure.deletePhysicalObjectFromTrack(electronFactory.produce(),
							trackFactory.produce((double) sourceRadius));
				} catch (NoObjectOnTrackException e) {
					System.err.println("Fail track: " + sourceRadius + ".");
					atomStructureLogger.log(Level.SEVERE, e.getMessage() + " Fail track: " + sourceRadius + ".", e);
					break;
				}
				atomStructure.addPhysicalObjectToTrack(electronFactory.produce(),
						trackFactory.produce((double) targetRadius));
				caretaker.addMemento(atomStructure.save());
				atomStructureLogger.log(Level.INFO, "Success: Transition an electron from track " + sourceRadius
						+ " to track" + targetRadius + ". Restore present state.");
				break;
			case 9: // Restore transition
				atomStructureLogger.info("Restore transition.");
				System.out.println("Which version countdown do you want to restore?");
				int countdownVersion = 0;
				try {
					countdownVersion = in.nextInt();
					in.nextLine();
				} catch (InputMismatchException e) {
					System.err.println(e.getMessage() + "Please input again.");
					in.nextLine();
					break;
				}
				atomStructure.restore(caretaker.getMemento(countdownVersion));
				atomStructureLogger.log(Level.INFO, "Success: Roll back " + countdownVersion + " transition versions.");
				break;
			case 10: // Log search
				apis.logSearch(new File("log/atomStructureLog.log"));
				break;
			default:
				in.close();
				atomStructureLogger.log(Level.INFO, "AtomStructure application deactivate.");
				System.exit(0);
				break;
			}
		}
	}

	/**
	 * Menu in atom structure application which indicates users' choices.
	 */
	private static void menu() {
		System.out.println("1. Read from file to generate a atomic structure.");
		System.out.println("2. Visualize.");
		System.out.println("3. Add a track.");
		System.out.println("4. Add an object to a track.");
		System.out.println("5. Delete a track.");
		System.out.println("6. Delete an object in a track.");
		System.out.println("7. Calculate the information entropy of the system.");
		System.out.println("8. Transition.");
		System.out.println("9. Restore transition.");
		System.out.println("10. Log search.");
	}

	/**
	 * Menu in read-from-file function which indicates which file can be read from.
	 */
	private static void readMenu() {
		System.out.println("1. AtomicStructure.txt");
		System.out.println("2. AtomicStructure_Medium.txt");
		System.out.println("3. Other file(Absolute path).");
	}
}
