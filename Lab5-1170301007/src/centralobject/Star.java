package centralobject;

/**
 * Star.
 * 
 * @author Shen
 *
 */
public class Star {

  // IMMUTABLE
  private final String name;

  private final double radius;

  @SuppressWarnings("unused")
  private final double mass;
  /*
   * Abstraction function: AF(name) = the name of this star. AF(radius) = the radius of this star.
   * AF(mass) = the mass of this star.
   * 
   * Representation invariant: name only consists of letters or numbers and shouldn't contain any
   * blank space or other character. radius must be positive. mass must be positive.
   * 
   * Safety from rep exposure: All representation are defined private and final. All representation
   * in Observer are immutable.
   */

  /**
   * checkRep.
   */
  private void checkRep() {
    assert !this.name.contains("[^a-zA-Z0-9]") && this.radius > 0 && this.mass > 0;
  }

  /**
   * Constructor.
   * 
   * @param name It should only consist of letters of an alphabet and contain no blank space or
   *        expression character.
   * @param radius It should be a positive decimal. The unit is kilometer.
   * @param mass It should be a positive decimal. The unit is kilogram.
   */
  public Star(String name, double radius, double mass) {
    this.name = name;
    this.radius = radius;
    this.mass = mass;
    checkRep();
  }

  /**
   * Observer.
   * 
   * @return radius of star
   */
  public double getRadius() {
    return this.radius;
  }

  /**
   * Observer.
   * 
   * @return name of star
   */
  public String getName() {
    return this.name;
  }

  /**
   * Observer.
   * 
   * @return mass of star
   */
  public double getMass() {
    return this.mass;
  }

  /**
   * Two stars are equals only when they have the same name with case sensitive.
   */
  @Override
  public boolean equals(Object star) {
    return star != null && star.getClass() == this.getClass()
        && ((Star) star).getName().equals(this.name);
  }

  /**
   * Because equality of stars only links with name, hash code of star can be defined as it's name's
   * hash code.
   */
  @Override
  public int hashCode() {
    return this.name.hashCode();
  }
}
