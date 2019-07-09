package P3;

/**
 * 
 * @author Shen
 *
 */
public interface Game extends Action{

	/**
	 * Check the piece in the position of board.
	 * 
	 * @param position a position to check
	 * @return true if position is valid, else false
	 */
	public boolean checkPosition(Position position);

	/**
	 * Get board
	 * 
	 * @return a copy of game's board
	 */
	public Board getBoard();

	/**
	 * Get player1
	 * 
	 * @return a copy of game's player1
	 */
	public Player getPlayer1();

	/**
	 * Get player2
	 * 
	 * @return a copy of game's player2
	 */
	public Player getPlayer2();

	/**
	 * get the opponent player
	 * 
	 * @param name a player's name, assume it should be opponent's
	 * @return a player who is opponent against the player "name", return null if no
	 *         such player
	 */
	public Player getOpponent(String name);

}
