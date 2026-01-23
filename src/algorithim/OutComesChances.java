package algorithim;

import game.State;

import java.util.List;

public class OutComesChances {

//    State state;
    double probability;

    public int getThrowing() {
        return throwing;
    }

    int throwing;


    OutComesChances( double probability, int throwing) {
        this.probability = probability;
        this.throwing = throwing;
//        this.state = state;
    }

    public OutComesChances(){}

//    public State getState() {
//        return state;
//    }

    public double getProbability() {
        return probability;
    }

    public static final List<OutComesChances> STATES = List.of(
            new OutComesChances(0.25, 1),
            new OutComesChances(0.375, 2),
            new OutComesChances(0.25, 3),
            new OutComesChances(0.0625, 4),
            new OutComesChances(0.0625, 5)
    );

    public static List<OutComesChances> makeStates() {
        return STATES;
    }
}
