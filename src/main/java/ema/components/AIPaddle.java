package ema.components;

import java.awt.Point;

import ema.mechanics.Difficulty;

public class AIPaddle extends Paddle {
    private Difficulty difficulty;

    public AIPaddle(Point startinglocation, int[] keyCodes, int[] regionBounds, String name) {
        super(startinglocation, keyCodes, regionBounds, name);
    }

    @Override
    public int getVelocity() {
        return (int)(super.getVelocity() * difficulty.getSpeedMultiplier());
    }

    public Difficulty getDifficulty() {
        return this.difficulty;
    }

    public void setDifficulty(Difficulty newDiff) {
        this.difficulty = newDiff;
    }
}
