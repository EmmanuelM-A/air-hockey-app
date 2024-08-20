package ema.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ema.ui.game.settings.SinglePlayerSettings;
import ema.ui.game.singlePlayer.SinglePlayerFrame;
import ema.ui.home.HomeMenuFrame;

public class StartSinglePlayerAction implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        //HomeMenuFrame.instance.switchFrame(new SinglePlayerFrame());
        SinglePlayerSettings settings = new SinglePlayerSettings(HomeMenuFrame.instance);

        settings.getDialog().setVisible(true);
    }
}
