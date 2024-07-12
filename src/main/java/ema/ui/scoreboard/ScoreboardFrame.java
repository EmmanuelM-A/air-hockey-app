package ema.ui.scoreboard;

import java.awt.Dimension;

import ema.ui.BaseFrame;

public class ScoreboardFrame extends BaseFrame {
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
