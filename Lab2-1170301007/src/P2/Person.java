package P2;

public class Person {

	private final String name;
	private boolean marked;

	/*
	 * Abstract function:
	 * 	AF(name, marked) = a person named name and is visited in graph if marked is true
	 * 
	 * Representation invariant:
	 * 	name is the person's name and it shouldn't be null
	 * 
	 * Safety from rep exposure:
	 * 	the rep marked has exposure because we need to modified marked when the person is visited
	 * 	the rep name has no exposure because it's defined private and final
	 * 
	 */

	// check rep
	private void checkRep() {
		assert this.name != null;
	}

	/**
	 * 
	 * @param name construct an object with name
	 */
	public Person(String name) {
		this.name = name;
		this.marked = false;
		checkRep();
	}

	/**
	 * Observer
	 * 
	 * @return the person's name
	 */
	public String getName() {
		String name = this.name;
		checkRep();
		return name;
	}

	/**
	 * Observer
	 * 
	 * @return a copy of marked
	 */
	public boolean getMarked() {
		boolean marked = this.marked;
		checkRep();
		return marked;
	}

	/**
	 * Mutator
	 * 
	 * @param a set marked to a(boolean)
	 */
	public void setMarked(boolean a) {
		this.marked = a;
		checkRep();
	}

}
