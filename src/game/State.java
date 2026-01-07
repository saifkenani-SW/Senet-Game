package game;

import java.util.HashMap;
import java.util.Map;

public class State {
    private final Cell[][] board = Board.getInstance().getCells();
    Map<Position, Player> players = new HashMap<>();
    Player currentPlayer;

    public State() {
        currentPlayer = Player.CPU;
        Position position = new Position(0, -1);
        int numberPieces = 14;
        for (int i = 0; i < numberPieces; i++) {
            position = position.nextPosition(1);
            players.put(position, currentPlayer);
            switchPlayer();
        }
    }

    public void switchPlayer() {
        currentPlayer = (currentPlayer == Player.CPU)
                ? Player.HUMAN : Player.CPU;
        /*currentPlayer = (currentPlayer == Player.PLAYER1)
                ? Player.PLAYER2 : Player.PLAYER1;*/

    }

    public boolean checkWinning() {
        for (Player player : players.values()) {
            if (player == currentPlayer) {
                return false;
            }
        }
        return true;
    }


    public Player getWinner() {
        if (checkWinningForPlayer(Player.CPU)) {
            return Player.CPU;
        }
        if (checkWinningForPlayer(Player.HUMAN)) {
            return Player.HUMAN;
        }
        return null;
    }

    private boolean checkWinningForPlayer(Player player) {
        for (Player p : players.values()) {
            if (p == player) {
                return false;
            }
        }
        return true;
    }


}
