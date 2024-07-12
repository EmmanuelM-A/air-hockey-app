package ema.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import ema.audio.AudioPlayer;
import ema.components.ButtonsPanel;

public class ToggleAudioAction implements ActionListener {
    private JPanel gamePanel;
    private ImageIcon muteIcon;
    private ImageIcon unmuteIcon;

    public ToggleAudioAction(JPanel gamePanel, ImageIcon muteIcon, ImageIcon unmuteIcon) {
        this.gamePanel = gamePanel;
        this.muteIcon = muteIcon;
        this.unmuteIcon = unmuteIcon;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        AudioPlayer.toggleSound();

        // Toggle the button icon
        if (AudioPlayer.isSoundDisabled()) {
            ButtonsPanel.instance.getMuteButton().setIcon(muteIcon);
        } else {
            ButtonsPanel.instance.getMuteButton().setIcon(unmuteIcon);
        }

        gamePanel.requestFocusInWindow();
    }
    
}
