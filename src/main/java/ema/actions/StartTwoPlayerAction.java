package ema.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ema.ui.game.settings.TwoPlayerSetting;
import ema.ui.home.HomeMenuFrame;

/**
 * This class handles the start two player action when the two player mode button is clicked
 * in the home menu.
 */
public class StartTwoPlayerAction implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Open the two player game settings 
        TwoPlayerSetting setting = new TwoPlayerSetting(HomeMenuFrame.instance);
        setting.getDialog().setVisible(true);
    }
}
