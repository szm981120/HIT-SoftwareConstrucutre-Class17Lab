package crossstrategy;

import monkeycrossriver.Ladder;
import monkeycrossriver.Monkey;

public class SpeedDistributionStrategy implements CrossStrategy {

  // Thread safety arguments:
  // - When finding an empty ladder, each iteration is synchronized. As soon as an empty ladder is
  // found, return it's index.
  // - When not finding an empty ladder, we should find a ladder according to speed distribution
  // strategy. The whole iteration will be synchronized. Only when the whole iteration is done, the
  // satisfactory ladder's index is returned. Therefore, when a monkey is choosing ladder, the other
  // monkey can't choose.

  @Override
  public int cross(Ladder[] ladders, Monkey monkey) {
    int ladderIndex = -1;
    boolean monkeySettled = false;
    synchronized (ladders) {
      int speedDiff = 1000;
      for (int i = 0; i < ladders.length; i++) {
        if (ladders[i].getState().equals(monkey.getDirection())
            && ladders[i].getLadder()[1] == null) {
          Monkey[] monkeys = ladders[i].getLadder();
          for (int j = 1; j <= monkeys.length - 1; j++) {
            if (monkeys[j] != null) {
              if (monkeys[j].getCurrentSpeed() >= monkey.getSpeed()
                  && monkeys[j].getCurrentSpeed() - monkey.getSpeed() < speedDiff) {
                speedDiff = monkeys[j].getCurrentSpeed() - monkey.getSpeed();
                ladderIndex = i;
                monkeySettled = true;
              }
              break;
            }
          }
        }
      }
      if (!monkeySettled) {
        for (int i = 0; i < ladders.length; i++) {
          if (ladders[i].getState().equals("empty")) {
            ladders[i].setState(monkey.getDirection());
            ladders[i].setMonkey(1, monkey);
            ladderIndex = i;
            return ladderIndex;
          }
        }
      }
      speedDiff = 1000;
      if (!monkeySettled) {
        for (int i = 0; i < ladders.length; i++) {
          if (ladders[i].getState().equals(monkey.getDirection())
              && ladders[i].getLadder()[1] == null) {
            Monkey[] monkeys = ladders[i].getLadder();
            for (int j = 1; j <= monkeys.length - 1; j++) {
              if (monkeys[j] != null) {
                if (monkeys[j].getCurrentSpeed() <= monkey.getSpeed()
                    && monkey.getSpeed() - monkeys[j].getCurrentSpeed() < speedDiff) {
                  speedDiff = monkey.getSpeed() - monkeys[j].getCurrentSpeed();
                  ladderIndex = i;
                  monkeySettled = true;
                }
                break;
              }
            }
          }
        }
      }
      if (monkeySettled) {
        ladders[ladderIndex].setMonkey(1, monkey);
      }
      return monkeySettled ? ladderIndex : -1;
    } // END synchronized(ladders)
  }
}
