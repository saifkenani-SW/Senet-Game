package logic;

import game.*;

import java.util.Map;

public class Move {

    public Move() {
    }


    public State move(State current, Position position, int step) {
        State state = new State(current);

        if (!canMove(state.getPlayers(), position, step)) {
            return current;
        }
        Map<Position, Player> players = state.getPlayers();
        Player currentPlayer = state.getCurrentPlayer();
        Position from = new Position(position);
        Position to = new Position(position.nextPosition(step));

        players.remove(from);
        if (to.isOut()) {
            state.switchPlayer();
            return state;
        }

        if (players.containsKey(to)) {
            Player other = players.get(to);
            players.put(from, players.get(to));
        }

        players.put(to, currentPlayer);
        state.switchPlayer();


        return state;
    }

    public boolean canMove(Map<Position, Player> players, Position position, int step) {
        Position to = position.nextPosition(step);
        if (hasSamePlayer(players, position, to)) return false;
        if (!CHECK_POINTCell(position, to)) return false;
        if (!TREECell(position, to)) return false;
        if (!TOWCell(position, to)) return false;
        return true;
    }

    private boolean hasSamePlayer(Map<Position, Player> players,
                                  Position from,
                                  Position to) {
        return players.containsKey(to)
                && players.get(from) == players.get(to);
    }

    private boolean CHECK_POINTCell(Position from, Position to) {
        int index = Type.CHECK_POINT.getIndex();
        /*if (from.getIndex() < index && to.getIndex() > index)
            return false;
        return true;*/
        return !(from.getIndex() < index && to.getIndex() > index);
    }

    private boolean TREECell(Position from, Position to) {
        int index = Type.TREE.getIndex();
        /*if (from.getIndex() == index && to.getIndex() != 31)
            return false;
        return true;*/
        return !(from.getIndex() == index && to.getIndex() != 31);
    }

    private boolean TOWCell(Position from, Position to) {
        int index = Type.TOW.getIndex();
        /*if (from.getIndex() == index && to.getIndex() != 31)
            return false;
        return true;*/
        return !(from.getIndex() == index && to.getIndex() != 31);
    }


}
