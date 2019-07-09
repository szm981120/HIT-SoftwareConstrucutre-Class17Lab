package P3;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author Shen
 *
 */
public class Piece {

	private final String type; // piece's type
	private final String color; // piece's color(i.e. player's name)

	/*
	 * Abstract function:
	 * 	AF(type, color) = a color's(player's) piece, the piece's type is type
	 * 
	 * Representation invariant:
	 * 	type can only be "pawn1", "pawn2", "pawn3", "pawn4", "pawn5", "pawn6", "pawn7", "pawn8",
	 * 	"rook1", "rook2", "knight1", "knight2", "bishop1", "bishop2", "king", "queen" or null
	 * 	color can't be null
	 * 
	 * Safety from rep exposure:
	 * 	all representations are defined private and final
	 * 	all representations are immutable
	 * 
	 */

	// check rep
	private void checkRep() {
		Set<String> validType = new HashSet<String>(Arrays.asList("pawn1", "pawn2", "pawn3", "pawn4", "pawn5", "pawn6", "pawn7", "pawn8", "rook1", "rook2",
				"knight1", "knight2", "bishop1", "bishop2", "king", "queen"));
		assert validType.contains(this.type) || this.type == null;
		assert color != null;
	}

	/**
	 * constructor
	 * 
	 * @param type  piece's type
	 * @param color piece's color(i.e. player's name)
	 */
	public Piece(String type, String color) {
		this.type = type;
		this.color = color;
		checkRep();
	}

	/**
	 * get piece's type
	 * 
	 * @return piece's type
	 */
	public String getType() {
		checkRep();
		return type;
	}

	/**
	 * get piece's color
	 * 
	 * @return piece's color(i.e. player's name)
	 */
	public String getColor() {
		checkRep();
		return color;
	}

}
