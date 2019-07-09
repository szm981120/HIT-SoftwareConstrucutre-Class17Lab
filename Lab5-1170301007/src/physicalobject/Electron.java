package physicalobject;

import java.util.Random;

/**
 * Electron implements PhysicalObject.
 * 
 * @author Shen
 *
 */
public class Electron implements PhysicalObject {

  // IMMUTABLE
  // factory method
  private final double degree;
  /*
   * Abstraction function: AF(degree) = degree of electron in track representing degree from
   * positive x-axis counter-clockwise to it's position.
   * 
   * Representation invariant: degree is no less than 0 and less than 360, unit is degree
   * 
   * Safety from rep exposure: all representation are defined private and final all representation
   * are immutable
   * 
   */

  /**
   * checkRep.
   */
  private void checkRep() {
    assert this.degree >= 0 && this.degree < 360;
  }

  /**
   * Constructor. Randomly generate a degree(decimal) from 0 to 360.
   */
  public Electron() {
    Random random = new Random();
    this.degree = random.nextDouble() * 360;
    checkRep();
  }

  @Override
  public double getDegree() {
    return this.degree;
  }

  /**
   * Because all electrons have no difference, they are all equal.
   */
  @Override
  public boolean equals(Object electron) {
    return electron != null && electron.getClass() == Electron.class;
  }

  /**
   * All electrons are equals, so they should have the same hash code.
   */
  @Override
  public int hashCode() {
    return -1;
  }

  @Override
  public String getState() {
    assert false : "shouldn't have this method";
    return null;
  }

  @Override
  public String getColor() {
    assert false : "shouldn't have this method";
    return null;
  }

  /**
   * This method is non-existent in atom structure.
   */
  @Override
  public String getName() {
    assert false : "shouldn't have this method";
    return null;
  }

  /**
   * This method is non-existent in atom structure.
   */
  @Override
  public boolean getDirect() {
    assert false : "shouldn't have this method";
    return false;
  }

  /**
   * This method is non-existent in atom structure.
   */
  @Override
  public double getSpeed() {
    assert false : "shouldn't have this method";
    return 0;
  }

  /**
   * This method is non-existent in atom structure.
   */
  @Override
  public double getRaidus() {
    assert false : "shouldn't have this method";
    return 0;
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
