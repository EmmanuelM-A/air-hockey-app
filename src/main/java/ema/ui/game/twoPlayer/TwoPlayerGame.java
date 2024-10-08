package ema.ui.game.twoPlayer;

import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import ema.audio.AudioPlayer;
import ema.components.GameText;
import ema.components.Goal;
import ema.components.Paddle;
import ema.components.Puck;
import ema.components.Score;
import ema.mechanics.GameEngine;
import ema.mechanics.PaddleControls;
import ema.ui.game.settings.TwoPlayerSetting;

/**
 * This class handles the creation of the game, the loading of its components and running of all the functions in the game loop.
 */
public class TwoPlayerGame extends GameEngine {
    /**
     * The width of the outer panel.
     */
    private static final int PANEL_WIDTH = 1000;

    /**
     * The height of the outer panel.
     */
    private static final int PANEL_HEIGHT = 600;
    
    /**
     * The friction on the baord.
     */
    private static final double FRICTION = 0.005;

    /**
     * The points required to win a match.
     */
    private int winningPoints;

    /**
     * The width of the game (inner) panel.
     */
    private static final int WIDTH = 920;

    /**
     * The heihgt of the game (inner) panel.
     */
    private static final int HEIGHT = 520;

    /**
     * Used to play the end game audio once.
     */
    private boolean hasEndAudioPlayed;

    /**
     * The outer panel of the game panel.
     */
    private JPanel outerPanel;

    /**
     * The inner panel of the game panel.
     */
    private JPanel innerPanel;

    /**
     * The player paddle objects.
     */
    private Paddle leftPaddle, rightPaddle;

    /**
     * The player goal objects.
     */
    private Goal leftGoal, rightGoal;

    /**
     * The puck on the game panel.
     */
    private Puck puck;

    /**
     * The set of keys the players will use to move their respective paddles.
     */
    private Set<Integer> pressedKeys;

    /**
     * The top label used to display game messages.
     */
    private GameText topLabel;

    /**
     * Used to display the player scores next to their respective goals.
     */
    private Score leftScore, rightScore;

    /**
     * An instance of the game.
     */
    public static TwoPlayerGame instance;

    /**
     * The game loop that where the game runs.
     */
    private Timer gameLoop;

    /**
     * Creates the game.
     * @param leftScore The left score to display the AI's score.
     * @param rightScore The right score to display the player's score.
     * @param topLabel The top label for displaying game messages.
     */
    public TwoPlayerGame(Score leftScore, Score rightScore, GameText topLabel) {
        this.leftScore = leftScore;
        this.rightScore = rightScore;
        this.topLabel = topLabel;

        runGame();

        instance = this;
    }

    /**
     * Gets the outer game panel.
     * @return The outer panel.
     */
    public JPanel getOuterPanel() {
        return this.outerPanel;
    }

    /**
     * Gets the inner game panel.
     * @return The inner panel.
     */
    public JPanel getInnerPanel() {
        return this.innerPanel;
    }

    /**
     * Gets the left player's paddle.
     * @return The left paddle object.
     */
    public Paddle getleftPaddle() {
        return this.leftPaddle;
    }

    /**
     * Gets the right player's paddle.
     * @return The right paddle object.
     */
    public Paddle getrightPaddle() {
        return this.rightPaddle;
    }

    /**
     * Gets the puck.
     * @return The puck object.
     */
    public Puck getPuck() {
        return this.puck;
    }

    /**
     * Gets the left goal.
     * @return The left goal object.
     */
    public Goal getLeftGoal() {
        return this.leftGoal;
    }

    /**
     * Gets the right goal.
     * @return The right goal object.
     */
    public Goal getRightGoal() {
        return this.rightGoal;
    }

    @Override
    public void init() {
        // Initialise the border panel
        this.outerPanel = new JPanel();
        outerPanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        outerPanel.setBackground(Color.BLACK);
        outerPanel.setLayout(new GridBagLayout());

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

        // Initialise game helper variables
        this.hasEndAudioPlayed = false;
        this.pressedKeys = new HashSet<>();
    }

