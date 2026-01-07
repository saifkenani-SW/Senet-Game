package game;

import java.util.HashMap;
import java.util.Map;

public class State {
    private final Cell[][] board = Board.getInstance().getCells();
    Map<Position, Player> players = new HashMap<>();
    Player currentPlayer;
}
