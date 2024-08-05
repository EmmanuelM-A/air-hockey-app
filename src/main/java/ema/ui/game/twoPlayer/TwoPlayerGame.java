package ema.ui.game.twoPlayer;

import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.Timer;

import ema.audio.AudioPlayer;
import ema.components.GameText;
import ema.components.Goal;
import ema.components.Paddle;
import ema.components.Puck;
import ema.components.Score;
import ema.mechanics.GameEngine;
import ema.ui.scoreboard.AddScorePopup;

public class TwoPlayerGame extends GameEngine {
    private final int PANEL_WIDTH = 1000;
    private final int PANEL_HEIGHT = 600;
    
    private double friction;
    private int winningPoints;

    private final int WIDTH = 920;
    private final int HEIGHT = 520;

    private boolean hasEndAudioPlayed;

    private int centralDiameter;

    private JPanel outerPanel;

    private JPanel innerPanel;

    private Paddle leftPaddle, rightPaddle;

    private Goal leftGoal, rightGoal;

    private Puck puck;

    private Set<Integer> pressedKeys;

    private GameText topLabel;

    private Score leftScore, rightScore;

    public static TwoPlayerGame instance;

    private boolean hasScoreDialogShown;

    private Timer gameLoop;

    public TwoPlayerGame() {

    }

    @Override
    protected void init() {
        // Default values
        this.friction = 0.005;
        this.winningPoints = 8;

        // Initialise the border panel
        this.outerPanel = new JPanel();
        outerPanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        outerPanel.setBackground(Color.BLACK);
        outerPanel.setLayout(new GridBagLayout());

        // Initialise the game panel
        this.innerPanel = new JPanel();

        // Initialise game components
        this.puck = new Puck(new Point((WIDTH - Puck.DIAMETER) / 2, (HEIGHT / 2) - 20));
        this.leftGoal = new Goal(new Point(0, 185), "leftGoal");
        this.rightGoal = new Goal(new Point(905, 185), "rightGoal");
        this.leftPaddle = new Paddle(
            new Point((WIDTH - Paddle.DIAMETER) / 6, (HEIGHT - Paddle.DIAMETER) / 2), 
            new int[]{KeyEvent.VK_A, KeyEvent.VK_W, KeyEvent.VK_D, KeyEvent.VK_S},
            new int[]{0, WIDTH / 2, 0, HEIGHT},
            "Player one"
        );
        this.rightPaddle = new Paddle(
            new Point((WIDTH - Paddle.DIAMETER) *  5/6, (HEIGHT - Paddle.DIAMETER) / 2),
            new int[]{KeyEvent.VK_LEFT, KeyEvent.VK_UP, KeyEvent.VK_RIGHT, KeyEvent.VK_DOWN},
            new int[]{WIDTH / 2, WIDTH, 0, HEIGHT},
            "Player two"
        );
        this.leftScore = new Score(0);
        this.rightScore = new Score(0);
        this.topLabel = new GameText("Welcome Players");

        // Initialise game helper variables
        this.hasEndAudioPlayed = false;
        this.pressedKeys = new HashSet<>();
        this.centralDiameter = 150;
        this.hasScoreDialogShown = false;
    }

