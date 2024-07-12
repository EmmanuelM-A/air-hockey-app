package ema.mechanics;

public abstract class GameEngine {
    public void startGame() {
        loadGame();
        initObjects();
        addObjects();
        runGame();
    }

    protected abstract void loadGame();

    protected abstract void initObjects();

    protected abstract void addObjects();

    protected abstract void runGame();

}
