package physicalObject;

import java.util.Random;

public class Friend implements PhysicalObject {

	protected final String name;
	protected final int age;
	protected final char sex;
	private final double degree;

	/*
	 * Abstraction function:
	 * 	AF(name) = name of this friend
	 *  AF(age) = age of this friend
	 *  AF(sex) = sex of this friend
	 *  AF(degree) = degree of this friend position in track
	 *  
	 * Representation invariant:
	 * 	name should be consist of only letters or numbers and shouldn't contain any blank space or any other characters.
	 * 	age should be a positive integer.
	 * 	sex should be either 'M' or 'F'. 'M' representing male and 'F' representing female.
	 * 	degree is no less than 0 and less than 360, unit is degree
	 * 
	 * Safety from rep exposure:
	 * 	All representations are defined private and final.
	 * 	All representations in Observer are immutable.
	 */
	// checkRep
	private void checkRep() {
		assert !this.name.contains("[^a-zA-Z0-9]");
		assert this.age > 0;
		assert this.sex == 'M' || this.sex == 'F';
		assert this.degree >= 0 && this.degree < 360;
	}

	/**
	 * Constructor.
	 * 
	 * @param name Name of this friend. This must be consist of only letters or
	 *             numbers and mustn't contain any blank space or any other
	 *             characters.
	 * @param age  Age of this friend. This must be a positive integer.
	 * @param sex  Sex of this friend. This must be either 'M' or 'F'. 'M'
	 *             representing male and 'F' representing female.
	 */
	public Friend(String name, int age, char sex) {
		Random random = new Random();
		this.name = name;
		this.age = age;
		this.sex = sex;
		this.degree = random.nextDouble() * 360;
		checkRep();
	}

	@Override
	public String getName() {
		String name = this.name;
		checkRep();
		return name;
	}

	@Override
	public double getDegree() {
		double degree = this.degree;
		checkRep();
		return degree;
	}

	/**
	 * This method is non-existent in atom structure.
	 */
	@Override
	public boolean getDirect() {
		assert false;
		return false;
	}

	/**
	 * This method is non-existent in atom structure.
	 */
	@Override
	public double getSpeed() {
		assert false;
		return 0;
	}

	/**
	 * This method is non-existent in atom structure.
	 */
	@Override
	public double getRaidus() {
		assert false;
		return 0;
	}

	/**
	 * Two friends are equal only when they have the same name.
	 */
	@Override
	public boolean equals(Object friend) {
		return friend != null && friend.getClass() == Friend.class && this.name.equals(((Friend) friend).name);
	}

	/**
	 * Equality of friends only links with name, so hash code of friends can be
	 * name's hash code.
	 */
	@Override
	public int hashCode() {
		return this.name.hashCode();
	}
}
