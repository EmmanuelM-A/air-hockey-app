package ema.ui;

import javax.swing.JFrame;

/**
 * The base frame which will be extends by other frame classes.
 */
public abstract class BaseFrame extends JFrame {
    /**
     * Ensures that allow frames have same settings.
     */
    public BaseFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Used to switch between frames that extend the base frame. 
     * @param newFrame The frame to switch to.
     */
    public void switchFrame(BaseFrame newFrame) {
        newFrame.setVisible(true);
        dispose();
    }
}
