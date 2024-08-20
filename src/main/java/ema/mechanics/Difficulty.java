package ema.mechanics;

public enum Difficulty {
    EASY(0.7),
    NORMAL(1.1),
    HARD(1.7);

    private double speedMultiplier; 

    private Difficulty(double speedMultiplier) {
        this.speedMultiplier = speedMultiplier;
    }

    public double getSpeedMultiplier() {
        return this.speedMultiplier;
    }
}
