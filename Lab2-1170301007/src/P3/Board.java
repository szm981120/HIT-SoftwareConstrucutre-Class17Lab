package P3;

/**
 * 
 * @author Shen
 *
 */
public class Board {

	private final Piece[][] board;

	/*
	 * Abstract function
	 * 	AF(board) = a game board
	 * 
	 * Representation invariant
	 * 	board should be a 9*9 or 20*20 square
	 * 
	 * Safety from rep exposure
	 * 	the only representation is defined private and final
	 * 	the rep board is mutable so it has a defensive copy
	 * 
	 */

	// check rep
	private void checkRep() {
		assert this.board.length == this.board[1].length;
		assert this.board.length == 9 || this.board.length == 20;
	}

	/**
	 * constructor
	 * 
	 * @param size the board's size, 8 if chess, else 19 if go.
	 */
	public Board(int size) {
		board = new Piece[size + 1][size + 1];
		for (int i = 1; i <= size; i++)
			for (int j = 1; j <= size; j++)
				board[i][j] = null;
		checkRep();
	}

	/**
	 * Observer. Get the board
	 * 
	 * @return a defensive copy of the board
	 */
	public Piece[][] getBoard() {
		Piece[][] board = new Piece[this.board.length][this.board.length];
		for (int i = 1; i < board.length; i++) {
			board[i] = this.board[i].clone();
		}
		checkRep();
		return board;
	}

	/**
	 * Mutator. Set a position in the board with piece
	 * 
	 * @param position the target set position in the board
	 * @param piece    a piece set in the target position
	 */
	public void setBoard(Position position, Piece piece) {
		this.board[position.getX()][position.getY()] = piece;
		checkRep();
	}

}
