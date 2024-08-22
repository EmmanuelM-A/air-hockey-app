package ema.ui.game.singlePlayer;

import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;

import javax.swing.*;

import ema.audio.AudioPlayer;
import ema.components.*;
import ema.mechanics.AIPaddleControls;
import ema.mechanics.Difficulty;
import ema.mechanics.GameEngine;
import ema.mechanics.PaddleControls;
import ema.mechanics.Scoring;
import ema.ui.game.settings.SinglePlayerSettings;
import ema.ui.scoreboard.AddScorePopup;

public class SinglePlayerGame extends GameEngine {
    /**
     * The WIDTH of the outer panel.
     */
    public static final int PANEL_WIDTH = 1000;

    /**
     * The HEIGHT of the outer panel.
     */
    public static final int PANEL_HEIGHT = 600;

    /**
     * The firction on the board.
     */
    private static final double FRICTION = 0.005;

    /**
     * Determines whether the game-end audio has played or not;
     */
    private boolean hasEndAudioPlayed;

    /**
     * The WIDTH of the inner panel.
     */
    private final int WIDTH = 920;

    /**
     * The HEIGHT of the inner panel.
     */
    private final int HEIGHT = 520;

    /**
     * The outer panel of the board.
     */
    private JPanel outerPanel;

    /**
     * The panel that represents the actual playing area where components are added.
     */
    private JPanel innerPanel;

    /**
     * The paddles the players control on the board.
     */
    private AIPaddle aiPaddle;
    

    private Paddle playerPaddle;

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
    private Set<Integer> pressedKeys;

    /**
     * Represents the textbox, top of the board.
     */
    private GameText topLabel;
     
    private CountDown countDownTimer;

    /**
     * Represents the left score and the right score on either side of the board.
     */
    private Score leftScore, rightScore;

    private int countDown;

    private long timePuckOnAISide = 0;

    /**
     * Represents 4 seconds
     */
    private final long MAX_TIME_PUCK_ON_AI_SIDE = 4000; 

    private boolean hasAddScoreShown = false;

    private Timer gameLoop;

    /**
     * An instance of the GamePanel
     */
    public static SinglePlayerGame instance;

    public SinglePlayerGame(Score leftScore, Score rightScore, final GameText topLabel) {
        this.leftScore = leftScore;
        this.rightScore = rightScore;
        this.topLabel = topLabel;

        runGame();

        instance = this;
    }

    public JPanel getOuterPanel() {
        return this.outerPanel;
    }

    public JPanel getInnerPanel() {
        return this.innerPanel;
    }

    public int getWIDTH() {
        return this.WIDTH;
    }

    public int getHeight() {
        return this.HEIGHT;
    }

    public Paddle getaiPaddle() {
        return this.aiPaddle;
    }

    public Paddle getplayerPaddle() {
        return this.playerPaddle;
    }

    public Puck getPuck() {
        return this.puck;
    }

    public Goal getLeftGoal() {
        return this.leftGoal;
    }

    public Goal getRightGoal() {
        return this.rightGoal;
    }

