package ema.mechanics;

/**
 * Stores the difficulty settings for the AI paddle.
 */
public enum Difficulty {
    EASY(0.75, 0.45),
    NORMAL(1.0, 1.2),
    HARD(1.5, 1.8);

    /**
     * The speed multiplier used to alter the speed of the AI paddle.
     */
    private double speedMultiplier; 

    /**
     * The difficulty multiplier of the AI paddle. Used for the scoring mechanism. 
     */
    private double diffMultiplier;

    /**
     * Creates a difficulty enum that provides the required information to set the AI paddle's difficulty.
     * @param speedMultiplier The speed multiplier
     * @param diffMultiplier The difficulty multiplier
     */
    private Difficulty(double speedMultiplier, double diffMultiplier) {
        this.speedMultiplier = speedMultiplier;
        this.diffMultiplier = diffMultiplier;
    }

    /**
     * Gets the corresponding speed multiplier for the difficulty selected.
     * @return The speed multiplier for the AI paddle.
     */
    public double getSpeedMultiplier() {
        return this.speedMultiplier;
    }

    /**
     * Gets the corresponding difficulty multiplier for the difficulty selected.
     * @return The difficulty multiplier for the AI paddle.
     */
    public double getDiffMultiplier() {
        return this.diffMultiplier;
    }
}
