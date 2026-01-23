package evaluation;

import java.util.Scanner;

public enum Difficulty {
    EASY(0.6),
    NORMAL(1.0),
    HARD(1.4),
    EXPERT(1.8);

    public final double k;
    Difficulty(double k){
        this.k = k;
    }

    public static Difficulty getDifficultyType(Scanner scanner) {
        System.out.println("Chose Difficulty :\n1 - Easy\n2 - Normal\n3 - Hard\n4 - Expert");
        int x = scanner.nextInt();

        while (true) {
            switch (x) {
                case 1:
                    return Difficulty.EASY;
                case 2:
                    return Difficulty.NORMAL;
                case 3:
                    return Difficulty.HARD;
                case 4:
                    return Difficulty.EXPERT;
                default:
                    System.out.println("Wrong Enter");
            }
        }

    }
}

