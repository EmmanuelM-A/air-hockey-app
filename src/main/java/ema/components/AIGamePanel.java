package ema.components;

import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;

import javax.swing.*;

import ema.audio.AudioPlayer;
import ema.mechanics.AIPaddleControls;
import ema.mechanics.Difficulty;
import ema.mechanics.Restartable;
import ema.ui.scoreboard.AddScorePopup;
import ema.mechanics.PaddleControls;

public class AIGamePanel implements Restartable {
    /**
     * The width of the outer panel.
     */
    public static final int PANEL_WIDTH = 1000;

    /**
     * The height of the outer panel.
     */
    public static final int PANEL_HEIGHT = 600;
    /**
     * The firction on the board.
     */
    public static final double FRICTION = 0.005;

    /**
     * Determines whether the game-end audio has played or not;
     */
    private boolean hasEndAudioPlayed;

    /**
     * The width of the inner panel.
     */
    private int width = 920;

    /**
     * The height of the inner panel.
     */
    private int height = 520;

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
    private final Set<Integer> pressedKeys = new HashSet<>();

    /**
     * Represents the textbox, top of the board.
     */
    private GameText topLabel;
     
    private CountDown countDownTimer;

    /**
     * Represents the left score and the right score on either side of the board.
     */
    private Score leftScore, rightScore;

    /**
     * An instance of the GamePanel
     */
    public static AIGamePanel instance;

    private boolean hasPopupShown = false;

    private Timer timer;

    /**
     * Creates the game panel where all game components are added.
     * @param leftScore The score on the left side of the board
     * @param rightScore The score on the right side of the board
     * @param topLabel The game message, top of the board
     * @param bottomLabel The buttons panel, bottom of the board
     */
    public AIGamePanel(Score leftScore, Score rightScore, final GameText topLabel) {
        this.leftScore = leftScore;
        this.rightScore = rightScore;
        this.topLabel = topLabel;
        this.countDownTimer = new CountDown(30, topLabel);

        this.outerPanel = new JPanel();
        outerPanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        outerPanel.setBackground(Color.BLACK);
        outerPanel.setLayout(new GridBagLayout());

        this.innerPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawObjects(g);
            }
        };
        innerPanel.setPreferredSize(new Dimension(width, height));
        innerPanel.setBackground(Color.WHITE);

        outerPanel.add(innerPanel, new GridBagConstraints());

        init();

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

        outerPanel.setFocusable(true);
        outerPanel.requestFocusInWindow();
        
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

        timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        AIPaddleControls.movePaddle(aiPaddle, innerPanel);
        
                        puck.handleCollsions(aiPaddle);
                        puck.handleCollsions(playerPaddle);
        
                        puck.handleWallCollisions(width, 0, height, 0);
        
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

        timer.start();

        instance = this;
    }

    public void init() {
        puck = new Puck(new Point((width - Puck.DIAMETER) / 2, (height / 2) - 20));
        leftGoal = new Goal(new Point(0, 185), "leftGoal");
        rightGoal = new Goal(new Point(905, 185), "rightGoal");
        aiPaddle = new AIPaddle(
            new Point((width - Paddle.DIAMETER) / 6, (height - Paddle.DIAMETER) / 2), 
            null,
            new int[]{0, width / 2, 0, height},
            "MegaBot",
            Difficulty.NORMAL
        );
        playerPaddle = new Paddle(
            new Point((width - Paddle.DIAMETER) *  5/6, (height - Paddle.DIAMETER) / 2),
            new int[]{KeyEvent.VK_LEFT, KeyEvent.VK_UP, KeyEvent.VK_RIGHT, KeyEvent.VK_DOWN},
            new int[]{width / 2, width, 0, height},
            "Challenger"
        );

        hasEndAudioPlayed = false;
    }

    public JPanel getOuterPanel() {
        return this.outerPanel;
    }

    public JPanel getInnerPanel() {
        return this.innerPanel;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
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

    public void stopGame() {
        if (timer != null) {
            timer.stop();
        }
    }

    public void startGame() {
        if(timer != null) {
            timer.start();
        }
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
            puck.setLocation(new Point((width - Puck.DIAMETER) / 2, (height / 2) - 20));
        }

        // Reset the location of the paddles
        aiPaddle.setLocation(new Point((width - Paddle.DIAMETER) / 6, (height - Paddle.DIAMETER) / 2));
        playerPaddle.setLocation(new Point((width - Paddle.DIAMETER) *  5/6, (height - Paddle.DIAMETER) / 2));

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
        int centerX = width / 2;
        g.drawLine(centerX, 0, centerX, (height - diameter) / 2);
        g.drawLine(centerX, (height + diameter) / 2, centerX, height);

        // Draws the central circle
        int x = (width - diameter) / 2;
        int y = (height - diameter) / 2;
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
        aiPaddle.setLocation(new Point((width - Paddle.DIAMETER) / 6, (height - Paddle.DIAMETER) / 2));
        playerPaddle.setLocation(new Point((width - Paddle.DIAMETER) *  5/6, (height - Paddle.DIAMETER) / 2));
        puck.setLocation(new Point((width - Puck.DIAMETER) / 2, (height / 2) - 20));

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
