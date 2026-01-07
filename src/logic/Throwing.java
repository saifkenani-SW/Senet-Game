package logic;

import java.util.Random;
public class Throwing {
    private static final Throwing instance = new Throwing();
    private Random random = new Random();

    private Throwing() {
    }
    public static Throwing getInstance() {
        return instance;
    }

    public int getResult() {
        int result = 0;
        for (int i = 0; i < 4; i++) {
            result += random.nextInt(2);
        }

        return result == 0 ? 5 : result;
    }
}