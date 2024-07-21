package ema.ui.game.twoPlayer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import ema.audio.AudioPlayer;
import ema.components.ButtonsPanel;
import ema.components.GamePanel;
import ema.components.GameText;
import ema.components.Score;
import ema.mechanics.GameMode;
import ema.ui.BaseFrame;

public class TwoPlayerFrame extends BaseFrame {
    /**
     * The width of the frame
     */
    public static final int FRAME_WIDTH = 1200;
    /**
     * The height of the frame
     */
    public static final int FRAME_HEIGHT = 800;

    /**
     * The background colour of the game
     */
    public static final Color MAIN_COLOUR = Color.BLUE;

    public static TwoPlayerFrame instance;

    private GamePanel gamePanel;

    /**
     * The frame that holds the game
     */
    public TwoPlayerFrame() {
        super.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        super.setLocationRelativeTo(null);
        super.setResizable(false);
        super.getContentPane().setBackground(MAIN_COLOUR);
        super.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new java.awt.Insets(10, 10, 10, 10);

        // Create and add the left score
        Score leftScore = new Score(0);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(leftScore.getLabel(), gbc);

        // Create and add the right score
        Score rightScore = new Score(0);
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(rightScore.getLabel(), gbc);

        // Create and add the top label
        GameText topLabel = new GameText("Welcome!");
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.SOUTH;
        add(topLabel.getLabel(), gbc);

        // Create and add the game panel depending on the game mode selected
        gamePanel = new GamePanel(leftScore, rightScore, topLabel);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(gamePanel.getOuterPanel(), gbc);

        // Create and add the game buttons to the bottom panel 
        ButtonsPanel.instance = new ButtonsPanel(this, gamePanel.getOuterPanel(), GameMode.TWOPLAYER);
        ButtonsPanel.instance.getButtonsPanel().setBackground(MAIN_COLOUR);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.NORTH;
        add(ButtonsPanel.instance.getButtonsPanel(), gbc);

        this.setVisible(true);

        // Play the game start audio.
        AudioPlayer.play("game-start.wav");

        instance = this;
    }
}
