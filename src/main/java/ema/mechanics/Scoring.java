package ema.mechanics;

import ema.components.CountDown;

public class Scoring {
    public static int calculateScore(double diffMultiplier, int countDown, int playerPoints) {
        double k = 1.2;

        double timeFactor = Math.pow((countDown / (double) CountDown.MAX_COUNTDOWN), k);

        int score = (int) (playerPoints * diffMultiplier * timeFactor * 10000);

        return score;
    }
}
