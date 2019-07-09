package crossstrategy;

import monkeycrossriver.Ladder;
import monkeycrossriver.Monkey;

public interface CrossStrategy {

  public int cross(Ladder[] ladders, Monkey monkey);
}
