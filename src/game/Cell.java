package game;

public class Cell {
    Type type;

    public Cell(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }
}

enum Type {
    NORMAL,
    NEW_BEGINNING,
    INSPECTION,
    RETURN,
    TREE,
    TOW,
    FREEDOM;
}
