package crossstrategy;

import monkeycrossriver.Ladder;
import monkeycrossriver.Monkey;

public class AverageDistributionStrategy implements CrossStrategy {

  // Thread safety arguments:
  // - When finding an empty ladder, each iteration is synchronized. As soon as an empty ladder is
  // found, return it's index.
  // - If there's no empty ladder, we should find a ladder according to average distribution
  // strategy. The whole iteration will be synchronized. Only when the whole iteration is done, the
  // satisfactory ladder's index is returned. Therefore, when a monkey is choosing ladder, the other
  // monkey can't choose.
  @Override
  public int cross(Ladder[] ladders, Monkey monkey) {
    int ladderIndex = -1;
    boolean monkeySettled = false;
    for (int i = 0; i < ladders.length; i++) {
      synchronized (ladders) {
        if (ladders[i].getState().equals("empty")) {
          ladders[i].setState(monkey.getDirection());
          ladders[i].setMonkey(1, monkey);
          return i;
        }
      } // END synchronized(ladders)
    }
    // int least = 1000;
    double weight = 0;
    synchronized (ladders) {
      for (int i = 0; i < ladders.length; i++) {
        if (ladders[i].getState().equals(monkey.getDirection())
            && ladders[i].getLadder()[1] == null) {
          Monkey[] monkeys = ladders[i].getLadder();
          int tempNum = 0;
          int farestSpeed = 20;
          for (int j = monkeys.length - 1; j >= 1; j--) {
            if (monkeys[j] != null) {
              if (tempNum == 0) {
                farestSpeed = monkeys[j].getSpeed();
              }
              tempNum++;
            }
          }
          double tempWeight = farestSpeed + (monkeys.length - 1 - tempNum) * 0.3;
          if (tempWeight > weight) {
            weight = tempWeight;
            ladderIndex = i;
            monkeySettled = true;
          }
        }
      }
      if (monkeySettled) {
        ladders[ladderIndex].setState(monkey.getDirection());
        ladders[ladderIndex].setMonkey(1, monkey);
      }
      return monkeySettled ? ladderIndex : -1;
    } // END synchronized(ladders)
  }

}
