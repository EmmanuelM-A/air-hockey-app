package ema.ui.scoreboard;

import java.awt.Dimension;

import ema.ui.BaseFrame;

/**
 * Displays the scoreboard frame.
 */
public class ScoreboardFrame extends BaseFrame {
    /**
     * Creates the scoreboard frame and adds its panel.
     */
    public ScoreboardFrame() {
        super.setTitle("");
        super.setSize(new Dimension(600, 700));
        super.setResizable(false);
        super.setLocationRelativeTo(null);

        ScoreboardPanel scoreboardPanel = new ScoreboardPanel(this);
        add(scoreboardPanel);

        super.setVisible(true);
    }
}
