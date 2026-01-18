package algorithim;

import game.State;
import game.StateEvaluation;

import java.util.List;

public class ExpectMiniMax {


    public StateEvaluation maxMove(State state, int depth,int bestThrowing){

        if (depth == 0){
            return new StateEvaluation(state, state.eval(state.getCurrentPlayer()), bestThrowing);

        }
        double bestExpectedEval = Double.NEGATIVE_INFINITY;
        State bestState = null;
        double expectedEvalForThisThrow = 0;
        State bestStateForThisThrow = null;
        double bestEvalForThisThrow = Double.NEGATIVE_INFINITY;

        List<State> possibleStates = state.getNextStates(bestThrowing);
        for (State s : possibleStates){
            List<OutComesChances> outComesChances = OutComesChances.makeStates(s);

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
        return new StateEvaluation(bestState, bestExpectedEval, bestThrowing);
    }



    public StateEvaluation minMove(State state, int depth,int bestThrowing){

        if (depth == 0){
            return new StateEvaluation(state, state.eval(state.getCurrentPlayer()), bestThrowing);

        }
        double bestExpectedEval = Double.POSITIVE_INFINITY;
        State bestState = null;
        double expectedEvalForThisThrow = 0;
        State bestStateForThisThrow = null;
        double bestEvalForThisThrow = Double.POSITIVE_INFINITY;

        List<State> possibleStates = state.getNextStates(bestThrowing);
        for (State s : possibleStates){
            List<OutComesChances> outComesChances = OutComesChances.makeStates(s);

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

        return new StateEvaluation(bestState, bestExpectedEval, bestThrowing);

    }



//    public StateEvaluation maxMove(State state, int depth,int bestThrowing){
//
//        if (depth == 0){
//            return new StateEvaluation(state, state.eval(state.getCurrentPlayer()), bestThrowing);
//
//        }
//
//
//        List<OutComesChances> outComesChances = OutComesChances.makeStates(state);
//        double bestExpectedEval = Double.NEGATIVE_INFINITY;
//        State bestState = null;
//
//        for (OutComesChances outComesChance : outComesChances){
//            List<State> possibleStates = state.getNextStates(outComesChance.throwing);
//
//
//            double expectedEvalForThisThrow = 0;
//            State bestStateForThisThrow = null;
//            double bestEvalForThisThrow = Double.NEGATIVE_INFINITY;
//
//
//            for (State s: possibleStates){
//                StateEvaluation boardEval = minMove(s, depth-1, outComesChance.throwing);
//                expectedEvalForThisThrow += boardEval.getEvaluation() * outComesChance.getProbability();
//
//                if(boardEval.getEvaluation() >= bestEvalForThisThrow){
//                    bestEvalForThisThrow = boardEval.getEvaluation();
//                    bestStateForThisThrow = s;
//                }
//            }
//            if (expectedEvalForThisThrow >= bestExpectedEval) {
//                bestExpectedEval = expectedEvalForThisThrow;
//                bestState = bestStateForThisThrow;
//                bestThrowing = outComesChance.throwing; // حفظ الرمية
//
//            }
//        }
//
//        return new StateEvaluation(bestState, bestExpectedEval, bestThrowing);
//
//    }
//
//    public StateEvaluation minMove(State state, int depth, int bestThrowing){
//        if (depth == 0){
//            return new StateEvaluation(state, state.eval(state.getCurrentPlayer()), bestThrowing);
//
//        }
//
//        List<OutComesChances> outComesChances = OutComesChances.makeStates(state);
//        double bestExpectedEval = Double.POSITIVE_INFINITY;
//        State bestState = null;
//        for (OutComesChances outComesChance : outComesChances){
//            List<State> possibleStates = state.getNextStates(outComesChance.throwing);
//
//
//            double expectedEvalForThisThrow = 0;
//            State bestStateForThisThrow = null;
//            double bestEvalForThisThrow = Double.POSITIVE_INFINITY;
//
//            for (State s: possibleStates){
//                StateEvaluation boardEval = maxMove(s, depth-1,outComesChance.throwing);
//                expectedEvalForThisThrow += boardEval.getEvaluation() * outComesChance.getProbability();
//                if(boardEval.getEvaluation() <= bestEvalForThisThrow){
//                    bestEvalForThisThrow = boardEval.getEvaluation();
//                    bestStateForThisThrow = s;
//                }
//            }
//            if (expectedEvalForThisThrow <= bestExpectedEval) {
//                bestExpectedEval = expectedEvalForThisThrow;
//                bestState = bestStateForThisThrow;
//                bestThrowing = outComesChance.throwing; // حفظ الرمية
//
//            }
//        }
//
//        return new StateEvaluation(bestState, bestExpectedEval, bestThrowing);
//
//    }
//


}
