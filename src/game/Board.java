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
                if (i == 1 && j == 5)
                    cells[i][j] = new Cell(Type.NEW_BEGINNING);
                else if (i == 2 && j == 5)
                    cells[i][j] = new Cell(Type.INSPECTION);
                else if (i == 2 && j == 6)
                    cells[i][j] = new Cell(Type.RETURN);
                else if (i == 2 && j == 7)
                    cells[i][j] = new Cell(Type.TREE);
                else if (i == 2 && j == 8)
                    cells[i][j] = new Cell(Type.TREE);
                else if (i == 2 && j == 9)
                    cells[i][j] = new Cell(Type.FREEDOM);
                else
                    cells[i][j] = new Cell(Type.NORMAL);
            }
        }
    }

    public Cell[][] getCells() {
        return cells;
    }
}