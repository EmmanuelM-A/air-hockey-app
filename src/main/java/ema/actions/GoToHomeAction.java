package ema.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ema.audio.AudioPlayer;
import ema.ui.BaseFrame;
import ema.ui.game.singlePlayer.SinglePlayerFrame;
import ema.ui.game.singlePlayer.SinglePlayerGame;
import ema.ui.game.twoPlayer.TwoPlayerFrame;
import ema.ui.game.twoPlayer.TwoPlayerGame;
import ema.ui.home.HomeMenuFrame;

/**
 * This class handles the go to home action when the home button is clicked on any frame or panel.
 */
public class GoToHomeAction implements ActionListener {
    /**
     * The frame that is currently being displayed and from which the home button was clicked.
     */
    private BaseFrame frame;

    /**
     * Constructs a button action for different game frames.
     * @param frame The current frame the action is being called from.
     */
    public GoToHomeAction(BaseFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Stop any running audio clips
        AudioPlayer.stopCurrentClip();

        if(frame instanceof SinglePlayerFrame) { // If the frame is the single player game
            SinglePlayerGame.instance.stopGame();
        } else if(frame instanceof TwoPlayerFrame) { // If the frame is the two playrt game
            TwoPlayerGame.instance.stopGame();
        }

        // Switch to that frame
        frame.switchFrame(new HomeMenuFrame());
    }
}
