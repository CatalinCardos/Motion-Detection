import com.sun.tools.javac.Main;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SoundPlayerUsingClip implements LineListener {

        boolean isPlaybackCompleted;

        @Override
        public void update(LineEvent event) {
            if (LineEvent.Type.START == event.getType()) {
                System.out.println("Playback started.");
            } else if (LineEvent.Type.STOP == event.getType()) {
                isPlaybackCompleted = true;
                System.out.println("Playback completed.");
            }
        }

        public static synchronized void playSound2() {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        Clip clip = AudioSystem.getClip();
                        String audioFilePath = "sound.wav";
                        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(audioFilePath);
                        AudioInputStream audioStream = AudioSystem.getAudioInputStream(inputStream);
                        clip.open(audioStream);
                        clip.start();
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                }
            }).start();
        }

    }
