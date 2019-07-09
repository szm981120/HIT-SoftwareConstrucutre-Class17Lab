package crossstrategy;

import monkeycrossriver.Ladder;
import monkeycrossriver.Monkey;

public class NaiveCross implements CrossStrategy {

  // Thread safety arguments:
  // - When finding an empty ladder, each iteration is synchronized. As soon as an empty ladder is
  // found, return it's index.
  // - If there's no empty ladder, we should find a ladder according to naive cross strategy. Each
  // iteration will be synchronized. Therefore, when a monkey is choosing ladder, the other monkeys
  // can't access to this ladder, but they can decide if the other ladders is satisfied.

  @Override
  public int cross(Ladder[] ladders, Monkey monkey) {
    int ladderIndex = -1;
    for (int i = 0; i < ladders.length; i++) {
      synchronized (ladders[i]) {
        if (ladders[i].getState().equals("empty")) {
          ladderIndex = i;
          ladders[i].setMonkey(1, monkey);
          ladders[i].setState(monkey.getDirection());
          return ladderIndex;
        }
      } // END synchronized(ladders)
    }
    for (int i = 0; i < ladders.length; i++) {
      synchronized (ladders[i]) {
        if (ladders[i].getState().equals(monkey.getDirection())
            && ladders[i].getLadder()[1] == null) {
          ladderIndex = i;
          ladders[i].setMonkey(1, monkey);
          ladders[i].setState(monkey.getDirection());
          return ladderIndex;
        }
      } // END synchronized(ladders)
    }
    return -1;
  }
}
