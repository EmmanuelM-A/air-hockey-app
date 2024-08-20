package ema.ui.game.settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class TwoPlayerSetting {
    private JDialog dailog;

    private JSlider points;

    private JPanel buttonsPanel;

    private JButton cancelBtn;

    private JButton defaultBtn;

    private JButton applyBtn;

    public TwoPlayerSetting(JFrame homeFrame) {
        this.dailog = new JDialog(homeFrame, "Two Player Settings", true);
        this.points = new JSlider(1, 20);

        this.buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        this.cancelBtn = new JButton("CANCEL");
        this.defaultBtn = new JButton("DEFAULT");
        this.applyBtn = new JButton("APPLY");

        buttonsPanel.add(cancelBtn);
        buttonsPanel.add(defaultBtn);
        buttonsPanel.add(applyBtn);

        dailog.setLocationRelativeTo(homeFrame);
        dailog.setPreferredSize(new Dimension(400, 200));
        dailog.setLayout(new BorderLayout());
        
        dailog.add(points, BorderLayout.CENTER);
        dailog.add(buttonsPanel, BorderLayout.SOUTH);

        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dailog.dispose();
            }
            
        });

        defaultBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                points.setValue(8);
            }
            
        });

        applyBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int pointsToWin = points.getValue();
                System.out.println("Selected points: " + pointsToWin);
                dailog.dispose();
                // Open TwoPlayerFrame, setting values for the game
            }
            
        });

        dailog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dailog.pack();
    }

    public JDialog getDialog() {
        return this.dailog;
    }
}
