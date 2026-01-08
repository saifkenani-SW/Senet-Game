package logic;

import game.*;

public class Move {

    public Move() {
    }


    public State move(State current, Position position, int step) {
        if (!canMove(position, step)) {
            return null;
        }
        State state = new State(current);
        Position from = new Position(position);
        Position to = new Position(position.nextPosition(step));


        return state;
    }

    public boolean canMove(Position position, int step) {
        Position to = position.nextPosition(step);

        if (!CHECK_POINTCell(position, to)) return false;
        if (!TREECell(position, to)) return false;
        if (!TOWCell(position, to)) return false;
        return true;
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
