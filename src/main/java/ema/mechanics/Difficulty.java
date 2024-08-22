package ema.mechanics;

public enum Difficulty {
    EASY(0.75, 0.45),
    NORMAL(1.0, 1.2),
    HARD(1.5, 1.8);

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