    private void handleGoals() {
        if(puck.goalDetected(leftGoal)) {
            if(leftGoal.getIsGoal() == false) {
                //topLabel.getLabel().setText(playerPaddle.getName() + " scored a goal!");
                rightScore.incrementScore();
                leftGoal.setIsGoal(true);
                resetGame();
                AudioPlayer.play("goal-sound.wav");
            }
        } else if(puck.goalDetected(rightGoal)) {
            if(rightGoal.getIsGoal() == false) {
                //topLabel.getLabel().setText(aiPaddle.getName() + " scored a goal!");
                leftScore.incrementScore();
                rightGoal.setIsGoal(true);
                resetGame();
                AudioPlayer.play("goal-sound.wav");
            }
        }
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
        } else if(leftScore.getScore() > rightScore.getScore()) {
            topLabel.getLabel().setText(aiPaddle.getName() + " Wins! Better luck next time");
        } else  if (rightScore.getScore() > leftScore.getScore() && hasAddScoreShown == false){
            hasAddScoreShown = true;
            topLabel.getLabel().setText(playerPaddle.getName() + " Wins!");

            // Calculate Score
            int score = Scoring.calculateScore(aiPaddle.getDifficulty().getDiffMultiplier(), countDown, rightScore.getScore());

            System.out.println("Player Score: " + score);

            // Display insert score box
            new AddScorePopup(score);
        }
    }

    private void handleWin() {
        if(countDownTimer.getTimeRemaining() == 0) {
            handlePlayerWin();
            stopGame();
        }
    }

    private void resetIfPuckStuck() {
        if(puck.getLocation().x < WIDTH / 2) {
            if(timePuckOnAISide == 0) {
                timePuckOnAISide = System.currentTimeMillis();
            } else {
                long timeOnAISide = System.currentTimeMillis() - timePuckOnAISide;
                if(timeOnAISide >= MAX_TIME_PUCK_ON_AI_SIDE) {
                    resetGame();
                    timePuckOnAISide = 0; // Reset the timer
                }
            }
        } else {
            timePuckOnAISide = 0;
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

        hasAddScoreShown = false;

        // Play game start audio
        AudioPlayer.play("game-start.wav");

        // Start count down timer
        countDownTimer.restartCountDown();

        startGame();
    }

    @Override
    protected void init() {
        // Default values
        this.countDownTimer = new CountDown(120, topLabel);

        // Initialise the border panel
        this.outerPanel = new JPanel();
        outerPanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        outerPanel.setBackground(Color.BLACK);
        outerPanel.setLayout(new GridBagLayout());

        // Initialise the game components
        puck = new Puck(new Point((WIDTH - Puck.DIAMETER) / 2, (HEIGHT / 2) - 20));
        leftGoal = new Goal(new Point(0, 185), "leftGoal");
        rightGoal = new Goal(new Point(905, 185), "rightGoal");
        aiPaddle = new AIPaddle(
            new Point((WIDTH - Paddle.DIAMETER) / 6, (HEIGHT - Paddle.DIAMETER) / 2), 
            null,
            new int[]{0, WIDTH / 2, 0, HEIGHT},
            "MegaBot"
        );
        playerPaddle = new Paddle(
            new Point((WIDTH - Paddle.DIAMETER) *  5/6, (HEIGHT - Paddle.DIAMETER) / 2),
            new int[]{KeyEvent.VK_LEFT, KeyEvent.VK_UP, KeyEvent.VK_RIGHT, KeyEvent.VK_DOWN},
            new int[]{WIDTH / 2, WIDTH, 0, HEIGHT},
            "Challenger"
        );

        // Initialise game helper variables
        this.hasEndAudioPlayed = false;
        this.hasAddScoreShown = false;
        this.pressedKeys = new HashSet<>();
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

        innerPanel.add(aiPaddle);
        innerPanel.add(playerPaddle);
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
        
        innerPanel.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                PaddleControls.movePaddleWithMouse(e, playerPaddle, innerPanel);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                PaddleControls.movePaddleWithMouse(e, playerPaddle, innerPanel);
            }
        });

        outerPanel.setFocusable(true);
        outerPanel.requestFocusInWindow();

        outerPanel.add(innerPanel, new GridBagConstraints());
    }

    @Override
    public void setGameSettings() {
        Difficulty aiDifficulty = SinglePlayerSettings.instance.getDiffLevel();
        this.countDown = SinglePlayerSettings.instance.getCountDown();
        countDownTimer.setTime(countDown);
        aiPaddle.setDifficulty(aiDifficulty);
    }

    @Override
    protected void runGameLoop() {
        gameLoop = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        AIPaddleControls.movePaddle(aiPaddle, innerPanel, aiPaddle.getDifficulty().getSpeedMultiplier());
        
                        puck.handleCollsions(aiPaddle);
                        puck.handleCollsions(playerPaddle);
        
                        puck.handleWallCollisions(WIDTH, 0, HEIGHT, 0);
        
                        puck.move();
        
                        handleGoals();
        
                        puck.setXVelocity(puck.getXVelocity() - (puck.getXVelocity() * FRICTION));
                        puck.setYVelocity(puck.getYVelocity() - (puck.getYVelocity() * FRICTION));
        
                        handleWin();

                        resetIfPuckStuck();
        
                        innerPanel.repaint();
                    }
                });
            }
        });
    }

    @Override
    protected void startGame() {
        if(gameLoop != null) {
            gameLoop.start();
        }
    }

    @Override
    protected void runGame() {
        init();
        setGameSettings();
        loadGame();
        runGameLoop();
        startGame();
    }

    @Override
    protected void resetGame() {
        // Reset the puck's location
        puck.setLocation(new Point((WIDTH - Puck.DIAMETER) / 2, (HEIGHT / 2) - 20));

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

    @Override
    public void stopGame() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
    }
}
