package game;

import algorithim.OutComesChances;
import logic.Move;

import java.util.List;
import java.util.Map;

public class EvaluationEngine {
    State state;
    double K ;

    double W_material;
    double W_progress;
    double W_position;
    double W_mobility;
    double W_capture;

    public void setDifficulty(Difficulty d){
        this.K = d.k;

        W_material  = 4 * K;
        W_progress  = 3 * K;
        W_position  = 2 * K;
        W_mobility  = 2 * K;
        W_capture   = 3 * K;
    }

    public EvaluationEngine(State state){
        this.state = state;
    }



    public double eval(){

        return
            W_material * (myFinishedPieces() - opponentFinishedPieces()) +
            W_progress * (myProgress() - opponentProgress()) +
            W_position * (myPositions() - opponentPositions()) +
            W_capture  * (possibleCapturesForMe() - possibleCapturesForOpponent()) +
            W_mobility * (mobilityForMe() - mobilityForOpponent());
    }


    public double myPositions(){
        double score = 0;
        for (Map.Entry<Position,Piece> entry: state.getCurrentPlayerPieces().entrySet()){
            Position pos = entry.getKey();
            Type cellType = Board.getInstance().getCellType(pos);

            double pieceValue = 1;

            switch (cellType) {
                case FREEDOM -> pieceValue += 10;
                case CHECK_POINT -> pieceValue += 5;
                case RETURN -> pieceValue -= 10;
                case NEW_BEGINNING -> pieceValue += 7;
                case TREE, TOW -> pieceValue += -5;
                case NORMAL -> pieceValue += 0;
            }

            score += pieceValue;
        }

        return score;
    }

    public double opponentPositions(){
        double score = 0;
        for (Map.Entry<Position,Piece> entry: state.getOtherPlayerPieces().entrySet()){
            Position pos = entry.getKey();
            Type cellType = Board.getInstance().getCellType(pos);

            double pieceValue = 1;

            // إضافة وزن حسب نوع الخلية
            switch (cellType) {
                case FREEDOM -> pieceValue += 10;
                case CHECK_POINT -> pieceValue += 5;
                case RETURN -> pieceValue -= 10;
                case NEW_BEGINNING -> pieceValue += 7;
                case TREE, TOW -> pieceValue += -5;
                case NORMAL -> pieceValue += 0;
            }

            score += pieceValue;

        }

        return score;
    }

    public double myProgress() {
        double score = 0;
        Map<Position, Piece> myPieces = state.getCurrentPlayerPieces();

        for (Map.Entry<Position, Piece> entry : myPieces.entrySet()) {
            Position pos = entry.getKey();

            double pieceValue = 0.0;

            switch (pos.getRow()) {
                case 0 -> pieceValue = pos.getCol() / 10.0;
                case 1 -> pieceValue = (9 - pos.getCol()) / 10.0 + 0.5;
                case 2 -> pieceValue = (pos.getCol() / 10.0) + 1.0;
            }

            score += pieceValue;
        }

        return score;
    }

    public double opponentProgress() {
        double score = 0;
        Map<Position, Piece> opponentPieces = state.getOtherPlayerPieces();

        for (Map.Entry<Position, Piece> entry : opponentPieces.entrySet()) {
            Position pos = entry.getKey();

            double pieceValue = 0.0;

            switch (pos.getRow()) {
                case 0 -> pieceValue = pos.getCol() / 10.0;
                case 1 -> pieceValue = (9 - pos.getCol()) / 10.0 + 0.5;
                case 2 -> pieceValue = (pos.getCol() / 10.0) + 1.0;
            }

            score += pieceValue;
        }

        return score;
    }


    public double myFinishedPieces(){

        Map<Position, Piece> myPieces = state.getCurrentPlayerPieces();

        return (7 - myPieces.size()) * 5;
    }

    public double opponentFinishedPieces(){

        Map<Position, Piece> opponentPieces = state.getOtherPlayerPieces();

        return (7 - opponentPieces.size()) * 5;
    }

