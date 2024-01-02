package Codes;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import javax.sound.sampled.FloatControl;
import java.net.URL;
public class Music {
    private Clip clip;

    private FloatControl gainControl;

    public void playMusic(String filePath) {
        try {
            ClassLoader classLoader = Sokoban.class.getClassLoader();

            URL resource = classLoader.getResource(filePath);

            AudioInputStream audioIn = AudioSystem.getAudioInputStream(resource);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    public void stopMusic() {
        if (clip != null) {
            clip.stop();
            clip.close();
        }
    }public void setVolume(float volume) {
        if (gainControl != null) {
            gainControl.setValue(volume);
        }
    }
}
