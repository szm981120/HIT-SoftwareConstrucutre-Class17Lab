package physicalObject;

/**
 * ConcreteFriendFactory provides a method to create a Friend instance.
 * 
 * @author Shen
 *
 */
public class ConcreteFriendFactory implements FriendFactory {

	/**
	 * Constructor
	 */
	public ConcreteFriendFactory() {
		
	}

	/**
	 * Create a friend.
	 */
	@Override
	public PhysicalObject produce(String name, int age, char sex) {
		return new Friend(name, age, sex);
	}

}
