package ema.mechanics;

public enum Difficulty {
    EASY(0.9, 1.0),
    NORMAL(1.5, 1.8),
    HARD(2.0, 2.4);

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
