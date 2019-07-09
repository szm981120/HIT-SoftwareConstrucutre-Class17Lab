package P3;

import java.util.List;
import java.util.Scanner;

public class MyChessAndGoGame {

	static Scanner in = new Scanner(System.in);

	/**
	 * My chess and go game.
	 * 
	 * Create a chess or go game with entering "chess" or "go", case-insensitive.
	 * There are only these two games, others will get a warning. Either game has
	 * two players, assume that players have different names.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Game game;
		String gameType = null;
		String player1 = null, player2 = null;
		System.out.println("Chess or Go?");
		gameType = in.nextLine();
		System.out.println("Please input two players' names.");
		player1 = in.nextLine();
		player2 = in.nextLine();
		if (gameType.equalsIgnoreCase("chess")) {
			game = new chessGame(player1, player2);
			newGame(game, true);
		} else if (gameType.equalsIgnoreCase("go")) {
			game = new goGame(player1, player2);
			newGame(game, false);
		} else {
			System.out.println("Sorry we don't have such game!");
		}
		in.close();
		return;
	}

	/**
	 * A new chess or go game.
	 * 
	 * Each player has 4 choices. They can input "1", "2", "3", "4" or "end". Others
	 * are invalid input and cause a warning.
	 * 
	 * "1" can access to move action. The turn won't change until a valid move is
	 * done.
	 * 
	 * "2" can access to kill action. If it's a chess game, the turn won't change
	 * until a valid kill is done. If it's a go game, the kill action can do more
	 * than once. Each time there is a request about killing one more. To kill one
	 * more, input "y", case-insensitive, else the kill action is done and the turn
	 * changes. While killing, if the opponent has no piece, the kill action also
	 * stops. Any invalid input about killing a piece will cause killing again.
	 * 
	 * "3" can access to position check. The check won't finish until the position
	 * is invalid.
	 * 
	 * "4" can access to total check. The check can only be done once.
	 * 
	 * "end" will end the game and access to history check.
	 * 
	 * Only when move and kill action are done, the turn changes. Any invalid input
	 * about choosing actions will cause a warning message. Only {@value "end"} can
	 * end the game.
	 * 
	 * @param game    a chess game
	 * @param isChess true if it's a chess game, else false if it's a go game
	 */
	public static void newGame(Game game, boolean isChess) {
		Player turn = game.getPlayer1();
		String choice = "";
		while (!choice.equalsIgnoreCase("end")) {
			Menu(turn.getName());
			choice = in.nextLine();
			switch (choice) {
			case "1":
				while (!gameAction(game, turn, true, isChess))
					;
				turn = game.getOpponent(turn.getName());
				break;
			case "2":
				String moreKill = "y";
				if (!isChess)
					System.out.println("Enter Y(y) to continue killing, else to end");
				do {
					while (!gameAction(game, turn, false, isChess))
						;
					if (!isChess)
						moreKill = in.nextLine();
				} while (!isChess && moreKill.equalsIgnoreCase("y")
						&& game.getOpponent(turn.getName()).getPieces().size() > 0);

				turn = game.getOpponent(turn.getName());
				break;
			case "3":
				System.out.println("Input the x and y of the poisition you want to check. Invalid input will end.");
				Position checkPosition = createPosition();
				while (game.checkPosition(checkPosition))
					checkPosition = createPosition();
				break;
			case "4":
				System.out.println(
						game.getPlayer1().getName() + ": " + String.valueOf(game.getPlayer1().getPieces().size()));
				System.out.println(
						game.getPlayer2().getName() + ": " + String.valueOf(game.getPlayer2().getPieces().size()));
				break;
			case "end":
				showHistory(game);
				return;
			default:
				System.out.println("Invalid input! If you want to end, input 'end'.");
				break;
			}
		}
	}

	/**
	 * gameAction will execute a move action or a kill action.
	 * 
	 * @param game   a chess or go game
	 * @param player the move or kill action executor
	 * @param move   true if the action is moving, else false if the action is
	 *               killing
	 * @param chess  true if the game is chess, else false if the game is false
	 * @return true if the action is valid, else false if the action is invalid
	 */
	public static boolean gameAction(Game game, Player player, boolean move, boolean chess) {
		Position source = null, target = null;
		if (chess) {
			System.out.println("Please input the source position.");
			source = createPosition();
		}
		System.out.println("Please input the target position.");
		target = createPosition();
		if (move)
			return game.move(player, source, target);
		else
			return game.kill(player, source, target);
	}

	/**
	 * In history check there are 4 valid choices as below.
	 * 
	 * {@value 1} can access to show player1's history.
	 * 
	 * {@value 2} can access to show player2's history.
	 * 
	 * {@value 3} can access to show both players' history.
	 * 
	 * Other input will end history check and exit.
	 * 
	 * @param game
	 */
	private static void showHistory(Game game) {
		System.out.println("1. Show " + game.getPlayer1().getName() + "'s history.");
		System.out.println("2. Show " + game.getPlayer2().getName() + "'s history.");
		System.out.println("3. Show both history.");
		System.out.println("Else to end.");
		int choice = in.nextInt();
		in.nextLine();
		List<String> player1History = game.getPlayer1().getHistory();
		List<String> player2History = game.getPlayer2().getHistory();
		int size1 = player1History.size();
		int size2 = player2History.size();
		switch (choice) {
		case 1:
			for (String history : player1History)
				System.out.println(history);
			break;
		case 2:
			for (String history : player2History)
				System.out.println(history);
			break;
		case 3:
			for (int i = 0; i < (size1 > size2 ? size2 : size1); i++) {
				System.out.println(player1History.get(i));
				System.out.println(player2History.get(i));
			}
			if ((size1 - size2) == 1)
				System.out.println(player1History.get(size1 - 1));
			if ((size2 - size1) == 1)
				System.out.println(player2History.get(size2 - 1));
			break;
		default:
			break;
		}
	}

	/**
	 * Show the menu.
	 * 
	 * @param name name of a player who is in turn
	 */
	public static void Menu(String name) {
		System.out.println("It's " + name + "'s turn.");
		System.out.println("1. Move.");
		System.out.println("2. Kill.");
		System.out.println("3. Check position.");
		System.out.println("4. Total of pieces.");
		System.out.println("Input 'end' to end.");
	}

	/**
	 * create a position
	 * 
	 * @return a position whose x-height and y-height are from I/O
	 */
	private static Position createPosition() {
		int x = in.nextInt();
		in.nextLine();
		int y = in.nextInt();
		in.nextLine();
		return new Position(x, y);
	}
}
