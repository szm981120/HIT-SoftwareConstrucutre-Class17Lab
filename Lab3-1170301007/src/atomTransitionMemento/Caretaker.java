package atomTransitionMemento;

import java.util.ArrayList;
import java.util.List;

/**
 * Caretaker works in Memento method as a state histories keeper in atom
 * structure. It keeps state histories.
 * 
 * @author Shen
 *
 * @param <L> class of central object which is mostly Nucleus
 * @param <E> class of physical object which is mostly PhysicalObject
 */
public class Caretaker<L, E> {

	private List<Memento<L, E>> mementos = new ArrayList<Memento<L, E>>();

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
	 * @throws Exception the number of versions rolled back is larger than the
	 *                   version total.
	 */
	public Memento<L, E> getMemento(int i) throws Exception {
		if (mementos.size() - 1 < i) {
			throw new Exception("Cannot rollback so many back!");
		}
		return this.mementos.get(this.mementos.size() - i - 1);
	}

}
