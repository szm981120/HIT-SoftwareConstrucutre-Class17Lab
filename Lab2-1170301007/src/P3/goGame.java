package P3;

/**
 * 
 * @author Shen
 *
 */
public class goGame implements Game {

	private final Board board = new Board(19); // go board
	private final Player player1, player2; // two players

	/*
	 * Abstract function:
	 * 	AF(board, player1, player2) = a go game with two players who are player1 and player2 and a go board
	 * 
	 * Representation invariant:
	 * 	player1 and player2 shouldn't have the same name case-insensitive
	 * 
	 * Safety from rep exposure:
	 * 	all representations are defined private and final
	 * 	all mutable representations observers have defensive copies
	 * 
	 */

	// check rep
	private void checkRep() {
		assert !this.player1.getName().equalsIgnoreCase(this.player2.getName());
	}

	/**
	 * constructor, assume the two names are different
	 * 
	 * @param player1 player1's name
	 * @param player2 player2's name
	 */
	public goGame(String player1, String player2) {
		if(player1.equalsIgnoreCase(player2)) {
			System.out.println("Two names should be different!");
			System.exit(-1);
		}
		this.player1 = new Player(player1);
		this.player2 = new Player(player2);
		checkRep();
	}

	@Override
	public boolean checkPosition(Position position) {
		if (position.getX() < 1 || position.getX() > 19 || position.getY() < 1 || position.getY() > 19) {
			System.out.println("This position is out of the board!");
			checkRep();
			return false;
		}
		Piece piece = this.board.getBoard()[position.getX()][position.getY()];
		if (piece == null)
			System.out.println("FREE");
		else {
			System.out.println("OCCUPIED by " + piece.getColor());
		}
		checkRep();
		return true;
	}

	@Override
	public Board getBoard() {
		Board board = new Board(19);
		for (int i = 1; i <= 19; i++) {
			for (int j = 1; j <= 19; j++) {
				board.setBoard(new Position(i, j), this.board.getBoard()[i][j]);
			}
		}
		checkRep();
		return this.board;
	}

	@Override
	public Player getPlayer1() {
		Player copyplayer1 = new Player(this.player1.getName());
		for (Piece piece : this.player1.getPieces()) {
			copyplayer1.addPiece(piece);
		}
		for (String history : this.player1.getHistory()) {
			copyplayer1.addHistory(history);
		}
		checkRep();
		return this.player1;
	}

	@Override
	public Player getPlayer2() {
		Player copyplayer2 = new Player(this.player2.getName());
		for (Piece piece : this.player2.getPieces()) {
			copyplayer2.addPiece(piece);
		}
		for (String history : this.player2.getHistory()) {
			copyplayer2.addHistory(history);
		}
		checkRep();
		return this.player2;
	}

	@Override
	public boolean move(Player executor, Position source, Position target) {
		Player player = this.player1.getName().equalsIgnoreCase(executor.getName()) ? this.player1 : this.player2;
		/* check whether the target position is out of board */
		if (outOfBoardWarning(target)) {
			checkRep();
			return false;
		}
		/* check whether there's an existed piece */
		if (this.board.getBoard()[target.getX()][target.getY()] != null) {
			System.out.println("A piece is on the target place now!");
			checkRep();
			return false;
		}
		String message = player.getName() + " moves at " + target.toString() + ".";
		player.addHistory(message);
		Piece piece = new Piece(null, player.getName());
		player.addPiece(piece);
		this.board.setBoard(target, piece);
		checkRep();
		return true;
	}

	@Override
	public boolean kill(Player executor, Position source, Position target) {
		Player player = this.player1.getName().equalsIgnoreCase(executor.getName()) ? this.player1 : this.player2;
		Player opponent = this.player1.getName().equalsIgnoreCase(executor.getName()) ? this.player2 : this.player1;
		/* check whether the target position is out of board */
		if (outOfBoardWarning(target)) {
			checkRep();
			return false;
		}
		/* check whether there's no piece in the target position */
		if (this.board.getBoard()[target.getX()][target.getY()] == null) {
			System.out.println("No piece is on the target place now!");
			checkRep();
			return false;
		}
		Piece piece = this.getBoard().getBoard()[target.getX()][target.getY()];
		/* check whether the piece in the target position is own */
		if (player.getPieces().contains(piece)) {
			System.out.println("You can't kill your own piece!");
			checkRep();
			return false;
		}
		String message = player.getName() + " kills " + opponent.getName() + " at " + target.toString() + ".";
		player.addHistory(message);
		/* remove the target piece from opponent's pieces */
		opponent.removePiece(piece);
		this.board.setBoard(target, null);
		checkRep();
		return true;
	}

	@Override
	public Player getOpponent(String name) {
		if (name.equals(this.player1.getName())) {
			checkRep();
			return this.player2;
		}
		if (name.equals(this.player2.getName())) {
			checkRep();
			return this.player1;
		}
		checkRep();
		return null;
	}

	/**
	 * check whether the position is out of board
	 * 
	 * @param position position the position to check
	 * @return true if the position is out of board, else false
	 */
	private boolean outOfBoardWarning(Position position) {
		if (position.getX() < 1 || position.getX() > 19 || position.getY() < 1 || position.getY() > 19) {
			System.out.println("The target place is out of the board!");
			checkRep();
			return true;
		}
		checkRep();
		return false;
	}

}
