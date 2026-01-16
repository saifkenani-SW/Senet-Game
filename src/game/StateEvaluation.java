package game;

public class StateEvaluation {
    State state;
    int evaluation;

    public StateEvaluation(State state, int evaluation) {
        this.state = state;
        this.evaluation = evaluation;
    }

    public State getState() {
        return state;
    }

    public void setState(State Board) {
        this.state = Board;
    }

    public int getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(int eval) {
        this.evaluation = eval;
    }
}
