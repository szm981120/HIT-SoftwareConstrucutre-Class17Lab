package monkeycrossriver;

public class Monkey {

  private final int id;
  private final String direction;
  private final int speed;
  private int currentSpeed;
  private long born;
  private long arrived;

  // Representation invariant:
  // - id must be a natural number
  // - direction must be either L->R or R->L
  // - speed must be a positive integer
  // - current speed must be a positive integer and mustn't be more than speed
  // Abstract function:
  // - AF(id) = monkey's id
  // - AF(direction) = monkey's crossing direction
  // - AF(speed) = monkey's max speed
  // - AF(currentSpeed) = monkey's real speed
  // - AF(born) = monkey's born time, obtained with System.currentTimeMillis()
  // - AF(arrived) = monkey's arrived time, obtained with System.currentTimeMillis()
  // Safety from rep exposure:
  // - All representations are immutable
  // Thread safety arguments:
  // - Strategy 4: All methods are declared synchronized
  /**
   * Constructor.
   * 
   * @param id        monkey's id, should be a natural number
   * @param speed     monkey's speed, should be a positive integer
   * @param direction monkey's direction, should be either L->R or R->L
   */
  public Monkey(int id, int speed, String direction) {
    this.id = id;
    this.direction = direction;
    this.speed = speed;
    this.currentSpeed = this.speed;
    this.born = 0;
    this.arrived = 0;
  }

  public synchronized int getId() {
    return this.id;
  }

  public synchronized String getDirection() {
    return this.direction;
  }

  public synchronized int getSpeed() {
    return this.speed;
  }

  public synchronized int getCurrentSpeed() {
    return this.currentSpeed;
  }

  public synchronized long getBorn() {
    return this.born;
  }

  public synchronized long getArrived() {
    return this.arrived;
  }

  public synchronized void setCurrentSpeed(int currentSpeed) {
    this.currentSpeed = currentSpeed;
  }

  public synchronized void setBorn(long born) {
    this.born = born;
  }

  public synchronized void setArrived(long arrived) {
    this.arrived = arrived;
  }

  @Override
  public synchronized boolean equals(Object object) {
    return object.getClass() == Monkey.class && this.id == ((Monkey) object).getId();
  }

  @Override
  public synchronized int hashCode() {
    return this.id;
  }
}
