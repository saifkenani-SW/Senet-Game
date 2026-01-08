package game;


public enum Type {

    NORMAL(0),
    NEW_BEGINNING(15),
    CHECK_POINT(25),
    RETURN(26),
    TREE(27),
    TOW(28),
    FREEDOM(29);
    private int index;

    Type(int index) {
        this.index = index + 1;
    }


    public int getIndex() {
        return index;
    }

}
