package centralObject;

/**
 * 
 * @author Shen
 *
 */
public class Person extends physicalObject.Friend {
	// IMMUTABLE

	/**
	 * Constructor
	 * 
	 * @param name It should only consist of letters of an alphabet and contain no
	 *             blank space or expression character.
	 * @param age  It should be a positive integer.
	 * @param sex  It should be either 'M' or 'F'. The former presents male and the
	 *             latter presents female.
	 */
	public Person(String name, int age, char sex) {
		super(name, age, sex);
	}

	@Override
	public boolean equals(Object person) {
		return person.getClass() == Person.class && this.name.equals(((Person) person).name)
				&& this.age == ((Person) person).age && this.sex == ((Person) person).sex;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + this.name.hashCode();
		result = 31 * result + this.age;
		result = 31 * result + this.sex;
		return result;
	}
}
