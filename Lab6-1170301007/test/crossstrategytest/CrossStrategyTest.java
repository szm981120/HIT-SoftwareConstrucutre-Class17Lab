package crossstrategytest;

import static org.junit.Assert.assertEquals;

import crossstrategy.AverageDistributionStrategy;
import crossstrategy.CrossContext;
import crossstrategy.NaiveCross;
import crossstrategy.PushFastStrategy;
import crossstrategy.SpeedDistributionStrategy;
import monkeycrossriver.Ladder;
import monkeycrossriver.Monkey;
import org.junit.Before;
import org.junit.Test;

public class CrossStrategyTest {

  private static CrossContext naiveCross = new CrossContext(new NaiveCross());
  private static CrossContext pushFastStrategy = new CrossContext(new PushFastStrategy());
  private static CrossContext speedDistributionStrategy =
      new CrossContext(new SpeedDistributionStrategy());
  private static CrossContext averageDistributionStrategy =
      new CrossContext(new AverageDistributionStrategy());
  private static Ladder[] ladders;

  /**
   * Initialize ladders before any test.
   */
  @Before
  public void constructLadders() {
    ladders = new Ladder[5];
    for (int i = 0; i < 5; i++) {
      ladders[i] = new Ladder(21);
    }
    ladders[0].setState("L->R");
    ladders[0].setMonkey(2, new Monkey(1, 3, "L->R"));
    ladders[2].setState("L->R");
    ladders[2].setMonkey(3, new Monkey(2, 2, "L->R"));
    ladders[2].setMonkey(6, new Monkey(3, 4, "L->R"));
    ladders[3].setState("R->L");
    ladders[3].setMonkey(3, new Monkey(4, 1, "R->L"));
    ladders[3].setMonkey(4, new Monkey(5, 2, "R->L"));
    ladders[4].setState("L->R");
    ladders[4].setMonkey(8, new Monkey(6, 6, "L->R"));
  }

  @Test(expected = AssertionError.class)
  public void testAssertionsEnabled() {
    assert false;
  }

  @Test
  public void testNaiveCross1() {
    int i = naiveCross.cross(ladders, new Monkey(20, 4, "L->R"));
    assertEquals("expected 1", 1, i);

  }

  @Test
  public void testNaiveCross2() {
    ladders[1].setState("R->L");
    ladders[1].setMonkey(5, new Monkey(7, 2, "R->L"));
    int ii = naiveCross.cross(ladders, new Monkey(21, 3, "R->L"));
    assertEquals("expected 1", 1, ii);
  }

  @Test
  public void testPushFastStrategy1() {
    int i = pushFastStrategy.cross(ladders, new Monkey(20, 4, "L->R"));
    assertEquals("expected 1", 1, i);
  }

  @Test
  public void testPushFastStrategy2() {
    ladders[1].setState("L->R");
    ladders[1].setMonkey(5, new Monkey(7, 2, "L->R"));
    int i = pushFastStrategy.cross(ladders, new Monkey(20, 4, "L->R"));
    assertEquals("expected 4", 4, i);
  }

  @Test
  public void testSpeedDistributionStrategy1() {
    int i = speedDistributionStrategy.cross(ladders, new Monkey(20, 3, "L->R"));
    assertEquals("expected 0", 0, i);
  }

  @Test
  public void testSpeedDistributionStrategy2() {
    int i = speedDistributionStrategy.cross(ladders, new Monkey(20, 7, "L->R"));
    assertEquals("expected 1", 1, i);
  }

  @Test
  public void testSpeedDistributionStrategy3() {
    ladders[1].setState("L->R");
    ladders[1].setMonkey(5, new Monkey(7, 2, "L->R"));
    int i = speedDistributionStrategy.cross(ladders, new Monkey(20, 7, "L->R"));
    assertEquals("expected 4", 4, i);
  }

  @Test
  public void testAverageDistributionStrategy1() {
    int i = averageDistributionStrategy.cross(ladders, new Monkey(20, 3, "L->R"));
    assertEquals("expected 1", 1, i);
  }

  @Test
  public void testAverageDistributionStrategy2() {
    ladders[1].setState("L->R");
    ladders[1].setMonkey(5, new Monkey(7, 2, "L->R"));
    int i = averageDistributionStrategy.cross(ladders, new Monkey(20, 3, "L->R"));
    assertEquals("expected 4", 4, i);
  }

}
