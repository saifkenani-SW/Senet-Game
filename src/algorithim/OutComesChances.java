package algorithim;

import game.State;

import java.util.List;

class OutComesChances {

    State state;
    double probability;
    int throwing;


    OutComesChances(State state, double probability, int throwing) {
        this.probability = probability;
        this.throwing = throwing;
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public double getProbability() {
        return probability;
    }

    public static List<OutComesChances> makeStates(State state) {
        return List.of(
                new OutComesChances(state, 0.25, 1),
                new OutComesChances(state, 0.375, 2),
                new OutComesChances(state, 0.25, 3),
                new OutComesChances(state, 0.0625, 4),
                new OutComesChances(state, 0.0625, 5)
        );

    }
}
