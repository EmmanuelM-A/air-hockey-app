package ema.components;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import ema.actions.GoToHomeAction;
import ema.actions.RestartGameAction;
import ema.actions.ToggleAudioAction;
import ema.mechanics.GameMode;
import ema.ui.BaseFrame;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * This class displays the game buttons on to the game.
 */
public class ButtonsPanel {
    /**
     * The panel that contains all the game buttons.
     */
    private JPanel buttonsPanel;

    /**
     * The home button to go back to the home screen.
     */
    private JButton home;

    /**
     * The restart button to restart the game.
     */
    private JButton restart;

    /**
     * The mute button to toggle the sound, on and off.
     */
    private JButton mute;

    /**
     * The icon of the sound button when muted.
     */
    private ImageIcon muteIcon;

    /**
     * The icon of the sound button when unmute.
     */
    private ImageIcon unmuteIcon;

    /**
     * An instance of the ButtonsPanel class
     */
    public static ButtonsPanel instance;

    /**
     * Creates an instance of the buttons Panel and all the game buttons added to it. 
     */
    public ButtonsPanel(final BaseFrame frame, JPanel panel, GameMode mode) {
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());
        buttonsPanel.setBackground(Color.BLUE);

        muteIcon = new ImageIcon(loadIcon("audio-mute.png"));
        unmuteIcon = new ImageIcon(loadIcon("audio-unmute.png"));

        home = createButton("home.png", new GoToHomeAction(frame));
        restart = createButton("restart.png", new RestartGameAction(mode));
        mute = createButton("audio-unmute.png", new ToggleAudioAction(panel, muteIcon, unmuteIcon));

        // Make the panel focusable
        buttonsPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                buttonsPanel.requestFocusInWindow();
            }
        });

        buttonsPanel.add(home);
        buttonsPanel.add(restart);
        buttonsPanel.add(mute);
    }

    /**
     * Gets the buttons panel.
     * @return The buttons panel
     */
    public JPanel getButtonsPanel() {
        return this.buttonsPanel;
    }

    /**
     * Gets the home button on the buttons panel.
     * @return The home button
     */
    public JButton getHomeButton() {
        return this.home;
    }

    /**
     * Gets the restart button on the buttons panel.
     * @return The restart button
     */
    public JButton getRestartButton() {
        return this.restart;
    }

    /**
     * Gets the mute button on the buttons panel.
     * @return The mute button
     */
    public JButton getMuteButton() {
        return this.mute;
    }

    /**
     * Creates a button to be added the buttons panel.
     * @param filename The name of the file that contains the icon.
     * @param listener The event listener called when the button is clicked.
     * @return A game button
     */
    private JButton createButton(String filename, ActionListener listener) {
        JButton button = new JButton();
        Image icon = loadIcon(filename);
        if (icon != null) {
            button.setIcon(new ImageIcon(icon));
        }
        button.addActionListener(listener);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(false);

        return button;
    }

    /**
     * Gets the icon from a file
     * @param file The location of the file
     * @return The icon in the file
     */
    private Image loadIcon(String file) {
        String source = "/images/" + file; 
        try {
            return ImageIO.read(getClass().getResource(source));
        } catch (IOException e) {
            System.err.println("Error loading the " + file + ": " + e.getMessage());
            return null;
        }
    }
}
