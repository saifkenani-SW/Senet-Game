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

        Position position_RETURN = new Position(2, 6);
        if (to.equals(position_RETURN)) {
            handleState(state);
            returnTo_NEW_BEGINNING(players, from);
            state.switchPlayer();
            return state;
        }


        if (to.isOut()) {
            players.remove(from);
            //handleState(state);
            state.switchPlayer();
            return state;
        }

        if (players.containsKey(to)) {
            Player other = state.otherPlayer();
            players.remove(to);
            players.put(from, other);
            if (to.getIndex() < 20) {
                players.put(to, currentPlayer);
                handleState(state);
            } else {
                handleState(state);
                players.put(to, currentPlayer);
            }
        } else {
            if (to.getIndex() < 20) {
                players.remove(from);
                players.put(to, currentPlayer);
                handleState(state);
            } else {
                players.remove(from);
                handleState(state);
                players.put(to, currentPlayer);
            }
        }
        state.switchPlayer();


        return state;
    }

    private void handleState(State current) {
        Map<Position, Player> players = current.getPlayers();
        Player currentPlayer = current.getCurrentPlayer();
        //  Position position_RETURN = new Position(2, 6);
        Position position_TREE = new Position(2, 7);
        Position position_TOW = new Position(2, 8);
        Position position_FREEDOM = new Position(2, 9);

        /*if (players.containsKey(position_RETURN)
                && !players.get(position_RETURN).equals(currentPlayer)
        ) {
            returnTo_NEW_BEGINNING(players, position_RETURN);
        }*/


        if (players.containsKey(position_TREE)
                && players.get(position_TREE).equals(currentPlayer)
        ) {
            returnTo_NEW_BEGINNING(players, position_TREE);
        }


        if (players.containsKey(position_TOW)
                && players.get(position_TOW).equals(currentPlayer)
        ) {
            returnTo_NEW_BEGINNING(players, position_TOW);
        }

        if (players.containsKey(position_FREEDOM)
                && players.get(position_FREEDOM).equals(currentPlayer)
        ) {
            returnTo_NEW_BEGINNING(players, position_FREEDOM);
        }


    }

    public void returnTo_NEW_BEGINNING(Map<Position, Player> playerMap, Position position) {
        Position newBeginningPosition = new Position(1, 5);
        Player owner = playerMap.get(position);

        while (playerMap.containsKey(newBeginningPosition)) {
            newBeginningPosition = newBeginningPosition.previosPosition();
            System.err.println("PRE POSITION :" + newBeginningPosition.getRow() + " , " + newBeginningPosition.getCol());
        }
        playerMap.remove(position);
        playerMap.put(newBeginningPosition, owner);

    }

    /*public Position getReturnTo_NEW_BEGINNING(Map<Position, Player> playerMap, Position position) {
        Position newBeginningPosition = new Position(1, 5);
        Player owner = playerMap.get(position);

        while (playerMap.containsKey(newBeginningPosition)) {
            newBeginningPosition = newBeginningPosition.previosPosition();
        }
        playerMap.remove(position);
        playerMap.put(newBeginningPosition, owner);

    }*/


    public boolean canMove(Map<Position, Player> players, Position position, int step) {
        Position to = position.nextPosition(step);
        if (hasSamePlayer(players, position, to)) return false;
        if (skip_CHECK_POINTCell(position, to)) return false;
        if (cantFrom_CHECK_POINTCell(position, to)) return false;
        if (cantFrom_TREECell(position, to)) return false;
        if (cantFrom_TOWCell(position, to)) return false;
        return true;
    }

    private boolean hasSamePlayer(Map<Position, Player> players,
                                  Position from,
                                  Position to) {
        return players.containsKey(to)
                && players.get(from) == players.get(to);
    }

    private boolean skip_CHECK_POINTCell(Position from, Position to) {
        int index = Type.CHECK_POINT.getIndex();
        /*if (from.getIndex() < index && to.getIndex() > index)
            return false;
        return true;*/
        return from.getIndex() < index && to.getIndex() > index;
    }

    private boolean cantFrom_CHECK_POINTCell(Position from, Position to) {
        int index = Type.CHECK_POINT.getIndex();
        return (from.getIndex() == index && to.getCol() > 9);
    }

    private boolean cantFrom_TREECell(Position from, Position to) {
        int index = Type.TREE.getIndex();
        /*if (from.getIndex() == index && to.getIndex() != 31)
            return false;
        return true;*/
        return (from.getIndex() == index && to.getIndex() != 31);
    }

    private boolean cantFrom_TOWCell(Position from, Position to) {
        int index = Type.TOW.getIndex();
        /*if (from.getIndex() == index && to.getIndex() != 31)
            return false;
        return true;*/
        return (from.getIndex() == index && to.getIndex() != 31);
    }


}
