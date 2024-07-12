package ema.ui.home;

import java.awt.BorderLayout;
import java.awt.Dimension;

import ema.ui.BaseFrame;

public class HomeMenuFrame extends BaseFrame {

    public static HomeMenuFrame instance;

    public HomeMenuFrame() {
        super.setTitle("");
        super.setSize(new Dimension(600, 700));
        super.setResizable(false);
        super.setLocationRelativeTo(null);
        super.setLayout(new BorderLayout());

        HomeMenuPanel homeMenu = new HomeMenuPanel();
        add(homeMenu, BorderLayout.CENTER);

        super.setVisible(true);

        instance = this;
    }
}
