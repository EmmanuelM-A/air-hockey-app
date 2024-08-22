package ema.actions;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ema.mechanics.GameMode;
import ema.ui.game.singlePlayer.SinglePlayerGame;
import ema.ui.game.twoPlayer.TwoPlayerGame;

/**
 * This calss handles the restart action when the restart button is clicked in either game modes.
 */
public class RestartGameAction implements ActionListener {
    /**
     * The current game mode that is being played by the user.
     */
    private GameMode mode;

    /**
     * Constructs a button action for diffrent game modes.
     * @param mode The current game mode.
     */
    public RestartGameAction(GameMode mode) {
        this.mode = mode;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(mode == GameMode.SINGLEPLAYER) { // Restart the single player game
            SinglePlayerGame.instance.restart();
        } else if(mode == GameMode.TWOPLAYER) { // Restart the two player game
            TwoPlayerGame.instance.restart();
        }
    }
}
