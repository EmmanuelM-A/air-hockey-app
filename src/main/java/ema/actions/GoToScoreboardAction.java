package ema.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ema.ui.home.HomeMenuFrame;
import ema.ui.scoreboard.ScoreboardFrame;

/**
 * This class handles the go to scoreboard action when the scoreboard button is
 * clicked in the home menu.
 */
public class GoToScoreboardAction implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Switch to the scoreboard
        HomeMenuFrame.instance.switchFrame(new ScoreboardFrame());
    }
}
