package game;

import java.util.HashMap;
import java.util.Map;

public class State {
    private final Cell[][] board = new Cell[3][10];
    Map<Position, Player> players = new HashMap<>();
    Player currentPlayer;
}
