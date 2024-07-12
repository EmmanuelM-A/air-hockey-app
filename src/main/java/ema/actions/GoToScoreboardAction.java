package ema.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ema.ui.home.HomeMenuFrame;
import ema.ui.scoreboard.ScoreboardFrame;

public class GoToScoreboardAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        HomeMenuFrame.instance.switchFrame(new ScoreboardFrame());
    }
    
}
