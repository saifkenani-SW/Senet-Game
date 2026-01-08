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

        return true;
    }



}
