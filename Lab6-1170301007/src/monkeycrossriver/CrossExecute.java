package monkeycrossriver;

import crossstrategy.CrossContext;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CrossExecute extends Thread {

  private final List<Monkey> monkeys;
  private final Set<Monkey> waitingMonkeys;
  private final Ladder[] ladders;
  private final Monkey monkey;
  private final CrossContext crossContext;
  private final int delay;
  private static Logger logger = Logger.getLogger("cross log");

  // Representation invariant:
  // - delay should be a positive integer
  // Abstract function:
  // - AF(waitingMonkeys) = synchronized set of all waiting monkeys who has been born
  // - AF(ladders) = an array of Ladder instances which preserve every monkey's position on each
  // ladder
  // Safety from rep exposure:
  // - all representations are declared private and final
  // - No observers or mutators.
  // Thread safety argument:
  // - waitingMonkeys is a final synchronized set
  // - ladders is a final array
  // - Every operation about ladders, it is synchronized. So the mutation and observation on ladders
  // are safe. In every monkey thread, i.e. run method in class CrossExecute, there are two parts
  // which are choosing ladder according to the specific strategy and crossing the river. The
  // choosing ladder thread safety is guaranteed in strategy class. Crossing the river thread safety
  // is guaranteed because every time a monkey is acting, the ladder on which it is is synchronized.
  // - waitingMonkeys has only remove method here, so it's safe.

  /**
   * The monkey thread. If this monkey has no ladder to go, according to strategy, it should be
   * waiting for a viable ladder. If this monkey is crossing, then it won't stop until it arrives
   * the other side.
   * 
   * @param ladders        all ladders
   * @param monkey         the running monkey
   * @param crossContext   crossing strategy context
   * @param waitingMonkeys the set of waiting monkeys
   * @param delay          run after delay seconds when the thread is created
   */
  public CrossExecute(Ladder[] ladders, Monkey monkey, CrossContext crossContext,
      List<Monkey> monkeys, Set<Monkey> waitingMonkeys, int delay) {
    this.monkeys = monkeys;
    this.waitingMonkeys = waitingMonkeys;
    this.ladders = ladders;
    this.monkey = monkey;
    this.crossContext = crossContext;
    this.delay = delay;
    Locale.setDefault(new Locale("en", "EN"));
    logger.setLevel(Level.INFO);
    logger.setUseParentHandlers(false);
  }

  @Override
  public synchronized void run() {
    try {
      if (delay > 0) {
        wait(delay * 1000);
      }
    } catch (InterruptedException e1) {
      e1.printStackTrace();
    }
    long start = System.currentTimeMillis();
    this.monkey.setBorn(start);
    int ladderIndex = 0;
    ladderIndex = crossContext.cross(ladders, monkey);
    while (ladderIndex < 0) {
      double live = ((double) (System.currentTimeMillis() - start) / 1000);
      LogHandler.write(logger, monkey.getId() + " monkey waiting. Living time: " + live + "s.");
      try {
        wait(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      ladderIndex = crossContext.cross(ladders, monkey);
    }
    try {
      wait(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    this.waitingMonkeys.remove(this.monkey);
    int position = 1;
    int h = ladders[ladderIndex].getLadder().length - 1;
    long startCross = System.currentTimeMillis();
    while (position <= h) {
      double live = ((double) (System.currentTimeMillis() - start) / 1000);
      LogHandler.write(logger,
          monkey.getId() + " monkey crossing " + monkey.getDirection() + ". Position: " + position
              + ". Living time: " + live + "s. Supposed speed: " + monkey.getSpeed() + "/s.");
      synchronized (this.ladders[ladderIndex]) {
        boolean isBlock = false;
        int blockPosition = 0;
        if (position < h) {
          Monkey[] monkeys = ladders[ladderIndex].getLadder();
          for (int i = 1; i <= monkey.getSpeed(); i++) {
            if (monkeys[Math.min(position + i, h)] != null) {
              isBlock = true;
              blockPosition = Math.min(position + i, h);
              break;
            }
          }
        }
        if (isBlock) {
          ladders[ladderIndex].removeMonkey(position);
          monkey.setCurrentSpeed(blockPosition - position);
          position = blockPosition - 1;
          ladders[ladderIndex].setMonkey(position, monkey);

        } else if (position + monkey.getSpeed() <= h) {
          ladders[ladderIndex].removeMonkey(position);
          monkey.setCurrentSpeed(monkey.getSpeed());
          position += monkey.getSpeed();
          ladders[ladderIndex].setMonkey(position, monkey);
        } else {
          ladders[ladderIndex].removeMonkey(position);
          monkey.setCurrentSpeed(100);
          long endCross = System.currentTimeMillis();
          LogHandler.write(logger, monkey.getId() + " has crossed! Runtime: "
              + ((double) (endCross - startCross) / 1000));
          this.monkey.setArrived(System.currentTimeMillis());
          this.monkeys.add(monkey);
          position = h + 1;
          boolean otherMonkey = false;
          for (int i = 0; i <= h; i++) {
            if (ladders[ladderIndex].getLadder()[i] != null) {
              otherMonkey = true;
              break;
            }
          }
          if (!otherMonkey) {
            ladders[ladderIndex].setState("empty");
          }
        }
      }
      if (position <= h) {
        try {
          wait(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    } // END while
  }
}
