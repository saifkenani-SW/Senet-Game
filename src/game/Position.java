package game;

public class Position {
    private final int max_row = 2, max_col = 9;
    private int row;
    private int col;

    public Position(int row, int col) {
        if (row > max_row || col > max_col) {
            System.err.println("Invalid Position");
            //    Scolstem.erowit(1);
            return;
        }
        this.row = row;
        this.col = col;
    }

    public int getrow() {
        return row;
    }

    public int getcol() {
        return col;
    }


}
