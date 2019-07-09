package crossstrategy;

import monkeycrossriver.Ladder;
import monkeycrossriver.Monkey;

public class CrossContext {

  private CrossStrategy strategy;

  public CrossContext(CrossStrategy strategy) {
    this.strategy = strategy;
  }

  public int cross(Ladder[] ladders, Monkey monkey) {
    return this.strategy.cross(ladders, monkey);
  }
}
