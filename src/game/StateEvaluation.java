package game;

public class StateEvaluation {
    State state;
    double evaluation;
    int throwing; // رمية النقلة التي أدت لهذه الحالة


    public StateEvaluation(State state, double evaluation, int throwing) {
        this.state = state;
        this.evaluation = evaluation;
        this.throwing = throwing;

    }

    public State getState() {
        return state;
    }
    public int getThrowing() { return throwing; }

    public void setState(State Board) {
        this.state = Board;
    }

    public double getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(double eval) {
        this.evaluation = eval;
    }
}
