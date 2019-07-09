package physicalobject;

/**
 * Planet implements PhysicalObject.
 * 
 * @author Shen
 *
 */
public class Planet implements PhysicalObject {

  // IMMUTABLE
  // factory method
  private final String name;

  private final String state;

  private final String color;

  private final double radius;

  private final double speed;

  private final boolean direct;

  private final double degree;
  /*
   * Abstraction function: AF(name) = name of this planet AF(state) = state of this planet AF(color)
   * = color of this planet AF(radius) = radius(km) of this planet AF(speed) = revolution
   * speed(km/s) of this planet AF(direct) = revolution direct of this planet, true if it's
   * clockwise, false if it's counter-clockwise. AF(degree) = degree of this planet position in
   * track
   * 
   * Representation invariant: name should be consist of only letters or numbers and mustn't contain
   * any blank space or any other characters. state should be consist of only letters or numbers and
   * mustn't contain any blank space or any other characters. color should be consist of only
   * letters or numbers and mustn't contain any blank space or any other characters. radius must be
   * positive speed must be non-negative degree is no less than 0 and less than 360, unit is degree
   * 
   * Safety from rep exposure: All representations are defined private and final. All
   * representations in Observer are immutable.
   */

  /**
   * checkRep.
   */
  private void checkRep() {
    assert !this.name.contains("[^a-zA-Z0-9]");
    assert !this.state.contains("[^a-zA-Z0-9]");
    assert !this.color.contains("[^a-zA-Z0-9]");
    assert this.radius > 0;
    assert this.speed >= 0;
    assert this.degree >= 0 && this.degree < 360;
  }

  /**
   * Constructor.
   * 
   * @param name planet's name, must be consist of only letters or numbers and mustn't contain any
   *        blank space or any other characters.
   * @param state planet's state, must be consist of only letters or numbers and mustn't contain any
   *        blank space or any other characters.
   * @param color planet's color, must be consist of only letters or numbers and mustn't contain any
   *        blank space or any other characters.
   * @param radius planet's radius, must be positive, unit is kilometer.
   * @param speed planet's speed, must be non-negative, unit is kilometer per second.
   * @param direct planet's revolution direction, true if it's clockwise, false if it's
   *        counter-clockwise.
   * @param degree planet's degree, must be no less than 0 and less than 360, unit is degree.
   */
  public Planet(String name, String state, String color, double radius, double speed,
      boolean direct, double degree) {
    this.name = name;
    this.state = state;
    this.color = color;
    this.radius = radius;
    this.speed = speed;
    this.direct = direct;
    this.degree = degree;
    checkRep();
  }

  @Override
  public String getName() {
    // String name = this.name;
    // checkRep();
    return this.name;
  }

  @Override
  public String getState() {
    // String state = this.state;
    // checkRep();
    return this.state;
  }

  @Override
  public String getColor() {
    // String color = this.color;
    // checkRep();
    return this.color;
  }

  @Override
  public double getDegree() {
    // double degree = this.degree;
    // checkRep();
    return this.degree;
  }

  @Override
  public boolean getDirect() {
    // boolean direct = this.direct;
    // checkRep();
    return this.direct;
  }

  @Override
  public double getSpeed() {
    // double speed = this.speed;
    // checkRep();
    return this.speed;
  }

  @Override
  public double getRaidus() {
    // double radius = this.radius;
    // checkRep();
    return this.radius;
  }

  /**
   * Two planets are equals only when they have the same name with case sensitive.
   */
  @Override
  public boolean equals(Object planet) {
    return planet != null && planet.getClass() == Planet.class
        && this.name.equals(((Planet) planet).name);
  }

  @Override
  public int hashCode() {
    return this.name.hashCode();
  }

  @Override
  public int getAge() {
    assert false : "shouldn't have this method";
    return 0;
  }

  @Override
  public char getSex() {
    assert false : "shouldn't have this method";
    return 0;
  }
}
