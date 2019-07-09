package P3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author Shen
 *
 */
public class Player {

	private final String name; // player's name
	private final Set<Piece> pieces = new HashSet<Piece>(); // player's pieces
	private final List<String> history = new ArrayList<String>(); // player's action history

	/*
	 * Abstract function:
	 * 	AF(name, pieces, history) = a player named name who has a set of pieces and his action history
	 * 
	 * Representation invariant:
	 * 	name shouldn't be null
	 * 
	 * Safety from rep exposure:
	 * 	all representations are defined private and final
	 * 	all mutable rep observers have a defensive copy
	 * 
	 */

	// check rep
	private void checkRep() {
		assert this.name != null;
	}

	/**
	 * Constructor
	 * 
	 * @param name player's name
	 */
	public Player(String name) {
		this.name = name;
		checkRep();
	}

	/**
	 * Observer
	 * 
	 * get player's name
	 * 
	 * @return name
	 */
	public String getName() {
		String name = this.name;
		checkRep();
		return name;
	}

	/**
	 * Mutator
	 * 
	 * get player's pieces
	 * 
	 * @return a copy of pieces
	 */
	public Set<Piece> getPieces() {
		Set<Piece> copyPieces = new HashSet<Piece>(this.pieces);
		checkRep();
		return copyPieces;
	}

	/**
	 * Mutator
	 * 
	 * add piece into player's pieces
	 * 
	 * @param piece
	 */
	public void addPiece(Piece piece) {
		this.pieces.add(piece);
		checkRep();
	}

	/**
	 * Mutator
	 * 
	 * remove piece from player's pieces
	 * 
	 * @param piece
	 */
	public void removePiece(Piece piece) {
		this.pieces.remove(piece);
		checkRep();
	}

	/**
	 * Observer
	 * 
	 * get player's action history
	 * 
	 * @return a copy of history
	 */
	public List<String> getHistory() {
		List<String> copyHistory = new ArrayList<String>(this.history);
		checkRep();
		return copyHistory;
	}

	/**
	 * Mutator
	 * 
	 * add a message into player's action history
	 * 
	 * @param message an action message
	 */
	public void addHistory(String message) {
		this.history.add(message);
		checkRep();
	}

}
