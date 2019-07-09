package centralObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Shen
 *
 */
public class Nucleus {
	// IMMUTABLE
	private final char[] elementName;
	private final List<Nucleon> nucleons = new ArrayList<Nucleon>();

	/*
	 * Abstraction function:
	 * 	AF(elementName) = the name of this nucleus.
	 * Representation invariant:
	 * 	The length of elementName must be one or two.
	 * 	If the length is one, elementName[0] must be a capital letter.
	 * 	If the length is two, elementName[1] must be a lower-case letter.
	 * Safety from rep exposure:
	 * 	All representation are defined private and final. 
	 * 	No Observer.
	 */

	// checkRep
	private void checkRep() {
		assert (this.elementName.length == 1 && Character.isUpperCase(this.elementName[0]))
				|| (this.elementName.length == 2 && Character.isUpperCase(this.elementName[0])
						&& Character.isLowerCase(this.elementName[1]));
	}

	/**
	 * Constructor
	 * 
	 * @param elementName It shouldn't be longer than 2 characters. First character
	 *                    should be capital. Second character, if exists, should be
	 *                    lowercase.
	 */
	public Nucleus(char[] elementName) {
		this.elementName = elementName;
		checkRep();
	}

	/**
	 * Mutator. Add a nucleon into this nucleus.
	 * 
	 * @param nucleon a Nucleon instance
	 */
	public void addNucleon(Nucleon nucleon) {
		this.nucleons.add(nucleon);
	}

	/**
	 * Override toString. This will show the element name of this nucleus.
	 */
	@Override
	public String toString() {
		String elementNameString = "";
		elementNameString += this.elementName[0];
		elementNameString += this.elementName[1];
		checkRep();
		return elementNameString;
	}

}
