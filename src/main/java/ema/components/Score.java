package ema.components;

import java.awt.Font;

/**
 * Represents the scores of the players on either side of the board.
 */
public class Score extends GameText {
    /**
     * The current score of the player.
     */
    private int score;

    /**
     * Creates a score object initialised with a score.
     * @param score The initial score
     */
    public Score(int score) {
        super(String.valueOf(score));
        this.score = score;

        super.getLabel().setFont(new Font("Cambria", 1, 70));
    }

    /**
     * Gets the current score. 
     * @return The score
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Sets the score to the provided score
     * @param newScore The new score
     */
    public void setScore(int newScore) {
        this.score = newScore;
        super.getLabel().setText(String.valueOf(newScore));
    }

    /**
     * Incrments the score and changes the score text
     */
    public void incrementScore() {
        this.score++;
        super.getLabel().setText(String.valueOf(this.score));
    }
    
}
