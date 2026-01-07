package logic;

public class Throwing {
    private static final Throwing instance = new Throwing();

    private Throwing() {
    }
    public static Throwing getInstance() {
        return instance;
    }


}