package ema.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ema.ui.game.settings.SinglePlayerSettings;
import ema.ui.home.HomeMenuFrame;

/**
 * This class handles the start single player game action when the button is clicked in the
 * home menu.
 */
public class StartSinglePlayerAction implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Open single player game settings
        SinglePlayerSettings settings = new SinglePlayerSettings(HomeMenuFrame.instance);
        settings.getDialog().setVisible(true);
    }
}
