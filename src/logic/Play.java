package logic;

import algorithim.ExpectMiniMax;
import evaluation.Difficulty;
import game.*;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Play {
    private State state;
    private Throwing throwing = Throwing.getInstance();
    int throwingResult = throwing.getResult();
    private Scanner scanner = new Scanner(System.in);
    private Move move = new Move();

    public Play(State state) {
        this.state = state;
    }

//    public State play() {
//        System.out.println("====== Senet Start ======");
//
//        while (true) {
//            if (state.checkWinning()) {
//                Player winner = state.getWinner();
//                System.err.println("Winner: " + winner + "!");
//                return state;
//            }
//            System.out.println("\n" + state.toString());
//            System.out.println("Enter your step: ");
//            int throwingResult = scanner.nextInt();
//            System.out.println("throwing: " + throwingResult + getDiceDescription(throwingResult));
//
//            if (state.getSizeofNextStates(throwingResult) == 0) {
//                System.err.println("You should skip your role !!!!");
//                System.err.println("You role have been skipped!");
//                state.skipRole();
//                continue;
//            }
//
//
//            List<Position> positions = state.getCurrentPlayerPieces();
//
//
//            System.out.println("\n Available parts for " + state.getCurrentPlayer());
//            for (int i = 0; i < positions.size(); i++) {
//                Position pos = positions.get(i);
//                int cellNumber = getCellNumber(pos);
//                System.out.printf("%d- part in cell : %d%n", i + 1, cellNumber);
//            }
//            System.out.print("\n enter the number of part");
//            int choice = scanner.nextInt();
//            if (choice < 1 || choice > positions.size()) {
//                System.err.println("Invalid choice");
//                continue;
//            }
//
//            Position selectedPosition = positions.get(choice - 1);
//
//            if (!move.canMove(state.getPiece(), selectedPosition, throwingResult)) {
//                System.err.println("You can not move it now!");
//                System.err.println("Try again");
//                continue;
//            }
//
//            //save player for "Try again"
//            Player currentPlayerBeforeMove = state.getCurrentPlayer();
//            state = move.move(state, selectedPosition, throwingResult);
//        }
//    }



    public State play() {
        System.out.println("====== Senet Start ======");

        Difficulty difficulty = Difficulty.getDifficultyType(scanner);
        System.out.print("Do you want to enable Debug mode? (y/n): ");
        String debugInput = scanner.next().trim().toLowerCase();
        boolean debug = debugInput.equals("y");

        ExpectMiniMax expectMiniMax = new ExpectMiniMax(difficulty, debug);

        while (true) {
            if (state.checkWinning()) {
                Player winner = state.getWinner();
                System.err.println("Winner: " + winner + "!");
                return state;
            }
            if (state.getCurrentPlayer() == Player.PLAYER1){

                System.out.println("\n" + state.toString());
                System.out.print("Enter your step : ");
                int throwingResult = scanner.nextInt();
                System.out.println("throwing: " + throwingResult + getDiceDescription(throwingResult));

                if (state.getSizeofNextStates(throwingResult) == 0) {
                    System.err.println("You should skip your role !!!!");
                    System.err.println("You role have been skipped!");
                    state.skipRole();
                    continue;
                }

                List<Position> positions = state.getCurrentPlayerPiecesPositions();

                System.out.println("\n Available parts for " + state.getCurrentPlayer());
                for (int i = 0; i < positions.size(); i++) {
                    Position pos = positions.get(i);
                    int cellNumber = getCellNumber(pos);
                    System.out.printf("%d- part in cell : %d%n", i + 1, cellNumber);
                }
                System.out.print("\n enter the number of part :  ");
                int choice = scanner.nextInt();
                if (choice < 1 || choice > positions.size()) {
                    System.err.println("Invalid choice");
                    continue;
                }

                Position selectedPosition = positions.get(choice - 1);

                if (!move.canMove(state.getPieces(), selectedPosition, throwingResult)) {
                    System.err.println("You can not move it now!");
                    System.err.println("Try again");
                    continue;
                }


                //save player for "Try again"
                Player currentPlayerBeforeMove = state.getCurrentPlayer();
//                System.out.println("pawn now in (" + selectedPosition.getRow() + " , " + selectedPosition.getCol() + ")");

                state = move.move(state, selectedPosition, throwingResult);

            } else {
                System.out.println("\n" + state.toString());
                System.out.print("Enter your step : ");

                int throwingResult = scanner.nextInt();

                System.out.println("throwing: " + throwingResult + getDiceDescription(throwingResult));

                StateEvaluation newState = expectMiniMax.maxMove(state, throwingResult);

                Position selectedPosition = getNewPosition(newState.getState());
                System.out.println("Evaluation for this state is : " + newState.getEvaluation());

                if (!move.canMove(state.getPieces(), selectedPosition, throwingResult)) {
                    System.err.println("You can not move it now!");
                    System.err.println("Try again");
                    continue;
                }

                Player currentPlayerBeforeMove = state.getCurrentPlayer();
                state = move.move(state, selectedPosition, throwingResult);
            }
        }
    }

    private Position getNewPosition(State newState){
        List<Position> positions1 = state.getCurrentPlayerPiecesPositions();
        List<Position> positions2 = newState.getOtherPlayerPiecesPositions();

        Iterator<Position> iterator1 = positions1.iterator();
        while (iterator1.hasNext()) {
            Position pos1 = iterator1.next();

            Iterator<Position> iterator2 = positions2.iterator(); // جديد لكل pos1
            while (iterator2.hasNext()) {
                Position pos2 = iterator2.next();
                if (pos2.equals(pos1)) {
                    iterator1.remove();
                    iterator2.remove();
                    break;
                }
            }
        }

        System.out.println("pawn in (" + (positions1.getFirst().getRow() + 1) + " , " + (positions1.getFirst().getCol() + 1) + ") to ("
        + (positions2.getFirst().getRow() + 1) + " , " + (positions2.getFirst().getCol() + 1) + ")");
        return positions1.getFirst();
    }


    private int getCellNumber(Position pos) {
        if (pos.getRow() == 0) return pos.getCol() + 1;
        if (pos.getRow() == 1) return 20 - pos.getCol();
        return 21 + pos.getCol();
    }

    private String getDiceDescription(int diceValue) {
        if (diceValue == 5) {
            return " All Is White !";
        }
        return " Black Number :" + diceValue;
    }

}