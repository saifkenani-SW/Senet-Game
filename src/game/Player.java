package game;

public enum Player {
    PLAYER1(Piece.WHITE),
    PLAYER2(Piece.BLACK);
    private final Piece piece;

    Player(Piece piece) {
        this.piece = piece;
    }

    public Piece getPiece() {
        return piece;
    }
}
