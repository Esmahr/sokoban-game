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
    public void playBackgroundMusic(String filePath) {
        playMusic(filePath, true); // Arka plan müziği için sürekli döngü
    }

    public void playSoundEffect(String filePath) {
        playMusic(filePath, false); // Ses efekti için tek seferlik oynatma
    }

    private void playMusic(String filePath, boolean loop) {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            URL resource = classLoader.getResource(filePath);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(resource);

            clip = AudioSystem.getClip();
            clip.open(audioIn);

            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY); // Sürekli tekrar için
            }
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
