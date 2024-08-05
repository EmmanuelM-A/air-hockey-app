package ema.actions;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ema.components.AIGamePanel;
import ema.mechanics.GameMode;
import ema.ui.game.twoPlayer.TwoPlayerGame;

public class RestartGameAction implements ActionListener {
    private GameMode mode;

    public RestartGameAction(GameMode mode) {
        this.mode = mode;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(mode == GameMode.SINGLEPLAYER) {
            AIGamePanel.instance.restart();
        } else if(mode == GameMode.TWOPLAYER) {
            TwoPlayerGame.instance.restart();
        }
    }
}
