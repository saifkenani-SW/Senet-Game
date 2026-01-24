package algorithim;

import game.State;

import java.util.List;

public class OutComesChances {

    double probability;
    int throwing;

    OutComesChances( double probability, int throwing) {
        this.probability = probability;
        this.throwing = throwing;
    }

    public static final List<OutComesChances> Throws = List.of(
            new OutComesChances(0.25, 1),
            new OutComesChances(0.375, 2),
            new OutComesChances(0.25, 3),
            new OutComesChances(0.0625, 4),
            new OutComesChances(0.0625, 5)
    );

    public static List<OutComesChances> getThrows() {
        return Throws;
    }

    public int getThrowing() {
        return throwing;
    }

    public double getProbability() {
        return probability;
    }

}
