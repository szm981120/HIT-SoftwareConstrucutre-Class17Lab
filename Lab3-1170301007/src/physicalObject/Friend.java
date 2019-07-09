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
	 * @param name Name of this friend. This should be consist of only letters or
	 *             numbers and shouldn't contain any blank space or any other
	 *             characters.
	 * @param age  Age of this friend. This should be a positive integer.
	 * @param sex  Sex of this friend. This should be either 'M' or 'F'. 'M'
	 *             representing male and 'F' representing female.
	 */
	public Friend(String name, int age, char sex) {
		this.name = name;
		this.age = age;
		this.sex = sex;
		this.degree = new Random().nextDouble() * 360;
		checkRep();
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public double getDegree() {
		return this.degree;
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
	 * Two friends are equal only when they have the same name, age and sex.
	 */
	@Override
	public boolean equals(Object friend) {
		return friend.getClass() == Friend.class && this.name.equals(((Friend) friend).name)
				&& this.age == ((Friend) friend).age && this.sex == ((Friend) friend).sex;
	}

	/**
	 * Equality of friends links with name, age and sex, so hash code of friends
	 * should be a particular combination of name's hash code, age and sex.
	 */
	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + this.name.hashCode();
		result = 31 * result + this.age;
		result = 31 * result + this.sex;
		return result;
	}
}