    @Override
    public void loadGame() {
        // Add game components to inner panel
        this.innerPanel = new JPanel(null) {
            @Override
            public void paintComponent(Graphics g) {
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
    public void setGameSettings() {
        this.winningPoints = TwoPlayerSetting.instance.getWinningPoints();
        //System.out.println("Winning points: " + winningPoints);
    }

    @Override
    public void startGame() {
        if(gameLoop != null) {
            gameLoop.start();
        }
    }

    @Override
    public void resetGame() {
        // If left player scored, position the puck on the right side next round
        if(leftGoal.getIsGoal() == false && rightGoal.getIsGoal() == true) {
            puck.setLocation(new Point(((WIDTH - Puck.DIAMETER) / 2) + 75, (HEIGHT / 2) - 20));
        } else
        // If right player scored, position the puck on the left side next round
        if(leftGoal.getIsGoal() == true && rightGoal.getIsGoal() == false) {
            puck.setLocation(new Point(((WIDTH - Puck.DIAMETER) / 2) - 75, (HEIGHT / 2) - 20));
        }
        // Reset the location of the paddles
        leftPaddle.setLocation(new Point((WIDTH - Paddle.DIAMETER) / 6, (HEIGHT - Paddle.DIAMETER) / 2));
        rightPaddle.setLocation(new Point((WIDTH - Paddle.DIAMETER) *  5/6, (HEIGHT - Paddle.DIAMETER) / 2));

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

    @Override
    public void runGameLoop() {
        gameLoop = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        PaddleControls.movePaddle(leftPaddle, pressedKeys, innerPanel);
                        PaddleControls.movePaddle(rightPaddle, pressedKeys, innerPanel);
        
                        puck.handleCollsions(leftPaddle);
                        puck.handleCollsions(rightPaddle);
        
                        puck.handleWallCollisions(WIDTH, 0, HEIGHT, 0);
        
                        puck.move();
        
                        handleGoals();
        
                        puck.setXVelocity(puck.getXVelocity() - (puck.getXVelocity() * FRICTION));
                        puck.setYVelocity(puck.getYVelocity() - (puck.getYVelocity() * FRICTION));
        
                        handleWin();
        
                        innerPanel.repaint();
                    }
                });
            }
        });
    }

    @Override
    public void runGame() {
        setGameSettings();
        init();
        loadGame();
        runGameLoop();
        startGame();
    }

    /**
     * Handles the steps involved when a goal is scored.
     */
    private void handleGoals() {
        if(puck.goalDetected(leftGoal)) {
            if(leftGoal.getIsGoal() == false) {
                topLabel.getLabel().setText(rightPaddle.getName() + " scored a goal!");
                rightScore.incrementScore();
                leftGoal.setIsGoal(true);
                resetGame();
                AudioPlayer.play("goal-sound.wav");
            }
        } else if(puck.goalDetected(rightGoal)) {
            if(rightGoal.getIsGoal() == false) {
                topLabel.getLabel().setText(leftPaddle.getName() + " scored a goal!");
                leftScore.incrementScore();
                rightGoal.setIsGoal(true);
                resetGame();
                AudioPlayer.play("goal-sound.wav");
            }
        }
    }

    /**
     * handles win a player wins and following game message outputs.
     */
    private void handlePlayerWin() {
        // Display winning message
        if(leftScore.getScore() == rightScore.getScore()) {
            topLabel.getLabel().setText("Draw");
        } else
        if(leftScore.getScore() == winningPoints) {
            topLabel.getLabel().setText(leftPaddle.getName() + " Wins!");
        } else
        if(rightScore.getScore() == winningPoints) {
            topLabel.getLabel().setText(rightPaddle.getName() + " Wins!");
        }

        // Disable paddle movemenets
        leftPaddle.setIsPaddleDisabled(true);
        rightPaddle.setIsPaddleDisabled(true);

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
    }

    /**
     * Handles when a player wins the match.
     */
    private void handleWin() {
        if(leftScore.getScore() == winningPoints) {
            handlePlayerWin();
        }
        if(rightScore.getScore() == winningPoints) {
            handlePlayerWin();
        }

    }

    /**
     * Draws the game components onto the game panel.
     * @param g The game panel's graphic instance.
     */
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
        leftPaddle.draw(g);
        rightPaddle.draw(g);
    }

    @Override
    public void restart() {
        // Reset object locations
        leftPaddle.setLocation(new Point((WIDTH - Paddle.DIAMETER) / 6, (HEIGHT - Paddle.DIAMETER) / 2));
        rightPaddle.setLocation(new Point((WIDTH - Paddle.DIAMETER) *  5/6, (HEIGHT - Paddle.DIAMETER) / 2));
        puck.setLocation(new Point((WIDTH - Puck.DIAMETER) / 2, (HEIGHT / 2) - 20));

        // Reset object velocities and default values
        puck.setXVelocity(0.0);
        puck.setYVelocity(0.0);

        leftPaddle.setIsPaddleDisabled(false);
        rightPaddle.setIsPaddleDisabled(false);

        hasEndAudioPlayed = false;

        // Reset scores and game text
        leftScore.setScore(0);
        rightScore.setScore(0);

        topLabel.getLabel().setText("");

        // Request focus to ensure key events are captured
        outerPanel.requestFocusInWindow();

        // Play game start audio
        AudioPlayer.play("game-start.wav");
    }    
}
