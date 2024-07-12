package ema.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ema.ui.BaseFrame;
import ema.ui.home.HomeMenuFrame;

public class GoToHomeAction implements ActionListener {

    private BaseFrame frame;

    public GoToHomeAction(BaseFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.switchFrame(new HomeMenuFrame());
    }
}
