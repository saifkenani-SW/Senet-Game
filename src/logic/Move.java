package logic;

import game.*;

import java.util.Map;

public class Move {

    public Move() {
    }


    public State move(State current, Position position, int step) {
        State state = new State(current);

        if (!canMove(state.getPiece(), position, step)) {
            return current;
        }
        Map<Position, Piece> players = state.getPiece();
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
            Player other = state.getOtherPlayer();
            players.remove(to);
            players.put(from, other.getPiece());
            if (to.getIndex() < 20) {
                players.put(to, currentPlayer.getPiece());
                handleState(state);
            } else {
                handleState(state);
                players.put(to, currentPlayer.getPiece());
            }
        } else {
            if (to.getIndex() < 20) {
                players.remove(from);
                players.put(to, currentPlayer.getPiece());
                handleState(state);
            } else {
                players.remove(from);
                handleState(state);
                players.put(to, currentPlayer.getPiece());
            }
        }
        state.switchPlayer();


        return state;
    }

    private void handleState(State current) {
        Map<Position, Piece> pieceMap = current.getPiece();
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


        if (pieceMap.containsKey(position_TREE)
                && pieceMap.get(position_TREE).equals(currentPlayer.getPiece())
        ) {
            returnTo_NEW_BEGINNING(pieceMap, position_TREE);
        }


        if (pieceMap.containsKey(position_TOW)
                && pieceMap.get(position_TOW).equals(currentPlayer.getPiece())
        ) {
            returnTo_NEW_BEGINNING(pieceMap, position_TOW);
        }

        if (pieceMap.containsKey(position_FREEDOM)
                && pieceMap.get(position_FREEDOM).equals(currentPlayer.getPiece())
        ) {
            returnTo_NEW_BEGINNING(pieceMap, position_FREEDOM);
        }


    }

    public void returnTo_NEW_BEGINNING(Map<Position, Piece> pieceMap, Position position) {
        Position newBeginningPosition = new Position(1, 5);
        Piece owner = pieceMap.get(position);

        while (pieceMap.containsKey(newBeginningPosition)) {
            newBeginningPosition = newBeginningPosition.previosPosition();
            System.err.println("PRE POSITION :" + newBeginningPosition.getRow() + " , " + newBeginningPosition.getCol());
        }
        pieceMap.remove(position);
        pieceMap.put(newBeginningPosition, owner);

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


    public boolean canMove(Map<Position, Piece> pieceMap, Position position, int step) {
        if (!pieceMap.containsKey(position)) return false;
        Position to = position.nextPosition(step);
        if (hasSamePiece(pieceMap, position, to)) return false;
        if (skip_CHECK_POINTCell(position, to)) return false;
        // if (cantFrom_CHECK_POINTCell(position, to)) return false;
        if (cantFrom_TREECell(position, to)) return false;
        if (cantFrom_TOWCell(position, to)) return false;
        return true;
    }

    private boolean hasSamePiece(Map<Position, Piece> pieceMap,
                                 Position from,
                                 Position to) {
        return pieceMap.containsKey(to)
                && pieceMap.get(from) == pieceMap.get(to);
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
