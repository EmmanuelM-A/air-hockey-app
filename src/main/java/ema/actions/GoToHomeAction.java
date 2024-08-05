package ema.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ema.ui.BaseFrame;
import ema.ui.game.singlePlayer.SinglePlayerFrame;
import ema.ui.game.twoPlayer.TwoPlayerFrame;
import ema.ui.game.twoPlayer.TwoPlayerGame;
import ema.ui.home.HomeMenuFrame;

public class GoToHomeAction implements ActionListener {

    private BaseFrame frame;

    public GoToHomeAction(BaseFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(frame instanceof TwoPlayerFrame) {
            //TwoPlayerGame.instance.stopGame();
        } else if (frame instanceof SinglePlayerFrame) {
            // Stop single player game
        }
        frame.switchFrame(new HomeMenuFrame());
    }
}
