import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/*
MusicPlayer allows playing background music in the game
 */
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
