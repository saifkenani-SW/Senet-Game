package evaluation;

import algorithim.OutComesChances;
import game.Board;
import game.Position;
import game.State;
import game.Type;
import logic.Move;

import java.util.List;

public class EvaluationEngine {

    private final State state;

    // أوزان العوامل
    private double W_material;
    private double W_progress;
    private double W_position;
    private double W_mobility;
    private double W_capture;
    private double W_safety;

    private double totalMaterialScore;
    private double totalProgressScore;
    private double totalPositionScore;
    private double totalMobilityScore;
    private double totalCaptureScore;
    private double totalSafetyScore;
    private double totalEvaluation;


    public EvaluationEngine(State state, Difficulty d){
        this.state = state;
        setDifficulty(d);
    }

    public void setDifficulty(Difficulty d) {
        double k;
        switch (d) {
            case EASY -> {
                k = 0.5;
                W_material  = 3 * k;
                W_progress  = 2 * k;
                W_position  = 1 * k;
                W_mobility  = 1 * k;
                W_capture   = 1 * k;
                W_safety    = 0.5 * k;
            }
            case NORMAL -> {
                k = 1.0;
                W_material  = 4 * k;
                W_progress  = 3 * k;
                W_position  = 2 * k;
                W_mobility  = 2 * k;
                W_capture   = 3 * k;
                W_safety    = 2 * k;
            }
            case HARD -> {
                k = 1.5;
                W_material  = 5 * k;
                W_progress  = 4 * k;
                W_position  = 3 * k;
                W_mobility  = 3 * k;
                W_capture   = 4 * k;
                W_safety    = 3 * k;
            }
            case EXPERT -> {
                k = 2.0;
                W_material  = 6 * k;
                W_progress  = 20 * k;
                W_position  = 4 * k;
                W_mobility  = 4 * k;
                W_capture   = 5 * k;
                W_safety    = 4 * k;
            }
        }
    }

    public void printResults(int indent){

        System.out.println("EvaluationEngine {");
        for (int i = 0 ; i < indent ; i++){
            System.out.print("\t");
        }
        System.out.println("totalMaterialScore = " + totalMaterialScore);
        for (int i = 0 ; i < indent ; i++){
            System.out.print("\t");
        }
        System.out.println("totalProgressScore = " + totalProgressScore);
        for (int i = 0 ; i < indent ; i++){
            System.out.print("\t");
        }
        System.out.println("totalPositionScore = " + totalPositionScore);
        for (int i = 0 ; i < indent ; i++){
            System.out.print("\t");
        }
        System.out.println("totalCaptureScore = " + totalCaptureScore);
        for (int i = 0 ; i < indent ; i++){
            System.out.print("\t");
        }
        System.out.println("totalMobilityScore = " + totalMobilityScore);
        for (int i = 0 ; i < indent ; i++){
            System.out.print("\t");
        }
        System.out.println("totalSafetyScore = " + totalSafetyScore);
        for (int i = 0 ; i < indent - 1 ; i++){
            System.out.print("\t");
        }
        System.out.println("}");


    }
    public double eval(){
        List<Position> myPositions = state.getCurrentPlayerPiecesPositions();
        List<Position> opponentPositions = state.getOtherPlayerPiecesPositions();

        totalMaterialScore = W_material * evaluateFinishedPieces(myPositions, opponentPositions);
        totalProgressScore = W_progress * evaluateProgress(myPositions, opponentPositions);
        totalPositionScore = W_position * evaluateCellPositions(myPositions, opponentPositions);
        totalCaptureScore = W_capture  * evaluateCaptures(myPositions, opponentPositions);
        totalMobilityScore = W_mobility * evaluateMobility(myPositions, opponentPositions);
        totalSafetyScore = W_safety   * evaluateSafety(myPositions, opponentPositions);
        totalEvaluation = totalMaterialScore + totalProgressScore + totalPositionScore + totalCaptureScore + totalMobilityScore + totalSafetyScore;
        return totalEvaluation;
    }

    // ------------------- Material -------------------
    private double evaluateFinishedPieces(List<Position> myPositions, List<Position> opponentPositions){
        return (7 - myPositions.size()) * 5 - (7 - opponentPositions.size()) * 5;
    }

    // ------------------- Progress -------------------
    private double evaluateProgress(List<Position> myPositions, List<Position> opponentPositions){
        return calculateProgress(myPositions) - calculateProgress(opponentPositions);
    }

