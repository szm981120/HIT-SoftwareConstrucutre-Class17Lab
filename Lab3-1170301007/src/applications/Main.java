package applications;

import java.util.Scanner;

/**
 * 
 * @author Shen
 *
 */
public class Main {

	private static Scanner in = new Scanner(System.in);

	/**
	 * main
	 * 
	 * @param args
	 * @throws Exception deal with some exception when user's input disobey
	 *                   scientific notation rules.
	 */
	public static void main(String[] args) throws Exception {
		menu();
		int choose = in.nextInt();
		switch (choose) {
		case 1:
			AtomStructureAPP.application();
			break;
		case 2:
			StellarSystemAPP.application();
			break;
		case 3:
			SocialNetworkCircleAPP.application();
			break;
		default:
			break;
		}
	}

	/**
	 * Menu which indicates user's choices on what kind of circular orbit
	 */
	private static void menu() {
		System.out.println("1. Atom Structure.");
		System.out.println("2. Stellar System.");
		System.out.println("3. Social Network Circle.");
	}

}
