package game;

public class Board {

    private static final Board instance = new Board();
    private final Cell[][] cells = new Cell[3][10];

    private Board() {
    }
}