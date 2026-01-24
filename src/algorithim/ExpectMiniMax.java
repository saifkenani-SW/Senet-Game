package algorithim;

import evaluation.Difficulty;
import evaluation.EvaluationEngine;
import game.State;
import game.StateEvaluation;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ExpectMiniMax {

    private HashMap<Integer, StateEvaluation> transpositionTable = new HashMap<>();
//    private HashMap<Integer, StateEvaluation> tempTranspositionTable = new HashMap<>();



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
        return maxMove(state, throwing, maxDepth, 0);
    }



//    private StateEvaluation maxMove(State state, int throwing, int depth, int indent) {
//        // 1. استخدام مفتاح أفضل
//        int key = Objects.hash(state, throwing, depth);
//
//        if (transpositionTable.containsKey(key)) {
//            if (debug)
//                System.out.println("]");
//            return transpositionTable.get(key);
//        }
//
//        if (state.checkWinning()) {
//            return new StateEvaluation(state, Integer.MAX_VALUE);
//        }
//
//        if (depth == 0 && tempTranspositionTable.containsKey(key)){
//            if (debug)
//                System.out.println("]");
//            return tempTranspositionTable.get(key);
//
//        }
//
//        if (depth == 0) {
//            EvaluationEngine engine = new EvaluationEngine(state, difficulty);
//            double eval = engine.eval();
//            StateEvaluation result = new StateEvaluation(state, eval);
//
//            tempTranspositionTable.put(key, result);
//            if (debug){
//                System.out.println();
//                for (int i = 0 ; i < indent  ; i++){
//                    System.out.print("\t");
//                }
//                System.out.print("Depth: " + depth + " | Node: Min | Eval: " + eval);
//
//                System.out.println(" | State:\n" + state.renderBoard());
//                for (int i = 0 ; i < indent - 1 ; i++){
//                    System.out.print("\t");
//                }
//                System.out.println("]");
//            }
//            return result;
//        }
//
//        double bestExpectedEval = Double.NEGATIVE_INFINITY;
//        State bestState = null;
//
//        List<State> possibleStates = state.getNextStates(throwing);
//
//
//        for (State s : possibleStates) {
//            if (debug) {
//                System.out.println();
//                for (int i = 0; i < indent; i++) {
//                    System.out.print("\t");
//                }
//                System.out.println("Max | These are all the throws for this state : " + s.hashCode() + " { ");
//            }
//            double expectedEvalForThisThrow = 0;
//            List<OutComesChances> nextThrows = OutComesChances.getThrows(); // يفضل جعلها static
//
//            for (OutComesChances oc : nextThrows) {
//                if (debug) {
//                    for (int i = 0; i < indent + 1; i++) {
//                        System.out.print("\t");
//                    }
//                    System.out.print("The Throw " + oc.throwing + " of this state : [");
//                }
//                StateEvaluation res = minMove(s, oc.throwing, depth - 1, indent + 2);
//                expectedEvalForThisThrow += res.getEvaluation() * oc.getProbability();
//            }
//            if (debug) {
//                for (int i = 0; i < indent; i++) {
//                    System.out.print("\t");
//                }
//                System.out.println("} Total expected evaluation for this throw = " + expectedEvalForThisThrow + "\n");
//            }
//            if (expectedEvalForThisThrow >= bestExpectedEval) {
//                bestExpectedEval = expectedEvalForThisThrow;
//                bestState = s;
//            }
//        }
//
//        StateEvaluation result = new StateEvaluation(bestState, bestExpectedEval);
//        transpositionTable.put(key, result);
//        return result;
//    }
//
//    private StateEvaluation minMove(State state, int throwing, int depth, int indent) {
//        int key = Objects.hash(state, throwing, depth);
//
//        if (transpositionTable.containsKey(key)) {
//            if (debug)
//                System.out.println("]");
//            return transpositionTable.get(key);
//        }
//
//        if (state.checkWinning()) {
//            return new StateEvaluation(state, Integer.MIN_VALUE);
//        }
//
//        if (depth == 0 && tempTranspositionTable.containsKey(key)){
//            if (debug)
//                System.out.println("]");
//            return tempTranspositionTable.get(key);
//
//        }
//
//        if (depth == 0) {
//            EvaluationEngine engine = new EvaluationEngine(state, difficulty);
//            double eval = engine.eval();
//            StateEvaluation result = new StateEvaluation(state, eval);
//
//            tempTranspositionTable.put(key, result);
//            if (debug){
//                System.out.println();
//                for (int i = 0 ; i < indent  ; i++){
//                    System.out.print("\t");
//                }
//                System.out.print("Depth: " + depth + " | Node: Min | Eval: " + eval);
//
//                System.out.println(" | State:\n" + state.renderBoard());
//                for (int i = 0 ; i < indent - 1 ; i++){
//                    System.out.print("\t");
//                }
//                System.out.println("]");
//            }
//            return result;
//        }
//
//        double bestExpectedEval = Double.POSITIVE_INFINITY;
//        State bestState = null;
//
//        List<State> possibleStates = state.getNextStates(throwing);
//
//        for (State s : possibleStates) {
//
//            if (debug) {
//                System.out.println();
//                for (int i = 0; i < indent; i++) {
//                    System.out.print("\t");
//                }
//                System.out.println("Min | These are all the throws for this state : " + s.hashCode() + " { ");
//            }
//            double expectedEvalForThisThrow = 0;
//            List<OutComesChances> nextThrows = OutComesChances.getThrows(); // يفضل جعلها static
//
//            for (OutComesChances oc : nextThrows) {
//                if (debug) {
//                    for (int i = 0; i < indent + 1; i++) {
//                        System.out.print("\t");
//                    }
//                    System.out.print("The Throw " + oc.throwing + " of this state : [");
//                }
//                StateEvaluation res = maxMove(s, oc.throwing, depth - 1, indent + 2);
//                expectedEvalForThisThrow += res.getEvaluation() * oc.getProbability();
//            }
//
//            if (debug) {
//                for (int i = 0; i < indent; i++) {
//                    System.out.print("\t");
//                }
//                System.out.println("} Total expected evaluation for this throw = " + expectedEvalForThisThrow + "\n");
//            }
//            if (expectedEvalForThisThrow <= bestExpectedEval) {
//                bestExpectedEval = expectedEvalForThisThrow;
//                bestState = s;
//            }
//        }
//
//        StateEvaluation result = new StateEvaluation(bestState, bestExpectedEval);
//        transpositionTable.put(key, result);
//        return result;
//    }


    private StateEvaluation maxMove(State state, int throwing, int depth, int indent) {

        int key = state.hashCode();

        if (state.checkWinning()) {
            return new StateEvaluation(state, Integer.MAX_VALUE);
        }

        if (depth == 0 && transpositionTable.containsKey(key)){
            if (debug)
                System.out.println("]");
            return transpositionTable.get(key);

        }

        if (depth == 0) {
            EvaluationEngine engine = new EvaluationEngine(state, difficulty);
            double eval = engine.eval();
            StateEvaluation result = new StateEvaluation(state, eval);

            transpositionTable.put(key, result);
            if (debug){
                System.out.println();
                for (int i = 0 ; i < indent  ; i++){
                    System.out.print("\t");
                }
                System.out.print("Depth: " + depth + " | Node: Min | Eval: " + eval + " | ");
                engine.printResults(indent + 1);

                System.out.println(" | State:\n" + state.renderBoard());
                for (int i = 0 ; i < indent - 1 ; i++){
                    System.out.print("\t");
                }
                System.out.println("]");
            }
            return result;
        }

        double bestExpectedEval = Double.NEGATIVE_INFINITY;
        State bestState = null;

        List<State> possibleStates = state.getNextStates(throwing);


        for (State s : possibleStates) {
            if (debug) {
                System.out.println();
                for (int i = 0; i < indent; i++) {
                    System.out.print("\t");
                }
                System.out.println("Max | These are all the throws for this state : " + s.hashCode() + " { ");
            }
            double expectedEvalForThisThrow = 0;
            List<OutComesChances> nextThrows = OutComesChances.getThrows(); // يفضل جعلها static

            for (OutComesChances oc : nextThrows) {
                if (debug) {
                    for (int i = 0; i < indent + 1; i++) {
                        System.out.print("\t");
                    }
                    System.out.print("The Throw " + oc.throwing + " of this state : [");
                }
                StateEvaluation res = minMove(s, oc.throwing, depth - 1, indent + 2);
                expectedEvalForThisThrow += res.getEvaluation() * oc.getProbability();
            }
            if (debug) {
                for (int i = 0; i < indent; i++) {
                    System.out.print("\t");
                }
                System.out.println("} Total expected evaluation for this throw = " + expectedEvalForThisThrow + "\n");
            }
            if (expectedEvalForThisThrow >= bestExpectedEval) {
                bestExpectedEval = expectedEvalForThisThrow;
                bestState = s;
            }
        }

        StateEvaluation result = new StateEvaluation(bestState, bestExpectedEval);
        transpositionTable.put(key, result);
        return result;
    }

    private StateEvaluation minMove(State state, int throwing, int depth, int indent) {
        int key = state.hashCode();

        if (state.checkWinning()) {
            return new StateEvaluation(state, Integer.MIN_VALUE);
        }

        if (depth == 0 && transpositionTable.containsKey(key)){
            if (debug)
                System.out.println("]");
            return transpositionTable.get(key);
        }

        if (depth == 0) {
            EvaluationEngine engine = new EvaluationEngine(state, difficulty);
            double eval = engine.eval();
            StateEvaluation result = new StateEvaluation(state, eval);

            transpositionTable.put(key, result);
            if (debug){
                System.out.println();
                for (int i = 0 ; i < indent  ; i++){
                    System.out.print("\t");
                }
                System.out.print("Depth: " + depth + " | Node: Min | Eval: " + eval + " | ");
                engine.printResults(indent + 1);
                System.out.println(" | State:\n" + state.renderBoard());
                for (int i = 0 ; i < indent - 1 ; i++){
                    System.out.print("\t");
                }
                System.out.println("]");
            }
            return result;
        }

        double bestExpectedEval = Double.POSITIVE_INFINITY;
        State bestState = null;

        List<State> possibleStates = state.getNextStates(throwing);

        for (State s : possibleStates) {

            if (debug) {
                System.out.println();
                for (int i = 0; i < indent; i++) {
                    System.out.print("\t");
                }
                System.out.println("Min | These are all the throws for this state : " + s.hashCode() + " { ");
            }
            double expectedEvalForThisThrow = 0;
            List<OutComesChances> nextThrows = OutComesChances.getThrows(); // يفضل جعلها static

            for (OutComesChances oc : nextThrows) {
                if (debug) {
                    for (int i = 0; i < indent + 1; i++) {
                        System.out.print("\t");
                    }
                    System.out.print("The Throw " + oc.throwing + " of this state : [");
                }
                StateEvaluation res = maxMove(s, oc.throwing, depth - 1, indent + 2);
                expectedEvalForThisThrow += res.getEvaluation() * oc.getProbability();
            }

            if (debug) {
                for (int i = 0; i < indent; i++) {
                    System.out.print("\t");
                }
                System.out.println("} Total expected evaluation for this throw = " + expectedEvalForThisThrow + "\n");
            }
            if (expectedEvalForThisThrow <= bestExpectedEval) {
                bestExpectedEval = expectedEvalForThisThrow;
                bestState = s;
            }
        }

        StateEvaluation result = new StateEvaluation(bestState, bestExpectedEval);
        transpositionTable.put(key, result);
        return result;
    }


}
