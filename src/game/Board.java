package game;

public class Board {

    private static final Board instance = new Board();

    private final Cell[][] cells = new Cell[3][10];

    private Board() {
        initializeBoard();
    }

    public static Board getInstance() {
        return instance;
    }

    private void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 10; j++) {
                cells[i][j] = new Cell();
            }
        }
    }

    public Cell[][] getCells() {
        return cells;
    }
}