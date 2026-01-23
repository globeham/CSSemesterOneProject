/**
 * MusicPlayer
 * 
 * Utility class for playing background music and audio in the game.
 * Manages audio clip initialization, looping, and playback control.
 * 
 * @author Abhineet Bhardwaj
 * @version 1.0
 */

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class MusicPlayer {
    private Clip clip;

    // main method that gets file and plays it
    public void playMusic(String filePath) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Could not play music: " + e.getMessage());
        }
    }

    // stops music
    public void stopMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}
