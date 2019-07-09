package physicalobject;

/**
 * FriendFactory.
 * 
 * @author Shen
 *
 */
public interface FriendFactory {

  /**
   * Produce a friend.
   * 
   * @param name friend's name
   * @param age friend's age
   * @param sex friend's sex
   * @return a Friend instance
   */
  public PhysicalObject produce(String name, int age, char sex);
}
