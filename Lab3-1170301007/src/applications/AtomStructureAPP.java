package applications;

import java.io.File;
import java.util.Scanner;

import APIs.CircularOrbitAPIs;
import APIs.CircularOrbitHelper;
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

	/**
	 * atom structure application method
	 * 
	 * @throws Exception restoring transition state exception
	 */
	public static void application() throws Exception {
		CircularOrbitAPIs<Nucleus, PhysicalObject> apis = new CircularOrbitAPIs<Nucleus, PhysicalObject>();
		circularOrbitFactory<Nucleus, PhysicalObject> factory = new AtomStructureFactory();
		CircularOrbit<Nucleus, PhysicalObject> atomStructure = factory.produce();
		ElectronFactory electronFactory = new ConcreteElectronFactory();
		TrackFactory trackFactory = new TrackFactory();
		Caretaker<Nucleus, PhysicalObject> caretaker = new Caretaker<Nucleus, PhysicalObject>();
		while (true) {
			menu();
			int choose = in.nextInt();
			switch (choose) {
			case 1: // Read from file to generate a atomic structure
				readMenu();
				int choose1 = in.nextInt();
				switch (choose1) {
				case 1:
					atomStructure.readFromFile(new File("input/AtomicStructure.txt"));
					break;
				case 2:
					atomStructure.readFromFile(new File("input/AtomicStructure_Medium.txt"));
					break;
				default:
					System.out.println("Wrong input");
					break;
				}
				caretaker.addMemento(atomStructure.save());
				break;
			case 2: // Visualize
				CircularOrbitHelper.visualize(atomStructure);
				break;
			case 3: // Add a track
				System.out.println("What's the radius(integer) of the added track?");
				int addTrackRadius = in.nextInt();
				in.nextLine();
				atomStructure.addTrack(trackFactory.produce((double) addTrackRadius));
				break;
			case 4:// Add an object to a track
				System.out.println("What's the radius(integer) of the track of the added electron?");
				int addElectronRadius = in.nextInt();
				in.nextLine();
				atomStructure.addPhysicalObjectToTrack(electronFactory.produce(),
						trackFactory.produce((double) addElectronRadius));
				break;
			case 5: // Delete a track
				System.out.println("What's the radius(integer) of the deleted track?");
				int deleteTrackRadius = in.nextInt();
				in.nextLine();
				atomStructure.deleteTrack(trackFactory.produce((double) deleteTrackRadius));
				break;
			case 6: // Delete an object in a track
				System.out.println("What's the radius(integer) of the track of the deleted electron?");
				int deleteElectronRadius = in.nextInt();
				in.nextLine();
				atomStructure.deletePhysicalObjectFromTrack(electronFactory.produce(),
						trackFactory.produce((double) deleteElectronRadius));
				break;
			case 7: // Calculate the information entropy of the system
				double entropy = apis.getObjectDistributionEntropy(atomStructure);
				System.out.println("Information entropy: " + entropy);
				break;
			case 8: // Transition
				System.out.println("What's the radius(integer) of the source track?");
				int sourceRadius = in.nextInt();
				in.nextLine();
				System.out.println("What's the radius(integer) of the target track?");
				int targetRadius = in.nextInt();
				in.nextLine();
				atomStructure.deletePhysicalObjectFromTrack(electronFactory.produce(),
						trackFactory.produce((double) sourceRadius));
				atomStructure.addPhysicalObjectToTrack(electronFactory.produce(),
						trackFactory.produce((double) targetRadius));
				caretaker.addMemento(atomStructure.save());
				break;
			case 9:
				System.out.println("Which version countdown do you want to restore?");
				int countdownVersion = in.nextInt();
				in.nextLine();
				atomStructure.restore(caretaker.getMemento(countdownVersion));
				break;
			default:
				in.close();
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
	}

	/**
	 * Menu in read-from-file function which indicates which file can be read from.
	 */
	private static void readMenu() {
		System.out.println("1. AtomicStructure.txt");
		System.out.println("2. AtomicStructure_Medium.txt");
	}
}
