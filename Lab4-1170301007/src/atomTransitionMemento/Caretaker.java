package atomTransitionMemento;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import MyException.MementoRollBackTooManyStepsException;

/**
 * Caretaker works in Memento method as a state histories keeper in atom
 * structure. It keeps state histories.
 * 
 * Attention: memento only keeps states in transition changes. Any other change
 * not in transition will not be kept in memento. For example, supposed that an
 * electron is deleted first and then a transition happens, if you roll back one
 * step from now, it will go back to the state before the electron is deleted.
 * 
 * @author Shen
 *
 * @param <L> class of central object which is mostly Nucleus
 * @param <E> class of physical object which is mostly PhysicalObject
 */
public class Caretaker<L, E> {

	private List<Memento<L, E>> mementos = new ArrayList<Memento<L, E>>();
	private static Logger atomStructureLogger = Logger.getLogger("AtomStructure Log");

	/**
	 * Mutator. Add a memento into mementos.
	 * 
	 * @param m a memento
	 */
	public void addMemento(Memento<L, E> m) {
		this.mementos.add(m);
	}

	/**
	 * Observer. Get a memento in order to roll back to a historical version.
	 * 
	 * @param i the number of version rolled back
	 * @return the memento after i versions rolled back
	 */
	public Memento<L, E> getMemento(int i) {
		assert i >= 0 : "version number can't be negative.";
		try {
			if (mementos.size() - 1 < i) {
				throw new MementoRollBackTooManyStepsException(
						"Roll back resetting fail! The state doesn't change. Cannot roll so many back!");
			}
		} catch (MementoRollBackTooManyStepsException e) {
			System.err.println(e.getMessage());
			atomStructureLogger.log(Level.SEVERE, e.getMessage(), e);
			return this.mementos.get(this.mementos.size() - 1);
		}
		return this.mementos.get(this.mementos.size() - i - 1);
	}

}
