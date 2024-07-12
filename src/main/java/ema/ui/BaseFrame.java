package ema.ui;

import javax.swing.JFrame;

public abstract class BaseFrame extends JFrame {
    public BaseFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void switchFrame(BaseFrame newFrame) {
        newFrame.setVisible(true);
        dispose();
    }
}
