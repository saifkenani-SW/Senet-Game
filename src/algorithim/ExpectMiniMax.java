package algorithim;

import evaluation.Difficulty;
import game.State;
import game.StateEvaluation;
import logic.Move;

import java.util.List;

public class ExpectMiniMax {

    private final Difficulty difficulty;
    public ExpectMiniMax(Difficulty d){
        difficulty = d;
    }

    public StateEvaluation maxMove(State state, int depth,int throwing){

        if (depth == 0){
            return new StateEvaluation(state, state.eval(difficulty), throwing);
        }

        double bestExpectedEval = Double.NEGATIVE_INFINITY;
        State bestState = null;
        double expectedEvalForThisThrow = 0;
        State bestStateForThisThrow = null;
        double bestEvalForThisThrow = Double.NEGATIVE_INFINITY;

        List<State> possibleStates = state.getNextStates(throwing);
        for (State s : possibleStates){
            List<OutComesChances> outComesChances = OutComesChances.makeStates();
            expectedEvalForThisThrow = 0; // ← هنا


            for (OutComesChances outComesChance : outComesChances){

                StateEvaluation boardEval = minMove(s, depth-1, outComesChance.throwing);
                expectedEvalForThisThrow += boardEval.getEvaluation() * outComesChance.getProbability();

                if(boardEval.getEvaluation() >= bestEvalForThisThrow){
                    bestEvalForThisThrow = boardEval.getEvaluation();
                    bestStateForThisThrow = s;
                }

            }
            if (expectedEvalForThisThrow >= bestExpectedEval) {
                bestExpectedEval = expectedEvalForThisThrow;
                bestState = bestStateForThisThrow;
//                    bestThrowing = outComesChance.throwing; // حفظ الرمية
            }
        }
        return new StateEvaluation(bestState, bestExpectedEval, throwing);
    }



    public StateEvaluation minMove(State state, int depth,int throwing){



        if (depth == 0){
            return new StateEvaluation(state, state.eval(difficulty), throwing);

        }
        double bestExpectedEval = Double.POSITIVE_INFINITY;
        State bestState = null;
        double expectedEvalForThisThrow = 0;
        State bestStateForThisThrow = null;
        double bestEvalForThisThrow = Double.POSITIVE_INFINITY;

        List<State> possibleStates = state.getNextStates(throwing);
        for (State s : possibleStates){
            List<OutComesChances> outComesChances = OutComesChances.makeStates();
            expectedEvalForThisThrow = 0; // ← هنا


            for (OutComesChances outComesChance : outComesChances){

                StateEvaluation boardEval = maxMove(s, depth-1, outComesChance.throwing);
                expectedEvalForThisThrow += boardEval.getEvaluation() * outComesChance.getProbability();

                if(boardEval.getEvaluation() <= bestEvalForThisThrow){
                    bestEvalForThisThrow = boardEval.getEvaluation();
                    bestStateForThisThrow = s;
                }


            }
            if (expectedEvalForThisThrow <= bestExpectedEval) {
                bestExpectedEval = expectedEvalForThisThrow;
                bestState = bestStateForThisThrow;
//                bestThrowing = outComesChance.throwing; // حفظ الرمية

            }

        }

        return new StateEvaluation(bestState, bestExpectedEval, throwing);

    }

}
