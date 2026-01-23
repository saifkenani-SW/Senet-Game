package algorithim;

import evaluation.Difficulty;
import game.State;
import game.StateEvaluation;

import java.util.List;

public class ExpectMiniMax {

    private final Difficulty difficulty;
    private final int maxDepth;
    private final boolean debug;

    public ExpectMiniMax(Difficulty d, boolean debug){
        difficulty = d;
        maxDepth = switch (d){
            case EASY -> 1;
            case NORMAL -> 2;
            case HARD -> 3;
            case EXPERT -> 4;
        };
        this.debug = debug;
    }

    public StateEvaluation maxMove(State state, int throwing) {
        return maxMove(state, throwing, maxDepth);
    }

    private StateEvaluation maxMove(State state,int throwing, int depth){

        if (state.checkWinning()){
            return new StateEvaluation(state, Integer.MAX_VALUE, throwing);
        }

        if (depth == 0){
            return new StateEvaluation(state, state.eval(difficulty, debug), throwing);
        }

        double bestExpectedEval = Double.NEGATIVE_INFINITY;
        State bestState = null;
        double expectedEvalForThisThrow = 0;
        int bestThrowing = 0;
        State bestStateForThisThrow = null;
        double bestEvalForThisThrow = Double.NEGATIVE_INFINITY;

        List<State> possibleStates = state.getNextStates(throwing);
        for (State s : possibleStates){
            List<OutComesChances> outComesChances = OutComesChances.makeStates();
            expectedEvalForThisThrow = 0;

            for (OutComesChances outComesChance : outComesChances){

                StateEvaluation boardEval = minMove(s, outComesChance.throwing, depth - 1);
                expectedEvalForThisThrow += boardEval.getEvaluation() * outComesChance.getProbability();

                if(boardEval.getEvaluation() >= bestEvalForThisThrow){
                    bestEvalForThisThrow = boardEval.getEvaluation();
                    bestStateForThisThrow = s;
                    bestThrowing = outComesChance.throwing;
                }

            }
            if (expectedEvalForThisThrow >= bestExpectedEval) {
                bestExpectedEval = expectedEvalForThisThrow;
                bestState = bestStateForThisThrow;
            }
        }
        return new StateEvaluation(bestState, bestExpectedEval, bestThrowing);
    }

    private StateEvaluation minMove(State state,int throwing, int depth){

        if (state.checkWinning()){
            return new StateEvaluation(state, Integer.MIN_VALUE, throwing);
        }

        if (depth == 0){
            return new StateEvaluation(state, state.eval(difficulty, debug), throwing);
        }
        double bestExpectedEval = Double.POSITIVE_INFINITY;
        State bestState = null;
        double expectedEvalForThisThrow = 0;
        State bestStateForThisThrow = null;
        double bestEvalForThisThrow = Double.POSITIVE_INFINITY;
        int bestThrowing = 0;

        List<State> possibleStates = state.getNextStates(throwing);
        for (State s : possibleStates){
            List<OutComesChances> outComesChances = OutComesChances.makeStates();
            expectedEvalForThisThrow = 0;

            for (OutComesChances outComesChance : outComesChances){

                StateEvaluation boardEval = maxMove(s,  outComesChance.throwing, depth - 1);
                expectedEvalForThisThrow += boardEval.getEvaluation() * outComesChance.getProbability();

                if(boardEval.getEvaluation() <= bestEvalForThisThrow){
                    bestEvalForThisThrow = boardEval.getEvaluation();
                    bestStateForThisThrow = s;
                    bestThrowing = outComesChance.throwing;

                }
            }
            if (expectedEvalForThisThrow <= bestExpectedEval) {
                bestExpectedEval = expectedEvalForThisThrow;
                bestState = bestStateForThisThrow;
            }
        }
        return new StateEvaluation(bestState, bestExpectedEval, bestThrowing);

    }

}
