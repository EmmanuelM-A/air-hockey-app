package ema.components;

import java.awt.Point;

import ema.mechanics.Difficulty;

public class AIPaddle extends Paddle {
    private Difficulty difficulty;

    public AIPaddle(Point startinglocation, int[] keyCodes, int[] regionBounds, String name, Difficulty difficulty) {
        super(startinglocation, keyCodes, regionBounds, name);
        this.difficulty = difficulty;
    }

    @Override
    public int getVelocity() {
        return (int)(super.getVelocity() * difficulty.getSpeedMultiplier());
    }
}
