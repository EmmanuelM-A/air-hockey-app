package ema.actions;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ema.components.AIGamePanel;
import ema.components.GamePanel;
import ema.mechanics.GameMode;

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
            GamePanel.instance.restart();
        }
    }
}
