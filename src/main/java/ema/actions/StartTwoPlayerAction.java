package ema.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ema.ui.game.settings.TwoPlayerSetting;
import ema.ui.home.HomeMenuFrame;

public class StartTwoPlayerAction implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        TwoPlayerSetting setting = new TwoPlayerSetting(HomeMenuFrame.instance);

        setting.getDialog().setVisible(true);
    }
}
