package ema.mechanics;

public enum Difficulty {
    EASY(0.5),
    NORMAL(0.9),
    HARD(1.3);

    private double speedMultiplier; 

    private Difficulty(double speedMultiplier) {
        this.speedMultiplier = speedMultiplier;
    }

    public double getSpeedMultiplier() {
        return this.speedMultiplier;
    }
}
