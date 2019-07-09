package monkeycrossriver;

import crossstrategy.AverageDistributionStrategy;
import crossstrategy.CrossContext;
import crossstrategy.NaiveCross;
import crossstrategy.PushFastStrategy;
import crossstrategy.SpeedDistributionStrategy;

import java.io.File;
import java.util.Scanner;


public class Main {

  /**
   * Main.
   * 
   * @param args parameters
   */
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    System.out.println("Please choose strategy:");
    System.out.println("1. Naive Cross.");
    System.out.println("2. Push Fast.");
    System.out.println("3. Speed Distribution.");
    System.out.println("4. Average Distributon(with weight).");
    int choice = in.nextInt();
    in.nextLine();
    CrossContext crossContext = null;
    switch (choice) {
      case 1:
        crossContext = new CrossContext(new NaiveCross());
        break;
      case 2:
        crossContext = new CrossContext(new PushFastStrategy());
        break;
      case 3:
        crossContext = new CrossContext(new SpeedDistributionStrategy());
        break;
      case 4:
        crossContext = new CrossContext(new AverageDistributionStrategy());
        break;
      default:
        break;
    }
    /* v1 */
    // System.out.println("Please input n:");
    // final int n = in.nextInt();
    // in.nextLine();
    // System.out.println("Please input h:");
    // final int h = in.nextInt();
    // in.nextLine();
    // System.out.println("Please input t:");
    // final int t = in.nextInt();
    // in.nextLine();
    // System.out.println("Please input k:");
    // final int k = in.nextInt();
    // in.nextLine();
    // System.out.println("Please input total:");
    // final int total = in.nextInt();
    // in.nextLine();
    // System.out.println("Please input maxSpeed:");
    // final int maxSpeed = in.nextInt();
    // in.nextLine();
    // MonkeyGenerator monkeyGenerator = new MonkeyGenerator(n, h, t, k, total, maxSpeed);
    // monkeyGenerator.visualize();
    // monkeyGenerator.generate(crossContext);
    /* v3 Competition */
    File file = null;
    System.out.println("Please choose competition file:");
    System.out.println("1. competition/Competition_1.txt");
    System.out.println("2. competition/Competition_2.txt");
    System.out.println("3. competition/Competition_3.txt");
    int fileChoice = in.nextInt();
    in.nextLine();
    switch (fileChoice) {
      case 1:
        file = new File("competition/Competition_1.txt");
        break;
      case 2:
        file = new File("competition/Competition_2.txt");
        break;
      case 3:
        file = new File("competition/Competition_3.txt");
        break;
      default:
        break;
    }
    Competition competition = new Competition(file);
    competition.visualize();
    competition.readFromFile(file, crossContext);
    in.close();
  }

}
