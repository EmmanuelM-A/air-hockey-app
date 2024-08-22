package ema.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

/**
 * This class displays the countdown timer for the single player mode.
 */
public class CountDown implements Runnable {
    /**
     * The timer for the match.
     */
    private Timer timer;

    /**
     * The initial length of the match in seconds.
     */
    private int initialSeconds;

    /**
     * The time remaining during the match.
     */
    private int timeRemaining;

    /**
     * The time once formatted to minutes.
     */
    private String formattedTime;

    /**
     * The countdown label which displays the time.
     */
    private GameText label;

    /**
     * The maximum countdown for a match in seconds.
     */
    public static final int MAX_COUNTDOWN = 300;

    /**
     * Constructs a CountDown instance with given countdown.
     * @param seconds The countdown length.
     * @param label The label that will display the countdown.
     */
    public CountDown(int seconds, final GameText label) {
        this.initialSeconds = seconds;
        this.timeRemaining = seconds;
        this.formattedTime = formatTime(seconds);
        this.label = label;      
        createCountDown();
        Thread thread = new Thread(this);
        thread.start();
    }

    /**
     * Creates countdown and ensures the label it updated every second.
     */
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

    /**
     * Gets the remaining time during a game.
     * @return The time left
     */
    public int getTimeRemaining() {
        return this.timeRemaining;
    }

    /**
     * Sets a new time for the countdown timer.
     * @param newTime The new time in seconds.
     */
    public void setTime(int newTime) {
        this.initialSeconds = newTime;
        this.timeRemaining = newTime;
    }

    /**
     * Gets the time formatted in minutes
     * @return The forametted time.
     */
    public String getFormattedTime() {
        return this.formattedTime;
    }

    /**
     * Restarts the countdown to the original countdown length inputted.
     */
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

    /**
     * Formats the time to minutes.
     * @param seconds The countdown time in seconds
     * @return The forametted time
     */
    private static String formatTime(int seconds) {
        int minutes = seconds / 60;
        int secs = seconds % 60;
        return String.format("%02d:%02d", minutes, secs);
    }    
}