    @Override
    protected void loadGame() {
        // Add game components to inner panel
        this.innerPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawObjects(g);
            }
        };
        innerPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        innerPanel.setBackground(Color.WHITE);

        innerPanel.add(leftPaddle);
        innerPanel.add(rightPaddle);
        innerPanel.add(puck);

        outerPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                pressedKeys.add(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                pressedKeys.remove(e.getKeyCode());
            }
        });

        outerPanel.setFocusable(true);
        outerPanel.requestFocusInWindow();

        outerPanel.add(innerPanel, new GridBagConstraints());
    }

    @Override
    protected void setGameSettings() {}

    @Override
    protected void startGame() {
        if(gameLoop != null) {
            gameLoop.start();
        }
    }

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

    private void handleGoals() {
        if(puck.goalDetected(leftGoal)) {
            if(leftGoal.getIsGoal() == false) {
                //topLabel.getLabel().setText(playerPaddle.getName() + " scored a goal!");
                rightScore.incrementScore();
                leftGoal.setIsGoal(true);
                nextRound();
                AudioPlayer.play("goal-sound.wav");
            }
        } else if(puck.goalDetected(rightGoal)) {
            if(rightGoal.getIsGoal() == false) {
                //topLabel.getLabel().setText(aiPaddle.getName() + " scored a goal!");
                leftScore.incrementScore();
                rightGoal.setIsGoal(true);
                nextRound();
                AudioPlayer.play("goal-sound.wav");
            }
        }
    }

    private void nextRound() {
        // If left player scored, position the puck on the right side next round
        if((leftGoal.getIsGoal() == false && rightGoal.getIsGoal() == true) || (leftGoal.getIsGoal() == true && rightGoal.getIsGoal() == false)) {
            puck.setLocation(new Point((WIDTH - Puck.DIAMETER) / 2, (HEIGHT / 2) - 20));
        }

        // Reset the location of the paddles
        aiPaddle.setLocation(new Point((WIDTH - Paddle.DIAMETER) / 6, (HEIGHT - Paddle.DIAMETER) / 2));
        playerPaddle.setLocation(new Point((WIDTH - Paddle.DIAMETER) *  5/6, (HEIGHT - Paddle.DIAMETER) / 2));

        // Reset the velocity of the puck to zero
        puck.setXVelocity(0.0);
        puck.setYVelocity(0.0);

        // Reset the is goal booleans of the goals to false
        leftGoal.setIsGoal(false);
        rightGoal.setIsGoal(false);
    }

    private void handlePlayerWin() {
        // Disable paddle movemenets
        aiPaddle.setIsPaddleDisabled(true);
        playerPaddle.setIsPaddleDisabled(true);

        // Play winning audio file after a delay
        if(hasEndAudioPlayed == false) {
            new java.util.Timer().schedule( 
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        AudioPlayer.play("game-end.wav");
                    }
                }, 1500
            );
            hasEndAudioPlayed = true;
        }

        // Display winning message
        if(leftScore.getScore() == rightScore.getScore()) {
            topLabel.getLabel().setText("Draw");
        } else
        if(leftScore.getScore() > rightScore.getScore()) {
            topLabel.getLabel().setText(aiPaddle.getName() + " Wins! Better luck next time");
        } else  if (rightScore.getScore() > leftScore.getScore() && hasPopupShown == false){
            hasPopupShown = true;
            topLabel.getLabel().setText(playerPaddle.getName() + " Wins!");

            // Display insert score box
            new AddScorePopup(rightScore.getScore());
        }
    }

    private void handleWin() {
        if(countDownTimer.getTimeRemaining() == 0) {
            handlePlayerWin();
            stopGame();
        }
    }

    private void drawObjects(Graphics g) {
        int diameter = 150;
        // Draws the central line
        g.setColor(Color.BLACK);
        int centerX = WIDTH / 2;
        g.drawLine(centerX, 0, centerX, (HEIGHT - diameter) / 2);
        g.drawLine(centerX, (HEIGHT + diameter) / 2, centerX, HEIGHT);

        // Draws the central circle
        int x = (WIDTH - diameter) / 2;
        int y = (HEIGHT - diameter) / 2;
        g.drawOval(x, y, diameter, diameter);

        // Draws Puck
        puck.draw(g);

        // Draws Goals
        leftGoal.draw(g);
        rightGoal.draw(g);

        // Draws the Paddles
        aiPaddle.draw(g);
        playerPaddle.draw(g);
    }

    @Override
    public void restart() {
        // Reset object locations
        aiPaddle.setLocation(new Point((WIDTH - Paddle.DIAMETER) / 6, (HEIGHT - Paddle.DIAMETER) / 2));
        playerPaddle.setLocation(new Point((WIDTH - Paddle.DIAMETER) *  5/6, (HEIGHT - Paddle.DIAMETER) / 2));
        puck.setLocation(new Point((WIDTH - Puck.DIAMETER) / 2, (HEIGHT / 2) - 20));

        // Reset object velocities and default values
        puck.setXVelocity(0.0);
        puck.setYVelocity(0.0);

        aiPaddle.setIsPaddleDisabled(false);
        playerPaddle.setIsPaddleDisabled(false);

        hasEndAudioPlayed = false;

        // Reset scores and game text
        leftScore.setScore(0);
        rightScore.setScore(0);

        topLabel.getLabel().setText("Welcome challenger!");

        // Request focus to ensure key events are captured
        outerPanel.requestFocusInWindow();

        hasPopupShown = false;

        // Play game start audio
        AudioPlayer.play("game-start.wav");

        // Start count down timer
        countDownTimer.restartCountDown();

        startGame();
    }
    
}
