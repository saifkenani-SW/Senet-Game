package game;

public class Position {
    private final int max_x = 2, max_y = 9;
    private int x;
    private int y;

    public Position(int x, int y) {
        if (x > max_x || y > max_y) {
            System.err.println("Invalid Position");
            //    System.exit(1);
            return;
        }
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
