package ema.mechanics;

public enum Difficulty {
    EASY(0.75, 1.0),
    NORMAL(1.0, 1.8),
    HARD(1.5, 2.4);

    private double speedMultiplier; 
    private double diffMultiplier;

    private Difficulty(double speedMultiplier, double diffMultiplier) {
        this.speedMultiplier = speedMultiplier;
        this.diffMultiplier = diffMultiplier;
    }

    public double getSpeedMultiplier() {
        return this.speedMultiplier;
    }

    public double getDiffMultiplier() {
        return this.diffMultiplier;
    }
}
