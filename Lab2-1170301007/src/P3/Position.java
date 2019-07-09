package P3;

/**
 * 
 * @author Shen
 *
 */
public class Position {

	private final int x, y; // x-height and y-height

	/*
	 * Abstract function:
	 * 	AF(x,y) = a coordinate with position (x,y)
	 * 
	 * Representation invariant:
	 * 	x and y should be non-negative integer
	 * 
	 * Safety from rep exposure:
	 * 	All representations are defined private and final
	 * 	All representations are immutable
	 */
	
	// checkRep
	private void checkRep() {
		assert x >= 0 && y >= 0;
	}
	/**
	 * Constructor
	 * 
	 * @param x x-height
	 * @param y y-height
	 */
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
		checkRep();
	}

	@Override
	public String toString() {
		return "(" + String.valueOf(this.x) + "," + String.valueOf(this.y) + ")";
	}

	/**
	 * Observer
	 * 
	 * get x-height of the position
	 * 
	 * @return x-height
	 */
	public int getX() {
		int x = this.x;
		checkRep();
		return x;
	}

	/**
	 * Observer
	 * 
	 * get y-height of the position
	 * 
	 * @return y-height
	 */
	public int getY() {
		int y = this.y;
		checkRep();
		return y;
	}

}
