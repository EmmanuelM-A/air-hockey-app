package ema.ui.game.settings;

import javax.swing.*;

import ema.ui.game.twoPlayer.TwoPlayerFrame;
import ema.ui.home.HomeMenuFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class handles the creation of the two player game settings window.
 */
public class TwoPlayerSetting {
    /**
     * The game settings window.
     */
    private JDialog dialog;

    /**
     * The the winning game points selector.
     */
    private JSlider points;

    /**
     * The points header for the points selector.
     */
    private JLabel pointsHeader;

    /**
     * The buttons panel that contains the game settings buttons.
     */
    private JPanel buttonsPanel;

    /**
     * The cancel button used to exit the game settings window.
     */
    private JButton cancelBtn;

    /**
     * The default button thats set the game settings back to deafult.
     */
    private JButton defaultBtn;

    /**
     * The apply button that applys the game settings before the game is laoded.
     */
    private JButton applyBtn;

    private final Color MAIN_COLOUR = new Color(173, 216, 230);

    /**
     * The selected winning points
     */
    private int winningPoints;

    /**
     * The instance of the game settings window. 
     */
    public static TwoPlayerSetting instance;

    /**
     * Creates a game settings window assigned to the gome frame.
     * @param homeFrame The home screen.
     */
    public TwoPlayerSetting(JFrame homeFrame) {
        this.dialog = new JDialog(homeFrame, "Two Player Settings", true);
        this.points = new JSlider(1, 15, 8);
        this.pointsHeader = createLabel("Select the number of points to win:");
        this.buttonsPanel = new JPanel(new FlowLayout());
        this.cancelBtn = createButton("Cancel");
        this.defaultBtn = createButton("Default");
        this.applyBtn = createButton("Apply");
        this.winningPoints = 8;

        // Dialog Box
        dialog.setLocationRelativeTo(homeFrame);
        dialog.setPreferredSize(new Dimension(300, 200));
        dialog.setLayout(new BorderLayout());

        // Points selection
        points.setBackground(MAIN_COLOUR);
        points.setForeground(Color.WHITE);
        points.setPaintTrack(true);
        points.setPaintTicks(true);
        points.setPaintLabels(true);
        points.setMajorTickSpacing(2);
        points.setMinorTickSpacing(1);
        
        // Buttons
        buttonsPanel.setBackground(MAIN_COLOUR);
        buttonsPanel.add(cancelBtn);
        buttonsPanel.add(defaultBtn);
        buttonsPanel.add(applyBtn);

        // Add components to the dialog box
        dialog.add(pointsHeader, BorderLayout.NORTH);
        dialog.add(points, BorderLayout.CENTER);
        dialog.add(buttonsPanel, BorderLayout.SOUTH);

        // Button action listeners
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
            
        });

        defaultBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                points.setValue(8);
                winningPoints = 8;
            }
        });

        applyBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                winningPoints = points.getValue();
                dialog.dispose();
                HomeMenuFrame.instance.switchFrame(new TwoPlayerFrame());
            }
            
        });

        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.pack();
        dialog.setLocationRelativeTo(homeFrame);

        instance = this;
    }

    /**
     * Gets the game settings window.
     * @return The dialog box that contains the game components.
     */
    public JDialog getDialog() {
        return this.dialog;
    }

    /**
     * Gets the selected winning points.
     * @return The winning points.
     */
    public int getWinningPoints() {
        return this.winningPoints;
    }

    /**
     * Creates and styles a button for the game settings window.
     * @param title The title of button.
     * @return A styled button.
     */
    private JButton createButton(String title) {
        JButton button = new JButton(title);

        button.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        button.setBackground(MAIN_COLOUR);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Cambria", Font.BOLD, 18));
        button.setPreferredSize(new Dimension(80, 40));

        return button;
    }

    /**
     * Creates and styles a label for the game settings window.
     * @param title The tite of the label.
     * @return A styled label.
     */
    private JLabel createLabel(String title) {
        JLabel label = new JLabel(title);

        label.setBackground(MAIN_COLOUR);
        label.setFont(new Font("Cmabria", Font.BOLD, 15));
        label.setForeground(Color.WHITE);
        label.setOpaque(true);

        return label;
    }
}
