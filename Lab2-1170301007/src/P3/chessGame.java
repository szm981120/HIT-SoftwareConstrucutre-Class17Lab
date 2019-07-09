package P3;

/**
 * 
 * @author Shen
 *
 */
public class chessGame implements Game {

	private final Board board = new Board(8); // chess board
	private final Player player1, player2; // two players

	/*
	 * Abstract function:
	 * 	AF(board, player1, player2) = a chess game with two players who are player1 and player2 and a chess board
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
	 * constructor
	 * 
	 * @param player1 player1's name
	 * @param player2 player2's name
	 */
	public chessGame(String player1, String player2){
		if(player1.equalsIgnoreCase(player2)) {
			System.out.println("Two names should be different!");
			System.exit(-1);
		}
			
		this.player1 = new Player(player1);
		this.player2 = new Player(player2);
		setPieces();
		checkRep();
	}

	@Override
	public boolean checkPosition(Position position) {
		/* check whether position is out of board */
		if (outOfBoardWarning("This position is out of board!", position)) {
			checkRep();
			return false;
		}
		Piece piece = this.board.getBoard()[position.getX()][position.getY()];
		/* check whether there's an existed piece */
		if (piece == null)
			System.out.println("FREE");
		else {
			System.out.println("OCCUPIED by " + piece.getColor() + "'s " + piece.getType());
		}
		checkRep();
		return true;
	}

	@Override
	public Board getBoard() {
		Board board = new Board(8);
		for (int i = 1; i <= 8; i++) {
			for (int j = 1; j <= 8; j++) {
				board.setBoard(new Position(i, j), this.board.getBoard()[i][j]);
			}
		}
		checkRep();
		return board;
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
		return player1;
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
		return player2;
	}

	@Override
	public boolean move(Player player, Position source, Position target) {
		return commonPartBetweenMoveAndKill(true, player, source, target);
	}

	@Override
	public boolean kill(Player player, Position source, Position target) {
		return commonPartBetweenMoveAndKill(false, player, source, target);
	}

	@Override
	public Player getOpponent(String name) {
		if (name.equals(this.player1.getName())) {
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

		if (name.equals(this.player2.getName())) {
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
		checkRep();
		return null;
	}

	/**
	 * common part between move and kill. move or kill the target with source.
	 * 
	 * @param moveFlag true if move, else false
	 * @param player   the move executor
	 * @param source   the position of the moved or killing piece
	 * @param target   the target position of the moved or killed piece
	 * @return true if action is done, else false
	 */
	private boolean commonPartBetweenMoveAndKill(Boolean moveFlag, Player executor, Position source, Position target) {
		Player player = this.player1.getName().equalsIgnoreCase(executor.getName()) ? this.player1 : this.player2;
		Player opponent = this.player1.getName().equalsIgnoreCase(executor.getName()) ? this.player2 : this.player1;
		/* check whether source position is out of board */
		if (outOfBoardWarning("The source place is out of the board!", source)) {
			checkRep();
			return false;
		}
		/* check whether target position is out of board */
		if (outOfBoardWarning("The target place is out of the board!", target)) {
			checkRep();
			return false;
		}
		/* check whether the source position is the same as the target */
		if (source.getX() == target.getX() && source.getY() == target.getY()) {
			System.out.println("Two same position!");
			checkRep();
			return false;
		}
		Piece movingPiece = this.board.getBoard()[source.getX()][source.getY()];
		/* check whether there is a piece in the source position */
		if (movingPiece == null) {
			System.out.println("No piece in the source place!");
			checkRep();
			return false;
		}
		/* check whether the piece in the source position is dead or the opponent's */
		if (!player.getPieces().contains(movingPiece)) {
			System.out.println("A dead piece or not your piece!");
			checkRep();
			return false;
		}
		Piece targetPiece = this.board.getBoard()[target.getX()][target.getY()];
		/* check whether an own piece is in the target position */
		if (targetPiece != null && player.getPieces().contains(targetPiece)) {
			System.out.println("Your piece in the target place!");
			checkRep();
			return false;
		}
		/* MOVE: check whether a piece is in the target position */
		else if (targetPiece != null && moveFlag) {
			System.out.println("There is a " + targetPiece.getColor() + "'s piece in the target place!");
			checkRep();
			return false;
		}
		/* KILL: a valid kill */
		else if (targetPiece != null) {
			String message = player.getName() + " kills "
					+ this.getBoard().getBoard()[target.getX()][target.getY()].getType() + " with "
					+ this.getBoard().getBoard()[source.getX()][source.getY()].getType() + " from " + source.toString()
					+ " to " + target.toString() + ".";
			player.addHistory(message);
			System.out.println(targetPiece.getColor() + "'s " + targetPiece.getType() + " is killed.");
			opponent.removePiece(targetPiece);
			;
			this.board.setBoard(source, null);
			this.board.setBoard(target, movingPiece);
		}
		/* KILL check whether no piece is in the target position */
		else if (!moveFlag) {
			System.out.println("No piece to kill!");
			checkRep();
			return false;
		} else {
			String message = player.getName() + " moves "
					+ this.getBoard().getBoard()[source.getX()][source.getY()].getType() + " from " + source.toString()
					+ " to " + target.toString() + ".";
			player.addHistory(message);
			this.board.setBoard(source, null);
			this.board.setBoard(target, movingPiece);
		}
		checkRep();
		return true;

	}

	/**
	 * check whether the position is out of board
	 * 
	 * @param warning  a message warning out of board
	 * @param position the position to check
	 * @return true if the position is out of board, else false
	 */
	private boolean outOfBoardWarning(String warning, Position position) {
		if (position.getX() < 1 || position.getX() > 8 || position.getY() < 1 || position.getY() > 8) {
			System.out.println(warning);
			checkRep();
			return true;
		}
		checkRep();
		return false;
	}

	/**
	 * initialize pieces on the chess board
	 */
	private void setPieces() {
		String player1Name = this.player1.getName();
		String[] pieces = { "rook1", "knight1", "bishop1", "king", "queen", "bishop2", "knight2", "rook2", "pawn1",
				"pawn2", "pawn3", "pawn4", "pawn5", "pawn6", "pawn7", "pawn8" };
		for (int j = 1; j <= 2; j++) {
			for (int i = 1; i <= 8; i++) {
				Piece piece = new Piece(pieces[(i - 1) + (j - 1) * 8], player1Name);
				this.player1.addPiece(piece);
				this.board.setBoard(new Position(i, j), piece);
			}
		}
		String player2Name = this.player2.getName();
		for (int j = 8; j >= 7; j--) {
			for (int i = 1; i <= 8; i++) {
				Piece piece = new Piece(pieces[(i - 1) + (8 - j) * 8], player2Name);
				this.player2.addPiece(piece);
				this.board.setBoard(new Position(i, j), piece);
			}
		}
		checkRep();
	}

}
