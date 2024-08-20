package ema.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ema.ui.game.settings.SinglePlayerSettings;
import ema.ui.home.HomeMenuFrame;

public class StartSinglePlayerAction implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        SinglePlayerSettings settings = new SinglePlayerSettings(HomeMenuFrame.instance);

        settings.getDialog().setVisible(true);
    }
}
