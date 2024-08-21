package ema.ui.game.settings;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ema.components.CountDown;
import ema.mechanics.Difficulty;
import ema.ui.game.singlePlayer.SinglePlayerFrame;
import ema.ui.home.HomeMenuFrame;

public class SinglePlayerSettings {
    private JDialog dialog;

    private JLabel diffHeader;

    private JComboBox<Difficulty> diffLevels;

    private JLabel countDownHeader;

    private JSlider countDownSelection;

    private JLabel selectedCountDown;

    private JPanel buttonsPanel;

    private JButton cancelBtn;

    private JButton defaultBtn;

    private JButton applyBtn;

    private Difficulty[] diffSelection = {Difficulty.EASY, Difficulty.NORMAL, Difficulty.HARD};

    private Difficulty diffLevel;

    private int countDown;

    private final int DEFAULT_COUNTDOWN = 120;

    private final Color MAIN_COLOUR = new Color(173, 216, 230);

    public static SinglePlayerSettings instance;

    public SinglePlayerSettings(JFrame homeFrame) {
        this.dialog = new JDialog(homeFrame, "Single Player Game Settings");
        this.diffHeader = createLabel("Choose MegaBot's difficulty:");
        this.diffLevels = new JComboBox<>(diffSelection);
        this.countDownHeader = createLabel("Select the count down timer:");
        this.countDownSelection = new JSlider(60, CountDown.MAX_COUNTDOWN, DEFAULT_COUNTDOWN);
        this.selectedCountDown = createLabel(formatTime(DEFAULT_COUNTDOWN));
        this.buttonsPanel = new JPanel(new FlowLayout());
        this.cancelBtn = createButton("Cancel");
        this.defaultBtn = createButton("Default");
        this.applyBtn = createButton("Apply");
        this.diffLevel = Difficulty.NORMAL;
        this.countDown = DEFAULT_COUNTDOWN;

        // Dialog Box
        dialog.setLocationRelativeTo(homeFrame);
        dialog.setPreferredSize(new Dimension(370, 250));
        dialog.setLayout(new GridBagLayout());
        dialog.getContentPane().setBackground(MAIN_COLOUR);

        // Difficulty selection
        diffLevels.setBackground(MAIN_COLOUR);
        diffLevels.setForeground(Color.WHITE);
        diffLevels.setSelectedIndex(1);

        // Countdown selection
        countDownSelection.setBackground(MAIN_COLOUR);
        countDownSelection.setForeground(Color.WHITE);
        countDownSelection.setPaintTrack(true);
        countDownSelection.setPaintTicks(true);
        countDownSelection.setPaintLabels(true);
        countDownSelection.setMajorTickSpacing(60);
        countDownSelection.setMinorTickSpacing(10);

        // Update selected count down label whenever slider changes
        countDownSelection.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int value = countDownSelection.getValue();
                selectedCountDown.setText(formatTime(value));
            }
        });
        
        // Buttons
        buttonsPanel.setBackground(MAIN_COLOUR);
        buttonsPanel.add(cancelBtn);
        buttonsPanel.add(defaultBtn);
        buttonsPanel.add(applyBtn);

        // Add components to the dialog box
        GridBagConstraints c = new GridBagConstraints();

        // Difficulty header label
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.insets = new Insets(10, 10, 5, 10); // top, left, bottom, right
        c.anchor = GridBagConstraints.LINE_START;
        dialog.add(diffHeader, c);

        // Difficulty combo box
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        dialog.add(diffLevels, c);

        // Countdown header label
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        c.insets = new Insets(10, 10, 5, 10);
        c.anchor = GridBagConstraints.LINE_START;
        dialog.add(countDownHeader, c);

        // Selected Countdown display label
        c.gridx = 2;
        c.gridy = 1;
        c.gridwidth = 1;
        c.insets = new Insets(10, 10, 5, 10);
        c.anchor = GridBagConstraints.LINE_END;
        dialog.add(selectedCountDown, c);

        // Countdown slider
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 3;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 10, 20, 10);
        dialog.add(countDownSelection, c);

        // Buttons
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 3;
        c.insets = new Insets(5, 10, 10, 10);
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.NONE;
        dialog.add(buttonsPanel, c);

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
                // Default difficulty
                diffLevels.setSelectedIndex(1);
                diffLevel = Difficulty.NORMAL;
                // Default Countdown
                countDownSelection.setValue(DEFAULT_COUNTDOWN);
                selectedCountDown.setText(formatTime(DEFAULT_COUNTDOWN));
                countDown = DEFAULT_COUNTDOWN;
            }
        });

        applyBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                diffLevel = (Difficulty) diffLevels.getSelectedItem();
                countDown = countDownSelection.getValue();
                dialog.dispose();
                HomeMenuFrame.instance.switchFrame(new SinglePlayerFrame());
            }
            
        });

        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.pack();
        dialog.setLocationRelativeTo(homeFrame);

        instance = this;
       
    }

    public JDialog getDialog() {
        return this.dialog;
    }

    public Difficulty getDiffLevel() {
        return this.diffLevel;
    }

    public int getCountDown() {
        return this.countDown;
    }

    private String formatTime(int seconds) {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        return String.format("%d min %02d sec", minutes, remainingSeconds);
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
