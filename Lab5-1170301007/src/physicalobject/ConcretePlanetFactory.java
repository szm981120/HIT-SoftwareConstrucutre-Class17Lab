package physicalobject;

/**
 * ConcretePlanetFactory provides a method to create a Planet instance.
 * 
 * @author Shen
 *
 */
public class ConcretePlanetFactory implements PlanetFactory {

  /**
   * Constructor.
   */
  public ConcretePlanetFactory() {

  }

  /**
   * Create a planet.
   */
  @Override
  public PhysicalObject produce(String name, String state, String color, double radius,
      double speed, boolean direct, double degree) {
    return new Planet(name, state, color, radius, speed, direct, degree);
  }

}
