package ema.ui.home;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import ema.actions.GoToScoreboardAction;
import ema.actions.StartSinglePlayerAction;
import ema.actions.StartTwoPlayerAction;
import ema.ui.MenuButtons;

public class HomeMenuPanel extends JPanel {
    private JLabel title, version, developedBy;

    private MenuButtons singlePlayer, twoPlayer, scoreboard;

    private Font fontOne = new Font("Cambria", Font.BOLD, 50);

    private Color defaultColour = Color.WHITE;

    public HomeMenuPanel() {
        super.setLayout(null);
        super.setBackground(new Color(173, 216, 230));
        super.setPreferredSize(new Dimension(600, 700));

        this.title = new JLabel("Air Hockey");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setVerticalAlignment(SwingConstants.CENTER);
        title.setFont(fontOne);
        title.setForeground(defaultColour);
        title.setBounds(100, 50, 400, 50);

        singlePlayer = new MenuButtons("Single Player", null, "Play against a computer! - STILL IN PROGRESS");
        singlePlayer.addActionListener(new StartSinglePlayerAction());
        singlePlayer.setBounds(150, 150, 300, 50);

        twoPlayer = new MenuButtons("Two Player", null, "Play against a friend! - 8 Goals to win");
        twoPlayer.addActionListener(new StartTwoPlayerAction());
        twoPlayer.setBounds(150, 250, 300, 50);

        scoreboard = new MenuButtons("Scoreboard", null, "View your scores against the computer");
        scoreboard.addActionListener(new GoToScoreboardAction());
        scoreboard.setBounds(150, 350, 300, 50);

        singlePlayer.getDescription().setBounds(160, 190, 300, 50);
        twoPlayer.getDescription().setBounds(160, 290, 300, 50);
        scoreboard.getDescription().setBounds(160, 390, 300, 50);

        version = new JLabel("Version 1.0.0");
        version.setForeground(defaultColour);
        version.setBounds(10, 630, 100, 50);

        developedBy = new JLabel("Developed by: A.E.M.A");
        developedBy.setForeground(defaultColour);
        developedBy.setBounds(440, 630, 200, 50);

        // Add the componenets to the panel
        add(title);
        add(singlePlayer);
        add(twoPlayer);
        add(scoreboard);
        add(singlePlayer.getDescription());
        add(twoPlayer.getDescription());
        add(scoreboard.getDescription());
        add(version);
        add(developedBy);
    }
}
