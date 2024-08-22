package ema.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import ema.audio.AudioPlayer;
import ema.components.ButtonsPanel;

/**
 * This class handles the toggle audio action when the audio button is clicked on either game frame.
 */
public class ToggleAudioAction implements ActionListener {
    /**
     * The game panel of the current game frame.
     */
    private JPanel gamePanel;

    /**
     * The mute icon image. 
     */
    private ImageIcon muteIcon;

    /**
     * The unmute icon image.
     */
    private ImageIcon unmuteIcon;

    /**
     * Constructs a button action for different game frames.
     * @param gamePanel The current game panel.
     * @param muteIcon The mute icon image.
     * @param unmuteIcon The unmute icon image.
     */
    public ToggleAudioAction(JPanel gamePanel, ImageIcon muteIcon, ImageIcon unmuteIcon) {
        this.gamePanel = gamePanel;
        this.muteIcon = muteIcon;
        this.unmuteIcon = unmuteIcon;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Toogle audio
        AudioPlayer.toggleSound();

        // Toggle the button icon
        if (AudioPlayer.isSoundDisabled()) {
            ButtonsPanel.instance.getMuteButton().setIcon(muteIcon);
        } else {
            ButtonsPanel.instance.getMuteButton().setIcon(unmuteIcon);
        }
        
        // Request focus within the game panel again after button click
        gamePanel.requestFocusInWindow();
    }
    
}
