package game;

import logic.Move;
import logic.Throwing;

import java.util.*;

public class State {
    private static final Cell[][] board = Board.getInstance().getCells();
    private Map<Position, Player> players = new HashMap<>();
    private Player currentPlayer;
    // private int throwingResult;


    public Map<Position, Player> getPlayers() {
        return players;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

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

    public State(State state) {
        this.currentPlayer = state.currentPlayer;
        //   this.throwingResult = state.throwingResult;
        this.players = new HashMap<>(state.players);
    }

    public void switchPlayer() {
        currentPlayer = (currentPlayer == Player.CPU)
                ? Player.HUMAN : Player.CPU;
        /*currentPlayer = (currentPlayer == Player.PLAYER1)
                ? Player.PLAYER2 : Player.PLAYER1;*/

    }

    public Player otherPlayer() {
        return (currentPlayer == Player.CPU)
                ? Player.HUMAN : Player.CPU;
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

    public List<Position> getCurrentPlayerPieces() {
        List<Position> piecePositions = new ArrayList<>();

        for (Map.Entry<Position, Player> entry : players.entrySet()) {
            if (entry.getValue() == currentPlayer) {
                piecePositions.add(entry.getKey());
            }
        }
        return piecePositions;
    }

    public List<State> getNextStates(int step) {
        List<State> nextStates = new ArrayList<>();
        Move move = new Move();
        for (Map.Entry<Position, Player> entry : players.entrySet()) {
            if (entry.getValue() == currentPlayer) {
                Position position = entry.getKey();
                if (move.canMove(getPlayers(), entry.getKey(), step)) {
                    nextStates.add(move.move(this, position, step));
                }
            }
        }
        return nextStates;
    }

    public int getSizeofNextStates(int step) {
        int size = 0;
        Move move = new Move();
        for (Map.Entry<Position, Player> entry : players.entrySet()) {
            if (entry.getValue() == currentPlayer) {
                if (move.canMove(getPlayers(), entry.getKey(), step)) {
                    size++;
                }
            }
        }
        return size;
    }


   /* public int getThrowingResult() {
       return throwingResult;
    }*/

   /* public void setThrowingResult() {
        this.throwingResult = Throwing.getInstance().getResult();
    }*/

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("\n==================== SENET GAME ====================\n\n");
        sb.append("Current Player : ").append(currentPlayer).append("\n");
        sb.append("CPU Pieces     : ").append(countPieces(Player.CPU)).append("\n");
        sb.append("HUMAN Pieces   : ").append(countPieces(Player.HUMAN)).append("\n\n");


        sb.append(renderBoard());

/*        sb.append("\nLegend:\n");
        sb.append("- CPU / HUMAN : Player pieces\n");
        sb.append("- BIRTH       : New Beginning (15)\n");
        sb.append("- CHECK       : Check Point (25)\n");
        sb.append("- WATER       : Return (26)\n");
        sb.append("- TREE        : Tree / Truth\n");
        sb.append("- FREE        : Freedom (Last cell)\n");
        sb.append("- .           : Empty normal cell\n");

        sb.append("===================================================\n");
*/

        return sb.toString();
    }

    private String renderBoard() {
        int rows = 3;
        int cols = 10;
        int cellWidth = 7;

        StringBuilder sb = new StringBuilder();
        String line = "-".repeat((cellWidth + 3) * cols) + "\n";

        for (int i = 0; i < rows; i++) {
            sb.append(line);
            for (int j = 0; j < cols; j++) {
                sb.append("| ");
                sb.append(String.format("%-" + cellWidth + "s", getCellLabel(i, j)));
                sb.append(" ");
            }
            sb.append("|\n");
        }
        sb.append(line);

        return sb.toString();
    }

    private String getCellLabel(int row, int col) {
        Position pos = new Position(row, col);

        // قطعة لاعب
        if (players.containsKey(pos)) {
            return players.get(pos).toString(); // CPU / HUMAN
        }

        // نوع الخلية
        return switch (board[row][col].getType()) {
            case NORMAL -> ".";
            case NEW_BEGINNING -> "BIRTH";
            case CHECK_POINT -> "CHECK";
            case RETURN -> "WATER";
            case TREE -> "TREE";
            case TOW -> "TOW";
            case FREEDOM -> "FREE";
        };
    }

    private int countPieces(Player player) {
        int count = 0;
        for (Player p : players.values()) {
            if (p == player) count++;
        }
        return count;
    }


    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        State state = (State) object;
        return currentPlayer == state.currentPlayer
                && players.equals(state.players);
    }

    @Override
    public int hashCode() {
        int result = currentPlayer.hashCode();
        result = 31 * result + players.hashCode();
        return result;
    }


}
