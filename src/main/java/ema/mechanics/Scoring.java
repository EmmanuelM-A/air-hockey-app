package ema.mechanics;

import ema.components.CountDown;

/**
 * Handles the scoring mechanism of the single player game if the user wins.
 */
public class Scoring {
    /**
     * Calculates the score using the AI paddle's set difficulty.
     * @param diffMultiplier The AI paddle's difficulty multiplier.
     * @param countDown The length of the round.
     * @param playerPoints The points the player accumulated during the round.
     * @return The calculated score
     */
    public static int calculateScore(double diffMultiplier, int countDown, int playerPoints) {
        double k = 1.2;

        double timeFactor = Math.pow((countDown / (double) CountDown.MAX_COUNTDOWN), k);

        int score = (int) (playerPoints * diffMultiplier * timeFactor * 1000);

        return score;
    }
}
