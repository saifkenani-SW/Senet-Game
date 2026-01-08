package logic;

import game.*;

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

    public State play() {
        System.out.println("====== Senet Start ======");

        while (true) {
            if (state.checkWinning()) {
                Player winner = state.getWinner();
                System.err.println("Winner: " + winner + "!");
                return state;
            }
            System.out.println("\n" + state.toString());
            System.out.println("Enter your step: ");
            int throwingResult = scanner.nextInt();
            System.out.println("throwing: " + throwingResult + getDiceDescription(throwingResult));

            if (state.getSizeofNextStates(throwingResult) == 0) {
                System.err.println("You should skip your role !!!!");
                System.err.println("You role have been skipped!");
                state.skipRole();
                continue;
            }


            List<Position> positions = state.getCurrentPlayerPieces();


            System.out.println("\n Available parts" + state.getCurrentPlayer());
            for (int i = 0; i < positions.size(); i++) {
                Position pos = positions.get(i);
                int cellNumber = getCellNumber(pos);
                System.out.printf("%d- part in cell : %d%n", i + 1, cellNumber);
            }
            System.out.print("\n enter the number of part");
            int choice = scanner.nextInt();
            if (choice < 1 || choice > positions.size()) {
                System.err.println("Invalid choice");
                continue;
            }

            Position selectedPosition = positions.get(choice - 1);

            if (!move.canMove(state.getPlayers(), selectedPosition, throwingResult)) {
                System.err.println("You can not move it now!");
                System.err.println("Try again");
                continue;
            }
//save player for "Try again"
            Player currentPlayerBeforeMove = state.getCurrentPlayer();
            state = move.move(state, selectedPosition, throwingResult);
        }
    }


    private int getCellNumber(Position pos) {
        if (pos.getRow() == 0) return pos.getCol() + 1;
        if (pos.getRow() == 1) return 20 - pos.getCol();
        return 21 + pos.getCol();
    }

    private String getDiceDescription(int diceValue) {
        if (diceValue == 5) {
            return "All Is White !";
        }
        return " Black Number :" + diceValue;
    }

}