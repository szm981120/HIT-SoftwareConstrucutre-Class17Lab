package physicalobject;

/**
 * PlanetFactory.
 * 
 * @author Shen
 *
 */
public interface PlanetFactory {

  /**
   * Produce a planet.
   * 
   * @param name planet's name
   * @param state planet's state
   * @param color planet's color
   * @param radius planet's radius, unit is kilometer
   * @param speed planet's speed, unit is kilometer per second
   * @param direct planet's revolution direction, true if it's clockwise, false if it's
   *        counter-clockwise.
   * @param degree planet's degree
   * @return a Planet instance
   */
  public PhysicalObject produce(String name, String state, String color, double radius,
      double speed, boolean direct, double degree);
}
