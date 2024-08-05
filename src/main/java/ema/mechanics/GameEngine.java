package ema.mechanics;

public abstract class GameEngine implements Restartable{
    /**
     * Initilise the game objects and components.
     */
    protected abstract void init();

    /**
     * Loads the game onto the screen.
     */
    protected abstract void loadGame();

    /** 
     * Sets the game settings/mode. 
     */
    public abstract void setGameSettings();

    /**
     * Runs the game frame loop..
     */
    protected abstract void runGameLoop();

    /**
     * Starts the game frame timer.
     */
    protected abstract void startGame();

    /**
     * Runs the game.
     */
    protected abstract void runGame();

    /**
     * Resets the game.
     */
    protected abstract void resetGame();

    /**
     * Ends the game timer.
     */
    public abstract void stopGame();

}
