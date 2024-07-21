package ema.mechanics;

public abstract class GameEngine {
    /**
     * Initilise the game objects and components.
     */
    protected abstract void init();

    /**
     * Loads the game onto the screen.
     */
    protected abstract void loadGame();

    /** Sets the game settings/mode */
    protected abstract void setGameSettings();

    /**
     * Starts the game frame timer
     */
    protected abstract void startGame();

    /**
     * Runs game
     */
    protected abstract void runGame();

    /**
     * Resets the game
     */
    protected abstract void resetGame();

    /**
     * Ends the game timer
     */
    protected abstract void stopGame();

}
