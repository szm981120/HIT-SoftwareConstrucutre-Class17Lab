package monkeycrossriver;


public class Ladder {

  private final Monkey[] ladder;
  private String state = "empty";

  // Representation invariant:
  // - state should only be emtpy, L->R or R->L
  // Abstract function:
  // - AF(ladder) = a Monkey array which preserve all monkeys on this ladder.
  // - AF(state) = a String presenting the crossing monkeys' state on this ladder.
  // Safety from rep exposure:
  // - ladder is declared private and final
  // - state is a immutable object
  // - Observer for ladder has a defensive copy
  // Thread safety arguments:
  // - all method are declared synchronized.

  public Ladder(int h) {
    ladder = new Monkey[h];
  }

  /**
   * Observer.
   * 
   * @return defensive copy of ladder
   */
  public synchronized Monkey[] getLadder() {
    Monkey[] copyLadder = new Monkey[this.ladder.length];
    for (int i = 0; i < this.ladder.length; i++) {
      copyLadder[i] = this.ladder[i];
    }
    return copyLadder;
  }

  /**
   * Observer.
   * 
   * @return state
   */
  public synchronized String getState() {
    return this.state;
  }

  /**
   * Mutator. Set monkey to ladder[i].
   * 
   * @param i      the mutated position index
   * @param monkey the set monkey
   */
  public synchronized void setMonkey(int i, Monkey monkey) {
    this.ladder[i] = monkey;
  }

  /**
   * Mutator. Remove monkey on ladder[i].
   * 
   * @param i the removed position index
   */
  public synchronized void removeMonkey(int i) {
    this.ladder[i] = null;
  }

  /**
   * Mutator. Set state to direction.
   * 
   * @param direction the set state
   */
  public synchronized void setState(String direction) {
    this.state = direction;
  }
}
