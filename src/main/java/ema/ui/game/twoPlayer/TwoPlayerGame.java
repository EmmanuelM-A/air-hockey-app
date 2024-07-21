package ema.ui.game.twoPlayer;

import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

import ema.components.GameText;
import ema.components.Goal;
import ema.components.Paddle;
import ema.components.Puck;
import ema.components.Score;
import ema.mechanics.GameEngine;

public class TwoPlayerGame extends GameEngine {
    private final int PANEL_WIDTH = 1000;
    private final int PANEL_HEIGHT = 600;
    
    private double friction;
    private int winningPoints;

    private final int WIDTH = 920;
    private final int HEIGHT = 520;

    /**
     * The outer panel of the board.
     */
    private JPanel borderPanel;

    /**
     * The panel that represents the actual playing area where components are added.
     */
    private JPanel gamePanel;

    /**
     * The paddles the players control on the board.
     */
    private Paddle leftPaddle, rightPaddle;

    /**
     * The goals on either side of the board.
     */
    private Goal leftGoal, rightGoal;

    /**
     * The puck that is on the board.
     */
    private Puck puck;

    /**
     * Holds the keys that were pressed
     */
    private final Set<Integer> pressedKeys = new HashSet<>();

    /**
     * Represents the textbox, top of the board.
     */
    private GameText topLabel;

    /**
     * Represents the left score and the right score on either side of the board.
     */
    private Score leftScore, rightScore;

    /**
     * An instance of the GamePanel
     */
    public static TwoPlayerGame instance;

    @Override
    protected void init() {
        this.friction = 0.005;
        this.winningPoints = 8;

        this.borderPanel = new JPanel();
        this.gamePanel = new JPanel();


    }

    @Override
    protected void loadGame() {}

    @Override
    protected void setGameSettings() {}

    @Override
    protected void startGame() {}

    @Override
    protected void resetGame() {}

    @Override
    protected void stopGame() {}

    @Override
    protected void runGame() {
        init();
        loadGame();
        setGameSettings();
        startGame();
    }
    
}
