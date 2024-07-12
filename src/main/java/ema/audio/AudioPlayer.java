package ema.audio;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * This class handles the playing of the sound effects and audio files throughout the game.
 */
public class AudioPlayer {
    /**
     * Determines if the sound is enabled or disabled.
     */
    private static boolean isSoundDisabled = false;

    /**
     * Holds the current playing audio file.
     */
    private static Clip currentClip = null;

    /**
     * A pool of resuable clips
     */
    private static Map<String, Clip> clipPool = new HashMap<>();

    /**
     * Plays the audio file provided.
     * @param file The audio to play
     */
    public static void play(String file) {
        if (!isSoundDisabled) {
            final String filename = "/audios/" + file;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        stopCurrentClip();

                        // Check if the clip is already loaded
                        if (!clipPool.containsKey(filename)) {
                            Clip clip = AudioSystem.getClip();
                            InputStream audioSrc = AudioPlayer.class.getResourceAsStream(filename);
                            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioSrc);
                            clip.open(audioStream);
                            clipPool.put(filename, clip);
                        }

                        currentClip = clipPool.get(filename);
                        currentClip.setFramePosition(0);  // Rewind to the beginning
                        currentClip.start();
                    } catch (Exception exc) {
                        exc.printStackTrace(System.out);
                    }
                }
            }).start();
        }
    }

    /**
     * Stops the current playing audio clip if there is one.
     */
    public static void stopCurrentClip() {
        if (currentClip != null && currentClip.isRunning()) {
            currentClip.stop();
            currentClip.setFramePosition(0);  // Reset to the beginning
        }
    }

    /**
     * Toggles the sound between enabled and disabled.
     */
    public static void toggleSound() {
        isSoundDisabled = !isSoundDisabled;
        if (isSoundDisabled) {
            stopCurrentClip();
        }
    }

    /**
     * Checks if the sound is currently disabled.
     * @return True if the sound is disabled, false otherwise.
     */
    public static boolean isSoundDisabled() {
        return isSoundDisabled;
    }
}
