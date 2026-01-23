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

    public EvaluationEngine(State state){
        this.state = state;
    }

    public void setDifficulty(Difficulty d) {
        double k;
        switch (d) {
            case EASY -> {
                k = 0.5;
                W_material  = 3 * k;   // يركز أقل على القوة
                W_progress  = 2 * k;   // لا يحسب التقدم كثيرًا
                W_position  = 1 * k;   // خفيف على وضعية القطع
                W_mobility  = 1 * k;   // قليل التفكير في الحركة
                W_capture   = 1 * k;   // لا يهاجم كثيرًا
                W_safety    = 0.5 * k; // لا يحمي القطع كثيرًا
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
                W_material  = 5 * k;   // يركز على القوة
                W_progress  = 4 * k;   // يقدر التقدم أكثر
                W_position  = 3 * k;   // يهتم بوضعية القطع
                W_mobility  = 3 * k;   // يخطط أكثر للحركة
                W_capture   = 4 * k;   // هجومي أكثر
                W_safety    = 3 * k;   // يحمي القطع جيدًا
            }
            case EXPERT -> {
                k = 2.0;
                W_material  = 6 * k;   // كل شيء مؤثر
                W_progress  = 5 * k;
                W_position  = 4 * k;
                W_mobility  = 4 * k;
                W_capture   = 5 * k;
                W_safety    = 4 * k;
            }
        }
    }


    // دالة التقييم الرئيسية
    public double eval(){
        List<Position> myPositions = state.getCurrentPlayerPiecesPositions();
        List<Position> opponentPositions = state.getOtherPlayerPiecesPositions();

        return
            W_material * evaluateFinishedPieces(myPositions, opponentPositions) +
            W_progress * evaluateProgress(myPositions, opponentPositions) +
            W_position * evaluateCellPositions(myPositions, opponentPositions) +
            W_capture  * evaluateCaptures(myPositions, opponentPositions) +
            W_mobility * evaluateMobility(myPositions, opponentPositions) +
            W_safety   * evaluateSafety(myPositions, opponentPositions);
    }

    // ------------------- Material -------------------
    private double evaluateFinishedPieces(List<Position> myPositions, List<Position> opponentPositions){
        return (7 - myPositions.size()) * 5 - (7 - opponentPositions.size()) * 5;
    }

    // ------------------- Progress -------------------
    private double evaluateProgress(List<Position> myPositions, List<Position> opponentPositions){
        return calculateProgress(myPositions) - calculateProgress(opponentPositions);
    }

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
        List<OutComesChances> ocs = OutComesChances.makeStates();
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
        List<OutComesChances> ocs = OutComesChances.makeStates();

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
        double score = 0;
        List<OutComesChances> ocs = OutComesChances.makeStates();

        for (Position pos : threatenedPositions){
            for (OutComesChances oc : ocs){
                Position target = pos.nextPosition(oc.getThrowing());
                if (attackerPositions.contains(target)){
                    score += oc.getProbability() * getThreatValue(target);
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

    private double getThreatValue(Position pos){
        Type type = Board.getInstance().getCellType(pos);
        return switch(type){
            case FREEDOM -> 2.5;
            case CHECK_POINT -> 1.5;
            case NEW_BEGINNING -> 2;
            case TREE, TOW -> 1.0;
            case RETURN -> 0;
            case NORMAL -> 0.5;
        };
    }

}
