package ema.components;

import java.awt.Point;

import ema.mechanics.Difficulty;

/**
 * This class constructs a an AIPaddle instance extended from the Paddle class.
 */
public class AIPaddle extends Paddle {
    /**
     * The difficulty of the AI opponent.
     */
    private Difficulty difficulty;

    /**
     * Constructs an AIPaddle object with the given arguments.
     * @param startinglocation The starting location of the AI when added to the game.
     * @param keyCodes The keycodes needed to control the AI which will be NULL.
     * @param regionBounds The region in which the AI is allowed to move.
     * @param name The name of the AI.
     */
    public AIPaddle(Point startinglocation, int[] keyCodes, int[] regionBounds, String name) {
        super(startinglocation, keyCodes, regionBounds, name);
    }

    @Override
    public int getVelocity() {
        return (int)(super.getVelocity() * difficulty.getSpeedMultiplier());
    }

    /* */
    public Difficulty getDifficulty() {
        return this.difficulty;
    }

    public void setDifficulty(Difficulty newDiff) {
        this.difficulty = newDiff;
    }
}
