package P3;

/**
 * 
 * @author Shen
 *
 */
public interface Action {

	/**
	 * Chess: move a piece from the source position to the target position, the
	 * executor is player
	 * 
	 * Go: move a piece to the target position, assume the source is null
	 * 
	 * @param player executor of moving
	 * @param source the moving piece's source position, assume null if it's a go
	 *               move
	 * @param target the moving piece's target position
	 * @return true if move is done, else false
	 */
	public boolean move(Player player, Position source, Position target);

	/**
	 * Chess: kill a piece in the target position with the piece in the source
	 * position, the executor is player
	 * 
	 * Go: kill a piece in the target position, assume the source is null
	 * 
	 * @param player executor of killing
	 * @param source the killing piece's position, assume null if it's a go
	 * @param target the killed piece's position
	 * @return true if kill is done, else false
	 */
	public boolean kill(Player player, Position source, Position target);

}
