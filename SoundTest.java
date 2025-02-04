import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundTest {
    public static void playClickSound() {
        try {
            File soundFile = new File("def.wav"); // Ensure this path is correct
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Error playing sound: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        playClickSound();
    }
}
