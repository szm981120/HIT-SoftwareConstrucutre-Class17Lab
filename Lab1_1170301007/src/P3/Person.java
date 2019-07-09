package P3;

/**
 * 
 * @author Shen
 *
 */
public class Person {

	private String name;
	public boolean marked;

	/**
	 * 
	 * @param name construct an object with name
	 */
	public Person(String name) {
		this.name = name;
		this.marked = false;
	}

	/**
	 * 
	 * @return the person's name
	 */
	public String getName() {
		return this.name;
	}
}