    // ------------------- Position -------------------
    private double calculateProgress(List<Position> positions){
        double score = 0;
        for (Position pos : positions){
            double value = 0;
            switch(pos.getRow()){
                case 0 -> value = pos.getCol() / 10.0;
                case 1 -> value = (9 - pos.getCol()) / 10.0 + 0.5;
                case 2 -> value = pos.getCol() / 10.0 + 1.0;
            }
            score += value;
        }
        return score;
    }

//    private double total(List<Position> myPositions, List<Position> opponentPositions){
//        double myProgressScore = 0;
//        double opponentProgressScore = 0;
//
//        for (Position pos : myPositions){
//            double value = 0;
//            switch(pos.getRow()){
//                case 0 -> value = pos.getCol() / 10.0;
//                case 1 -> value = (9 - pos.getCol()) / 10.0 + 0.5;
//                case 2 -> value = pos.getCol() / 10.0 + 1.0;
//            }
//            score += value;
//        }
//        return score;
//    }

    // ------------------- Cell Positions -------------------
    private double evaluateCellPositions(List<Position> myPositions, List<Position> opponentPositions){
        return calculateCellScore(myPositions) - calculateCellScore(opponentPositions);
    }

    private double calculateCellScore(List<Position> positions){
        double score = 0;
        for (Position pos : positions){
            Type type = Board.getInstance().getCellType(pos);
            score += switch(type){
                case FREEDOM -> 11;
                case CHECK_POINT -> 6;
                case NEW_BEGINNING -> 8;
                case TREE, TOW -> -4;
                case RETURN -> -10;
                case NORMAL -> 1;
            };
        }
        return score;
    }

    // ------------------- Mobility -------------------
    private double evaluateMobility(List<Position> myPositions, List<Position> opponentPositions){
        double myScore = calculateMobility(myPositions);
        double opponentScore = calculateMobility(opponentPositions);
        return myScore - opponentScore;
    }

    private double calculateMobility(List<Position> positions){
        double score = 0;
        List<OutComesChances> ocs = OutComesChances.getThrows();
        Move move = new Move();

        for (Position pos : positions){
            for (OutComesChances oc : ocs){
                Position target = pos.nextPosition(oc.getThrowing());
                if (move.canMove(state.getPieces(), target, oc.getThrowing())){
                    score += getCellValue(target);
                }
            }
        }
        return score;
    }

    // ------------------- Captures -------------------
    private double evaluateCaptures(List<Position> myPositions, List<Position> opponentPositions){
        return calculateCaptureScore(myPositions, opponentPositions) - calculateCaptureScore(opponentPositions, myPositions);
    }

    private double calculateCaptureScore(List<Position> attackerPositions, List<Position> defenderPositions){
        double score = 0;
        List<OutComesChances> ocs = OutComesChances.getThrows();

        for (Position pos : attackerPositions){
            for (OutComesChances oc : ocs){
                Position target = pos.nextPosition(oc.getThrowing());
                if (defenderPositions.contains(target)){
                    score += oc.getProbability() * getCellValue(target);
                }
            }
        }
        return score;
    }

    // ------------------- Safety -------------------
    private double evaluateSafety(List<Position> myPositions, List<Position> opponentPositions){
        return calculateSafetyScore(myPositions, opponentPositions) - calculateSafetyScore(opponentPositions, myPositions);
    }

    private double calculateSafetyScore(List<Position> threatenedPositions, List<Position> attackerPositions){
        double directAttackers = 0;
        double score = 0;
        double directThreat = 0;

        List<OutComesChances> ocs = OutComesChances.getThrows();

        for (Position pos : attackerPositions){
            for (OutComesChances oc : ocs){
                Position target = pos.nextPosition(oc.getThrowing());
                if (threatenedPositions.contains(target)){
                    directThreat += oc.getProbability() * getCellValue(target);
                    directAttackers++;
                }
            }
        }
        score -= directThreat * 12;

        if (directAttackers > 1){
            score -= directAttackers * 4;   // كل مهاجم إضافي خطر
        }

        for (Position pos : attackerPositions){
            for (Position pos2 : threatenedPositions){
                double attackerDistance =  pos2.getIndexForEval() - pos.getIndexForEval();

                if (attackerDistance > 0) {
                    int d = (int) Math.ceil(attackerDistance / 5);
                    attackerDistance = switch (d) {
                        case 1 -> 5;
                        case 2 -> 3;
                        case 3 -> 1.5;
                        default -> 0;
                    };
                    score -= attackerDistance;
                }
            }
        }


        return score;
    }

    // ------------------- Helper: cell value -------------------
    private double getCellValue(Position pos){
        Type type = Board.getInstance().getCellType(pos);
        return switch(type){
            case FREEDOM -> 10;
            case CHECK_POINT -> 5;
            case NEW_BEGINNING -> 7;
            case TREE, TOW -> -5;
            case RETURN -> -10;
            case NORMAL -> 0;
        };
    }



}
