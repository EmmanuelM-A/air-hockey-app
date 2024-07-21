package ema.ui.scoreboard;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import ema.actions.GoToHomeAction;
import ema.database.DatabaseHandler;
import ema.ui.BaseFrame;
import ema.ui.MenuButtons;

public class ScoreboardPanel extends JPanel {    
    public ScoreboardPanel(BaseFrame frame) {
        super.setLayout(null);
        super.setBackground(new Color(173, 216, 230));

        JLabel title = new JLabel("Scoreboard");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setVerticalAlignment(SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Cambria", Font.BOLD, 50));
        title.setBounds(100, 30, 400, 50);
        add(title);

        String[] columnNames = {"Position", "Name", "Score", "Date"};

        String[][] data = DatabaseHandler.getScoreboardData();

        JTable scoreboard = new JTable(data, columnNames);
        scoreboard.getTableHeader().setBounds(100, 100, 400, 40);
        scoreboard.setBounds(100, 140, 400, 400);
        scoreboard.setRowHeight(40);
        scoreboard.getColumnModel().getColumn(0).setPreferredWidth(10);
        scoreboard.setForeground(Color.BLACK);
        scoreboard.setBackground(Color.WHITE);

        MenuButtons exitBtn = new MenuButtons("Home", null, "");
        exitBtn.addActionListener(new GoToHomeAction(frame));
        exitBtn.setBounds(250, 550, 100, 50);
        add(exitBtn);

        add(scoreboard.getTableHeader());
        add(scoreboard, new GridBagConstraints());
    }

}
