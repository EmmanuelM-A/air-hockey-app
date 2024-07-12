package ema.components;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class GameText {
    /**
     * The text displayed around the board.
     */
    private JLabel label;

    /**
     * An instance of the class.
     */
    public static GameText instance;

    /**
     * 
     * @param string The text to be displayed.
     */
    public GameText(String string) {
        this.label = new JLabel(string);
        label.setFont(new Font("Cambria", 1, 30));
        label.setBorder(null);
        label.setForeground(Color.BLACK);
        label.setText(String.valueOf(string));
    }

    /**
     * Gets the label
     * @return The JLabel
     */
    public JLabel getLabel() {
        return this.label;
    }
}
