package evaluation;

public enum Difficulty {
    EASY(0.6),
    NORMAL(1.0),
    HARD(1.4),
    EXPERT(1.8);

    public final double k;
    Difficulty(double k){
        this.k = k;
    }
}

