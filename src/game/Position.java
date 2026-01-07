package game;

public class Position {
    private final int maxRow = 2, maxCol = 9;
    private int row;
    private int col;

    public Position(int row, int col) {
        if (row < 0 || col < -1 || row > maxRow || col > maxCol) {
            throw new IllegalArgumentException(
                    "Invalid Position: row=" + row + ", col=" + col
            );
        }
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Position previosPosition() {
        if (row == 1) {
            return col == 9
                    ? new Position(row - 1, col)
                    : new Position(row, col + 1);

        } else if (row == 2) {
            return col == 0
                    ? new Position(row - 1, col)
                    : new Position(row, col - 1);
        }

        return new Position(0, col - 1);

    }


    public Position nextPosition(int steps) {
        Position current = this;

        for (int i = 0; i < steps; i++) {
            current = current.nextOne();
        }
        return current;
    }

    private Position nextOne() {
        if (row == 0) {
            return col == 9
                    ? new Position(1, col)
                    : new Position(0, col + 1);
        }

        if (row == 1) {
            return col == 0
                    ? new Position(2, col)
                    : new Position(1, col - 1);
        }

        return new Position(2, col + 1);
    }


    public Position nextPositionRec(int step) {
        if (step == 0) {
            return this;
        }
        Position position;
        if (row == 0) {
            if (col == 9)
                position = new Position(row + 1, col);
            else
                position = new Position(row, col + 1);
            return position.nextPositionRec(step - 1);
        } else if (row == 1) {
            if (col == 0)
                position = new Position(row + 1, col);
            else
                position = new Position(row, col - 1);
            return position.nextPositionRec(step - 1);

        } else {
            position = new Position(row, col + 1);
            return position.nextPositionRec(step - 1);
        }

    }


}
