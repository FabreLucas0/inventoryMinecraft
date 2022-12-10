import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Musique extends Thread {
    private String filename;

    public Musique(String filename) {
        this.filename = filename;
    }
    public void keyPressed(KeyEvent a)
    {
        if(a.getKeyCode()==KeyEvent.VK_ENTER)
        {

        }
    }

    public void run() {
        try {
            File audioFile = new File(filename);

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);

            clip.start();

            Thread.sleep(clip.getMicrosecondLength() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}