package ema.ui.game.settings;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

import ema.ui.MenuButtons;

public class SinglePlayerSettings extends JFrame {
    private JPanel settings;

    private JLabel title;

    private JLabel diffHeader;

    private JLabel timeHeader;

    private JComboBox<String> diffSelection;

    private JSlider timeSelection;

    private JButton cancelBtn;

    private JButton defaultBtn;

    private JButton applyBtn;

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

    public SinglePlayerSettings() {
        super.setSize(new Dimension(300, 300));
        super.setLocationRelativeTo(null);
        super.setResizable(false);

        this.settings = new JPanel();
        this.title = new JLabel("Choose your game settings");
        this.diffHeader = new JLabel("Select your difficulty:");
        this.timeHeader = new JLabel("Set the timer:");
        this.diffSelection = new JComboBox<>(new String[]{"Easy", "Normal", "Hard"});
        this.timeSelection = new JSlider(1, 5, 3);
        this.cancelBtn = new MenuButtons("Cancel", null, null);
        this.defaultBtn = new JButton("Default");
        this.applyBtn = new JButton("Apply");

        settings.setLayout(null);
        //settings.setPreferredSize(new Dimension(500, 500));
        settings.setBackground(new Color(173, 216, 230));

        title.setBounds(50, 10, 200, 30);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setVerticalAlignment(SwingConstants.CENTER);
        settings.add(title);

        diffHeader.setBounds(20, 70, 150, 30);
        settings.add(diffHeader);

        diffSelection.setBounds(160, 70, 100, 30);
        diffSelection.setSelectedIndex(1);
        settings.add(diffSelection);

        timeHeader.setBounds(20, 100, 150, 30);
        settings.add(timeHeader);

        timeSelection.setBounds(20, 130, 180, 50);
        timeSelection.setBackground(new Color(173, 216, 230));
        timeSelection.setPaintTrack(true);
        timeSelection.setPaintTicks(true);
        timeSelection.setPaintLabels(true);
        timeSelection.setMajorTickSpacing(1);
        timeSelection.setMinorTickSpacing(10);
        settings.add(timeSelection);

        cancelBtn.setBounds(20, 200, 100, 50);
        cancelBtn.setFont(new Font("Cambria", Font.BOLD, 11));
        settings.add(cancelBtn);



        

        add(settings);

        super.setVisible(true);        
    }

    public int configureGame() {
        return 1;
    }
    
}