    public double possibleCapturesForMe(){
        double score = 0;
        List<OutComesChances> ocs = OutComesChances.makeStates();
        Map<Position, Piece> myPieces = state.getCurrentPlayerPieces();

        for (Map.Entry<Position, Piece> entry: myPieces.entrySet()){
            for (OutComesChances oc : ocs){
                score += oc.getProbability() * canCaptureOpponent(oc.getThrowing() , entry.getKey());
            }
        }
        return score;
    }

    public double possibleCapturesForOpponent(){
        double score = 0;
        List<OutComesChances> ocs = OutComesChances.makeStates();
        Map<Position, Piece> opponentPieces = state.getOtherPlayerPieces();

        for (Map.Entry<Position, Piece> entry: opponentPieces.entrySet()){
            for (OutComesChances oc : ocs){
                score += oc.getProbability() * canCaptureMe(oc.getThrowing() , entry.getKey());
            }
        }
        return score;
    }

    private double canCaptureOpponent(int throwingResult, Position myPiece) {
        double score = 0;
        List<Position> otherPieces = state.getOtherPlayerPiecesPositions();

        Position targetPos = myPiece.nextPosition(throwingResult);

        if (!otherPieces.contains(targetPos))
            return score;

        Type cellType = Board.getInstance().getCellType(targetPos);
        double pieceValue;
        pieceValue = switch (cellType) {
            case CHECK_POINT -> 5;
            case FREEDOM -> 10;
            case NEW_BEGINNING -> 7;
            case TREE, TOW -> -5;
            case NORMAL -> 3;
            case RETURN -> -10;
        };
        score += pieceValue;

        return score;
    }

    private double canCaptureMe(int throwingResult, Position myPiece) {
        double score = 0;
        List<Position> otherPieces = state.getCurrentPlayerPiecesPositions();

        Position targetPos = myPiece.nextPosition(throwingResult);

        if (!otherPieces.contains(targetPos))
            return score;

        Type cellType = Board.getInstance().getCellType(targetPos);
        double pieceValue;
        pieceValue = switch (cellType) {
            case CHECK_POINT -> 5;
            case FREEDOM -> 10;
            case NEW_BEGINNING -> 7;
            case TREE, TOW -> -5;
            case NORMAL -> 3;
            case RETURN -> -10;
        };
        score += pieceValue;

        return score;
    }

    public double mobilityForMe(){
        double score = 0;
        List<OutComesChances> ocs = OutComesChances.makeStates();
        Map<Position, Piece> myPieces = state.getCurrentPlayerPieces();

        for (Map.Entry<Position, Piece> entry: myPieces.entrySet()){
            for (OutComesChances oc : ocs){
                score += getAllLegalMoves(oc.getThrowing() , entry.getKey());
            }
        }
        return score;
    }

    private double getAllLegalMoves(int throwingResult, Position myPiece){
        double score = 0;

        Position targetPos = myPiece.nextPosition(throwingResult);

        Move move = new Move();
        if (move.canMove(state.getPieces(), targetPos, throwingResult)){
            Type cellType = Board.getInstance().getCellType(targetPos);
            double pieceValue;
            pieceValue = switch (cellType) {
                case CHECK_POINT -> 5;
                case FREEDOM -> 10;
                case NEW_BEGINNING -> 7;
                case TREE, TOW -> -5;
                case NORMAL -> 0;
                case RETURN -> -10;
            };
            score += pieceValue;
        }

        return score;
    }

    public double mobilityForOpponent(){
        double score = 0;
        List<OutComesChances> ocs = OutComesChances.makeStates();
        Map<Position, Piece> opponentPieces = state.getOtherPlayerPieces();

        for (Map.Entry<Position, Piece> entry: opponentPieces.entrySet()){
            for (OutComesChances oc : ocs){
                score += getAllLegalMoves(oc.getThrowing() , entry.getKey());
            }
        }
        return score;
    }




}
