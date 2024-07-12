package ema.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class CountDown implements Runnable {
    private Timer timer;
    private final int initialSeconds;
    private int timeRemaining;
    private String formattedTime;
    private GameText label;

    public CountDown(int seconds, final GameText label) {
        this.initialSeconds = seconds;
        this.timeRemaining = seconds;
        this.formattedTime = formatTime(seconds);
        this.label = label;      
        createCountDown();
        Thread thread = new Thread(this);
        thread.start();
    }

    private void createCountDown() {
        this.timer = new Timer(1000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               if(timeRemaining > 0) {
                    timeRemaining--;
                    formattedTime = formatTime(timeRemaining);
                    label.getLabel().setText(formattedTime);
               } else {
                    timer.stop();
               }
            }
        });
    }

    @Override
    public void run() {
        timer.start();
    }

    public int getTimeRemaining() {
        return this.timeRemaining;
    }

    public String getFormattedTime() {
        return this.formattedTime;
    }

    public void restartCountDown() {
        if(timer != null) {
            timer.stop();
        }
        this.timeRemaining = initialSeconds;
        this.formattedTime = formatTime(initialSeconds);
        createCountDown();
        Thread thread = new Thread(this);
        thread.start();
    }

    private static String formatTime(int seconds) {
        int minutes = seconds / 60;
        int secs = seconds % 60;
        return String.format("%02d:%02d", minutes, secs);
    }    
}
