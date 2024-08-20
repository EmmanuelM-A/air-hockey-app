package ema.ui.game.settings;

import javax.swing.*;

import ema.ui.game.twoPlayer.TwoPlayerFrame;
import ema.ui.home.HomeMenuFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class TwoPlayerSetting {
    private JDialog dialog;

    private JSlider points;

    private JLabel pointsHeader;

    private JPanel buttonsPanel;

    private JButton cancelBtn;

    private JButton defaultBtn;

    private JButton applyBtn;

    private final Color MAIN_COLOUR = new Color(173, 216, 230);

    private int winningPoints;

    public static TwoPlayerSetting instance;

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

    public JDialog getDialog() {
        return this.dialog;
    }

    public int getWinningPoints() {
        return this.winningPoints;
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
