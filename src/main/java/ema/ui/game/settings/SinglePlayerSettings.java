package ema.ui.game.settings;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

import ema.ui.MenuButtons;

public class SinglePlayerSettings {
    private JDialog dialog;

    private JLabel diffHeader;

    private JComboBox<String> diffLevels;

    private JLabel timeHeader;

    private JSlider timeSelection;

    private JPanel buttonsPanel;

    private JButton cancelBtn;

    private JButton defaultBtn;

    private JButton applyBtn;

    private String[] diffSelection = {"Easy", "Normal", "Hard"};

    private int diffLevel;

    private int time;

    private final Color MAIN_COLOUR = new Color(173, 216, 230);

    public SinglePlayerSettings(JFrame homeFrame) {
        this.dialog = new JDialog(homeFrame, "Single Player Settings");
        this.diffHeader = createLabel("Choose MegaBot's difficulty:");
        this.diffLevels = new JComboBox<>(diffSelection);
        this.timeHeader = createLabel("Select a timer:");
        this.timeSelection = new JSlider(1, 5);
        this.buttonsPanel = new JPanel(new FlowLayout());
        this.cancelBtn = createButton("Cancel");
        this.defaultBtn = createButton("Default");
        this.applyBtn = createButton("Apply");

        // Dialog Box
        dialog.setLocationRelativeTo(homeFrame);
        dialog.setPreferredSize(new Dimension(300, 200));
        dialog.setLayout(new GridBagLayout());

        // Difficulty selection
        
        
       
    }

    public JDialog getDialog() {
        return this.dialog;
    }

    public int getDiffLevel() {
        return this.diffLevel;
    }

    public int getTime() {
        return this.time;
    }

    private JButton createButton(String title) {
        JButton button = new JButton(title);

        button.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        button.setBackground(MAIN_COLOUR);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Cambria", Font.BOLD, 18));
        button.setPreferredSize(new Dimension(80, 40));

        return button;
    }

    private JLabel createLabel(String title) {
        JLabel label = new JLabel(title);

        label.setBackground(MAIN_COLOUR);
        label.setFont(new Font("Cmabria", Font.BOLD, 15));
        label.setForeground(Color.WHITE);
        label.setOpaque(true);

        return label;
    }

    
}
